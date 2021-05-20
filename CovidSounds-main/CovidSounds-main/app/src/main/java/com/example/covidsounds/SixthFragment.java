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
public class SixthFragment extends Fragment {
    //variables
    private CheckBox otherNone;
    private CheckBox diabetes;
    private CheckBox cancer;
    private CheckBox previousOrganTransplant;
    private CheckBox hiv;
    private CheckBox otherLongTermCondition;
    private Spinner inHospital;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sixth, container, false);
        return view;
    }

    /**
     * @param view
     * @param savedInstanceState
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //get all values
        otherNone = (CheckBox) view.findViewById(R.id.other_none);
        diabetes = (CheckBox) view.findViewById(R.id.diabetes);
        cancer = (CheckBox) view.findViewById(R.id.cancer);
        previousOrganTransplant = (CheckBox) view.findViewById(R.id.previous_organ_transplant);
        hiv = (CheckBox) view.findViewById(R.id.hiv);
        otherLongTermCondition = (CheckBox) view.findViewById(R.id.other_long_term_condition);
        inHospital = (Spinner) view.findViewById(R.id.inHospital);
        /**
         * move to next fragment
         */
        view.findViewById(R.id.button_sixth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!otherNone.isChecked() && !diabetes.isChecked() && !cancer.isChecked() &&
                        !previousOrganTransplant.isChecked() && !hiv.isChecked() && !otherLongTermCondition.isChecked()) {
                    //show error to select any Cardiovascular condition
                    Toast.makeText(getContext(), "Please select any condition or None.", Toast.LENGTH_LONG).show();
                } else if (otherNone.isChecked() && (diabetes.isChecked() || cancer.isChecked() ||
                        previousOrganTransplant.isChecked() || hiv.isChecked() || otherLongTermCondition.isChecked())) {
                    //show error to select any Respiratory condition
                    Toast.makeText(getContext(), "Either select None or any other Condition.", Toast.LENGTH_LONG).show();
                } else {
                    List<String> medConds = new LinkedList<>();
                    if (otherNone.isChecked())
                        medConds.add(getResources().getString(R.string.NONE));
                    if (diabetes.isChecked())
                        medConds.add(getResources().getString(R.string.diabetes));
                    if (cancer.isChecked())
                        medConds.add(getResources().getString(R.string.cancer));
                    if (previousOrganTransplant.isChecked())
                        medConds.add(getResources().getString(R.string.previous_organ_transplant));
                    if (hiv.isChecked())
                        medConds.add(getResources().getString(R.string.hiv));
                    if (otherLongTermCondition.isChecked())
                        medConds.add(getResources().getString(R.string.other_conditions));
                    MainActivity.demographicDTO.setOtherMedicalConditions(medConds);
                    MainActivity.demographicDTO.setInHospital(inHospital.getSelectedItem().toString());
                    NavHostFragment.findNavController(SixthFragment.this)
                            .navigate(R.id.action_SixthFragment_to_SeventhFragment);
                }
            }
        });
    }
}