package util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileOperations {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void writeToFile(JsonObject jsonObject, String filename) throws FileNotFoundException, IOException {
        JsonArray arr = new JsonParser().parse(new FileReader(filename)).getAsJsonArray();
        arr.add(jsonObject);
        parseToJSON(arr, filename);
    }

    public static JsonArray readFromFile(String filename) throws FileNotFoundException {
        return new JsonParser().parse(new FileReader(filename)).getAsJsonArray();
    }

    private static void parseToJSON(JsonArray array, String filename) throws IOException {
        FileWriter writer;
        writer = new FileWriter(filename);
        gson.toJson(array, writer);
        writer.flush();
        writer.close();
    }
}
