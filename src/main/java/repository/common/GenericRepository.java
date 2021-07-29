package repository.common;

import exceptions.EmptyListException;
import exceptions.NoSuchEntryException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

//TODO: REMOVE EXCEPTIONS
public interface GenericRepository<T, ID> {
    T save(T t);
    T update(T t);
    boolean delete(ID id);
    T findById(ID id);
    List<T> getAll();
}
