package ru.nsu.shebanov;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

/**
 * Utility class to deserialize restaurant config with GSON.
 */
class ConfigDeserializer implements JsonDeserializer<Restaurant> {

    /**
     * Deserialization utility.
     *
     * @param json     The JSON element to deserialize.
     * @param typeOfT  The type of the object to deserialize to.
     * @param context  The deserialization context.
     * @return object populated with data from the JSON
     * @throws JsonParseException If the JSON is invalid or missing required fields
     */
    @Override
    public Restaurant deserialize(
            JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        int numCookers = jsonObject.get("number_cookers").getAsInt();
        int numCouriers = jsonObject.get("number_couriers").getAsInt();
        int storageSize = jsonObject.get("storage_size").getAsInt();

        return new Restaurant(numCookers, numCouriers, storageSize);
    }
}
