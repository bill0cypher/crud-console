package bootstrap;

import enums.Categories;
import enums.Operations;
import exceptions.NoSuchCategory;
import model.Post;
import model.Region;
import model.Writer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Objects;


public class Bootstrap {
    public static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    public static void init() {
        String option;
        System.out.println("What do you want to work with?" +
                "A. Posts" +
                "B. Writers" +
                "C. Finish");
        try {
            option = bufferedReader.readLine();
            do {
                if (Categories.valueOf(option) == Categories.WRITERS)
                    workWithWriters();
                else if (Categories.valueOf(option) == Categories.POSTS)
                    workWithPosts();
                else if (Categories.valueOf(option) == Categories.FINISH)
                    System.exit(200);
                else
                    throw new NoSuchCategory(String.format(NoSuchCategory.DEFAULT_MESSAGE_TEXT, option));
            } while (Categories.valueOf(option) != Categories.FINISH);

        } catch (NoSuchCategory noSuchCategory) {
            noSuchCategory.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void workWithWriters() {
        String option = "";
        System.out.println("Choose an option: " +
                "A. Create writer" +
                "B. Update writer" +
                "C. Delete writer" +
                "D. Get all writers" +
                "E. Get writer by ID");
    }

    public static void workWithPosts() {
        String option;
        System.out.println("Choose an option: " +
                "A. Create post" +
                "B. Update post" +
                "C. Delete post" +
                "D. Get all post" +
                "E. Get post by ID");
        try {
            option = bufferedReader.readLine();
            if (Operations.valueOf(option) == Operations.SAVE)
            {
                Post post = createPost(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Post createPost(boolean fromWriter) throws IOException {
        System.out.println("Post creation...");
        Post post = new Post();
        System.out.println("Write post content: ");
        post.setContent(bufferedReader.readLine());
        post.setCreated(new Date().getTime());
        post.setUpdated(new Date().getTime());
        if (!fromWriter) {
            System.out.println("Written by: ");
            createWriter(true);
        }

        return null;
    }

    private static void createWriter(boolean fromPost) throws IOException {
        System.out.println("Writer creation...");
        Writer writer = new Writer();
        System.out.println("Author's last name: ");
        writer.setLastName(bufferedReader.readLine());
        System.out.println("Living place: ");
        createRegion();
        if (!fromPost) {
            System.out.println("Create a post: ");
            createPost(true);
        }
    }

    private static void createRegion() throws IOException {
        System.out.println("Living place creation: ");
        Region region = new Region();
        System.out.println("City: ");
        region.setName(bufferedReader.readLine());
    }
}
