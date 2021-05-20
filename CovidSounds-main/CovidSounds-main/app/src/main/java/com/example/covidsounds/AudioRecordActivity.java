package com.example.covidsounds;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.util.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.util.IOUtils;
import com.example.covidsounds.dto.DemographicDTO;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AudioRecordActivity extends AppCompatActivity {
    private static FileWriter file1;
    private static final int PERMISSION_RECORD_AUDIO = 0;
    public static String fileName;
    public static String fragment;
    public static int buttonId;
    private RecordWaveTask recordTask = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AWSMobileClient.getInstance().initialize(this).execute();
        credentials = new BasicAWSCredentials(KEY, SECRET);
        s3Client = new AmazonS3Client(credentials);

        super.onCreate(savedInstanceState);
        buttonId = R.id.button_ninth;
        if (fragment.contains(TenthActivity.class.toString())) {
            setContentView(R.layout.fragment_tenth);
            buttonId = R.id.button_tenth;
        } else if (fragment.contains(TwelfthActivity.class.toString())) {
            setContentView(R.layout.fragment_eleventh);
            buttonId = R.id.submit;
        } else if (fragment.contains(NinthFragment.class.toString())) {
            setContentView(R.layout.fragment_ninth);
            buttonId = R.id.button_ninth;
        }

        //noinspection ConstantConditions
        findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AudioRecordActivity.this, Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Request permission
                    ActivityCompat.requestPermissions(AudioRecordActivity.this,
                            new String[]{Manifest.permission.RECORD_AUDIO},
                            PERMISSION_RECORD_AUDIO);
                    return;
                }
                // Permission already available
                Toast.makeText(getApplicationContext(), "Recording has been started.", Toast.LENGTH_LONG).show();
                launchTask();
            }
        });

        findViewById(buttonId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragment.contains(NinthFragment.class.toString())) {
                    if (MainActivity.demographicDTO.getCoughFilePath() == null) {
                        Toast.makeText(AudioRecordActivity.this, "Please record audio by pressing start and then stop.", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent i = new Intent(getApplicationContext(), TenthActivity.class);
                        startActivity(i);
                        setContentView(R.layout.fragment_tenth);
                    }
                } else if (fragment.contains(TenthActivity.class.toString())) {
                    if (MainActivity.demographicDTO.getBreathFilePath() == null) {
                        Toast.makeText(AudioRecordActivity.this, "Please record audio by pressing start and then stop.", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent i = new Intent(getApplicationContext(), EleventhActivity.class);
                        startActivity(i);
                        setContentView(R.layout.fragment_eleventh);
                    }
                } else if (fragment.contains(TwelfthActivity.class.toString())) {
                    if (MainActivity.demographicDTO.getReadSentenceFilePath() == null) {
                        Toast.makeText(AudioRecordActivity.this, "Please record audio by pressing start and then stop.", Toast.LENGTH_SHORT).show();
                    } else {
                        uploadFile();
                        Intent i = new Intent(getApplicationContext(), TwelfthActivity.class);
                        startActivity(i);
                        setContentView(R.layout.fragment_twelfth);
                    }
                }
            }
        });

        //noinspection ConstantConditions
        findViewById(R.id.btnStop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!recordTask.isCancelled() && recordTask.getStatus() == AsyncTask.Status.RUNNING) {
                    recordTask.cancel(false);
                } else {
                    Toast.makeText(AudioRecordActivity.this, "Task not running.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Restore the previous task or create a new one if necessary
        recordTask = (RecordWaveTask) getLastCustomNonConfigurationInstance();
        if (recordTask == null) {
            recordTask = new RecordWaveTask(this);
        } else {
            recordTask.setContext(this);
        }
    }

    public void zip(String[] files) throws IOException {
        List<String> srcFiles = Arrays.asList("test1.txt", "test2.txt");
        FileOutputStream fos = new FileOutputStream("zipfile.zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (String srcFile : srcFiles) {
            File fileToZip = new File(srcFile);
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();
        fos.close();
    }

    public void toJSon(DemographicDTO data) {
        try {
            // Here we convert Java Object to JSON
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("Age Group", data.getAgeGroup()); // Set the first name/pair
            jsonObj.put("Gender", data.getGender());
            jsonObj.put("Medical conditions", data.getRespiratoryMedicalConditions()); // Set the first name/pair
            jsonObj.put("Cardio vascularConditions", data.getCardiovascularMedicalConditions());
            jsonObj.put("other Medical Conditions", data.getOtherMedicalConditions());
            jsonObj.put("Smoking habit", data.getSmokingHabit());
            jsonObj.put("In hospital", data.getInHospital());
            jsonObj.put("Ever had covid", data.getEverHadCovid());
            jsonObj.put("Test Status", data.getTestStatus());

            String userString = jsonObj.toString();
            // Define the File Path and its Name
            File file = new File(getFilesDir(),"demographic_data");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(userString);
            bufferedWriter.close();
        }
        catch(JSONException | IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_RECORD_AUDIO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    launchTask();
                } else {
                    // Permission denied
                    Toast.makeText(this, "\uD83D\uDE41", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void uploadFile() {

        if (fileUri != null) {

            final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "/" + MainActivity.demographicDTO.getBreathFilePath());

            createFile(getApplicationContext(), fileUri, file);

            TransferUtility transferUtility =
                    TransferUtility.builder()
                            .context(getApplicationContext())
                            .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                            .s3Client(s3Client)
                            .build();

            TransferObserver uploadObserver =
                    transferUtility.upload("covS3/" + AudioRecordActivity.fileName + "." + getFileExtension(fileUri), file);

            uploadObserver.setTransferListener(new TransferListener() {

                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (TransferState.COMPLETED == state) {
                        Toast.makeText(getApplicationContext(), "Upload Completed!", Toast.LENGTH_SHORT).show();

                        file.delete();
                    } else if (TransferState.FAILED == state) {
                        file.delete();
                    }
                }
                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                    int percentDone = (int) percentDonef;

                    //tvFileName.setText("ID:" + id + "|bytesCurrent: " + bytesCurrent + "|bytesTotal: " + bytesTotal + "|" + percentDone + "%");
                }
                @Override
                public void onError(int id, Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void createFile(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            IOUtils.copy(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void launchTask() {
        switch (recordTask.getStatus()) {
            case RUNNING:
                Toast.makeText(this, "Task already running...", Toast.LENGTH_SHORT).show();
                return;
            case FINISHED:
                recordTask = new RecordWaveTask(this);
                break;
            case PENDING:
                if (recordTask.isCancelled()) {
                    recordTask = new RecordWaveTask(this);
                }
        }
        File wavFile = new File(getFilesDir(), fileName);
        if (getFileName().contains("cough"))
            MainActivity.demographicDTO.setCoughFilePath(wavFile.getAbsolutePath());
        else if (getFileName().contains("breathing"))
            MainActivity.demographicDTO.setBreathFilePath(wavFile.getAbsolutePath());
        else if (getFileName().contains("sentence"))
            MainActivity.demographicDTO.setReadSentenceFilePath(wavFile.getAbsolutePath());
        recordTask.execute(wavFile);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        recordTask.setContext(null);
        return recordTask;
    }

    private static class RecordWaveTask extends AsyncTask<File, Void, Object[]> {

        // Configure me!
        private static final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
        private static final int SAMPLE_RATE = 44100; // Hz
        private static final int ENCODING = AudioFormat.ENCODING_PCM_16BIT;
        private static final int CHANNEL_MASK = AudioFormat.CHANNEL_IN_MONO;
        //

        private static final int BUFFER_SIZE = 2 * AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_MASK, ENCODING);

        private Context ctx;

        private RecordWaveTask(Context ctx) {
            setContext(ctx);
        }

        private void setContext(Context ctx) {
            this.ctx = ctx;
        }

        /**
         * Opens up the given file, writes the header, and keeps filling it with raw PCM bytes from
         * AudioRecord until it reaches 4GB or is stopped by the user. It then goes back and updates
         * the WAV header to include the proper final chunk sizes.
         *
         * @param files Index 0 should be the file to write to
         * @return Either an Exception (error) or two longs, the filesize, elapsed time in ms (success)
         */
        @Override
        protected Object[] doInBackground(File... files) {
            AudioRecord audioRecord = null;
            FileOutputStream wavOut = null;
            long startTime = 0;
            long endTime = 0;

            try {
                // Open our two resources
                audioRecord = new AudioRecord(AUDIO_SOURCE, SAMPLE_RATE, CHANNEL_MASK, ENCODING, BUFFER_SIZE);
                wavOut = new FileOutputStream(files[0]);

                // Write out the wav file header
                writeWavHeader(wavOut, CHANNEL_MASK, SAMPLE_RATE, ENCODING);

                // Avoiding loop allocations
                byte[] buffer = new byte[BUFFER_SIZE];
                boolean run = true;
                int read;
                long total = 0;

                // Let's go
                startTime = SystemClock.elapsedRealtime();
                audioRecord.startRecording();
                while (run && !isCancelled()) {
                    read = audioRecord.read(buffer, 0, buffer.length);

                    // WAVs cannot be > 4 GB due to the use of 32 bit unsigned integers.
                    if (total + read > 4294967295L) {
                        // Write as many bytes as we can before hitting the max size
                        for (int i = 0; i < read && total <= 4294967295L; i++, total++) {
                            wavOut.write(buffer[i]);
                        }
                        run = false;
                    } else {
                        // Write out the entire read buffer
                        wavOut.write(buffer, 0, read);
                        total += read;
                    }
                }
            } catch (IOException ex) {
                return new Object[]{ex};
            } finally {
                if (audioRecord != null) {
                    try {
                        if (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
                            audioRecord.stop();
                            endTime = SystemClock.elapsedRealtime();
                        }
                    } catch (IllegalStateException ex) {
                        //
                    }
                    if (audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
                        audioRecord.release();
                    }
                }
                if (wavOut != null) {
                    try {
                        wavOut.close();
                    } catch (IOException ex) {
                        //
                    }
                }
            }

            try {
                // This is not put in the try/catch/finally above since it needs to run
                // after we close the FileOutputStream
                updateWavHeader(files[0]);
            } catch (IOException ex) {
                return new Object[]{ex};
            }

            return new Object[]{files[0].length(), endTime - startTime};
        }

        /**
         * Writes the proper 44-byte RIFF/WAVE header to/for the given stream
         * Two size fields are left empty/null since we do not yet know the final stream size
         *
         * @param out         The stream to write the header to
         * @param channelMask An AudioFormat.CHANNEL_* mask
         * @param sampleRate  The sample rate in hertz
         * @param encoding    An AudioFormat.ENCODING_PCM_* value
         * @throws IOException
         */
        private static void writeWavHeader(OutputStream out, int channelMask, int sampleRate, int encoding) throws IOException {
            short channels;
            switch (channelMask) {
                case AudioFormat.CHANNEL_IN_MONO:
                    channels = 1;
                    break;
                case AudioFormat.CHANNEL_IN_STEREO:
                    channels = 2;
                    break;
                default:
                    throw new IllegalArgumentException("Unacceptable channel mask");
            }

            short bitDepth;
            switch (encoding) {
                case AudioFormat.ENCODING_PCM_8BIT:
                    bitDepth = 8;
                    break;
                case AudioFormat.ENCODING_PCM_16BIT:
                    bitDepth = 16;
                    break;
                case AudioFormat.ENCODING_PCM_FLOAT:
                    bitDepth = 32;
                    break;
                default:
                    throw new IllegalArgumentException("Unacceptable encoding");
            }

            writeWavHeader(out, channels, sampleRate, bitDepth);
        }

        /**
         * Writes the proper 44-byte RIFF/WAVE header to/for the given stream
         * Two size fields are left empty/null since we do not yet know the final stream size
         *
         * @param out        The stream to write the header to
         * @param channels   The number of channels
         * @param sampleRate The sample rate in hertz
         * @param bitDepth   The bit depth
         * @throws IOException
         */
        private static void writeWavHeader(OutputStream out, short channels, int sampleRate, short bitDepth) throws IOException {
            // Convert the multi-byte integers to raw bytes in little endian format as required by the spec
            byte[] littleBytes = ByteBuffer
                    .allocate(14)
                    .order(ByteOrder.LITTLE_ENDIAN)
                    .putShort(channels)
                    .putInt(sampleRate)
                    .putInt(sampleRate * channels * (bitDepth / 8))
                    .putShort((short) (channels * (bitDepth / 8)))
                    .putShort(bitDepth)
                    .array();

            // Not necessarily the best, but it's very easy to visualize this way
            out.write(new byte[]{
                    // RIFF header
                    'R', 'I', 'F', 'F', // ChunkID
                    0, 0, 0, 0, // ChunkSize (must be updated later)
                    'W', 'A', 'V', 'E', // Format
                    // fmt subchunk
                    'f', 'm', 't', ' ', // Subchunk1ID
                    16, 0, 0, 0, // Subchunk1Size
                    1, 0, // AudioFormat
                    littleBytes[0], littleBytes[1], // NumChannels
                    littleBytes[2], littleBytes[3], littleBytes[4], littleBytes[5], // SampleRate
                    littleBytes[6], littleBytes[7], littleBytes[8], littleBytes[9], // ByteRate
                    littleBytes[10], littleBytes[11], // BlockAlign
                    littleBytes[12], littleBytes[13], // BitsPerSample
                    // data subchunk
                    'd', 'a', 't', 'a', // Subchunk2ID
                    0, 0, 0, 0, // Subchunk2Size (must be updated later)
            });
        }

        /**
         * Updates the given wav file's header to include the final chunk sizes
         *
         * @param wav The wav file to update
         * @throws IOException
         */
        private static void updateWavHeader(File wav) throws IOException {
            byte[] sizes = ByteBuffer
                    .allocate(8)
                    .order(ByteOrder.LITTLE_ENDIAN)
                    // There are probably a bunch of different/better ways to calculate
                    // these two given your circumstances. Cast should be safe since if the WAV is
                    // > 4 GB we've already made a terrible mistake.
                    .putInt((int) (wav.length() - 8)) // ChunkSize
                    .putInt((int) (wav.length() - 44)) // Subchunk2Size
                    .array();

            RandomAccessFile accessWave = null;
            //noinspection CaughtExceptionImmediatelyRethrown
            try {
                accessWave = new RandomAccessFile(wav, "rw");
                // ChunkSize
                accessWave.seek(4);
                accessWave.write(sizes, 0, 4);

                // Subchunk2Size
                accessWave.seek(40);
                accessWave.write(sizes, 4, 4);
            } catch (IOException ex) {
                // Rethrow but we still close accessWave in our finally
                throw ex;
            } finally {
                if (accessWave != null) {
                    try {
                        accessWave.close();
                    } catch (IOException ex) {
                        //
                    }
                }
            }
        }

        @Override
        protected void onCancelled(Object[] results) {
            // Handling cancellations and successful runs in the same way
            onPostExecute(results);
        }

        @Override
        protected void onPostExecute(Object[] results) {
            Throwable throwable = null;
            if (results[0] instanceof Throwable) {
                // Error
                throwable = (Throwable) results[0];
                Log.e(RecordWaveTask.class.getSimpleName(), throwable.getMessage(), throwable);
            }

            // If we're attached to an activity
            if (ctx != null) {
                if (throwable == null) {
                    // Display final recording stats
                    double size = (long) results[0] / 1000000.00;
                    long time = (long) results[1] / 1000;
//                    Toast.makeText(ctx, String.format(Locale.getDefault(), "%.2f MB / %d seconds",
////                            size, time), Toast.LENGTH_LONG).show();
                    Toast.makeText(ctx, "Recording complete.", Toast.LENGTH_LONG).show();
                } else {
                    // Error
                    Toast.makeText(ctx, throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}