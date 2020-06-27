package com.example.yapily_app.rest;

import com.example.yapily_app.models.BankAccount;
import com.example.yapily_app.models.ConnectedResponse;
import com.example.yapily_app.models.MessageResponse;
import com.example.yapily_app.models.AccountAuthResponse;
import com.example.yapily_app.models.Institution;
import com.example.yapily_app.models.Login;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @FormUrlEncoded
    @POST("/login")
    Call<Login> login(
        @Field("username") String username
    );

    @FormUrlEncoded
    @POST("/users")
    Call<Login> createUser(
        @Field("username") String username
    );

    @GET("/institutions")
    Call<List<Institution>> getInstitutions();

    @FormUrlEncoded
    @POST("/accounts")
    Call<AccountAuthResponse> initiateAccountAuthorisationRequest(
        @Field("institutionId") String institutionId,
        @Field("applicationUserId") String applicationUserId,
        @Field("callbackUrl") String callbackUrl
    );

    @FormUrlEncoded
    @PATCH("/accounts")
    Call<AccountAuthResponse> reauthoriseAccountRequest(
        @Field("applicationUserId") String applicationUserId
    );

    @GET("/users/{applicationUserId}/connected")
    Call<ConnectedResponse> getIsConnected(
        @Path("applicationUserId") String applicationUserId
    );

    @GET("/users/{applicationUserId}/accounts")
    Call<List<BankAccount>> getAccounts(
        @Path("applicationUserId") String applicationUserId
    );

    @DELETE("/users/{applicationUserId}/consents/{consentId}")
    Call<MessageResponse> deleteConsent(
        @Path("applicationUserId") String applicationUserId,
        @Path("consentId") String consentId
    );
}
