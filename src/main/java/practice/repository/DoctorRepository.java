package practice.repository;

import org.springframework.data.repository.CrudRepository;
import practice.model.DoctorEntity;

/**
 * Created by ehunzeker on 5/15/17.
 */
public interface DoctorRepository extends CrudRepository<DoctorEntity, Integer> {

}
