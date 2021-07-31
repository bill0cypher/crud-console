import exceptions.EmptyBodyException;
import exceptions.EmptyListException;
import exceptions.NoSuchEntryException;
import model.Post;
import model.Region;
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
import repository.hibernate.WriterRepositoryImpl;
import service.PostService;
import service.WriterService;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.Silent.class)
public class WriterORMCRUDTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private WriterRepositoryImpl writerRepository;
    @InjectMocks
    private WriterService writerService;
    @Mock
    private Writer writer;
    @Mock
    private List<Post> posts;

    @BeforeAll
    public void createWriter() {
        Post post = Mockito.mock(Post.class);
        post.setContent("Post content created");
        post.setCreated(new Date(99999999L));
        post.setUpdated(new Date(9999999L));
        Region region = Mockito.mock(Region.class);
        writer.setRegion(region);
        writer.setPosts(List.of(post));
        writer.setLastName("Karkunov");
    }
    @Test
    public void shouldSuccessSaveWriter() {
        try {
            Mockito.when(writerRepository.save(writer)).then(new Answer<Writer>() {
                int sequence = 1;
                @Override
                public Writer answer(InvocationOnMock invocationOnMock) throws Throwable {
                    Writer wr = invocationOnMock.getArgument(0);
                    wr.setId(sequence++);
                    return wr;
                }
            });
            writerService.save(writer);
            Mockito.verify(writerRepository).save(writer);
            Assertions.assertNotNull(writer.getId());
        } catch (EmptyBodyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldFailSaveWriter() {
        try {
            Mockito.when(writerRepository.save(writer)).then((Answer<Writer>) invocationOnMock -> null);
            postService.save(null);
            Mockito.verify(writerRepository).save(writer);
            Assertions.assertThrows(EmptyBodyException.class, () -> new EmptyBodyException(EmptyBodyException.DEFAULT_MESSAGE_TEXT));
        } catch (EmptyBodyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldSuccessUpdateWriter() {
        try {
            writer = new Writer("BORODOV", new Region(0, "Kharkiv"), Collections.emptyList());
            Mockito.when(writerRepository.update(writer)).then((Answer<Writer>) invocationOnMock -> {
                Writer writer = invocationOnMock.getArgument(0, Writer.class);
                writer.setLastName("NE BORODOV");
                writer.setRegion(new Region(0, "Vologda"));
                return writer;
            });
            writerService.update(writer);
            Mockito.verify(writerRepository).update(writer);
            assertEquals("Vologda", writerService.update(writer).getRegion().getName());
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
        } catch (EmptyBodyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testShouldFailUpdateWriter() {
        try {
            writer = new Writer("BORODOV", new Region(0, "Kharkiv"), Collections.emptyList());
            Mockito.when(writerRepository.update(writer)).then((Answer<Writer>) invocationOnMock -> null);
            writerService.update(writer);
            Mockito.verify(writerRepository).update(writer);
            Assertions.assertThrows(NoSuchEntryException.class, () -> new NoSuchEntryException(NoSuchEntryException.DEFAULT_MESSAGE_TEXT));
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
        } catch (EmptyBodyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testShouldFailWritersReturn() {
        try {
            Mockito.when(writerRepository.getAll()).then((Answer<List<Writer>>) invocationOnMock -> null);
            writerService.getAll();
            Mockito.verify(writerRepository).getAll();
            Assertions.assertThrows(EmptyListException.class, () -> new EmptyListException(EmptyListException.DEFAULT_MESSAGE_TEXT));
        } catch (EmptyListException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testShouldSuccessPostsReturn() {
        try {
            Mockito.when(writerRepository.getAll()).then((Answer<List<Writer>>) invocationOnMock -> new ArrayList<>());
            List<Writer> posts = writerService.getAll();
            Mockito.verify(writerRepository).getAll();
            assertNotNull(posts);
        } catch (EmptyListException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void setShouldSuccessFindPostById() {
        try {
            Mockito.when(writerRepository.findById(1)).then((Answer<Writer>) invocationOnMock -> new Writer(1));
            Writer writer = writerService.findById(1);
            Mockito.verify(writerRepository).findById(1);
            assertNotNull(writer);
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setShouldFailFindPostById() {
        try {
            Mockito.when(writerRepository.findById(1)).then((Answer<Post>) invocationOnMock -> null);
            Writer writer = writerService.findById(1);
            Mockito.verify(writerRepository).findById(1);
            Assertions.assertThrows(NoSuchEntryException.class, () -> new NoSuchEntryException(NoSuchEntryException.DEFAULT_MESSAGE_TEXT));
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
        }
    }
}
