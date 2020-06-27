package com.example.yapily_app.rest;

import com.example.yapily_app.deserializers.BankAccountDeserializer;
import com.example.yapily_app.deserializers.InstitutionDeserializer;
import com.example.yapily_app.models.BankAccount;
import com.example.yapily_app.models.Institution;
import com.example.yapily_app.utilities.Authentication;
import com.example.yapily_app.utilities.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;

import static retrofit2.converter.gson.GsonConverterFactory.create;

public class ApiClient {

    private static Retrofit retrofit;

    public static Retrofit getApiClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Institution.class, new InstitutionDeserializer())
                    .registerTypeAdapter(BankAccount.class, new BankAccountDeserializer())
                    .create();

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("token", Authentication.getInstance().getToken() == null ? "" : Authentication.getInstance().getToken())
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            });

            OkHttpClient client = httpClient.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.API_BASE_URL)
                    .addConverterFactory(create(gson))
                    .client(client)
                    .build();
        }

        return retrofit;
    }
}
