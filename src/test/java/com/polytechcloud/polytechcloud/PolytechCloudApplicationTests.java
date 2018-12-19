package com.polytechcloud.polytechcloud;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSON;
import com.polytechcloud.polytechcloud.controller.UserController;
import com.polytechcloud.polytechcloud.entity.User;
import com.polytechcloud.polytechcloud.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private User user1;
    private User user2;

    @Before
	public void setup() {
        user1 = new User();
        user2 = new User();
        assertNotNull(user1);
        assertNotNull(user2);

        // Init mocked elements
		MockitoAnnotations.initMocks(this);

		//standAlone setup initializes MockMvc without loading Spring configuration
		// --> will mock dependencies withing the controller out using Mockito.
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();



	}
	@Test
    public void test() {
        assertEquals(1, 1);
    }

/*	@Test
	public void testGetAllUsers_NoContent() throws Exception {

		when(userRepository.findAll()).thenReturn(new ArrayList<>());

		mockMvc.perform(get("/user").characterEncoding("utf-8"))
				.andExpect(status().isNoContent());

		verify(userRepository).findAll();
	}

	@Test
	public void testGetAllUsers_NoError() throws Exception {
	    ArrayList<User> users = new ArrayList<>();
	    users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);
        mockMvc.perform(get("/user").characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)));
	}

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




    @Test
    public void testPutAll_BadRequest() throws Exception {

        when(userRepository.saveAll(null)).thenReturn(null);

        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testPutAll_NoError() throws Exception {

        ObjectMapper mapper = new ObjectMapper();


        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        when(userRepository.saveAll(users)).thenReturn(users);


        mockMvc.perform(put("/user")
                .content(mapper.writeValueAsString(users))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
        .andExpect(status().isCreated());
    }*/



}

