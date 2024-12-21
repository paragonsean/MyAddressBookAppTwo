package com.sbake021.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

/**
 * DatePickerFragment is a DialogFragment that displays a date picker dialog.
 * It allows the user to select a date and notifies a listener when a date is selected.
 */
public class DatePickerFragment extends DialogFragment {

    /**
     * Interface for listening to date selection events.
     */
    public interface DatePickerListener {
        /**
         * Called when a date is selected.
         * @param selectedDate The selected date.
         */
        void onDateSelected(Calendar selectedDate);
    }

    private DatePickerListener listener;

    /**
     * Sets the listener for date selection events.
     * @param listener The listener to set.
     */
    public void setDatePickerListener(DatePickerListener listener) {
        this.listener = listener;
    }

    /**
     * Called to create the dialog.
     * @param savedInstanceState If the dialog is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     * @return A new instance of DatePickerDialog.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(requireContext(), (view, year1, month1, dayOfMonth) -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year1, month1, dayOfMonth);
            if (listener != null) {
                listener.onDateSelected(selectedDate);
            }
        }, year, month, day);
    }
}