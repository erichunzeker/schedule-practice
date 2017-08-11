package practice.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import practice.model.VisitEntity;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ehunzeker on 5/16/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Rollback
@Transactional
public class VisitRepositoryTest{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private VisitRepository visitRepository;

    @Test
    public void testVisitTable() {
        String selectQuery = "SELECT * from visit WHERE visit_id = 1";
        List<Map<String, Object>> resultSet = jdbcTemplate.queryForList(selectQuery);
        System.out.println(resultSet);
    }

    @Test
    public void saveOneNewVisitTest()
    {
        VisitEntity v = new VisitEntity();
        visitRepository.save(v);
        assertTrue(visitRepository.exists(v.getVisitId()));
    }


    @Test
    public void saveListOfVisitsTest()
    {
        ArrayList<VisitEntity> cLists = new ArrayList<>();
        VisitEntity e1 = new VisitEntity();
        VisitEntity e2 = new VisitEntity();
        VisitEntity e3 = new VisitEntity();


        cLists.add(e1);
        cLists.add(e2);
        cLists.add(e3);

        visitRepository.save(cLists);

        assertTrue(visitRepository.exists(e1.getVisitId()));
        assertTrue(visitRepository.exists(e2.getVisitId()));
        assertTrue(visitRepository.exists(e3.getVisitId()));
    }


    @Test
    public void findOneTest()
    {
        Integer i = 1;
        VisitEntity v = visitRepository.findOne(i);
        assertEquals(new Integer(1), new Integer(v.getVisitId()));
    }


    @Test
    public void existsTest()
    {
        assertTrue(visitRepository.exists(2));
        assertFalse(visitRepository.exists(7));
    }


    @Test
    public void findAllTest()
    {
        Iterable<VisitEntity> v = visitRepository.findAll();
        assertEquals(v.spliterator().getExactSizeIfKnown(), visitRepository.count());
    }


    @Test
    public void findAllSpecifiedTest()
    {
        ArrayList<VisitEntity> cList = new ArrayList<>();
        cList.add(0, visitRepository.findOne(1));
        cList.add(1, visitRepository.findOne(2));
        cList.add(2, visitRepository.findOne(3));
        cList.add(3, visitRepository.findOne(4));

        Iterator<VisitEntity> iterator = cList.iterator();

        assertEquals(new Integer(iterator.next().getVisitId()), new Integer(1));
        assertEquals(new Integer(iterator.next().getVisitId()), new Integer(2));
        assertEquals(new Integer(iterator.next().getVisitId()), new Integer(3));
        assertNotEquals(new Integer(iterator.next().getVisitId()), new Integer(5));
    }

    @Test
    public void countTest()
    {

        String selectQuery = "SELECT * FROM visit\n";
        List resultSet = jdbcTemplate.queryForList(selectQuery);

        assertEquals("Count:", resultSet.size(), visitRepository.count());
    }

    @Test
    public void deleteByVisitIdTest()
    {
        visitRepository.delete(1);
        assertFalse(visitRepository.exists(1));
    }


    @Test
    public void deleteByVisitEntityTest()
    {
        visitRepository.delete(visitRepository.findOne(1));
        assertFalse(visitRepository.exists(1));
    }


    @Test
    public void deleteMultipleVisitsTest()
    {
        ArrayList<VisitEntity> cLists = new ArrayList<>();

        cLists.add(visitRepository.findOne(1));
        cLists.add(visitRepository.findOne(2));
        cLists.add(visitRepository.findOne(3));

        visitRepository.delete(cLists);

        assertFalse(visitRepository.exists(1));
        assertFalse(visitRepository.exists(2));
        assertFalse(visitRepository.exists(3));
    }


    @Test
    public void deleteAllTest()
    {
        Iterable<VisitEntity> v = visitRepository.findAll();
        visitRepository.delete(v);
        assertEquals(0, visitRepository.count());
    }
}