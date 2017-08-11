package practice.repository;

import org.springframework.data.repository.CrudRepository;
import practice.model.VisitEntity;

/**
 * Created by ehunzeker on 5/15/17.
 */
public interface VisitRepository extends CrudRepository<VisitEntity, Integer>{
}
