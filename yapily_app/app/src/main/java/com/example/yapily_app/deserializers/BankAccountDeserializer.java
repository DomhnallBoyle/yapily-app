package com.example.yapily_app.deserializers;

import com.example.yapily_app.models.BankAccount;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;

public class BankAccountDeserializer implements JsonDeserializer<BankAccount> {

    @Override
    public BankAccount deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonBankAccount = json.getAsJsonObject();
        JsonArray jsonAccountIdentifications = jsonBankAccount.getAsJsonArray("accountIdentifications");

        HashMap<String, String> accountIdentifications = new HashMap<>();
        for (JsonElement jsonAccountIdentication: jsonAccountIdentifications) {
            String type = jsonAccountIdentication.getAsJsonObject().get("type").getAsString().toLowerCase();
            String identification = jsonAccountIdentication.getAsJsonObject().get("identification").getAsString();
            accountIdentifications.put(type, identification);
        }

        return new BankAccount(
            jsonBankAccount.get("type").getAsString(),
            jsonBankAccount.get("consentId").getAsString(),
            jsonBankAccount.get("status").getAsString(),
            accountIdentifications
        );
    }
}
