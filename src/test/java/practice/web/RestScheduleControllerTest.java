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
import practice.model.DoctorEntity;
import practice.model.FrontDeskStaffEntity;
import practice.model.ScheduleEntity;
import practice.repository.DoctorRepository;
import practice.repository.FrontDeskStaffRepository;
import practice.repository.ScheduleRepository;

import javax.print.Doc;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
public class RestScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    private FrontDeskStaffRepository frontDeskStaffRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public RestScheduleControllerTest()
    {

        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void getAllSchedulesTest() throws Exception
    {

        String s = mockMvc.perform(get("/schedule/all")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<ScheduleEntity> scheduleEntityList = objectMapper.readValue(s, new TypeReference<List<ScheduleEntity>>(){});
        assertNotNull(scheduleEntityList);
        System.out.println(s);

    }

    @Test
    public void testAddSchedule() throws Exception {
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setDoctorByDocId(doctorRepository.findOne(3));
        scheduleEntity.setFrontDeskStaffByFrontDeskStaffId(frontDeskStaffRepository.findOne(6));

        String s = mockMvc.perform(post("/schedule/add")
                .header("PracticeId",1)
                .content(objectMapper.writeValueAsString(scheduleEntity))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ScheduleEntity scheduleEntity1 = objectMapper.readValue(s, ScheduleEntity.class);
        assertNotNull(scheduleEntity1);
    }

    @Test
    public void getScheduleById() throws Exception
    {
        String s = mockMvc.perform(get("/schedule/find").param("id", "1")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ScheduleEntity scheduleEntity1 = objectMapper.readValue(s, ScheduleEntity.class);
        assertNotNull(scheduleEntity1);

    }

    @Test
    public void testDeleteSchedule() throws Exception {
        mockMvc.perform(get("/schedule/delete").param("id", "1")
                .contentType("application/json"));
        assertEquals(5, scheduleRepository.count());
        assertFalse(scheduleRepository.exists(1));
    }

}