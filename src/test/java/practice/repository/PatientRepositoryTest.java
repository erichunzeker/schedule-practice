package practice.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import practice.model.PatientEntity;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;

/**
 * Created by ehunzeker on 5/17/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Rollback
@Transactional
public class PatientRepositoryTest {
    private static final Logger log = LoggerFactory.getLogger(PatientRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void testPatientTable() {
        String selectQuery = "Select * from patient where name = 'Patient2'";
        List resultSet = jdbcTemplate.queryForList(selectQuery);
        log.debug(resultSet.toString());
    }

    @Test
    public void saveOneNewPatientTest() {
        PatientEntity p = new PatientEntity();
        p.setPatId(7);
        p.setName("NewPatient7");
        patientRepository.save(p);
        assertTrue(patientRepository.exists(7));
    }


    @Test
    public void saveListOfPatientsTest() {
        ArrayList<PatientEntity> pList = new ArrayList<>();
        PatientEntity e1 = new PatientEntity();
        PatientEntity e2 = new PatientEntity();
        PatientEntity e3 = new PatientEntity();
        e1.setName("NewPatient7");
        e2.setName("NewPatient8");
        e3.setName("NewPatient9");

        pList.add(e1);
        pList.add(e2);
        pList.add(e3);

        patientRepository.save(pList);

        assertTrue(patientRepository.exists(e1.getPatId()));
        assertTrue(patientRepository.exists(e2.getPatId()));
        assertTrue(patientRepository.exists(e3.getPatId()));
    }


    @Test
    public void findOneTest() {
        Integer i = 1;
        PatientEntity p = patientRepository.findOne(i);
        assertEquals("Patient1", p.getName());
    }


    @Test
    public void existsTest() {
        assertTrue(patientRepository.exists(2));
        assertFalse(patientRepository.exists(7));
    }


    @Test
    public void findAllTest() {
        Iterable<PatientEntity> p = patientRepository.findAll();
        assertEquals(p.spliterator().getExactSizeIfKnown(), patientRepository.count());
    }


    @Test
    public void findAllSpecifiedTest() {
        ArrayList<PatientEntity> pList = new ArrayList<>();
        pList.add(0, patientRepository.findOne(1));
        pList.add(1, patientRepository.findOne(2));
        pList.add(2, patientRepository.findOne(3));
        pList.add(3, patientRepository.findOne(4));

        Iterator<PatientEntity> iterator = pList.iterator();

        assertEquals(new Integer(iterator.next().getPatId()), new Integer(1));
        assertEquals(new Integer(iterator.next().getPatId()), new Integer(2));
        assertEquals(new Integer(iterator.next().getPatId()), new Integer(3));
        assertNotEquals(new Integer(iterator.next().getPatId()), new Integer(7));
    }

    @Test
    public void countTest() {

        String selectQuery = "SELECT * FROM patient\n";
        List resultSet = jdbcTemplate.queryForList(selectQuery);

        assertEquals("Count:", resultSet.size(), patientRepository.count());
    }

    @Test
    public void deleteByPatientIdTest() {
        patientRepository.delete(1);
        assertFalse(patientRepository.exists(1));
    }


    @Test
    public void deleteByPatientEntityTest() {
        patientRepository.delete(patientRepository.findOne(1));
        assertFalse(patientRepository.exists(1));
    }


    @Test
    public void deleteMultiplePatientsTest() {
        ArrayList<PatientEntity> pList = new ArrayList<>();

        pList.add(patientRepository.findOne(1));
        pList.add(patientRepository.findOne(2));
        pList.add(patientRepository.findOne(3));

        patientRepository.delete(pList);

        assertFalse(patientRepository.exists(1));
        assertFalse(patientRepository.exists(2));
        assertFalse(patientRepository.exists(3));
    }


    @Test
    public void deleteAllTest() {
        Iterable<PatientEntity> p = patientRepository.findAll();
        patientRepository.delete(p);
        assertEquals(0, patientRepository.count());
    }
}