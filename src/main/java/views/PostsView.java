package views;

import controller.PostController;
import enums.Operations;
import exceptions.NoSuchOperation;
import model.Post;
import repository.hibernate.PostRepositoryImpl;
import service.PostService;

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
                System.out.println(Objects.requireNonNull(postController.createPost(post)).toString());
                break;
            case UPDATE:
                System.out.println(Objects.requireNonNull(postController.updatePost(post)).toString());
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
