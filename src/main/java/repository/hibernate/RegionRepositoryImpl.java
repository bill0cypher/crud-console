package repository.hibernate;

import bootstrap.JDBCConnector;
import model.Region;
import repository.common.RegionRepository;
import repository.database.Mappable;
import util.SQLOperations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegionRepositoryImpl implements RegionRepository {
    @Override
    public Region save(Region region) {
        return null;
    }

    @Override
    public Region update(Region region) {
        return null;
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }

    @Override
    public Region findById(Integer integer) {
        return null;
    }

    @Override
    public List<Region> getAll() {
        return null;
    }
}
