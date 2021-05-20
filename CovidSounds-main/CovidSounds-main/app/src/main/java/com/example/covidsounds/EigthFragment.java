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

import java.util.LinkedList;
import java.util.List;

/**
 * @author Abhishek
 */
public class EigthFragment extends Fragment {
    //variables
    private CheckBox noneCovid;
    private CheckBox preferNot2SayCovid;
    private CheckBox fever;
    private CheckBox chills;
    private CheckBox dryCough;
    private CheckBox wetCough;
    private CheckBox difficultyBreathing;
    private CheckBox tightnessInChest;
    private CheckBox muscleAches;
    private CheckBox headache;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eighth, container, false);
        return view;
    }

    /**
     * @param view
     * @param savedInstanceState
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //get all values
        noneCovid = (CheckBox) view.findViewById(R.id.none_covid);
        preferNot2SayCovid = (CheckBox) view.findViewById(R.id.prefer_not_2_say_covid);
        fever = (CheckBox) view.findViewById(R.id.fever);
        chills = (CheckBox) view.findViewById(R.id.chills);
        dryCough = (CheckBox) view.findViewById(R.id.dry_cough);
        wetCough = (CheckBox) view.findViewById(R.id.wet_cough);
        difficultyBreathing = (CheckBox) view.findViewById(R.id.difficulty_breathing);
        tightnessInChest = (CheckBox) view.findViewById(R.id.tightness_in_chest);
        muscleAches = (CheckBox) view.findViewById(R.id.muscle_aches);
        headache = (CheckBox) view.findViewById(R.id.headache);
        /**
         * move to next fragment
         */
        view.findViewById(R.id.button_eighth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!noneCovid.isChecked() && !preferNot2SayCovid.isChecked() && !fever.isChecked()
                        && !chills.isChecked() && !dryCough.isChecked() && !wetCough.isChecked() && !difficultyBreathing.isChecked()
                        && !tightnessInChest.isChecked() && !muscleAches.isChecked() && !headache.isChecked()) {
                    //show error to select any Cardiovascular condition
                    Toast.makeText(getContext(), "Please select any Covid symptoms or None.", Toast.LENGTH_LONG).show();
                } else if (noneCovid.isChecked() && (preferNot2SayCovid.isChecked() || fever.isChecked() ||
                        chills.isChecked() || dryCough.isChecked() || wetCough.isChecked() || difficultyBreathing.isChecked()
                        || tightnessInChest.isChecked() || muscleAches.isChecked() || headache.isChecked())) {
                    //show error to select any Respiratory condition
                    Toast.makeText(getContext(), "Either select None or any other Condition.", Toast.LENGTH_LONG).show();
                } else {
                    List<String> medConds = new LinkedList<>();
                    if (noneCovid.isChecked())
                        medConds.add(getResources().getString(R.string.NONE));
                    if (preferNot2SayCovid.isChecked())
                        medConds.add(getResources().getString(R.string.prefer_not_to_say));
                    if (fever.isChecked())
                        medConds.add(getResources().getString(R.string.fever));
                    if (chills.isChecked())
                        medConds.add(getResources().getString(R.string.chills));
                    if (dryCough.isChecked())
                        medConds.add(getResources().getString(R.string.dry_cough));
                    if (wetCough.isChecked())
                        medConds.add(getResources().getString(R.string.wet_cough));
                    if (difficultyBreathing.isChecked())
                        medConds.add(getResources().getString(R.string.difficulty_breathing));
                    if (tightnessInChest.isChecked())
                        medConds.add(getResources().getString(R.string.tightness_in_chest));
                    if (muscleAches.isChecked())
                        medConds.add(getResources().getString(R.string.muscle_aches));
                    if (headache.isChecked())
                        medConds.add(getResources().getString(R.string.headache));
                    MainActivity.demographicDTO.setSymptoms(medConds);
                    NavHostFragment.findNavController(EigthFragment.this)
                            .navigate(R.id.action_EighthFragment_to_NinthFragment);
                }
            }
        });
    }
}