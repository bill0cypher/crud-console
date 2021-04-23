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
        super("posts.json", Post.class, Post[].class);
    }

    @Override
    public boolean save(Post post) throws IOException {
        return writeToFile(post);
    }

    @Override
    public boolean update(Post post) throws NoSuchEntryException, EmptyListException, IOException {
        List<Post> posts = readFromFile();
        posts.stream().filter(entry -> entry.getId().equals(post.getId())).findAny().ifPresentOrElse(entry -> {
            posts.set(posts.indexOf(entry), post);
        }, () -> {
            try {
                throw new NoSuchEntryException(String.format(NoSuchEntryException.DEFAULT_MESSAGE_TEXT, post.getId()));
            } catch (NoSuchEntryException e) {
                e.printStackTrace();
            }
        });
        return updateFile(posts);
    }

    @Override
    public boolean delete(Integer id) throws IOException, EmptyListException {
        List<Post> posts = Objects.requireNonNull(readFromFile()).stream().filter(post -> !post.getId().equals(id)).collect(Collectors.toList());
        return updateFile(posts);
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
