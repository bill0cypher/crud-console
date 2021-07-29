import bootstrap.JDBCConnector;
import exceptions.EmptyBodyException;
import exceptions.EmptyListException;
import exceptions.NoSuchEntryException;
import model.Post;
import model.Region;
import model.Writer;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import repository.database.PostRepositoryImplDB;
import repository.database.WriterRepositoryImplDB;
import service.PostService;
import service.WriterService;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@RunWith(JUnit4ClassRunner.class)
public class PostDBCRUDTest {
    private Post post;
    private WriterService writerService;
    private PostService postService;
    @Before
    public void createPost() {
        try {
            JDBCConnector.connect("jdbc:postgresql://localhost:5432/library", "root", "1111");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        writerService = new WriterService(new WriterRepositoryImplDB());
        postService = new PostService(new PostRepositoryImplDB());
        Region region = new Region();
        region.setName("Kiev");
        Writer writer = new Writer();
        writer.setLastName("George");
        writer.setRegion(region);
        try {
            writerService.save(writer);
            post = new Post();
            post.setWriter(writer);
            post.setCreated(new Date(new java.util.Date().getTime()));
            post.setUpdated(new Date(new java.util.Date().getTime()));
            post.setContent("SOME #1");
        } catch (EmptyBodyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreatePostFunc() {
        try {
            org.junit.Assert.assertNotNull(postService.save(post));
        } catch (EmptyBodyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdatePostFunc() {
        try {
            testCreatePostFunc();
            post.setContent("content updated");
            post.setCreated(new Date(1231231111));
            post.setUpdated(new Date(123123123123L));
            org.junit.Assert.assertNotNull(postService.update(post));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EmptyListException e) {
            e.printStackTrace();
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeletePostFunc() {
        try {
            postService.save(post);
            org.junit.Assert.assertTrue(writerService.delete(post.getId()));
        } catch (EmptyBodyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EmptyListException e) {
            e.printStackTrace();
        }
    }
}
