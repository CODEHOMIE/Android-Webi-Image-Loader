package com.johnwebi.webiimageloaderexample.Models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links_ implements Serializable
{

    @SerializedName("self")
    @Expose
    private String self;
    @SerializedName("photos")
    @Expose
    private String photos;
    private final static long serialVersionUID = 5892108550245877162L;

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

}
