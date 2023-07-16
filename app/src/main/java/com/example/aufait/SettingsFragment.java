package com.example.aufait;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    // Declare shared preferences key
    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_GENERAL_SETTINGS = "general_settings";
    private static final String KEY_ACCOUNT_SETTINGS = "account_settings";
    private static final String KEY_DISPLAY_SETTINGS = "display_settings";
    private static final String KEY_NOTIFICATION_SETTINGS = "notification_settings";
    private static final String KEY_ABOUT_HELP_SETTINGS = "about_help_settings";

    private Spinner generalSettingsSpinner;
    private Spinner accountSettingsSpinner;
    private Spinner displaySettingsSpinner;
    private Spinner notificationSettingsSpinner;
    private Spinner aboutAndHelpSpinner;

    private String[] generalSettingsOptions;
    private String[] accountSettingsOptions;
    private String[] displaySettingsOptions;
    private String[] notificationSettingsOptions;
    private String[] aboutAndHelpOptions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        generalSettingsSpinner = view.findViewById(R.id.generalSettingsSpinner);
        accountSettingsSpinner = view.findViewById(R.id.accountSettingsSpinner);
        displaySettingsSpinner = view.findViewById(R.id.displaySettingsSpinner);
        notificationSettingsSpinner = view.findViewById(R.id.notificationSettingsSpinner);
        aboutAndHelpSpinner = view.findViewById(R.id.aboutHelpSpinner);

        generalSettingsOptions = getResources().getStringArray(R.array.general_settings_options);
        accountSettingsOptions = getResources().getStringArray(R.array.account_settings_options);
        displaySettingsOptions = getResources().getStringArray(R.array.display_settings_options);
        notificationSettingsOptions = getResources().getStringArray(R.array.notification_settings_options);
        aboutAndHelpOptions = getResources().getStringArray(R.array.about_and_help_options);

        ArrayAdapter<String> generalSettingsAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, generalSettingsOptions);
        ArrayAdapter<String> accountSettingsAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, accountSettingsOptions);
        ArrayAdapter<String> displaySettingsAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, displaySettingsOptions);
        ArrayAdapter<String> notificationSettingsAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, notificationSettingsOptions);
        ArrayAdapter<String> aboutAndHelpAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, aboutAndHelpOptions);

        generalSettingsSpinner.setAdapter(generalSettingsAdapter);
        accountSettingsSpinner.setAdapter(accountSettingsAdapter);
        displaySettingsSpinner.setAdapter(displaySettingsAdapter);
        notificationSettingsSpinner.setAdapter(notificationSettingsAdapter);
        aboutAndHelpSpinner.setAdapter(aboutAndHelpAdapter);

        generalSettingsSpinner.setOnItemSelectedListener(this);
        accountSettingsSpinner.setOnItemSelectedListener(this);
        displaySettingsSpinner.setOnItemSelectedListener(this);
        notificationSettingsSpinner.setOnItemSelectedListener(this);
        aboutAndHelpSpinner.setOnItemSelectedListener(this);

        loadSelectedOptions(); // Load selected options from shared preferences

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        String selectedOption = spinner.getSelectedItem().toString();
        saveSelectedOption(spinner.getId(), selectedOption);
        Toast.makeText(requireContext(), "Selected: " + selectedOption, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    // Save the selected option to shared preferences
    private void saveSelectedOption(int spinnerId, String selectedOption) {
        SharedPreferences preferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Store the selected option based on the spinner ID using if-else statements
        if (spinnerId == R.id.generalSettingsSpinner) {
            editor.putString(KEY_GENERAL_SETTINGS, selectedOption);
        } else if (spinnerId == R.id.accountSettingsSpinner) {
            editor.putString(KEY_ACCOUNT_SETTINGS, selectedOption);
        } else if (spinnerId == R.id.displaySettingsSpinner) {
            editor.putString(KEY_DISPLAY_SETTINGS, selectedOption);
        } else if (spinnerId == R.id.notificationSettingsSpinner) {
            editor.putString(KEY_NOTIFICATION_SETTINGS, selectedOption);
        } else if (spinnerId == R.id.aboutHelpSpinner) {
            editor.putString(KEY_ABOUT_HELP_SETTINGS, selectedOption);
        }

        editor.apply();
    }

    // Access the selected options from shared preferences
    private void loadSelectedOptions() {
        SharedPreferences preferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Retrieve the selected options from shared preferences
        String generalSettingsOption = preferences.getString(KEY_GENERAL_SETTINGS, generalSettingsOptions[0]);
        String accountSettingsOption = preferences.getString(KEY_ACCOUNT_SETTINGS, accountSettingsOptions[0]);
        String displaySettingsOption = preferences.getString(KEY_DISPLAY_SETTINGS, displaySettingsOptions[0]);
        String notificationSettingsOption = preferences.getString(KEY_NOTIFICATION_SETTINGS, notificationSettingsOptions[0]);
        String aboutHelpOption = preferences.getString(KEY_ABOUT_HELP_SETTINGS, aboutAndHelpOptions[0]);

        // Set the selected options in the spinners
        generalSettingsSpinner.setSelection(getIndex(generalSettingsOptions, generalSettingsOption));
        accountSettingsSpinner.setSelection(getIndex(accountSettingsOptions, accountSettingsOption));
        displaySettingsSpinner.setSelection(getIndex(displaySettingsOptions, displaySettingsOption));
        notificationSettingsSpinner.setSelection(getIndex(notificationSettingsOptions, notificationSettingsOption));
        aboutAndHelpSpinner.setSelection(getIndex(aboutAndHelpOptions, aboutHelpOption));
    }

    // Get the index of a given option in an array of options
    private int getIndex(String[] options, String option) {
        for (int i = 0; i < options.length; i++) {
            if (options[i].equals(option)) {
                return i;
            }
        }
        return 0; // Return 0 if option not found
    }
}
