package com.example.yapily_app.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.yapily_app.models.ConnectedResponse;
import com.example.yapily_app.models.Login;
import com.example.yapily_app.models.MessageResponse;
import com.example.yapily_app.rest.ApiClient;
import com.example.yapily_app.rest.ApiService;
import com.example.yapily_app.utilities.Authentication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.yapily_app.utilities.Constant.TAG;

public class UserRepository {

    public LiveData<Login> login(String username) {
        final MutableLiveData<Login> data = new MutableLiveData<>();

        Call<Login> call = ApiClient.getApiClient()
                .create(ApiService.class)
                .login(username);

        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                data.setValue(response.body());
                Log.d(TAG, "Login: " + response.body());
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.e(TAG, "Login: " + t.getMessage());
            }
        });

        return data;
    }

    public LiveData<Login> createUser(String username) {
        final MutableLiveData<Login> data = new MutableLiveData<>();

        Call<Login> call = ApiClient.getApiClient()
                .create(ApiService.class)
                .createUser(username);

        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                data.setValue(response.body());
                Log.d(TAG, "Create User: " + response.body());
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.e(TAG, "Create User: " + t.getMessage());
            }
        });

        return data;
    }

    public LiveData<ConnectedResponse> getIsConnected() {
        final MutableLiveData<ConnectedResponse> data = new MutableLiveData<>();

        Call<ConnectedResponse> call = ApiClient.getApiClient()
                .create(ApiService.class)
                .getIsConnected(Authentication.getInstance().getApplicationUserId());

        call.enqueue(new Callback<ConnectedResponse>() {
            @Override
            public void onResponse(Call<ConnectedResponse> call, Response<ConnectedResponse> response) {
                data.setValue(response.body());
                Log.d(TAG, "Get Consent: " + response.body());
            }

            @Override
            public void onFailure(Call<ConnectedResponse> call, Throwable t) {
                Log.e(TAG, "Get Consent: " + t.getMessage());
            }
        });

        return data;
    }

    public LiveData<MessageResponse> deleteConsent(String consentId) {
        final MutableLiveData<MessageResponse> data = new MutableLiveData<>();

        Call<MessageResponse> call = ApiClient.getApiClient()
                .create(ApiService.class)
                .deleteConsent(Authentication.getInstance().getApplicationUserId(), consentId);

        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                data.setValue(response.body());
                Log.d(TAG, "Delete Consent: " + response.body());
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Log.e(TAG, "Delete Consent: " + t.getMessage());
            }
        });

        return data;
    }
}
