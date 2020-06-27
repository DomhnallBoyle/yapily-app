package com.example.yapily_app.models;

import com.google.gson.annotations.SerializedName;

public class AccountAuthResponse {

    private String id;

    @SerializedName("userUuid")
    private String yapilyUserId;

    private String applicationUserId;

    private String institutionId;

    private String authorisationUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYapilyUserId() {
        return yapilyUserId;
    }

    public void setYapilyUserId(String yapilyUserId) {
        this.yapilyUserId = yapilyUserId;
    }

    public String getApplicationUserId() {
        return applicationUserId;
    }

    public void setApplicationUserId(String applicationUserId) {
        this.applicationUserId = applicationUserId;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getAuthorisationUrl() {
        return authorisationUrl;
    }

    public void setAuthorisationUrl(String authorisationUrl) {
        this.authorisationUrl = authorisationUrl;
    }
}
