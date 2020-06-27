package com.example.yapily_app.models;

import java.util.HashMap;

public class BankAccount {

    private String type;
    private String consentId;
    private String status;
    private HashMap<String, String> accountIdentifications;

    public BankAccount(String type, String consentId, String status, HashMap<String, String> accountIdentifications) {
        this.type = type;
        this.consentId = consentId;
        this.status = status;
        this.accountIdentifications = accountIdentifications;
    }

    public String getType() { return this.type; }

    public String getStatus() {
        return this.status;
    }

    public String getConsentId() {
        return this.consentId;
    }

    public HashMap<String, String> getAccountIdentifications() {
        return accountIdentifications;
    }

    public void setAccountIdentifications(HashMap<String, String> accountIdentifications) {
        this.accountIdentifications = accountIdentifications;
    }

    public void setConsentId(String consentId) {
        this.consentId = consentId;
    }

    public void setStatus(String status) {this.status = status;}
}
