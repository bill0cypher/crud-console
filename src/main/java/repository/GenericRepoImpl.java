package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import exceptions.EmptyListException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class GenericRepoImpl<T, ID> implements GenericRepository<T, ID>{
    public String filename;
    public final Class<T> type;
    public final Class<T[]> typeArr;

    protected GenericRepoImpl(String filename, Class<T> instance, Class<T[]> arrInstance) {
        this.filename = filename;
        this.type = instance;
        this.typeArr = arrInstance;
    }

    protected List<T> readFromFile() throws IOException, EmptyListException {
        return parseFromJSON();
    }

    protected boolean writeToFile(T t) throws FileNotFoundException, IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileReader reader = new FileReader(filename);
        JsonObject jsonObject = new JsonParser().parse(gson.toJson(t)).getAsJsonObject();
        List<T> entries = gson.fromJson(reader, new TypeToken<List<T>>(){}.getType());
        reader.close();
        if (entries.isEmpty()) {
            entries = new ArrayList<>();
            jsonObject.addProperty("id", Integer.valueOf(0));
        }
        else
            jsonObject.addProperty("id", entries.size());
        t = gson.fromJson(jsonObject, new TypeToken<T>(){}.getType());
        entries.add(t);
        return parseToJSON(entries);
    }

    protected boolean updateFile(List<T> t) throws FileNotFoundException, IOException {
        return parseToJSON(t);
    }

    private <T> List<T> parseFromJSON() throws EmptyListException, IOException {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileReader fileReader = new FileReader(filename);
            T[] arr = gson.fromJson(fileReader, (Type) typeArr);
            fileReader.close();
            if (arr.length == 0)
                throw new EmptyListException(String.format(EmptyListException.DEFAULT_MESSAGE_TEXT, type.getName()));
            return Arrays.asList(arr);
    }

    private boolean parseToJSON(List<T> t) throws FileNotFoundException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        FileWriter writer;
        try {
            writer = new FileWriter(filename);
            gsonBuilder.setPrettyPrinting().create().toJson(t, writer);
            writer.flush();
            writer.close();
            return true;
        } catch (FileNotFoundException e) {
          throw new FileNotFoundException("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
