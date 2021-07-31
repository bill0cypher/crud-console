import bootstrap.JDBCConnector;
import bootstrap.SessionRunner;
import exceptions.EmptyBodyException;
import exceptions.EmptyListException;
import exceptions.NoSuchEntryException;
import model.Post;
import model.Region;
import model.Writer;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import repository.database.PostRepositoryImplDB;
import repository.database.WriterRepositoryImplDB;
import repository.hibernate.WriterRepositoryImpl;
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
    public void initConnections() {
        try {
            SessionRunner.initSessionFactory();
            JDBCConnector.connect("jdbc:postgresql://localhost:5432/library", "root", "1111");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Before
    public void createPost() {
        writerService = new WriterService(new WriterRepositoryImpl());
        postService = new PostService(new PostRepositoryImplDB());
        /*Region region = new Region();
        region.setName("Kiev");
        Writer writer = new Writer();
        writer.setLastName("George");
        writer.setRegion(region);*/
        // try {
            // writerService.save(writer);
            post = new Post();
          //  post.setWriter(writer);
            post.setCreated(new Date(new java.util.Date().getTime()));
            post.setUpdated(new Date(new java.util.Date().getTime()));
            post.setContent("SOME #1");
        /*}*/ /*catch (EmptyBodyException e) {
            e.printStackTrace();
        }*/
    }

    @Test
    public void testCreateWriterFunc() {
        try {
            Writer writer = new Writer();
            Post post = new Post(null, "content#1", new Date(924124L), new Date(924124L));
            post.setWriter(writer);
            Post post2 = new Post(null, "content#2", new Date(824124L), new Date(724124L));
            post2.setWriter(writer);
            Post post3 = new Post(null, "content#3", new Date(724124L), new Date(524124L));
            post3.setWriter(writer);
            Post post4 = new Post(null, "content#4", new Date(624124L), new Date(324124L));
            post4.setWriter(writer);
            Post post5 = new Post(null, "content#5", new Date(524124L), new Date(124124L));
            post5.setWriter(writer);
            Post post6 = new Post(null, "content#6", new Date(924124L), new Date(924124L));
            post6.setWriter(writer);
            Post post7 = new Post(null, "content#7", new Date(824124L), new Date(724124L));
            post7.setWriter(writer);
            Post post8 = new Post(null, "content#8", new Date(724124L), new Date(524124L));
            post8.setWriter(writer);
            Post post9 = new Post(null, "content#9", new Date(624124L), new Date(324124L));
            post9.setWriter(writer);
            Post post10 = new Post(null, "content#10", new Date(524124L), new Date(124124L));
            post10.setWriter(writer);
            Post post11 = new Post(null, "content#11", new Date(924124L), new Date(924124L));
            post11.setWriter(writer);
            Post post12 = new Post(null, "content#12", new Date(824124L), new Date(724124L));
            post12.setWriter(writer);
            Post post13 = new Post(null, "content#13", new Date(724124L), new Date(524124L));
            post13.setWriter(writer);
            Post post14 = new Post(null, "content#14", new Date(624124L), new Date(324124L));
            post14.setWriter(writer);
            Post post15 = new Post(null, "content#15", new Date(524124L), new Date(124124L));
            post15.setWriter(writer);
            writer.setLastName("Koker poker");
            writer.setPosts(List.of(post, post2, post3, post4, post5, post6, post7, post8, post9, post10, post11, post12, post13, post14, post15));
            writer.setRegion(new Region(null, "BIGREGION"));
            writerService.save(writer);
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
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
        } catch (EmptyBodyException e) {
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
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
        }
    }
}
