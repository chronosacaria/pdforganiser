package me.chronosacaria.pdforganiser.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonUtils {

    private static Gson gson = new Gson();

    public static <T> void writeJson(T object, String filePath) {
        gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(object, writer);
        } catch (IOException e) {
            Logger.getLogger(JsonUtils.class.getName()).log(Level.SEVERE, "Failed to write JSON to file: " + filePath, e);
        }
    }

    public static <T> T readJson(String filePath, TypeToken<T> typeToken) {
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, typeToken.getType());
        } catch (IOException e) {
            Logger.getLogger(JsonUtils.class.getName()).log(Level.SEVERE, "Failed to read JSON from file: " + filePath, e);

            return null;
        }
    }
}
