package repository.filesource;

import com.google.gson.*;
import exceptions.EmptyListException;
import exceptions.NoSuchEntryException;
import model.Post;
import model.Writer;
import repository.common.GenericRepository;
import repository.common.PostRepository;
import util.FileOperations;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PostRepositoryImpl implements PostRepository, GenericRepository<Post, Integer> {

    private final Gson gson;
    private final String filename;

    public PostRepositoryImpl() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.filename = "posts.json";
    }

    @Override
    public Post save(Post post) {
        try {
            FileOperations.writeToFile(gson.toJsonTree(post).getAsJsonObject(), filename);
            return post;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Post update(Post post) {
        try {
            JsonArray array = FileOperations.readFromFile(filename);
            array.iterator().forEachRemaining(element -> {
                if (element.isJsonObject() && element.getAsJsonObject().get("id").getAsInt() == post.getId())
                {
                    array.remove(element);
                    array.add(gson.toJsonTree(post, Post.class));
                }
            });
            FileOperations.writeToFile(array.getAsJsonObject(), filename);
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
            List<Post> posts = parseToPostsList(FileOperations.readFromFile(filename));
            posts.iterator().forEachRemaining(post -> {
                if (post.getId().equals(id))
                    posts.remove(post);
            });
            FileOperations.writeToFile(gson.toJsonTree(posts).getAsJsonObject(), filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Post findById(Integer id) {
        try {
            return parseToPostsList(FileOperations.readFromFile(filename)).stream().filter(post -> post.getId().equals(id)).collect(Collectors.toList()).get(0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Post> getAll() {
        try {
            return parseToPostsList(FileOperations.readFromFile(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Post> findByWriter(Writer writer) {
        return null;
    }

    private List<Post> parseToPostsList(JsonArray array) {
        if (array.isJsonNull()) return Collections.emptyList();
        List<Post> posts = new ArrayList<>();
        array.forEach(element -> posts.add(gson.fromJson(element, Post.class)));
        return posts;
    }
}
