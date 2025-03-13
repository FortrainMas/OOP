package ru.nsu.shebanov;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * GSON utility config.
 */
public class ConfigLoader {
    /**
     * Loads configuration for Restaurant from config.json in resources.
     *
     * @return Restaurant instance
     */
    public static Restaurant loadConfig() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Restaurant.class, new ConfigDeserializer())
                .create();

        try (Reader reader = new InputStreamReader(
                Objects.requireNonNull(ConfigLoader.class.getClassLoader().getResourceAsStream("config.json")),
                StandardCharsets.UTF_8)) {

            return gson.fromJson(reader, Restaurant.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config", e);
        }
    }
}
