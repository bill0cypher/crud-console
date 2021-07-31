package service;

import exceptions.EmptyBodyException;
import exceptions.EmptyListException;
import exceptions.NoSuchEntryException;
import model.Post;
import repository.common.GenericRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PostService {

    private final GenericRepository<Post, Integer> postRepository;
    public PostService(GenericRepository<Post, Integer> repository) {
        this.postRepository = repository;
    }

    public Post save(Post post) throws EmptyBodyException {
        if (Objects.isNull(post))
            throw new EmptyBodyException(EmptyBodyException.DEFAULT_MESSAGE_TEXT);
        return postRepository.save(post);
    }

    public Post update(Post post) throws NoSuchEntryException, EmptyBodyException {
        if (Objects.isNull(post))
            throw new EmptyBodyException(EmptyBodyException.DEFAULT_MESSAGE_TEXT);
        return Optional.ofNullable(postRepository.update(post)).orElseThrow(() -> new NoSuchEntryException(NoSuchEntryException.DEFAULT_MESSAGE_TEXT));
    }

    public boolean delete(Integer id) throws NoSuchEntryException {
        return Optional.of(postRepository.delete(id)).orElseThrow(() -> new NoSuchEntryException(NoSuchEntryException.DEFAULT_MESSAGE_TEXT));
    }


    public Post findById(Integer id) throws NoSuchEntryException {
        return Optional.ofNullable(postRepository.findById(id)).orElseThrow(() -> new NoSuchEntryException(String.format(NoSuchEntryException.DEFAULT_MESSAGE_TEXT, id)));
    }


    public List<Post> getAll() throws EmptyListException {
        return Optional.ofNullable(postRepository.getAll()).orElseThrow(() -> new EmptyListException(EmptyListException.DEFAULT_MESSAGE_TEXT));
    }
}
