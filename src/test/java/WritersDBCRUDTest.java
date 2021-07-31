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
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import repository.database.PostRepositoryImplDB;
import repository.database.WriterRepositoryImplDB;
import service.PostService;
import service.WriterService;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(JUnit4ClassRunner.class)
public class WritersDBCRUDTest {

    private Writer writer;
    private WriterService writerService;
    private PostService postService;
    @Before
    public void createWriter() {
        writerService = new WriterService(new WriterRepositoryImplDB());
        postService = new PostService(new PostRepositoryImplDB());
        writer = new Writer();
        Region region = new Region();
        region.setName("Kiev");
        Post post1 = new Post();
        post1.setWriter(writer);
        post1.setCreated(new Date(new java.util.Date().getTime()));
        post1.setUpdated(new Date(new java.util.Date().getTime()));
        post1.setContent("Some postcontent");
        Post post2 = new Post();
        post2.setWriter(writer);
        post2.setCreated(new Date(new java.util.Date().getTime()));
        post2.setUpdated(new Date(new java.util.Date().getTime()));
        post2.setContent("Some post content2");
        writer.setId(0);
        writer.setLastName("George");
        writer.setRegion(region);
        writer.setPosts(List.of(post1, post2));
        try {
            JDBCConnector.connect("jdbc:postgresql://localhost:5432/library", "root", "1111");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateWriterFunc() {
        try {
            org.junit.Assert.assertNotNull(writerService.save(writer));
        } catch (EmptyBodyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateWriterFunc() {
        try {
            testCreateWriterFunc();
            Post post = new Post(null, "CONTENT3", new Date(new java.util.Date().getTime()), new Date(new java.util.Date().getTime()));
            post.setWriter(writer);
            writer.setPosts(List.of(post));
            writer.setLastName("Bogod");
            org.junit.Assert.assertNotNull(writerService.update(writer));
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
        } catch (EmptyBodyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteWriterFunc() {
        try {
            Writer writer1 = writerService.save(writer);
            org.junit.Assert.assertTrue(writerService.delete(writer1.getId()));
        } catch (EmptyBodyException e) {
            e.printStackTrace();
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
        }
    }
}

