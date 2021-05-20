package com.example.covidsounds;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

/**
 * @author Abhishek
 */
public class NinthFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_ninth, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String fileName = "cough_" + System.currentTimeMillis() / 1000 + ".wav";
        AudioRecordActivity.fileName = fileName;
        AudioRecordActivity.fragment = NinthFragment.class.toString();
        Intent myIntent = new Intent(getActivity(), AudioRecordActivity.class);
        getActivity().startActivity(myIntent);

    }
}