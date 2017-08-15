package practice.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import practice.model.ContactEntity;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Rollback
@Transactional
public class ContactRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void testContactTable() {
        String selectQuery = "SELECT *\n" +
                "FROM contact\n" +
                "LEFT JOIN patient ON contact.pat_id = patient.pat_id";
        List resultSet = jdbcTemplate.queryForList(selectQuery);

        System.out.println(resultSet);
    }


    @Test
    public void saveOneNewContactTest() {
        ContactEntity c = new ContactEntity();
        c.setContactName("NewContact10");
        c.setPatientByPatId(patientRepository.findOne(5));
        contactRepository.save(c);

        assertNotNull(contactRepository.exists(10));
    }


    @Test
    public void saveListOfContactsTest() {
        ArrayList<ContactEntity> cLists = new ArrayList<>();
        ContactEntity e1 = new ContactEntity();
        ContactEntity e2 = new ContactEntity();
        ContactEntity e3 = new ContactEntity();
        e1.setContactId(10);
        e1.setContactName("NewContact10");
        e2.setContactId(11);
        e2.setContactName("NewContact11");
        e3.setContactId(12);
        e3.setContactName("NewContact12");

        cLists.add(e1);
        cLists.add(e2);
        cLists.add(e3);

        contactRepository.save(cLists);

        assertTrue(contactRepository.exists(10));
        assertTrue(contactRepository.exists(11));
        assertTrue(contactRepository.exists(12));
    }


    @Test
    public void findOneTest() {
        Integer i = 1;
        ContactEntity c = contactRepository.findOne(i);
        assertEquals("Contact1", c.getContactName());
    }


    @Test
    public void existsTest() {
        assertTrue(contactRepository.exists(2));
        assertFalse(contactRepository.exists(10));
    }


    @Test
    public void findAllTest() {
        Iterable<ContactEntity> c = contactRepository.findAll();
        assertEquals(c.spliterator().getExactSizeIfKnown(), contactRepository.count());
    }


    @Test
    public void findAllSpecifiedTest() {
        ArrayList<ContactEntity> cList = new ArrayList<>();
        cList.add(0, contactRepository.findOne(1));
        cList.add(1, contactRepository.findOne(2));
        cList.add(2, contactRepository.findOne(3));
        cList.add(3, contactRepository.findOne(4));

        Iterator<ContactEntity> iterator = cList.iterator();

        assertEquals(iterator.next().getContactId(), new Integer(1));
        assertEquals(iterator.next().getContactId(), new Integer(2));
        assertEquals(iterator.next().getContactId(), new Integer(3));
        assertNotEquals(iterator.next().getContactId(), new Integer(5));
    }

    @Test
    public void countTest() {

        String selectQuery = "SELECT * FROM contact\n";
        List resultSet = jdbcTemplate.queryForList(selectQuery);

        assertEquals("Count:", resultSet.size(), contactRepository.count());
    }

    @Test
    public void deleteByContactIdTest() {
        contactRepository.delete(1);
        assertFalse(contactRepository.exists(1));
    }


    @Test
    public void deleteByContactEntityTest() {
        contactRepository.delete(contactRepository.findOne(1));
        assertFalse(contactRepository.exists(1));
    }


    @Test
    public void deleteMultipleContactsTest() {
        ArrayList<ContactEntity> cLists = new ArrayList<>();

        cLists.add(contactRepository.findOne(1));
        cLists.add(contactRepository.findOne(2));
        cLists.add(contactRepository.findOne(3));

        contactRepository.delete(cLists);

        assertFalse(contactRepository.exists(1));
        assertFalse(contactRepository.exists(2));
        assertFalse(contactRepository.exists(3));
    }


    @Test
    public void deleteAllTest() {
        Iterable<ContactEntity> c = contactRepository.findAll();
        contactRepository.delete(c);
        assertEquals(0, contactRepository.count());
    }
}