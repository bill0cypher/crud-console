package views;

import controller.PostController;
import enums.Operations;
import exceptions.NoSuchOperation;
import model.Post;
import repository.impl.PostRepositoryImpl;
import service.PostService;

public class PostsView {
    private final PostController postController;
    private final Post post;

    public PostsView(Post post) {
        this.postController = new PostController(new PostService(new PostRepositoryImpl()));
        this.post = post;
    }

    public void executeOperation(Operations operation) throws NoSuchOperation {
        switch (operation) {
            case SAVE:
                postController.createPost(post);
                break;
            case UPDATE:
                postController.updatePost(post);
                break;
            case DELETE:
                postController.deletePost(post.getId());
                break;
            case READ:
                postController.findPostById(post.getId());
            case READ_ALL:
                postController.getAllPosts();
            default:
                throw new NoSuchOperation(NoSuchOperation.DEFAULT_EXCEPTION_MESSAGE);
        }
    }
}
