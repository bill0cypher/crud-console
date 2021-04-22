package repository.impl;

import exceptions.EmptyListException;
import exceptions.NoSuchEntryException;
import model.Post;
import model.Writer;
import repository.GenericRepoImpl;
import repository.PostRepository;


import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PostRepositoryImpl extends GenericRepoImpl<Post, Integer> implements PostRepository {

    public PostRepositoryImpl() {
        super("post.json", Post.class, Post[].class);
    }

    @Override
    public boolean save(Post post) throws IOException {
        return writeToFile(post);
    }

    @Override
    public boolean update(Post post) throws NoSuchEntryException, EmptyListException, IOException {
        List<Post> posts = readFromFile();
        for (Post entry : posts) {
            if (entry.getId().equals(post.getId())) {
                posts.set(posts.indexOf(entry), post);
            } else {
                if (posts.indexOf(entry) == posts.size() - 1)
                    throw new NoSuchEntryException("Such entry doesn't exist");
            }
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) throws IOException, EmptyListException {
        List<Post> posts = Objects.requireNonNull(readFromFile()).stream().filter(post -> !post.getId().equals(id)).collect(Collectors.toList());
        return updateFile(posts);
    }

    @Override
    public Post findById(Integer id) throws NoSuchEntryException, EmptyListException, IOException {
        List<Post> posts = readFromFile();
        if (id > posts.size())
            throw new NoSuchEntryException(String.format(NoSuchEntryException.DEFAULT_MESSAGE_TEXT, id));
        return posts.get(id);
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
