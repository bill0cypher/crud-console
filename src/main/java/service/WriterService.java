package service;

import exceptions.EmptyBodyException;
import exceptions.EmptyListException;
import exceptions.NoSuchEntryException;
import model.Writer;
import repository.common.GenericRepository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class WriterService {
    private final GenericRepository<Writer, Integer> writerRepository;
    public WriterService(GenericRepository<Writer, Integer> repository) {
        this.writerRepository = repository;
    }

    public Writer save(Writer writer) throws EmptyBodyException {
        if (Objects.isNull(writer))
            throw new EmptyBodyException(EmptyBodyException.DEFAULT_MESSAGE_TEXT);
        return writerRepository.save(writer);
    }

    public Writer update(Writer writer) throws IOException, EmptyListException, NoSuchEntryException {
        return writerRepository.update(writer);
    }

    public boolean delete(Integer id) throws IOException, EmptyListException {
        return writerRepository.delete(id);
    }

    public Writer findById(Integer id) throws NoSuchEntryException, IOException, EmptyListException {
        return writerRepository.findById(id);
    }

    public List<Writer> getAll() throws IOException, EmptyListException {
        return writerRepository.getAll();
    }
}
