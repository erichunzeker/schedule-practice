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
import practice.model.FrontDeskStaffEntity;
import practice.repository.FrontDeskStaffRepository;

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
public class RestFrontDeskStaffControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    private FrontDeskStaffRepository frontDeskStaffRepository;

    public RestFrontDeskStaffControllerTest() {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void getAllFrontDeskStaffTest() throws Exception {
        String s = mockMvc.perform(get("/frontdeskstaff/all")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<FrontDeskStaffEntity> frontDeskStaffEntityList = objectMapper.readValue(s, new TypeReference<List<FrontDeskStaffEntity>>() {
        });
        assertNotNull(frontDeskStaffEntityList);
    }

    @Test
    public void testAddFrontDeskStaff() throws Exception {
        FrontDeskStaffEntity frontDeskStaffEntity = new FrontDeskStaffEntity();
        frontDeskStaffEntity.setLastName("NewFrontDeskStaff");

        String s = mockMvc.perform(post("/frontdeskstaff/add")
                .header("PracticeId", 1)
                .content(objectMapper.writeValueAsString(frontDeskStaffEntity))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        FrontDeskStaffEntity frontDeskStaffEntity1 = objectMapper.readValue(s, FrontDeskStaffEntity.class);
        assertNotNull(frontDeskStaffEntity1);
    }

    @Test
    public void getFrontDeskStaffById() throws Exception {
        String s = mockMvc.perform(get("/frontdeskstaff/find").param("id", "1")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        FrontDeskStaffEntity frontDeskStaffEntity1 = objectMapper.readValue(s, FrontDeskStaffEntity.class);
        assertNotNull(frontDeskStaffEntity1);

    }

    @Test
    public void testDeleteFrontDeskStaff() throws Exception {
        mockMvc.perform(get("/frontdeskstaff/delete").param("id", "1")
                .contentType("application/json"));
        assertEquals(5, frontDeskStaffRepository.count());
        assertFalse(frontDeskStaffRepository.exists(1));
    }
}