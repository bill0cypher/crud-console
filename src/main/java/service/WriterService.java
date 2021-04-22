package service;

import exceptions.EmptyListException;
import exceptions.NoSuchEntryException;
import model.Writer;
import repository.impl.WriterRepositoryImpl;
import java.io.IOException;
import java.util.List;

public class WriterService {
    private final WriterRepositoryImpl writerRepository;
    public WriterService(WriterRepositoryImpl repository) {
        this.writerRepository = repository;
    }

    public boolean save(Writer writer) throws IOException {
        return writerRepository.save(writer);
    }

    public boolean update(Writer writer) throws IOException, EmptyListException, NoSuchEntryException {
        return writerRepository.update(writer);
    }

    public boolean delete(Integer id) throws IOException, EmptyListException {
        return writerRepository.delete(id);
    }

    public Writer findById(Integer id) throws NoSuchEntryException, IOException, EmptyListException {
        Writer writer =  writerRepository.findById(id);
        if (writer == null)
            throw new NoSuchEntryException(String.format(NoSuchEntryException.DEFAULT_MESSAGE_TEXT, id));
        return writer;
    }

    public List<Writer> getAll() throws IOException, EmptyListException {
        return writerRepository.getAll();
    }
}
