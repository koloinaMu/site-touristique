package com.example.asus.madagascar.controlleur;

import com.example.asus.madagascar.modele.Site;

import retrofit2.http.GET;
import retrofit2.Call;
/**
 * Created by ASUS on 29/07/2023.
 */

public interface MyDataAPI {
    //http://localhost:3000/sites
    @GET("/sites")
    Call<Site> getSite();

}
