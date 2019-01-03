package com.polytechcloud.polytechcloud;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polytechcloud.polytechcloud.controller.UserController;
import com.polytechcloud.polytechcloud.entity.User;
import com.polytechcloud.polytechcloud.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.data.domain.Sort;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class PolytechCloudApplicationTests {

	private MockMvc mockMvc;

	//@Autowired
	//WebApplicationContext wac;

	@Mock
	private UserRepository userRepository;

	// Using @InjectMock because userRepository is @Autowired in UserController and userRepository is @Mock
	@InjectMocks
	private UserController userController;

	@Before
	public void setup() {
        // Init mocked elements
		MockitoAnnotations.initMocks(this);

		//standAlone setup initializes MockMvc without loading Spring configuration
		// --> will mock dependencies withing the controller out using Mockito.
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        //mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}

	/* Tests GET */

    // ---------- GET /user -> retourne tous les utilisateurs ----------
	@Test
	public void testGetAllUsers_NoContent() throws Exception {


        when(userRepository.findAll(new Sort(Sort.Direction.ASC, "id")))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get("/user").characterEncoding("utf-8"))
				.andExpect(status().isNoContent());

		verify(userRepository).findAll(new Sort(Sort.Direction.ASC, "id"));
	}

	@Test
	public void testGetAllUsers_NoError() throws Exception {
	    ArrayList<User> users = new ArrayList<>();
	    users.add(new User());
        users.add(new User());

        when(userRepository.findAll()).thenReturn(users);
        mockMvc.perform(get("/user").characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)));

        verify(userRepository).findAll();
	}
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
    public void testPutAll_BadRequest() throws Exception {
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
        System.out.println(json);

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
        System.out.println(json);

        when(userRepository.findById("2")).thenReturn(Optional.empty());

        mockMvc.perform(put("/user/{i}", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json))
                .andExpect(status().isNotFound());

        verify(userRepository).findById("2");
    }

    @Test
    public void testPutUserId_BadRequest() throws Exception {
        mockMvc.perform(put("/user/{i}", "2"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPutUserId_NoError() throws Exception {
        User user = new User();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);
        System.out.println(json);

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
        mockMvc.perform(MockMvcRequestBuilders.post("/user"))
                .andExpect(status().isBadRequest());

    }
    @Test
    public void testPostUser_NoError() throws Exception {
        User user = new User();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);


        mockMvc.perform(MockMvcRequestBuilders.post("/user")
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
        mockMvc.perform(MockMvcRequestBuilders.delete("/user"))
                .andExpect(status().isOk());
        verify(userRepository).deleteAll();
    }
    // ---------------------------------------------------------------------------

    // ------ DELETE /user/{id} -> supprime l'utilisateur correspondant ------
    @Test
    public void testDeleteById_NoError() throws Exception {
        User user = new User();
        user.setId("2");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        when(userRepository.findById("2")).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/{id}", "2"))
                .andExpect(status().isOk());

        verify(userRepository).deleteById(user.getId());
        verify(userRepository).findById(user.getId());
    }

    @Test
    public void testDeleteById_NotFound() throws Exception {


        when(userRepository.findById("2")).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/{id}", "2"))
                .andExpect(status().isNotFound());

        verify(userRepository).findById("2");
    }





}

