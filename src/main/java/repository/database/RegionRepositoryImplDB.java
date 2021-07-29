package repository.database;

import bootstrap.JDBCConnector;
import model.Region;
import repository.common.RegionRepository;
import util.SQLOperations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegionRepositoryImplDB implements RegionRepository, Mappable<Region> {
    @Override
    public Region save(Region region) {
        try {
            PreparedStatement statement = JDBCConnector.createPreparedStatement("insert into region (name) values (?)");
            statement.setString(1, region.getName());
            region.setId(SQLOperations.executeCreateAndgetId(statement));
            return region;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Region update(Region region) {
        try {
            PreparedStatement statement = JDBCConnector.createPreparedStatement("update region set name=? where id=?");
            statement.setString(1, region.getName());
            statement.setInt(2, region.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Integer aLong) {
        try {
            PreparedStatement statement = JDBCConnector.createPreparedStatement("DELETE from region where id=?");;
            statement.setLong(1, aLong);
            return statement.executeUpdate() == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public Region findById(Integer aLong) {
        try {
            PreparedStatement statement = JDBCConnector.createPreparedStatement(SQLOperations.SQLTemplateGenerator.findByOneCriteria("region", "id"));
            statement.setLong(1, aLong);
            return castFromResultSet(statement.executeQuery());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Region> getAll() {
        try {
            PreparedStatement statement = JDBCConnector.createPreparedStatement("select * from region;");
            ResultSet resultSet = statement.executeQuery();
            List<Region> regions = new ArrayList<>();
            while (resultSet.next()) {
                regions.add(resultSet.unwrap(Region.class));
            }
            return regions;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Region castFromResultSet(ResultSet resultSet) {
        try {
            Region region = new Region();
            while (resultSet.next()) {
                    region.setId(resultSet.getInt( "id"));
                    region.setName(resultSet.getString("name"));
            }
            return region;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
