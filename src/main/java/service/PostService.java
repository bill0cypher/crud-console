package service;

import exceptions.EmptyBodyException;
import exceptions.EmptyListException;
import exceptions.NoSuchEntryException;
import model.Post;
import model.Writer;
import repository.common.GenericRepository;
import repository.filesource.PostRepositoryImpl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class PostService {

    private final GenericRepository<Post, Integer> postRepository;
    public PostService(GenericRepository<Post, Integer> repository) {
        this.postRepository = repository;
    }

    public Post save(Post post) throws EmptyBodyException, IOException {
        if (Objects.isNull(post))
            throw new EmptyBodyException(EmptyBodyException.DEFAULT_MESSAGE_TEXT);
        return postRepository.save(post);
    }

    public Post update(Post post) throws IOException, EmptyListException, NoSuchEntryException {
        return postRepository.update(post);
    }

    public boolean delete(Integer id) throws IOException, EmptyListException {
        return postRepository.delete(id);
    }


    public boolean deleteById(Integer integer) {
        return false;
    }


    public Post findById(Integer id) throws NoSuchEntryException, EmptyListException, IOException {
        Post post = postRepository.findById(id);
        if ( post == null)
            throw new NoSuchEntryException(String.format(NoSuchEntryException.DEFAULT_MESSAGE_TEXT, id));
        return post;
    }


    public List<Post> getAll() throws IOException, EmptyListException {
        return postRepository.getAll();
    }
}
