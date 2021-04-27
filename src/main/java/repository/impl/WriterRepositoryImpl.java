package repository.impl;

import exceptions.EmptyListException;
import exceptions.NoSuchEntryException;
import model.Region;
import model.Writer;
import repository.GenericRepoImpl;
import repository.WriterRepository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class WriterRepositoryImpl extends GenericRepoImpl<Writer, Integer> implements WriterRepository {

    public WriterRepositoryImpl() {
        super("writer.json", Writer.class, Writer[].class, Integer.class);
    }

    @Override
    public Writer save(Writer writer) throws IOException {
        return writeToFile(writer);
    }

    @Override
    public Writer update(Writer writer) throws NoSuchEntryException, EmptyListException, IOException {
        return updateFile(writer);
    }

    @Override
    public boolean delete(Integer id) throws EmptyListException, IOException {
        return deleteFromFile(id);
    }

    @Override
    public Writer findById(Integer id) throws NoSuchEntryException, EmptyListException, IOException {
        List<Writer> writers = readFromFile();
        return Optional.of(id > writers.size()).map(aBoolean -> !aBoolean ? writers.get(id) : null).orElseThrow(() -> new NoSuchEntryException(String.format(NoSuchEntryException.DEFAULT_MESSAGE_TEXT, id)));
    }

    @Override
    public List<Writer> getAll() throws IOException, EmptyListException {
        return readFromFile();
    }

    @Override
    public List<Writer> findByRegion(Region region) {
        return null;
    }
}
