package repository.common;

import model.Post;
import model.Writer;

import java.util.List;

public interface PostRepository extends GenericRepository<Post, Integer> {
    List<Post> findByWriter(Writer writer);
}
