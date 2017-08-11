package practice.repository;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import practice.model.DoctorEntity;
import practice.model.FrontDeskStaffEntity;
import practice.model.ScheduleEntity;
import practice.model.ScheduleEntity;

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
public class ScheduleRepositoryTest
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Test
    @After
    public void testScheduleTable()
    {
        String selectQuery = "Select * from schedule";// where schedule_id = 4";
        List resultSet = jdbcTemplate.queryForList(selectQuery);

        System.out.println(resultSet);
    }

    @Test
    public void saveOneNewScheduleTest()
    {
        ScheduleEntity s = new ScheduleEntity();

        DoctorEntity doctor = new DoctorEntity();
        doctor.setDocId(1);
        s.setDoctorByDocId(doctor);

        FrontDeskStaffEntity staff = new FrontDeskStaffEntity();
        staff.setFrontDeskStaffId(1);
        s.setFrontDeskStaffByFrontDeskStaffId(staff);

        scheduleRepository.save(s);
        assertTrue(scheduleRepository.exists(s.getScheduleId()));
    }


    @Test
    public void saveListOfSchedulesTest()
    {
        ArrayList<ScheduleEntity> sList = new ArrayList<>();
        ScheduleEntity e1 = new ScheduleEntity();
        ScheduleEntity e2 = new ScheduleEntity();
        ScheduleEntity e3 = new ScheduleEntity();
        e1.setScheduleId(7);
        e2.setScheduleId(8);
        e3.setScheduleId(9);

        DoctorEntity doctor = new DoctorEntity();
        doctor.setDocId(1);
        e1.setDoctorByDocId(doctor);
        e2.setDoctorByDocId(doctor);
        doctor.setDocId(2);
        e3.setDoctorByDocId(doctor);

        FrontDeskStaffEntity staff = new FrontDeskStaffEntity();
        staff.setFrontDeskStaffId(1);
        e1.setFrontDeskStaffByFrontDeskStaffId(staff);
        e2.setFrontDeskStaffByFrontDeskStaffId(staff);
        staff.setFrontDeskStaffId(2);
        e3.setFrontDeskStaffByFrontDeskStaffId(staff);

        sList.add(e1);
        sList.add(e2);
        sList.add(e3);

        scheduleRepository.save(sList);

        assertTrue(scheduleRepository.exists(7));
        assertTrue(scheduleRepository.exists(8));
        assertTrue(scheduleRepository.exists(9));
    }


    @Test
    public void findOneTest()
    {
        Integer i = 1;
        ScheduleEntity s = scheduleRepository.findOne(i);
        assertEquals(new Integer(1), s.getScheduleId());
    }


    @Test
    public void existsTest()
    {
        assertTrue(scheduleRepository.exists(2));
        assertFalse(scheduleRepository.exists(7));
    }


    @Test
    public void findAllTest()
    {
        Iterable<ScheduleEntity> s = scheduleRepository.findAll();
        assertEquals(s.spliterator().getExactSizeIfKnown(), scheduleRepository.count());
    }


    @Test
    public void findAllSpecifiedTest()
    {
        ArrayList<ScheduleEntity> cList = new ArrayList<>();
        cList.add(0, scheduleRepository.findOne(1));
        cList.add(1, scheduleRepository.findOne(2));
        cList.add(2, scheduleRepository.findOne(3));
        cList.add(3, scheduleRepository.findOne(4));

        Iterator<ScheduleEntity> iterator = cList.iterator();

        assertEquals(new Integer(iterator.next().getScheduleId()), new Integer(1));
        assertEquals(new Integer(iterator.next().getScheduleId()), new Integer(2));
        assertEquals(new Integer(iterator.next().getScheduleId()), new Integer(3));
        assertNotEquals(new Integer(iterator.next().getScheduleId()), new Integer(5));
    }

    @Test
    public void countTest()
    {

        String selectQuery = "SELECT * FROM schedule\n";
        List resultSet = jdbcTemplate.queryForList(selectQuery);

        assertEquals("Count:", resultSet.size(), scheduleRepository.count());
    }

    @Test
    public void deleteByScheduleIdTest()
    {
        scheduleRepository.delete(1);
        assertFalse(scheduleRepository.exists(1));
    }


    @Test
    public void deleteByScheduleEntityTest()
    {
        scheduleRepository.delete(scheduleRepository.findOne(1));
        assertFalse(scheduleRepository.exists(1));
    }


    @Test
    public void deleteMultipleSchedulesTest()
    {
        ArrayList<ScheduleEntity> sList = new ArrayList<>();

        sList.add(scheduleRepository.findOne(1));
        sList.add(scheduleRepository.findOne(2));
        sList.add(scheduleRepository.findOne(3));

        scheduleRepository.delete(sList);

        assertFalse(scheduleRepository.exists(1));
        assertFalse(scheduleRepository.exists(2));
        assertFalse(scheduleRepository.exists(3));
    }


    @Test
    public void deleteAllTest()
    {
        Iterable<ScheduleEntity> s = scheduleRepository.findAll();
        scheduleRepository.delete(s);
        assertEquals(0, scheduleRepository.count());
    }
}