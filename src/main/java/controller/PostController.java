package controller;

import exceptions.EmptyBodyException;
import exceptions.EmptyListException;
import exceptions.NoSuchEntryException;
import model.Post;
import service.PostService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    public boolean createPost(Post post) {
        try {
            postService.save(post);
        } catch (EmptyBodyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePost(Post post) {
        try {
            return postService.update(post);
        } catch (EmptyListException e) {
            e.printStackTrace();
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deletePost(Integer id) {
        try {
            return postService.delete(id);
        } catch (EmptyListException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Post> getAllPosts() {
        try {
            return postService.getAll();
        } catch (EmptyListException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public Post findPostById(Integer id) {
        try {
            return postService.findById(id);
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
        } catch (EmptyListException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
