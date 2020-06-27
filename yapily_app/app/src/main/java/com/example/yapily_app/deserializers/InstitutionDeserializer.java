package com.example.yapily_app.deserializers;

import com.example.yapily_app.models.Institution;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class InstitutionDeserializer implements JsonDeserializer<Institution> {

    @Override
    public Institution deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject response = json.getAsJsonObject();

        Institution institution = new Institution(response.get("id").getAsString(),
                response.get("name").getAsString());

        JsonArray media = response.getAsJsonArray("media");
        for (JsonElement imageElement: media) {
            JsonObject imageJsonObject = imageElement.getAsJsonObject();
            if (imageJsonObject.get("type").getAsString().equalsIgnoreCase("icon")) {
                institution.setImageUrl(imageJsonObject.get("source").getAsString());
            }
        }

        return institution;
    }
}
