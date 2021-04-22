package bootstrap;

import enums.Categories;
import enums.Operations;
import exceptions.NoSuchCategory;
import exceptions.NoSuchOperation;
import model.Post;
import model.Region;
import model.Writer;
import views.PostsView;
import views.WritersView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Date;


public class Bootstrap {
    public static BufferedReader bufferedReader;

    public static void init(InputStream inputStream) {
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String option;
        System.out.println("What do you want to work with? \n" +
                "POSTS \n" +
                "WRITERS \n" +
                "FINISH \n");
        try {
            option = bufferedReader.readLine();
            if (Categories.valueOf(option) == Categories.WRITERS)
                workWithWriters();
            else if (Categories.valueOf(option) == Categories.POSTS)
                workWithPosts();
            else if (Categories.valueOf(option) == Categories.FINISH)
                System.exit(200);
            else
                throw new NoSuchCategory(String.format(NoSuchCategory.DEFAULT_MESSAGE_TEXT, option));

        } catch (NoSuchCategory noSuchCategory) {
            noSuchCategory.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void workWithWriters() {
        String option;
        System.out.println("Choose an option: \n" +
                " CREATE writer\n" +
                " UPDATE writer\n" +
                " DELETE writer\n" +
                " READ_ALL writer\n" +
                " READ writer");
        try {
            WritersView view = new WritersView();
            option = bufferedReader.readLine();
            String operation = null;
            if (Operations.valueOf(option) == Operations.CREATE) {
                Writer writer = createWriter(false);
                view.setWriter(writer);
                operation = Operations.CREATE.toString();
            } else if (Operations.valueOf(option) == Operations.UPDATE) {
                Writer writer = updateWriter();
                view.setWriter(writer);
                operation = Operations.UPDATE.toString();
            } else if (Operations.valueOf(option) == Operations.DELETE) {
                view.setWriterId(getEntityId());
                operation = Operations.DELETE.toString();
            } else if (Operations.valueOf(option) == Operations.READ) {
                view.setWriterId(getEntityId());
                operation = Operations.READ.toString();
            } else if (Operations.valueOf(option) == Operations.READ_ALL) {
                operation = Operations.READ_ALL.toString();
            } else
                throw new NoSuchOperation(NoSuchOperation.DEFAULT_EXCEPTION_MESSAGE);
            view.executeOperation(Operations.valueOf(operation));
        } catch (NoSuchOperation noSuchOperation) {
            noSuchOperation.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void workWithPosts() {
        String option;
        System.out.println("Choose an option: \n" +
                " CREATE post\n" +
                " UPDATE post\n" +
                " DELETE post\n" +
                " READ_ALL post\n" +
                " READ post");
        try {
            PostsView view = new PostsView();
            option = bufferedReader.readLine();
            String operation = null;
            if (Operations.valueOf(option) == Operations.CREATE) {
                Post post = createPost(false);
                view.setPost(post);
                operation = Operations.CREATE.toString();
            } else if (Operations.valueOf(option) == Operations.UPDATE) {
                Post post = updatePost();
                view.setPost(post);
                operation = Operations.UPDATE.toString();
            } else if (Operations.valueOf(option) == Operations.DELETE) {
                view.setPostId(getEntityId());
                operation = Operations.DELETE.toString();
            } else if (Operations.valueOf(option) == Operations.READ) {
                view.setPostId(getEntityId());
                operation = Operations.READ.toString();
            } else if (Operations.valueOf(option) == Operations.READ_ALL) {
                operation = Operations.READ_ALL.toString();
            } else
                throw new NoSuchOperation(NoSuchOperation.DEFAULT_EXCEPTION_MESSAGE);
            view.executeOperation(Operations.valueOf(operation));
        } catch (NoSuchOperation noSuchOperation) {
            noSuchOperation.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Post createPost(boolean fromWriter) throws IOException {
        return fillUpPost(fromWriter);
    }

    private static Writer createWriter(boolean fromPost) throws IOException {
        System.out.println("Writer creation...");
        return fillUpWriter(fromPost);
    }

    private static Region createRegion() throws IOException {
        System.out.println("Living place creation: ");
        Region region = new Region();
        System.out.println("City: ");
        region.setName(bufferedReader.readLine());
        return region;
    }

    private static Writer updateWriter() throws IOException {
        System.out.println("Writer update...");
        System.out.println("Enter id of entity to be updated");
        Integer id = Integer.parseInt(bufferedReader.readLine());
        Writer writer = fillUpWriter(false);
        writer.setId(id);
        return writer;
    }

    private static Post updatePost() throws IOException {
        System.out.println("Post update...");
        System.out.println("Enter id of entity to be updated");
        Integer id = Integer.parseInt(bufferedReader.readLine());
        Post post = fillUpPost(false);
        post.setId(id);
        return post;
    }

    private static Integer getEntityId() throws IOException {
        System.out.println("Enter entity id to be deleted");
        return Integer.parseInt(bufferedReader.readLine());
    }

    private static Writer fillUpWriter(boolean fromPost) throws IOException {
        Writer writer = new Writer();
        System.out.println("Author's last name: ");
        writer.setLastName(bufferedReader.readLine());
        System.out.println("Living place: ");
        writer.setRegion(createRegion());
        if (!fromPost) {
            System.out.println("Create a post: ");
            writer.setPosts(Collections.singletonList(createPost(true)));
        }
        return writer;
    }

    private static Post fillUpPost(boolean fromWriter) throws IOException {
        Post post = new Post();
        System.out.println("Write post content: ");
        post.setContent(bufferedReader.readLine());
        post.setCreated(new Date().getTime());
        post.setUpdated(new Date().getTime());
        if (!fromWriter) {
            System.out.println("Written by: ");
            post.setWriter(createWriter(true));
        }
        return post;
    }
}
