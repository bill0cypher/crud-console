package repository.database;

import bootstrap.JDBCConnector;
import model.Post;
import model.Writer;
import repository.common.PostRepository;
import util.SQLOperations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PostRepositoryImplDB implements PostRepository, Mappable<Post> {

    public PostRepositoryImplDB() {
    }

    @Override
    public Post save(Post post) {
        try {
            PreparedStatement statement = JDBCConnector.createPreparedStatement(
                    SQLOperations.SQLTemplateGenerator.generateInsertStatement("post", new String[]{"content", "created", "updated", "writer_id"}));
            statement.setString(1, post.getContent());
            statement.setDate(2, post.getCreated());
            statement.setDate(3, post.getUpdated());
            statement.setInt(4, post.getWriter().getId());
            post.setId(SQLOperations.executeCreateAndgetId(statement));
            return post;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Post update(Post post) {
        try {
            Post existing = findById(post.getId());
            if (!Objects.isNull(existing)) {
                existing.setContent(post.getContent());
                existing.setUpdated(post.getUpdated());
                existing.setCreated(post.getCreated());
                PreparedStatement statement = JDBCConnector.createPreparedStatement(
                        SQLOperations.SQLTemplateGenerator.generateUpdateStatement("post", new String[]{
                                "content",
                                "created",
                                "updated"
                        }));
                statement.setString(1, post.getContent());
                statement.setDate(2, post.getCreated());
                statement.setDate(3, post.getUpdated());
                return statement.executeUpdate() >=1 ? existing : null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Integer integer) {
        try {
            PreparedStatement statement = JDBCConnector.createPreparedStatement(
                    SQLOperations.SQLTemplateGenerator.generateDeleteByCriteriaStatement("post", "id"));
            statement.setInt(1, integer);
            return statement.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Post findById(Integer integer) {
        try {
            PreparedStatement preparedStatement = JDBCConnector.createPreparedStatement(
                    SQLOperations.SQLTemplateGenerator.findByOneCriteria("post", "id"));
            preparedStatement.setInt(1, integer);
            return castFromResultSet(preparedStatement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Post> getAll() {
        try {
            PreparedStatement preparedStatement = JDBCConnector.createPreparedStatement("select * from post");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Post> posts = new ArrayList<>();
            while (resultSet.next()) {
                posts.add(castFromResultSet(resultSet));
            }
            return posts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Post> findByWriter(Writer writer) {
        try {
            PreparedStatement preparedStatement = JDBCConnector.createPreparedStatement("select * from post where writer_id=?");
            preparedStatement.setInt(1, writer.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Post> posts = new ArrayList<>();
            while (resultSet.next()) {
                posts.add(castFromResultSet(resultSet));
            }
            return posts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean batchSave(List<Post> posts) {
        try {
            PreparedStatement statement = JDBCConnector.createPreparedStatement(
                    SQLOperations.SQLTemplateGenerator.generateInsertStatement("post", new String[]{
                            "content", "created", "updated", "writer_id"
                    }));
            for (Post post : posts) {
                statement.setString(1, post.getContent());
                statement.setDate(2, post.getCreated());
                statement.setDate(3, post.getUpdated());
                statement.setInt(4, post.getWriter().getId());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean batchDelete(List<Post> posts) {
        try {
            PreparedStatement statement = JDBCConnector.createPreparedStatement(
                    SQLOperations.SQLTemplateGenerator.generateDeleteByCriteriaStatement("post", "writer_id"));
            for (Post post : posts) {
                statement.setInt(1, post.getWriter().getId());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public Post castFromResultSet(ResultSet resultSet) {
        try {
            Post post = new Post();
            while (resultSet.next()) {
                post.setId(resultSet.getInt( "id"));
                post.setContent(resultSet.getString("content"));
                post.setCreated(resultSet.getDate("created"));
                post.setUpdated(resultSet.getDate("updated"));
                post.setWriter(new Writer(resultSet.getInt("writer_id")));
            }
            return post;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
