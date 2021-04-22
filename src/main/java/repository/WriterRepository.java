package repository;

import model.Region;
import model.Writer;

import java.util.List;

public interface WriterRepository extends GenericRepository<Writer, Integer> {
    List<Writer> findByRegion(Region region);
}
