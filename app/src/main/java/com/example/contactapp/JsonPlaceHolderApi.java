package com.example.contactapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("getcontact")
    Call<List<ContactModel>> getContacts();
}
