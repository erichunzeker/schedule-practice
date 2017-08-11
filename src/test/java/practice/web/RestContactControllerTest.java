package practice.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.ResponseStatus;
import practice.model.ContactEntity;
import practice.repository.ContactRepository;
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
public class RestContactControllerTest
{
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private ContactRepository contactRepository;

    public RestContactControllerTest()
    {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void getAllContactsTest() throws Exception
    {

        String s = mockMvc.perform(get("/contact/all")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<ContactEntity> contactEntityList = objectMapper.readValue(s, new TypeReference<List<ContactEntity>>(){});
        assertNotNull(contactEntityList);
    }

    @Test
    public void testAddContact() throws Exception {
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setContactName("NewContact");
        contactEntity.setPatientByPatId(patientRepository.findOne(2));

        String s = mockMvc.perform(post("/contact/add")
                .header("PracticeId",1)
                .content(objectMapper.writeValueAsString(contactEntity))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ContactEntity contactEntity1 = objectMapper.readValue(s, ContactEntity.class);
        assertNotNull(contactEntity1);
    }

    @Test
    public void testDeleteContact() throws Exception {
        mockMvc.perform(get("/contact/delete").param("id", "1")
                .contentType("application/json"));
        assertEquals(8, contactRepository.count());
        assertFalse(contactRepository.exists(1));
    }

    @Test
    public void getContactById() throws Exception
    {
        String s = mockMvc.perform(get("/contact/find").param("id", "1")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ContactEntity contactEntity1 = objectMapper.readValue(s, ContactEntity.class);
        assertNotNull(contactEntity1);

    }

}