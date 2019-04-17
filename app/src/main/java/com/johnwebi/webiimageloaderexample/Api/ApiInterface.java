package com.johnwebi.webiimageloaderexample.Api;

import com.johnwebi.webiimageloaderexample.Models.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("raw/wgkJgazE")
    Call<List<Photo>> getPhotos();
}
