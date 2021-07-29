package repository.database;

import java.sql.ResultSet;

public interface Mappable<T> {
    T castFromResultSet(ResultSet resultSet);
}
