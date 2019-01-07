package com.polytechcloud.polytechcloud;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.polytechcloud.polytechcloud.controller.UserController;
import com.polytechcloud.polytechcloud.entity.User;
import com.polytechcloud.polytechcloud.repository.UserRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;


import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class PolytechCloudApplicationTests {

    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    // Using @InjectMock because userRepository is @Autowired in UserController and userRepository is @Mock
    @InjectMocks
    private UserController userController;


    @Before
    public void setup() throws Exception {
        // Init mocked elements
        MockitoAnnotations.initMocks(this);

        //standAlone setup initializes MockMvc without loading Spring configuration
        // --> will mock dependencies withing the controller out using Mockito.
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

	/* Tests GET */

    // ---------- GET /user -> retourne tous les utilisateurs ----------

	@Test
	public void testGetAllUsers_NoContent() throws Exception {
        when(userRepository.findAll(new Sort(Sort.Direction.ASC, "id")))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get("/user").characterEncoding("utf-8"))
				.andExpect(status().isOk());

		verify(userRepository).findAll(new Sort(Sort.Direction.ASC, "id"));
	}

	@Test
    public void testGetAllUsers_NoError_pageNull() throws Exception {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        when(userRepository.findAll(new Sort(Sort.Direction.ASC, "id"))).thenReturn(users);

        mockMvc.perform(get("/user").characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)));

        verify(userRepository).findAll(new Sort(Sort.Direction.ASC, "id"));
    }

	@Test
    public void testGetAllUsers_NoError_page1() throws Exception {
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i<102; i++) {
            users.add(new User());
        }

        when(userRepository.findAll(new Sort(Sort.Direction.ASC, "id"))).thenReturn(users);

        mockMvc.perform(get("/user").characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(100)));
        verify(userRepository).findAll(new Sort(Sort.Direction.ASC, "id"));
    }

    // ---------- GET /user?page= -> deux demandes pour le même page doivent retourner les mêmes utilisateurs ----------

    @Test
    public void testGetAllUsers_NoError_samePageForTwoRequests() throws Exception {
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i<200; i++) {
            User user = new User();
            user.setId(Integer.toString(i));
            users.add(user);
        }

        when(userRepository.findAll(new Sort(Sort.Direction.ASC, "id"))).thenReturn(users);

        MvcResult page0 = mockMvc.perform(get("/user?page=0").characterEncoding("utf-8")).andReturn();
        MvcResult page0SecondTime = mockMvc.perform(get("/user?page=0").characterEncoding("utf-8")).andReturn();

        JSONArray page0Users = new JSONArray(page0.getResponse().getContentAsString());
        JSONArray page0SecondTimeUsers = new JSONArray(page0SecondTime.getResponse().getContentAsString());
        for(int i=0; i<page0Users.length(); i++)
        {
            JSONObject page0User = page0Users.getJSONObject(i);
            JSONObject page0SecondTimeUser = page0SecondTimeUsers.getJSONObject(i);
            assertEquals(page0User.getString("id"), page0SecondTimeUser.getString("id"));
        }

        verify(userRepository, Mockito.times(2)).findAll(new Sort(Sort.Direction.ASC, "id"));
    }

    // ---------- GET /user?page= -> deux pages différentes ne peuvent pas contenir le même utilisateur ----------

    @Test
    public void testGetAllUsers_NoError_differentPage1AndPage2() throws Exception {
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i<200; i++) {
            User user = new User();
            user.setId(Integer.toString(i));
            users.add(user);
        }

        when(userRepository.findAll(new Sort(Sort.Direction.ASC, "id"))).thenReturn(users);

        MvcResult page0 = mockMvc.perform(get("/user?page=0").characterEncoding("utf-8")).andReturn();
        MvcResult page1 = mockMvc.perform(get("/user?page=1").characterEncoding("utf-8")).andReturn();

        JSONArray page0Users = new JSONArray(page0.getResponse().getContentAsString());
        JSONArray page1Users = new JSONArray(page1.getResponse().getContentAsString());
        for(int i=0; i<page0Users.length(); i++)
        {
            JSONObject page0User = page0Users.getJSONObject(i);
            for(int j=0; j<page1Users.length(); j++)
            {
                JSONObject page1User = page1Users.getJSONObject(j);
                assertNotEquals(page0User.getString("id"), page1User.getString("id"));
            }
        }

        verify(userRepository, Mockito.times(2)).findAll(new Sort(Sort.Direction.ASC, "id"));
    }

    // ---------- GET /user?page= -> deux pages différentes ne peuvent pas contenir le même utilisateur ----------

    /*@Test
    public void testGetAllUsers_NoError_gt) throws Exception {
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i<200; i++) {
            User user = new User();
            user.setId(Integer.toString(i));
            users.add(user);
        }

        when(userRepository.findAll(new Sort(Sort.Direction.ASC, "id"))).thenReturn(users);

        MvcResult page0 = mockMvc.perform(get("/user?page=0").characterEncoding("utf-8")).andReturn();
        MvcResult page1 = mockMvc.perform(get("/user?page=1").characterEncoding("utf-8")).andReturn();

        JSONArray page0Users = new JSONArray(page0.getResponse().getContentAsString());
        JSONArray page1Users = new JSONArray(page1.getResponse().getContentAsString());
        for(int i=0; i<page0Users.length(); i++)
        {
            JSONObject page0User = page0Users.getJSONObject(i);
            for(int j=0; j<page1Users.length(); j++)
            {
                JSONObject page1User = page1Users.getJSONObject(j);
                assertNotEquals(page0User.getString("id"), page1User.getString("id"));
            }
        }

        verify(userRepository, Mockito.times(2)).findAll(new Sort(Sort.Direction.ASC, "id"));
    }*/

    // ----------------------------------------------------------------------------

    // ---------- GET /user/{id} -> retourne l'utilisateur correspondant ----------

    @Test
	public void testGetUserById_NotFound() throws Exception {
		when(userRepository.findById("2")).thenReturn(Optional.empty());

		mockMvc.perform(get("/user/{i}", "2").characterEncoding("utf-8"))
				.andExpect(status().isNotFound());

		verify(userRepository).findById("2");
	}


	@Test
	public void testGetUserById_NoError() throws Exception {
		when(userRepository.findById("2")).thenReturn(Optional.of(new User()));

		mockMvc.perform(get("/user/{i}", 2).characterEncoding("utf-8"))
				.andExpect(status().isOk());

		verify(userRepository).findById("2");
	}

    // ---------------------------------------------------------------------------------------------------------

	/* Tests PUT */

    // ------ PUT /user -> permet de remplacer la collection entière par une nouvelle liste d'utilisateur ------

    @Test
    public void testPutAll_BadRequest_emptyList() throws Exception {
        List<User> users = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(users);

        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPutAll_BadRequest_emptyContent() throws Exception {
        mockMvc.perform(put("/user"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPutAll_NoError() throws Exception {

        ArrayList<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(users);

        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.*", hasSize(2)));

        verify(userRepository).deleteAll();
    }

    // --------------------------------------------------------

    // ------ PUT /user/{id} -> met à jour l'utilisateur ------

    @Test
    public void testPutUserId_NotFound() throws Exception {
        User user = new User();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        when(userRepository.findById("2")).thenReturn(Optional.empty());

        mockMvc.perform(put("/user/{i}", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json))
                .andExpect(status().isNotFound());

        verify(userRepository).findById("2");
    }

    @Test
    public void testPutUserId_BadRequest_emptyContent() throws Exception {
        mockMvc.perform(put("/user/{i}", "2"))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testPutUserId_NoError() throws Exception {
        User user = new User();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        when(userRepository.findById("2")).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        mockMvc.perform(put("/user/{i}", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json))
                .andExpect(status().isOk());

        verify(userRepository).findById("2");
        verify(userRepository).save(user);
    }

    // ---------------------------------------------------------------------------

    /* Tests POST */

    // ------ POST /user -> ajoute un nouvel utilisateur passé en paramètre ------

    @Test
    public void testPostUser_BadRequest() throws Exception {
        mockMvc.perform(post("/user"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostUser_NoError() throws Exception {
        User user = new User();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json))
                .andExpect(status().isCreated());
    }

    // ---------------------------------------------------------------------------

    /* Tests DELETE */

    // ------ DELETE /user -> supprime toute la collection des utilisateurs ------

    @Test
    public void testDelete_NoError() throws Exception {
        mockMvc.perform(delete("/user"))
                .andExpect(status().isOk());
        verify(userRepository).deleteAll();
    }

    // ---------------------------------------------------------------------------

    // ------ DELETE /user/{id} -> supprime l'utilisateur correspondant ------

    @Test
    public void testDeleteById_NoError() throws Exception {
        User user = new User();
        user.setId("2");

        when(userRepository.findById("2")).thenReturn(Optional.of(user));

        mockMvc.perform(delete("/user/{id}", "2"))
                .andExpect(status().isOk());

        verify(userRepository).deleteById(user.getId());
        verify(userRepository).findById(user.getId());
    }

    @Test
    public void testDeleteById_NotFound() throws Exception {
        when(userRepository.findById("2")).thenReturn(Optional.empty());

        mockMvc.perform(delete("/user/{id}", "2"))
                .andExpect(status().is(500));

        verify(userRepository).findById("2");
    }



}