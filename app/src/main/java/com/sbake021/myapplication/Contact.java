package com.sbake021.myapplication;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable, Serializable {
    private static final long serialVersionUID = 1L; // Recommended for Serializable classes

    private long id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String phone;
    private String email;
    private double latitude;
    private double longitude;
    private String birthday;

    // Default constructor
    public Contact() {
    }

    // Parcelable constructor
    protected Contact(Parcel in) {
        id = in.readLong();
        name = in.readString();
        address = in.readString();
        city = in.readString();
        state = in.readString();
        zipCode = in.readString();
        phone = in.readString();
        email = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        birthday = in.readString();
    }

    // Parcelable Creator
    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(zipCode);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(birthday);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters and setters for all fields
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
