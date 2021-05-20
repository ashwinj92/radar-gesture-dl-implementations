package com.example.covidsounds;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

/**
 * @author Abhishek
 */
public class ThirdFragment extends Fragment {
    //variables
    private RadioButton radioMale;
    private RadioButton radioFemale;
    private RadioButton preferNotTwoSay;
    private RadioButton other;
    private Spinner enteredAge;
    private Spinner enteredSmokingHabit;
    private Button button_third;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        return view;
    }

    /**
     *
     * @param view
     * @param savedInstanceState
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //get all values on the 3rd fragment
        radioMale = (RadioButton) view.findViewById(R.id.radioMale);
        radioFemale = (RadioButton) view.findViewById(R.id.radioFemale);
        preferNotTwoSay = (RadioButton) view.findViewById(R.id.prefer_not_two_say);
        other = (RadioButton) view.findViewById(R.id.other);
        enteredAge = (Spinner) view.findViewById(R.id.enteredAge);
        enteredSmokingHabit = (Spinner) view.findViewById(R.id.enteredSmokingHabit);
        button_third = view.findViewById(R.id.button_third);
        /**
         * move to next fragment
         */
        view.findViewById(R.id.button_third).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!radioMale.isChecked() && !radioFemale.isChecked() && !preferNotTwoSay.isChecked() && !other.isChecked()) {
                    //show error to select any gender
                    Toast.makeText(getContext(), "Please select any gender.", Toast.LENGTH_LONG).show();
                } else {
                    if(radioFemale.isChecked())
                        MainActivity.demographicDTO.setGender(getResources().getString(R.string.radio_female));
                    else if(radioMale.isChecked())
                        MainActivity.demographicDTO.setGender(getResources().getString(R.string.radio_male));
                    else if(preferNotTwoSay.isChecked())
                        MainActivity.demographicDTO.setGender(getResources().getString(R.string.prefer_not_to_say));
                    else if(other.isChecked())
                        MainActivity.demographicDTO.setGender(getResources().getString(R.string.other));
                    MainActivity.demographicDTO.setAgeGroup(enteredAge.getSelectedItem().toString());
                    MainActivity.demographicDTO.setSmokingHabit(enteredSmokingHabit.getSelectedItem().toString());
                    NavHostFragment.findNavController(ThirdFragment.this)
                            .navigate(R.id.action_ThirdFragment_to_FourthFragment);
                }
            }
        });
    }
}