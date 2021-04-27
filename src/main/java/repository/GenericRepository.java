package repository;

import exceptions.EmptyListException;
import exceptions.NoSuchEntryException;

import java.io.IOException;
import java.util.List;

public interface GenericRepository<T, ID> {
    T save(T t) throws IOException;
    T update(T t) throws NoSuchEntryException, EmptyListException, IOException;
    boolean delete(ID id) throws IOException, EmptyListException;
    T findById(ID id) throws NoSuchEntryException, EmptyListException, IOException;
    List<T> getAll() throws IOException, EmptyListException;
}
