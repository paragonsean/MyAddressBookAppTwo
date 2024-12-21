package com.sbake021.myapplication;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Utility class for address-related operations.
 */
public class AddressUtils {

    /**
     * Converts a given address into its corresponding latitude and longitude.
     *
     * @param address The address to be converted.
     * @param context The context in which the geocoder is being used.
     * @return A LatLng object containing the latitude and longitude of the address, or null if the address could not be geocoded.
     */
    public static LatLng getLatLngFromAddress(String address, Context context) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address location = addresses.get(0);
                return new LatLng(location.getLatitude(), location.getLongitude());
            }
        } catch (IOException e) {
            Log.e("AddressUtils", "Error in geocoding", e);
        }
        return null;
    }
}