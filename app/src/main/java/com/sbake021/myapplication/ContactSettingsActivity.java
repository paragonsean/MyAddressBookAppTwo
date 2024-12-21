package com.sbake021.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

/**
 * ContactSettingsActivity class for managing the settings screen of the application.
 */
public class ContactSettingsActivity extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_settings);

        // Initialize "Back to Main" button
        Button backToMainButton = findViewById(R.id.buttonBackToMain);
        backToMainButton.setOnClickListener(view -> {
            // Return to MainActivity
            Intent intent = new Intent(ContactSettingsActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Ensure no multiple instances
            startActivity(intent);
        });
    }
}