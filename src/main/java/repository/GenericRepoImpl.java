package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import exceptions.EmptyListException;
import exceptions.NoSuchEntryException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class GenericRepoImpl<T, ID> implements GenericRepository<T, ID>{
    public String filename;
    public final Class<T> type;
    public final Class<ID> idType;
    public final Class<T[]> typeArr;
    private final Gson gson;

    protected GenericRepoImpl(String filename, Class<T> instance, Class<T[]> arrInstance, Class<ID> idType) {
        this.filename = filename;
        this.type = instance;
        this.typeArr = arrInstance;
        this.idType = idType;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    protected List<T> readFromFile() throws IOException, EmptyListException {
        return parseFromJSON();
    }

    protected T writeToFile(T t) throws IOException {
        FileReader reader = new FileReader(filename);
        JsonObject jsonObject = new JsonParser().parse(gson.toJson(t)).getAsJsonObject();
        List<T> entries = gson.fromJson(reader, new TypeToken<List<T>>(){}.getType());
        reader.close();
        if (entries.isEmpty()) {
            entries = new ArrayList<>();
            jsonObject.addProperty("id", 0);
        }
        else
            jsonObject.addProperty("id", entries.size());
        t = gson.fromJson(jsonObject, new TypeToken<T>(){}.getType());
        entries.add(t);
        return parseToJSON(entries) ? t : null;
    }

    protected T updateFile(T t) throws IOException, EmptyListException {
        List<T> entries = readFromFile();
        entries.stream()
                .filter(entry -> getObjectId(entry, idType).equals(getObjectId(t, idType)))
                .findAny()
                .ifPresentOrElse(entry -> entries.set(entries.indexOf(entry), t), () -> {
                    try {
                        throw new NoSuchEntryException(String.format(NoSuchEntryException.DEFAULT_MESSAGE_TEXT, getObjectId(t, idType)));
                    } catch (NoSuchEntryException e) {
                        e.printStackTrace();
                    }
                });
        parseToJSON(entries);
        return t;
    }

    protected boolean deleteFromFile(ID id) throws IOException, EmptyListException {
        List<T> entries = Objects.requireNonNull(readFromFile()).stream()
                .filter(t -> getObjectId(t, idType) != id).collect(Collectors.toList());
        return parseToJSON(entries);
    }

    private <T> List<T> parseFromJSON() throws EmptyListException, IOException {
            FileReader fileReader = new FileReader(filename);
            T[] arr = gson.fromJson(fileReader, (Type) typeArr);
            fileReader.close();
            if (arr.length == 0)
                throw new EmptyListException(String.format(EmptyListException.DEFAULT_MESSAGE_TEXT, type.getName()));
            return Arrays.asList(arr);
    }

    private boolean parseToJSON(List<T> t) throws IOException {
        FileWriter writer;
        writer = new FileWriter(filename);
        gson.toJson(t, writer);
        writer.flush();
        writer.close();
        return true;
    }

    private ID getObjectId(T t, Class<ID> clazz) {
        JsonObject object = new JsonParser().parse(gson.toJson(t)).getAsJsonObject();
        ID id = gson.fromJson(object.get("id"), clazz);
        return id;
    }
}
