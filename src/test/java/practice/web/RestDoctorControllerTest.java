package practice.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import practice.model.DoctorEntity;
import practice.repository.DoctorRepository;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
/**
 * Created by ehunzeker on 5/17/17.
 */
public class RestDoctorControllerTest
{
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    private DoctorRepository doctorRepository;


    public RestDoctorControllerTest()
    {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void getAllDoctorsTest() throws Exception
    {
        String s = mockMvc.perform(get("/doctor/all")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<DoctorEntity> doctorEntityList = objectMapper.readValue(s, new TypeReference<List<DoctorEntity>>(){});
        assertNotNull(doctorEntityList);
        System.out.println(s);
    }

    @Test
    public void testAddDoctor() throws Exception {

        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setDocName("NewDoctor");

        String s = mockMvc.perform(post("/doctor/add")
                .header("PracticeId",1)
                .content(objectMapper.writeValueAsString(doctorEntity))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        DoctorEntity doctorEntity1 = objectMapper.readValue(s, DoctorEntity.class);
        assertNotNull(doctorEntity1);
    }


    @Test
    public void getDoctorById() throws Exception
    {
        String s = mockMvc.perform(get("/doctor/find").param("id", "1")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        DoctorEntity doctorEntity1 = objectMapper.readValue(s, DoctorEntity.class);
        assertNotNull(doctorEntity1);
    }

    @Test
    public void testDeleteDoctor() throws Exception
    {
        mockMvc.perform(get("/doctor/delete").param("id", "1")
                .contentType("application/json"));
        assertEquals(2, doctorRepository.count());
        assertFalse(doctorRepository.exists(1));
    }

}