package practice.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import practice.model.FrontDeskStaffEntity;

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
public class FrontDeskStaffRepositoryTest 
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private FrontDeskStaffRepository frontDeskStaffRepository;

    @Test
    public void testFrontDeskStaffTable()
    {
        String selectQuery = "Select * from front_desk_staff where front_desk_staff_id = 2";
        List resultSet = jdbcTemplate.queryForList(selectQuery);

        System.out.println(resultSet);
    }

    @Test
    public void saveOneNewFrontDeskStaffTest()
    {
        FrontDeskStaffEntity f = new FrontDeskStaffEntity();
        f.setLastName("NewFrontDeskStaff7");
        f.setFirstName("Staff");
        frontDeskStaffRepository.save(f);
        assertTrue(frontDeskStaffRepository.exists(f.getFrontDeskStaffId()));
    }


    @Test
    public void saveListOfFrontDeskStaffsTest()
    {
        ArrayList<FrontDeskStaffEntity> fList = new ArrayList<>();
        FrontDeskStaffEntity e1 = new FrontDeskStaffEntity();
        FrontDeskStaffEntity e2 = new FrontDeskStaffEntity();
        FrontDeskStaffEntity e3 = new FrontDeskStaffEntity();
        e1.setFrontDeskStaffId(7);
        e1.setLastName("NewFrontDeskStaff7");
        e1.setFirstName("Staff");
        e2.setFrontDeskStaffId(8);
        e2.setLastName("NewFrontDeskStaff8");
        e2.setFirstName("Staff");
        e3.setFrontDeskStaffId(9);
        e3.setLastName("NewFrontDeskStaff9");
        e3.setFirstName("Staff");

        fList.add(e1);
        fList.add(e2);
        fList.add(e3);

        frontDeskStaffRepository.save(fList);

        assertTrue(frontDeskStaffRepository.exists(7));
        assertTrue(frontDeskStaffRepository.exists(8));
        assertTrue(frontDeskStaffRepository.exists(9));
    }


    @Test
    public void findOneTest()
    {
        Integer i = 1;
        FrontDeskStaffEntity f = frontDeskStaffRepository.findOne(i);
        assertEquals(new Integer(1), new Integer(f.getFrontDeskStaffId()));
    }


    @Test
    public void existsTest()
    {
        assertTrue(frontDeskStaffRepository.exists(2));
        assertFalse(frontDeskStaffRepository.exists(7));
    }


    @Test
    public void findAllTest()
    {
        Iterable<FrontDeskStaffEntity> f = frontDeskStaffRepository.findAll();
        assertEquals(f.spliterator().getExactSizeIfKnown(), frontDeskStaffRepository.count());
    }


    @Test
    public void findAllSpecifiedTest()
    {
        ArrayList<FrontDeskStaffEntity> fList = new ArrayList<>();
        fList.add(0, frontDeskStaffRepository.findOne(1));
        fList.add(1, frontDeskStaffRepository.findOne(2));
        fList.add(2, frontDeskStaffRepository.findOne(3));
        fList.add(3, frontDeskStaffRepository.findOne(4));

        Iterator<FrontDeskStaffEntity> iterator = fList.iterator();

        assertEquals(new Integer(iterator.next().getFrontDeskStaffId()), new Integer(1));
        assertEquals(new Integer(iterator.next().getFrontDeskStaffId()), new Integer(2));
        assertEquals(new Integer(iterator.next().getFrontDeskStaffId()), new Integer(3));
        assertNotEquals(new Integer(iterator.next().getFrontDeskStaffId()), new Integer(5));
    }

    @Test
    public void countTest()
    {

        String selectQuery = "SELECT * FROM front_desk_staff";
        List resultSet = jdbcTemplate.queryForList(selectQuery);

        assertEquals("Count:", resultSet.size(), frontDeskStaffRepository.count());
    }

    @Test
    public void deleteByFrontDeskStaffIdTest()
    {
        frontDeskStaffRepository.delete(1);
        assertFalse(frontDeskStaffRepository.exists(1));
    }


    @Test
    public void deleteByFrontDeskStaffEntityTest()
    {
        frontDeskStaffRepository.delete(frontDeskStaffRepository.findOne(1));
        assertFalse(frontDeskStaffRepository.exists(1));
    }


    @Test
    public void deleteMultipleFrontDeskStaffsTest()
    {
        ArrayList<FrontDeskStaffEntity> fList = new ArrayList<>();

        fList.add(frontDeskStaffRepository.findOne(1));
        fList.add(frontDeskStaffRepository.findOne(2));
        fList.add(frontDeskStaffRepository.findOne(3));

        frontDeskStaffRepository.delete(fList);

        assertFalse(frontDeskStaffRepository.exists(1));
        assertFalse(frontDeskStaffRepository.exists(2));
        assertFalse(frontDeskStaffRepository.exists(3));
    }


    @Test
    public void deleteAllTest()
    {
        Iterable<FrontDeskStaffEntity> f = frontDeskStaffRepository.findAll();
        frontDeskStaffRepository.delete(f);
        assertEquals(0, frontDeskStaffRepository.count());
    }
}