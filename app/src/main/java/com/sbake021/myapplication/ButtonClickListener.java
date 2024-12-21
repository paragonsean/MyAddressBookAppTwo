package com.sbake021.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.function.Consumer;

/**
 * A custom click listener for handling button actions in the MainActivity.
 */
public class ButtonClickListener implements View.OnClickListener {

    private final Context context;
    private final Consumer<Void> action;

    /**
     * Constructor to initialize the ButtonClickListener with a specific action.
     *
     * @param context The context in which the listener operates.
     * @param action  The action to perform on button click.
     */
    public ButtonClickListener(Context context, Consumer<Void> action) {
        this.context = context;
        this.action = action;
    }

    @Override
    public void onClick(View view) {
        try {
            // Execute the provided action
            action.accept(null);
        } catch (Exception e) {
            Toast.makeText(context, "An error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
