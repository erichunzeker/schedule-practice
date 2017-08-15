package practice.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import practice.model.VisitEntity;
import practice.repository.ScheduleRepository;
import practice.repository.VisitRepository;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ehunzeker on 5/17/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class RestVisitControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private VisitRepository visitRepository;

    public RestVisitControllerTest() {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void getAllVisitsTest() throws Exception {

        String s = mockMvc.perform(get("/visit/all")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<VisitEntity> visitEntityList = objectMapper.readValue(s, new TypeReference<List<VisitEntity>>() {
        });
        assertNotNull(visitEntityList);
        System.out.println(s);
    }

    @Test
    public void testAddVisit() throws Exception {
        VisitEntity visitEntity = new VisitEntity();
        //visitEntity.setVisitId(6);
        visitEntity.setPatId(3);
        visitEntity.setScheduleByScheduleId(scheduleRepository.findOne(6));

        String s = mockMvc.perform(post("/visit/add")
                .header("PracticeId", 1)
                .content(objectMapper.writeValueAsString(visitEntity))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        VisitEntity visitEntity1 = objectMapper.readValue(s, VisitEntity.class);
        assertNotNull(visitEntity1);
    }

    @Test
    public void getVisitById() throws Exception {
        String s = mockMvc.perform(get("/visit/find").param("id", "1")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        VisitEntity visitEntity1 = objectMapper.readValue(s, VisitEntity.class);
        assertNotNull(visitEntity1);

    }

    @Test
    public void testDeleteVisit() throws Exception {
        mockMvc.perform(get("/visit/delete").param("id", "1")
                .contentType("application/json"));
        assertEquals(4, visitRepository.count());
        assertFalse(visitRepository.exists(1));
    }
}