#include <IFXRadarPulsedDoppler.h>
#include <LED.h>
IFXRadarPulsedDoppler radarDev;
#define FRAME_SAMPLES 256
float raw_i[FRAME_SAMPLES];
float raw_q[FRAME_SAMPLES];

void myRawDataCallback(raw_data_context_t context)
{
  uint32_t frameCnt   = radarDev.getRawDataFrameCount(context);
  uint16_t numSamples = radarDev.getNumRawDataSamples(context);
  radarDev.getRawData(context, raw_i, raw_q, FRAME_SAMPLES);
  // use only one sample out of 32, so just one per frame
  Serial.print(raw_i[5]);
  Serial.print("\t");
  Serial.println(raw_q[5]);
}
void setup() {
  Serial.begin(500000);
  radarDev.registerRawDataCallback(myRawDataCallback);  // register a handler to receive raw data
  radarDev.enableAlgoProcessing(false); // set to false to disables the lib internal radar algo processing
  // start the radarDevice, to read the default parameter
  radarDev.initHW();
  radarDev.begin();
  
  // Frame rate of 75Hz
  radarDev.setNumSamples(32);   // 32 Samples
  radarDev.setSampleFreq(3000);  
  radarDev.setSkipSamples(8);   // add 8 skip samples, to "burn" 4 msec before, so 40 Samples at 3kHz gives 75Hz
  
  // read minimum possible Frame period (after setting skip count!)
  uint32_t minFramePeriod = radarDev.getMinFramePeriod();
  // stop the device to change parameters
  radarDev.end();
  radarDev.setFramePeriod(minFramePeriod); 
  // Restart the radar device
  radarDev.begin();
}
void loop() {
  // put your main code here, to run repeatedly:
  radarDev.run();
}
