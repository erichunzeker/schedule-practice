package practice.repository;

import org.springframework.data.repository.CrudRepository;

import practice.model.ContactEntity;

/**
 * Created by ehunzeker on 5/15/17.
 */
public interface ContactRepository extends CrudRepository<ContactEntity, Integer>
{

}
