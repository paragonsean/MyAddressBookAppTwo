package com.sbake021.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageButton;

import java.util.ArrayList;

/**
 * Activity to display a list of contacts.
 */
public class ContactListActivity extends AppCompatActivity {
    private ContactDataSource contactDataSource;
    private ContactAdapter adapter;
    private ArrayList<Contact> contactList;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list); // Ensure layout has RecyclerView with id "contactRecyclerView"

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.contactRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize data source and load contacts
        contactDataSource = new ContactDataSource(this);
        contactList = new ArrayList<>();

        contactDataSource.open();
        contactList = (ArrayList<Contact>) contactDataSource.getAllContacts(); // Ensure this method returns ArrayList<Contact>
        contactDataSource.close();

        // Set up the adapter
        adapter = new ContactAdapter(contactList, this);
        adapter.setDelete(true); // Disable delete functionality for this use case
        recyclerView.setAdapter(adapter);

        // Initialize buttons
        initSettingsButton();
        initMapButton();
    }

    /**
     * Initializes the settings button and sets its click listener.
     */
    private void initSettingsButton() {
        ImageButton settingsButton = findViewById(R.id.imageButtonSettings);
        settingsButton.setOnClickListener(view -> {
            Intent intent = new Intent(ContactListActivity.this, ContactSettingsActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Initializes the map button and sets its click listener.
     */
    private void initMapButton() {
        ImageButton mapButton = findViewById(R.id.imageButtonMap);
        mapButton.setOnClickListener(view -> {
            Intent intent = new Intent(ContactListActivity.this, ContactMapActivity.class);
            intent.putParcelableArrayListExtra("CONTACT_LIST", contactList); // Pass all contacts
            startActivity(intent);
        });
    }
}
