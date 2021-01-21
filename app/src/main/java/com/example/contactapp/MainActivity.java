package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<ContactModel>> call = jsonPlaceHolderApi.getContacts();

        call.enqueue(new Callback<List<ContactModel>>() {
            @Override
            public void onResponse(Call<List<ContactModel>> call, Response<List<ContactModel>> response) {

                if(!response.isSuccessful()){
                    textView.setText(response.code());
                    Log.e("Response Unsuccessful", response.message());
                    return;
                }

                List<ContactModel> contacts = response.body();
                for(ContactModel contact : contacts){
                    String text = "";
                    text += "Name: " + contact.getName() + "\n";
                    text += "Number: " + contact.getNumber() + "\n";

                    textView.append(text);
                }
            }

            @Override
            public void onFailure(Call<List<ContactModel>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }
}