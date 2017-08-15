package practice.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import practice.model.DoctorEntity;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;

/**
 * Created by ehunzeker on 5/16/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Rollback
@Transactional
public class DoctorRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    public void testDoctorTable() {
        String selectQuery = "SELECT * from doctor WHERE doc_id = 1";
        List<Map<String, Object>> resultSet = jdbcTemplate.queryForList(selectQuery);
        System.out.println(resultSet);
    }

    @Test
    public void saveOneNewDoctorTest() {
        DoctorEntity d = new DoctorEntity();
        d.setDocId(4);
        d.setDocName("NewDoctor4");
        doctorRepository.save(d);
        assertTrue(doctorRepository.exists(4));
    }


    @Test
    public void saveListOfDoctorsTest() {

        DoctorEntity e1 = new DoctorEntity();
        e1.setDocName("NewDoctor4");
        DoctorEntity e2 = new DoctorEntity();
        e2.setDocName("NewDoctor5");
        DoctorEntity e3 = new DoctorEntity();
        e3.setDocName("NewDoctor6");
        //e1.setDocId(new Integer(4));

        //e2.setDocId(new Integer(5));

        //e3.setDocId(new Integer(6));
        ArrayList<DoctorEntity> dLists = new ArrayList<>();
        dLists.add(e1);
        dLists.add(e2);
        dLists.add(e3);

        doctorRepository.save(dLists);
        assertTrue(doctorRepository.exists(e1.getDocId()));
        assertTrue(doctorRepository.exists(e2.getDocId()));
        assertTrue(doctorRepository.exists(e3.getDocId()));
    }


    @Test
    public void findOneTest() {
        Integer i = 1;
        DoctorEntity d = doctorRepository.findOne(i);
        assertEquals("Doctor1", d.getDocName());
    }


    @Test
    public void existsTest() {
        assertTrue(doctorRepository.exists(2));
        assertFalse(doctorRepository.exists(10));
    }


    @Test
    public void findAllTest() {
        Iterable<DoctorEntity> d = doctorRepository.findAll();
        assertEquals(d.spliterator().getExactSizeIfKnown(), doctorRepository.count());
    }


    @Test
    public void findAllSpecifiedTest() {
        ArrayList<DoctorEntity> dList = new ArrayList<>();
        dList.add(0, doctorRepository.findOne(1));
        dList.add(1, doctorRepository.findOne(2));
        dList.add(2, doctorRepository.findOne(3));

        Iterator<DoctorEntity> iterator = dList.iterator();

        assertEquals(iterator.next().getDocId(), new Integer(1));
        assertEquals(iterator.next().getDocId(), new Integer(2));
        assertNotEquals(iterator.next().getDocId(), new Integer(5));
    }

    @Test
    public void countTest() {

        String selectQuery = "SELECT * FROM doctor";
        List resultSet = jdbcTemplate.queryForList(selectQuery);

        assertEquals("Count:", resultSet.size(), doctorRepository.count());
    }

    @Test
    public void deleteByDocIdTest() {
        doctorRepository.delete(1);
        assertFalse(doctorRepository.exists(1));
    }


    @Test
    public void deleteByDoctorEntityTest() {
        doctorRepository.delete(doctorRepository.findOne(1));
        assertFalse(doctorRepository.exists(1));
    }


    @Test
    public void deleteMultipleDoctorsTest() {
        ArrayList<DoctorEntity> cLists = new ArrayList<>();

        cLists.add(doctorRepository.findOne(1));
        cLists.add(doctorRepository.findOne(2));
        cLists.add(doctorRepository.findOne(3));

        doctorRepository.delete(cLists);

        assertFalse(doctorRepository.exists(1));
        assertFalse(doctorRepository.exists(2));
        assertFalse(doctorRepository.exists(3));
    }


    @Test
    public void deleteAllTest() {
        Iterable<DoctorEntity> d = doctorRepository.findAll();
        doctorRepository.delete(d);
        assertEquals(0, doctorRepository.count());
    }
}