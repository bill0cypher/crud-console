package views;

import controller.WriterController;
import enums.Operations;
import exceptions.NoSuchOperation;
import model.Writer;
import repository.hibernate.WriterRepositoryImpl;
import service.WriterService;

import java.util.Objects;

public class WritersView {
    private final WriterController writerController;
    private Writer writer;
    private Integer writerId;

    public WritersView() {
        this.writerController = new WriterController(new WriterService(new WriterRepositoryImpl()));
    }

    public void executeOperation(Operations option) throws NoSuchOperation {
        switch (option) {
            case READ:
                System.out.println(Objects.requireNonNull(writerController.getWriter(writerId)).toString());
                break;
            case CREATE:
                System.out.println(Objects.requireNonNull(writerController.saveWriter(writer)).toString());
                break;
            case DELETE:
                writerController.deleteWriter(writerId);
                break;
            case UPDATE:
                System.out.println(Objects.requireNonNull(writerController.updateWriter(writer)).toString());
                break;
            case READ_ALL:
                writerController.getAllWriters().forEach(System.out::println);
            default:
                throw new NoSuchOperation(NoSuchOperation.DEFAULT_EXCEPTION_MESSAGE);
        }
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    public Integer getWriterId() {
        return writerId;
    }

    public void setWriterId(Integer writerId) {
        this.writerId = writerId;
    }
}
