package repository.database;

import bootstrap.JDBCConnector;
import model.Post;
import model.Region;
import model.Writer;
import repository.common.WriterRepository;
import util.SQLOperations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class WriterRepositoryImplDB implements WriterRepository, Mappable<Writer> {

    private final RegionRepositoryImplDB regionRepositoryImplDB;
    private final PostRepositoryImplDB postsRepositoryImplDB;

    public WriterRepositoryImplDB() {
        this.regionRepositoryImplDB = new RegionRepositoryImplDB();
        this.postsRepositoryImplDB = new PostRepositoryImplDB();
    }

    @Override
    public Writer save(Writer writer) {
        try {
            this.regionRepositoryImplDB.save(writer.getRegion());
            PreparedStatement statement = JDBCConnector.createPreparedStatement(
                    SQLOperations.SQLTemplateGenerator.generateInsertStatement("writer", new String[]{"lastName", "region_id"}));
            statement.setString(1, writer.getLastName());
            statement.setLong(2, writer.getRegion().getId());
            writer.setId(SQLOperations.executeCreateAndgetId(statement));
            if (!Objects.isNull(writer.getPosts()) && !writer.getPosts().isEmpty())
                this.postsRepositoryImplDB.batchSave(writer.getPosts());
            return writer;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Writer update(Writer writer) {
        Writer existing = findById(writer.getId());
        List<String> params = new ArrayList<>();
        if (!Objects.isNull(existing)) {
            Optional.of(Objects.isNull(writer.getLastName())).ifPresent(aBoolean -> {
                existing.setLastName(writer.getLastName());
                params.add("lastName");
            });
            Optional.of(Objects.isNull(writer.getRegion())).ifPresent(aBoolean -> {
                existing.setRegion(writer.getRegion());
                params.add("region_id");
            });
            Optional.of(Objects.isNull(writer.getPosts())).ifPresent(aBoolean -> {
                existing.setPosts(writer.getPosts());
                postsRepositoryImplDB.batchSave(writer.getPosts());
            });
            try {
                PreparedStatement statement = JDBCConnector.createPreparedStatement(
                        SQLOperations.SQLTemplateGenerator.generateUpdateStatement("writer", params.toArray(String[]::new)));
                for (int i = 0; i < params.size(); i++) {
                    switch (params.get(i)) {
                        case "lastName" -> statement.setString(i + 1, writer.getLastName());
                        case "region_id" -> statement.setInt(i + 1, writer.getRegion().getId());
                    }
                }
                return statement.executeUpdate() > 1 ? existing : null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean delete(Integer integer) {
        try {
            Writer writer = findById(integer);
            if (!Objects.isNull(writer)) {
                Optional.of(writer.getPosts().isEmpty()).ifPresent(aBoolean -> this.postsRepositoryImplDB.batchDelete(writer.getPosts()));
                PreparedStatement statement = JDBCConnector.createPreparedStatement(
                        SQLOperations.SQLTemplateGenerator.generateDeleteByCriteriaStatement("writer", "id"));
                statement.setInt(1, integer);
                return statement.executeUpdate() >= 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Writer findById(Integer integer)
    {
        try {
            PreparedStatement preparedStatement = JDBCConnector.createPreparedStatement(
                    SQLOperations.SQLTemplateGenerator.findByOneCriteria("writer", "id"));
            preparedStatement.setInt(1, integer);
            Writer writer = castFromResultSet(preparedStatement.executeQuery());
            List<Post> posts = this.postsRepositoryImplDB.findByWriter(writer);
            writer.setPosts(posts);
            return writer;
        } catch (SQLException e) {
            e.printStackTrace();;
        }
        return null;
    }

    @Override
    public List<Writer> getAll() {
        try {
            PreparedStatement statement = JDBCConnector.createPreparedStatement("select * from writer");
            ResultSet resultSet = statement.executeQuery();
            List<Writer> writers = new ArrayList<>();
            while (resultSet.next()) {
                writers.add(castFromResultSet(resultSet));
            }
            return writers;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Writer> findByRegion(Region region) {
        try {
            PreparedStatement statement = JDBCConnector.createPreparedStatement(
                    SQLOperations.SQLTemplateGenerator.findByOneCriteria("writer", "region_id"));
            statement.setInt(1, region.getId());
            ResultSet resultSet = statement.executeQuery();
            List<Writer> writers = new ArrayList<>();
            while (resultSet.next()) {
                writers.add(resultSet.unwrap(Writer.class));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Writer castFromResultSet(ResultSet resultSet) {
        try {
            Writer writer = new Writer();
            while (resultSet.next()) {
                writer.setId(resultSet.getInt( "id"));
                writer.setLastName(resultSet.getString("lastName"));
                writer.setRegion(this.regionRepositoryImplDB.findById(resultSet.getInt("region_id")));
            }
            writer.setPosts(Collections.emptyList());
            return writer;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
