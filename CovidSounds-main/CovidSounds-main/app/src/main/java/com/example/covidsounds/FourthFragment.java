package com.example.covidsounds;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
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
public class FourthFragment extends Fragment {
    //variables
    private CheckBox none;
    private CheckBox respPnts;
    private CheckBox fibrosis;
    private CheckBox emphysema;
    private CheckBox asthma;
    private CheckBox pulmonaryFibrosis;
    private CheckBox otherLungDiseases;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth, container, false);
        return view;
    }

    /**
     * @param view
     * @param savedInstanceState
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //get all values on the 3rd fragment
        none = (CheckBox) view.findViewById(R.id.none);
        respPnts = (CheckBox) view.findViewById(R.id.resp_pnts);
        fibrosis = (CheckBox) view.findViewById(R.id.fibrosis);
        emphysema = (CheckBox) view.findViewById(R.id.emphysema);
        asthma = (CheckBox) view.findViewById(R.id.asthma);
        pulmonaryFibrosis = (CheckBox) view.findViewById(R.id.pulmonary_fibrosis);
        otherLungDiseases = (CheckBox) view.findViewById(R.id.other_lung_diseases);
        /**
         * move to next fragment
         */
        view.findViewById(R.id.button_fourth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!none.isChecked() && !respPnts.isChecked() && !fibrosis.isChecked() &&
                        !emphysema.isChecked() && !asthma.isChecked() && !pulmonaryFibrosis.isChecked() &&
                        !otherLungDiseases.isChecked()) {
                    //show error to select any Respiratory condition
                    Toast.makeText(getContext(), "Please select any Respiratory condition or None.", Toast.LENGTH_LONG).show();
                } else if (none.isChecked() && (respPnts.isChecked() || fibrosis.isChecked() ||
                        emphysema.isChecked() || asthma.isChecked() || pulmonaryFibrosis.isChecked() ||
                        otherLungDiseases.isChecked())) {
                    //show error to select any Respiratory condition
                    Toast.makeText(getContext(), "Either select None or any other Respiratory Condition.", Toast.LENGTH_LONG).show();
                } else {
                    List<String> medConds = new LinkedList<>();
                    if (none.isChecked())
                        medConds.add(getResources().getString(R.string.NONE));
                    if (respPnts.isChecked())
                        medConds.add(getResources().getString(R.string.prefer_not_to_say));
                    if (fibrosis.isChecked())
                        medConds.add(getResources().getString(R.string.CRYSTIC_FIBROSIS));
                    if (emphysema.isChecked())
                        medConds.add(getResources().getString(R.string.COPD_EMPHYSEMA));
                    if (asthma.isChecked())
                        medConds.add(getResources().getString(R.string.ASTHMA));
                    if (pulmonaryFibrosis.isChecked())
                        medConds.add(getResources().getString(R.string.PULMONARY_FIBROSIS));
                    if (otherLungDiseases.isChecked())
                        medConds.add(getResources().getString(R.string.OTHER_LUNG_DISEASES));
                    MainActivity.demographicDTO.setRespiratoryMedicalConditions(medConds);
                    NavHostFragment.findNavController(FourthFragment.this)
                            .navigate(R.id.action_FourthFragment_to_FifthFragment);
                }
            }
        });
    }

}