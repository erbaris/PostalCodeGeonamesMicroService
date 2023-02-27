package com.gokhandiyaroglu.android.library.geonames.postalcodesearch.retrofit.data.entity;


import androidx.annotation.NonNull;

public class PostalCode {
    public String code;
    public String adminCode1;
    public String adminCode2;

    public String adminName1;
    public String adminName2;

    public double longitude;

    public double latitude;

    public String plate;

    public String placeName;

    @NonNull
    @Override
    public String toString() {
        return code + " " + adminName2 + " " + placeName ;
    }
}

