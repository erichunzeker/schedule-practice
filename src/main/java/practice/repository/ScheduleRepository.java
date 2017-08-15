package practice.repository;

import org.springframework.data.repository.CrudRepository;
import practice.model.ScheduleEntity;

/**
 * Created by ehunzeker on 5/15/17.
 */
public interface ScheduleRepository extends CrudRepository<ScheduleEntity, Integer> {

}
