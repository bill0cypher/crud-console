package repository.impl;

import exceptions.EmptyListException;
import exceptions.NoSuchEntryException;
import model.Post;
import model.Writer;
import repository.GenericRepoImpl;
import repository.PostRepository;


import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PostRepositoryImpl extends GenericRepoImpl<Post, Integer> implements PostRepository {

    public PostRepositoryImpl() {
        super("posts.json", Post.class, Post[].class, Integer.class);
    }

    @Override
    public Post save(Post post) throws IOException {
        return writeToFile(post);
    }

    @Override
    public Post update(Post post) throws NoSuchEntryException, EmptyListException, IOException {
        return updateFile(post);
    }

    @Override
    public boolean delete(Integer id) throws IOException, EmptyListException {
        return deleteFromFile(id);
    }

    @Override
    public Post findById(Integer id) throws NoSuchEntryException, EmptyListException, IOException {
        List<Post> posts = readFromFile();
        return Optional.of(id > posts.size()).map(aBoolean -> !aBoolean ? posts.get(id) : null)
                .orElseThrow(() -> new NoSuchEntryException(String.format(NoSuchEntryException.DEFAULT_MESSAGE_TEXT, id)));
    }

    @Override
    public List<Post> getAll() throws IOException, EmptyListException {
        return readFromFile();
    }

    @Override
    public List<Post> findByWriter(Writer writer) {
        return null;
    }
}
