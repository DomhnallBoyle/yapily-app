package com.example.yapily_app.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.yapily_app.models.BankAccount;
import com.example.yapily_app.models.AccountAuthResponse;
import com.example.yapily_app.rest.ApiClient;
import com.example.yapily_app.rest.ApiService;
import com.example.yapily_app.utilities.Authentication;
import com.example.yapily_app.utilities.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.yapily_app.utilities.Constant.TAG;

public class AccountRepository {

    public LiveData<AccountAuthResponse> initiateAccountAuthorisationRequest(String institutionId,
                                                                             String applicationUserId) {
        final MutableLiveData<AccountAuthResponse> data = new MutableLiveData<>();

        Call<AccountAuthResponse> call = ApiClient.getApiClient()
                .create(ApiService.class)
                .initiateAccountAuthorisationRequest(institutionId, applicationUserId, Constant.API_AUTH_CALLBACK_URL);

        call.enqueue(new Callback<AccountAuthResponse>() {
            @Override
            public void onResponse(Call<AccountAuthResponse> call, Response<AccountAuthResponse> response) {
                data.setValue(response.body());
                Log.d(TAG, "Account Auth Request: " + response.body());
            }

            @Override
            public void onFailure(Call<AccountAuthResponse> call, Throwable t) {
                Log.e(TAG, "Account Auth Request: " + t.getMessage());
            }
        });

        return data;
    }

    public LiveData<AccountAuthResponse> reauthoriseAccountRequest() {
        final MutableLiveData<AccountAuthResponse> data = new MutableLiveData<>();

        Call<AccountAuthResponse> call = ApiClient.getApiClient()
                .create(ApiService.class)
                .reauthoriseAccountRequest(Authentication.getInstance().getApplicationUserId());

        call.enqueue(new Callback<AccountAuthResponse>() {
            @Override
            public void onResponse(Call<AccountAuthResponse> call, Response<AccountAuthResponse> response) {
                data.setValue(response.body());
                Log.d(TAG, "Account Re-auth Request: " + response.body());
            }

            @Override
            public void onFailure(Call<AccountAuthResponse> call, Throwable t) {
                Log.e(TAG, "Account Re-auth Request: " + t.getMessage());
            }
        });

        return data;
    }

    public LiveData<List<BankAccount>> getAccounts() {
        final MutableLiveData<List<BankAccount>> data = new MutableLiveData<>();

        Call<List<BankAccount>> call = ApiClient.getApiClient()
                .create(ApiService.class)
                .getAccounts(Authentication.getInstance().getApplicationUserId());

        call.enqueue(new Callback<List<BankAccount>>() {
            @Override
            public void onResponse(Call<List<BankAccount>> call, Response<List<BankAccount>> response) {
                data.setValue(response.body());
                Log.d(TAG, "Get Accounts: " + response.body());
            }

            @Override
            public void onFailure(Call<List<BankAccount>> call, Throwable t) {
                Log.e(TAG, "Get Accounts: " + t.getMessage());
            }
        });

        return data;
    }
}
