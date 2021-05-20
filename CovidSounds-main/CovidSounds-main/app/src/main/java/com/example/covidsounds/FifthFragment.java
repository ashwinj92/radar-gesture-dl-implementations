package com.example.covidsounds;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import java.util.LinkedList;
import java.util.List;

/**
 * @author Abhishek
 */
public class FifthFragment extends Fragment {
    //variables
    private CheckBox cardioNone;
    private CheckBox highBloodPressure;
    private CheckBox angina;
    private CheckBox previousStroke;
    private CheckBox previousHeartAttack;
    private CheckBox valvularHeartDisease;
    private CheckBox otherHeartDiseases;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fifth, container, false);
        return view;
    }

    /**
     * @param view
     * @param savedInstanceState
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //get all values on the 3rd fragment
        cardioNone = (CheckBox) view.findViewById(R.id.cardio_none);
        highBloodPressure = (CheckBox) view.findViewById(R.id.high_blood_pressure);
        angina = (CheckBox) view.findViewById(R.id.angina);
        previousStroke = (CheckBox) view.findViewById(R.id.previous_stroke);
        previousHeartAttack = (CheckBox) view.findViewById(R.id.previous_heart_attack);
        valvularHeartDisease = (CheckBox) view.findViewById(R.id.valvular_heart_disease);
        otherHeartDiseases = (CheckBox) view.findViewById(R.id.other_heart_diseases);
        /**
         * move to next fragment
         */
        view.findViewById(R.id.button_fifth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cardioNone.isChecked() && !highBloodPressure.isChecked() && !angina.isChecked() &&
                        !previousStroke.isChecked() && !previousHeartAttack.isChecked() && !valvularHeartDisease.isChecked() &&
                        !otherHeartDiseases.isChecked()) {
                    //show error to select any Cardiovascular condition
                    Toast.makeText(getContext(), "Please select any Cardiovascular condition or None.", Toast.LENGTH_LONG).show();
                } else if (cardioNone.isChecked() && (highBloodPressure.isChecked() || angina.isChecked() ||
                        previousStroke.isChecked() || previousHeartAttack.isChecked() || valvularHeartDisease.isChecked() ||
                        otherHeartDiseases.isChecked())) {
                    //show error to select any Respiratory condition
                    Toast.makeText(getContext(), "Either select None or any other Cardiovascular Condition.", Toast.LENGTH_LONG).show();
                } else {
                    List<String> medConds = new LinkedList<>();
                    if(cardioNone.isChecked())
                        medConds.add(getResources().getString(R.string.NONE));
                    if(highBloodPressure.isChecked())
                        medConds.add(getResources().getString(R.string.high_blood_pressure));
                    if(angina.isChecked())
                        medConds.add(getResources().getString(R.string.angina));
                    if(previousStroke.isChecked())
                        medConds.add(getResources().getString(R.string.previous_stroke));
                    if(previousHeartAttack.isChecked())
                        medConds.add(getResources().getString(R.string.previous_heart_attack));
                    if(valvularHeartDisease.isChecked())
                        medConds.add(getResources().getString(R.string.valvular_heart_disease));
                    if(otherHeartDiseases.isChecked())
                        medConds.add(getResources().getString(R.string.other_heart_disease));
                    MainActivity.demographicDTO.setCardiovascularMedicalConditions(medConds);
                    NavHostFragment.findNavController(FifthFragment.this)
                            .navigate(R.id.action_FifthFragment_to_SixthFragment);
                }
            }
        });
    }

}