package views;

import controller.WriterController;
import enums.Operations;
import exceptions.NoSuchOperation;
import model.Writer;
import repository.impl.WriterRepositoryImpl;
import service.WriterService;

public class WritersView {
    private final WriterController writerController;
    private final Writer writer;

    public WritersView(Writer writer) {
        this.writerController = new WriterController(new WriterService(new WriterRepositoryImpl()));
        this.writer = writer;
    }

    public void executeOperation(Operations option) throws NoSuchOperation {
        switch (option) {
            case READ:
                this.writerController.getWriter(writer.getId());
                break;
            case SAVE:
                this.writerController.saveWriter(writer);
                break;
            case DELETE:
                this.writerController.deleteWriter(writer.getId());
                break;
            case UPDATE:
                this.writerController.updateWriter(writer);
                break;
            default:
                throw new NoSuchOperation(NoSuchOperation.DEFAULT_EXCEPTION_MESSAGE);
        }
    }
}
