package service;

import exceptions.EmptyBodyException;
import exceptions.EmptyListException;
import exceptions.NoSuchEntryException;
import model.Writer;
import repository.common.GenericRepository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public Writer update(Writer writer) throws EmptyBodyException, NoSuchEntryException {
        if (writer == null) throw new EmptyBodyException(EmptyBodyException.DEFAULT_MESSAGE_TEXT);
        return Optional.ofNullable(writerRepository.update(writer)).orElseThrow(() -> new NoSuchEntryException(NoSuchEntryException.DEFAULT_MESSAGE_TEXT));
    }

    public boolean delete(Integer id) throws NoSuchEntryException {
        return Optional.of(writerRepository.delete(id)).orElseThrow(() -> new NoSuchEntryException(NoSuchEntryException.DEFAULT_MESSAGE_TEXT));
    }

    public Writer findById(Integer id) throws NoSuchEntryException {
        return Optional.ofNullable(writerRepository.findById(id)).orElseThrow(() -> new NoSuchEntryException(NoSuchEntryException.DEFAULT_MESSAGE_TEXT));
    }

    public List<Writer> getAll() throws  EmptyListException {
        return Optional.ofNullable(writerRepository.getAll()).orElseThrow(() -> new EmptyListException(EmptyListException.DEFAULT_MESSAGE_TEXT));
    }
}
