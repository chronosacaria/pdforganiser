package me.chronosacaria.pdforganiser.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.chronosacaria.pdforganiser.model.PdfDataBootstrap;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonUtils {

    private static Gson gson = new Gson();

    // Serialize
    public static <T> void writeJson(T object, String filePath) {
        gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(object, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Deserialize
    //public static <T> T readJson(String filePath, Class<T> classOfT) {
    //    try (FileReader reader = new FileReader(filePath)) {
    //        return gson.fromJson(reader, classOfT);
    //    } catch (IOException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}

    // Deserialize to a generic type
    public static <T> T readJson(String filePath, TypeToken<T> typeToken) {
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, typeToken.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
