package repository.filesource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import exceptions.EmptyListException;
import exceptions.NoSuchEntryException;
import model.Post;
import model.Region;
import model.Writer;
import repository.common.WriterRepository;
import util.FileOperations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class WriterRepositoryImpl implements WriterRepository {

    private final Gson gson;
    private final String filename;

    public WriterRepositoryImpl() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.filename = "writers.json";
    }
    @Override
    public Writer save(Writer writer) {
        try {
            FileOperations.writeToFile(gson.toJsonTree(writer).getAsJsonObject(), filename);
            return writer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Writer update(Writer writer) {
        try {
            List<Writer> array = parseToWritersList(FileOperations.readFromFile(filename));
            array.iterator().forEachRemaining(element -> {
                if ( element.getId().equals(writer.getId()))
                {
                    array.remove(element);
                    array.add(writer);
                }
            });
            FileOperations.writeToFile(gson.toJsonTree(array).getAsJsonObject(), filename);
            throw new NoSuchEntryException(NoSuchEntryException.DEFAULT_MESSAGE_TEXT);
        } catch (NoSuchEntryException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        try {
            List<Writer> writers = parseToWritersList(FileOperations.readFromFile(filename));
            writers.iterator().forEachRemaining(post -> {
                if (post.getId().equals(id))
                    writers.remove(post);
            });
            FileOperations.writeToFile(gson.toJsonTree(writers).getAsJsonObject(), filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Writer findById(Integer id) {
        try {
            return parseToWritersList(FileOperations.readFromFile(filename)).stream().filter(post -> post.getId().equals(id)).collect(Collectors.toList()).get(0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Writer> getAll() {
        try {
            return parseToWritersList(FileOperations.readFromFile(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Writer> findByRegion(Region region) {
        return null;
    }

    private List<Writer> parseToWritersList(JsonArray array) {
        if (array.isJsonNull()) return Collections.emptyList();
        List<Writer> writers = new ArrayList<>();
        array.forEach(element -> writers.add(gson.fromJson(element, Writer.class)));
        return writers;
    }
}
