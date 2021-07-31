import bootstrap.SessionRunner;
import exceptions.EmptyBodyException;
import exceptions.EmptyListException;
import exceptions.NoSuchEntryException;
import model.Post;
import model.Writer;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import repository.hibernate.PostRepositoryImpl;
import repository.hibernate.WriterRepositoryImpl;
import service.PostService;
import service.WriterService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.Silent.class)
public class PostORMCRUDTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepositoryImpl postRepository;
    @InjectMocks
    private WriterService writerService;
    @Mock
    private Post post;
    @Mock
    private List<Post> posts;

    @BeforeAll
    public void createPost() {
        SessionRunner.initSessionFactory();
        writerService = new WriterService(new WriterRepositoryImpl());
            Writer writer = Mockito.mock(Writer.class);
            post = new Post();
            post.setContent("Post content created");
            post.setCreated(new Date(99999999L));
            post.setUpdated(new Date(9999999L));
            post.setWriter(writer);
    }
    @Test
    public void shouldSuccessSavePost() {
        try {
            Mockito.when(postRepository.save(post)).then(new Answer<Post>() {
                int sequence = 1;
                @Override
                public Post answer(InvocationOnMock invocationOnMock) throws Throwable {
                    Post post = invocationOnMock.getArgument(0);
                    post.setId(sequence++);
                    return post;
                }
            });
            postService.save(post);
            Mockito.verify(postRepository).save(post);
            Assertions.assertNotNull(post.getId());
        } catch (EmptyBodyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldFailSavePost() {
        try {
            Mockito.when(postRepository.save(post)).then((Answer<Post>) invocationOnMock -> null);
            postService.save(null);
            Mockito.verify(postRepository).save(post);
            Assertions.assertThrows(EmptyBodyException.class, () -> new EmptyBodyException(EmptyBodyException.DEFAULT_MESSAGE_TEXT));
        } catch (EmptyBodyException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void shouldSuccessUpdatePost() {
        try {
            post = new Post(0, "BEGIN", new Date(22222L), new Date(3333L));
            Mockito.when(postRepository.update(post)).then(new Answer<Post>() {
                int sequence = 1;
                @Override
                public Post answer(InvocationOnMock invocationOnMock) throws Throwable {
                    Post post = invocationOnMock.getArgument(0, Post.class);
                    post.setId(sequence);
                    post.setContent("changed content###");
                    return post;
                }
            });
            postService.update(post);
            Mockito.verify(postRepository).update(post);
            assertEquals("changed content###", postService.update(post).getContent());
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
        } catch (EmptyBodyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testShouldFailUpdatePost() {
        try {
            post = new Post(0, "BEGIN", new Date(22222L), new Date(3333L));
            Mockito.when(postRepository.update(post)).then((Answer<Post>) invocationOnMock -> null);
            postService.update(post);
            Mockito.verify(postRepository).update(post);
            Assertions.assertThrows(NoSuchEntryException.class, () -> new NoSuchEntryException(NoSuchEntryException.DEFAULT_MESSAGE_TEXT));
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
        } catch (EmptyBodyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testShouldFailPostsReturn() {
        try {
            Mockito.when(postRepository.getAll()).then((Answer<List<Post>>) invocationOnMock -> null);
            postService.getAll();
            Mockito.verify(postRepository).getAll();
            Assertions.assertThrows(EmptyListException.class, () -> new EmptyListException(EmptyListException.DEFAULT_MESSAGE_TEXT));
        } catch (EmptyListException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testShouldSuccessPostsReturn() {
        try {
            Mockito.when(postRepository.getAll()).then((Answer<List<Post>>) invocationOnMock -> new ArrayList<>());
            List<Post> posts = postService.getAll();
            Mockito.verify(postRepository).getAll();
            assertNotNull(posts);
        } catch (EmptyListException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setShouldSuccessFindPostById() {
        try {
            Mockito.when(postRepository.findById(1)).then((Answer<Post>) invocationOnMock -> new Post(1, null, null, null));
            Post post = postService.findById(1);
            Mockito.verify(postRepository).findById(1);
            assertNotNull(post);
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setShouldFailFindPostById() {
        try {
            Mockito.when(postRepository.findById(1)).then((Answer<Post>) invocationOnMock -> null);
            Post post = postService.findById(1);
            Mockito.verify(postRepository).findById(1);
            Assertions.assertThrows(NoSuchEntryException.class, () -> new NoSuchEntryException(NoSuchEntryException.DEFAULT_MESSAGE_TEXT));
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
        }
    }
}
