package controller;

import exceptions.EmptyListException;
import exceptions.NoSuchEntryException;
import model.Writer;
import service.WriterService;

import java.io.IOException;
import java.util.List;

public class WriterController {

    private final WriterService writerService;

    public WriterController(WriterService writerService) {
        this.writerService = writerService;
    }

    public List<Writer> getAllWriters() {
        try {
            return writerService.getAll();
        } catch (EmptyListException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Writer getWriter(Integer id) {
        try {
            return writerService.findById(id);
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
        } catch (EmptyListException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteWriter(Integer id) {
        try {
            return writerService.delete(id);
        } catch (EmptyListException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean saveWriter(Writer writer) {
        try {
            return writerService.save(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateWriter(Writer writer) {
        try {
            return writerService.update(writer);
        } catch (EmptyListException e) {
            e.printStackTrace();
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
