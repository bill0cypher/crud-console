package controller;

import exceptions.EmptyBodyException;
import exceptions.EmptyListException;
import exceptions.NoSuchEntryException;
import model.Writer;
import service.WriterService;

import java.io.IOException;
import java.util.Collections;
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
        return Collections.emptyList();
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

    public Writer saveWriter(Writer writer) {
        try {
            return writerService.save(writer);
        } catch (EmptyBodyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Writer updateWriter(Writer writer) {
        try {
            return writerService.update(writer);
        } catch (EmptyListException e) {
            e.printStackTrace();
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
