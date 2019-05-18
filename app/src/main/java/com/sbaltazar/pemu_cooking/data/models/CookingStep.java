package com.sbaltazar.pemu_cooking.data.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.net.URL;

public class CookingStep {

    private int id;
    private String shortDescription;
    @SerializedName("description")
    private String completeDescription;
    @SerializedName("videoURL")
    private String videoUrl;
    @SerializedName("thumbnailURL")
    private String thumbnailUrl;
}
