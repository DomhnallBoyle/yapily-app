package com.example.yapily_app.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.yapily_app.models.Institution;
import com.example.yapily_app.rest.ApiClient;
import com.example.yapily_app.rest.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.yapily_app.utilities.Constant.TAG;

public class InstitutionRepository {

    public LiveData<List<Institution>> getInstitutions() {
        final MutableLiveData<List<Institution>> data = new MutableLiveData<>();

        Call<List<Institution>> call = ApiClient.getApiClient()
                .create(ApiService.class)
                .getInstitutions();

        call.enqueue(new Callback<List<Institution>>() {
            @Override
            public void onResponse(Call<List<Institution>> call, Response<List<Institution>> response) {
                data.setValue(response.body());
                Log.d(TAG, "Get Institutions: " + response.body());
            }

            @Override
            public void onFailure(Call<List<Institution>> call, Throwable t) {
                Log.e(TAG, "Get Institutions: " + t.getMessage());
            }
        });

        return data;
    }
}
