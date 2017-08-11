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
import practice.model.PatientEntity;
import practice.model.DoctorEntity;
import practice.model.PatientEntity;
import practice.repository.DoctorRepository;
import practice.repository.PatientRepository;

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
public class RestPatientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;

    public RestPatientControllerTest()
    {

        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void getAllPatientsTest() throws Exception
    {

        String s = mockMvc.perform(get("/patient/all")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<PatientEntity> patientEntityList = objectMapper.readValue(s, new TypeReference<List<PatientEntity>>(){});
        assertNotNull(patientEntityList);
        System.out.println(s);
    }

    @Test
    public void testAddPatient() throws Exception {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setName("NewPatient");
        patientEntity.setDoctorByDocId(doctorRepository.findOne(3));

        String s = mockMvc.perform(post("/patient/add")
                .header("PracticeId",1)
                .content(objectMapper.writeValueAsString(patientEntity))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        PatientEntity patientEntity1 = objectMapper.readValue(s, PatientEntity.class);
        assertNotNull(patientEntity1);
    }

    @Test
    public void getPatientById() throws Exception
    {
        String s = mockMvc.perform(get("/patient/find").param("id", "1")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        PatientEntity patientEntity1 = objectMapper.readValue(s, PatientEntity.class);
        assertNotNull(patientEntity1);

    }

    @Test
    public void testDeletePatient() throws Exception {
        mockMvc.perform(get("/patient/delete").param("id", "1")
                .contentType("application/json"));

        assertEquals(5, patientRepository.count());
        assertFalse(patientRepository.exists(1));
    }
}