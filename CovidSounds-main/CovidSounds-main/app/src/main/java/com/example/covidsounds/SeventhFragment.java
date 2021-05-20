package com.example.covidsounds;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

/**
 * @author Abhishek
 */
public class SeventhFragment extends Fragment {
    //variables
    private Spinner infesTestingQues;
    private Spinner covidTestingQues;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seventh, container, false);
        return view;
    }

    /**
     * @param view
     * @param savedInstanceState
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //get all values
        infesTestingQues = (Spinner) view.findViewById(R.id.infes_testing_ques);
        covidTestingQues = (Spinner) view.findViewById(R.id.covid_testing_ques);
        /**
         * move to next fragment
         */
        view.findViewById(R.id.button_seventh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.demographicDTO.setEverHadCovid(infesTestingQues.getSelectedItem().toString());
                MainActivity.demographicDTO.setTestStatus(covidTestingQues.getSelectedItem().toString());
                NavHostFragment.findNavController(SeventhFragment.this)
                        .navigate(R.id.action_SeventhFragment_to_EighthFragment);
            }
        });
    }
}