package practice.repository;

import org.springframework.data.repository.CrudRepository;
import practice.model.PatientEntity;

/**
 * Created by ehunzeker on 5/15/17.
 */
public interface PatientRepository extends CrudRepository<PatientEntity, Integer>
{

}
