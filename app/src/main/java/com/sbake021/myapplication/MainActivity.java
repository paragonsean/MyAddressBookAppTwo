package com.sbake021.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

/**
 * MainActivity class for managing the main screen of the application.
 */
public class MainActivity extends AppCompatActivity {
    private ContactDataSource contactDataSource;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactDataSource = new ContactDataSource(this);

        initListButton();
        initSaveButton();
        initToggleButton();
        initSettingsButton();
        initMapButton();
        setForEditing(false);
        initChangeDateButton();
    }

    /**
     * Initializes the list button and sets its click listener.
     */
    private void initListButton() {
        ImageButton listButton = findViewById(R.id.imageButtonList);
        listButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ContactListActivity.class)));
    }

    /**
     * Initializes the save button and sets its click listener.
     */
    private void initSaveButton() {
        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(view -> saveContact());
    }

    /**
     * Saves the contact information entered by the user.
     */
    private void saveContact() {
        Contact newContact = getContactFromInput();
        if (newContact == null) {
            Toast.makeText(this, "Unable to fetch location for the address.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            contactDataSource.open();
            boolean wasSuccessful = contactDataSource.insertContact(newContact);
            contactDataSource.close();

            if (wasSuccessful) {
                Toast.makeText(this, "Contact Saved!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ContactListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Error saving contact!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Database Error!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Retrieves contact details from input fields and creates a Contact object.
     *
     * @return A Contact object with the entered details, or null if geocoding fails.
     */
    private Contact getContactFromInput() {
        EditText editName = findViewById(R.id.editName);
        EditText editAddress = findViewById(R.id.editAddress);
        EditText editCity = findViewById(R.id.editCity);
        EditText editState = findViewById(R.id.editState);
        EditText editZipCode = findViewById(R.id.editZipcode);
        EditText editPhone = findViewById(R.id.editHome);
        EditText editEmail = findViewById(R.id.editEMail);

        String fullAddress = editAddress.getText().toString() + ", " +
                editCity.getText().toString() + ", " +
                editState.getText().toString() + " " +
                editZipCode.getText().toString();

        LatLng latLng = AddressUtils.getLatLngFromAddress(fullAddress, this);
        if (latLng == null) {
            return null;
        }

        Contact contact = new Contact();
        contact.setName(editName.getText().toString());
        contact.setAddress(fullAddress);
        contact.setCity(editCity.getText().toString());
        contact.setState(editState.getText().toString());
        contact.setZipCode(editZipCode.getText().toString());
        contact.setPhone(editPhone.getText().toString());
        contact.setEmail(editEmail.getText().toString());
        contact.setLatitude(latLng.latitude);
        contact.setLongitude(latLng.longitude);

        return contact;
    }

    /**
     * Initializes the toggle button and sets its click listener.
     */
    private void initToggleButton() {
        ToggleButton editToggle = findViewById(R.id.toggleButtonEdit);
        editToggle.setOnClickListener(view -> setForEditing(editToggle.isChecked()));
    }

    /**
     * Enables or disables editing of contact fields.
     *
     * @param enabled True to enable editing, false to disable.
     */
    private void setForEditing(boolean enabled) {
        int[] editableIds = {
                R.id.editName, R.id.editAddress, R.id.editCity, R.id.editState,
                R.id.editZipcode, R.id.editHome, R.id.editEMail, R.id.buttonSave
        };

        for (int id : editableIds) {
            findViewById(id).setEnabled(enabled);
        }

        if (enabled) {
            findViewById(R.id.editName).requestFocus();
        }
    }

    /**
     * Initializes the change date button and sets its click listener.
     */
    private void initChangeDateButton() {
        Button changeDateButton = findViewById(R.id.buttonBirthday);
        changeDateButton.setOnClickListener(view -> {
            DatePickerFragment datePicker = new DatePickerFragment();
            datePicker.setDatePickerListener(this::didFinishDatePickerDialog);
            datePicker.show(getSupportFragmentManager(), "datePicker");
        });
    }

    /**
     * Initializes the settings button and sets its click listener.
     */
    private void initSettingsButton() {
        ImageButton settingsButton = findViewById(R.id.imageButtonSettings);
        settingsButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ContactSettingsActivity.class)));
    }

    /**
     * Initializes the map button and sets its click listener.
     */
    private void initMapButton() {
        ImageButton mapButton = findViewById(R.id.imageButtonMap);
        mapButton.setOnClickListener(view -> {
            Contact contact = getContactFromInput();
            if (contact == null) {
                Toast.makeText(this, "Unable to fetch location for the address.", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(MainActivity.this, ContactMapActivity.class);
            intent.putExtra("CONTACT", (Parcelable) contact);
            startActivity(intent);
        });
    }

    /**
     * Callback method for the date picker dialog.
     *
     * @param selectedDate The selected date from the date picker.
     */
    public void didFinishDatePickerDialog(Calendar selectedDate) {
        TextView birthDay = findViewById(R.id.textBirthday);
        birthDay.setText(DateFormat.format("MM/dd/yyyy", selectedDate));
    }
}