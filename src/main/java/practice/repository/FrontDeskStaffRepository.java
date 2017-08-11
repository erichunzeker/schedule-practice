package practice.repository;

import org.springframework.data.repository.CrudRepository;
import practice.model.FrontDeskStaffEntity;

/**
 * Created by ehunzeker on 5/15/17.
 */
public interface FrontDeskStaffRepository extends CrudRepository<FrontDeskStaffEntity, Integer>
{

}
