package com.example.yapily_app.utilities;

public class Authentication {

    // singleton for a user's authentication

    private static Authentication authentication;
    private String applicationUserId, username, yapilyUserId, token;

    private Authentication() {}

    public static Authentication getInstance() {
        if (authentication == null) {
            authentication = new Authentication();
        }

        return authentication;
    }

    public void remove() {
        if (authentication != null) {
            authentication = null;
        }
    }

    public String getUsername() { return username; }

    public void setUsername(String username) {
        this.username = username;
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

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
