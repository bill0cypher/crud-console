package views;

import controller.PostController;
import enums.Operations;
import exceptions.NoSuchOperation;
import model.Post;
import repository.impl.PostRepositoryImpl;
import service.PostService;

import java.util.Arrays;
import java.util.Objects;

public class PostsView {
    private final PostController postController;
    private Post post;
    private Integer postId;

    public PostsView() {
        this.postController = new PostController(new PostService(new PostRepositoryImpl()));
    }

    public void executeOperation(Operations operation) throws NoSuchOperation {
        switch (operation) {
            case CREATE:
                postController.createPost(post);
                break;
            case UPDATE:
                postController.updatePost(post);
                break;
            case DELETE:
                postController.deletePost(postId);
                break;
            case READ:
                System.out.println(Objects.requireNonNull(postController.findPostById(postId)).toString());
                break;
            case READ_ALL:
                postController.getAllPosts().forEach(System.out::println);
                break;
            default:
                throw new NoSuchOperation(NoSuchOperation.DEFAULT_EXCEPTION_MESSAGE);
        }
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }
}
