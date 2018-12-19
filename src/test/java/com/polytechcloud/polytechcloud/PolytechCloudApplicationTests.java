package com.polytechcloud.polytechcloud;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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

	@Before
	public void setup() {
		// Init mocked elements
		MockitoAnnotations.initMocks(this);

		//standAlone setup initializes MockMvc without loading Spring configuration
		// --> will mock dependencies withing the controller out using Mockito.
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

	}

	/* Tests GET */
	@Test
	public void testGetAllUsers_NoContent() throws Exception {

		when(userRepository.findAll()).thenReturn(new ArrayList<>());
		mockMvc.perform(get("/user").characterEncoding("utf-8"))
				.andExpect(status().isNoContent());

		verify(userRepository).findAll();
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



	/* Tests PUT */

	@Test
	public void testPutAll() throws Exception {
		when(userRepository.findById("2")).thenReturn(Optional.of(new User()));

		mockMvc.perform(get("/user/{i}", 2).characterEncoding("utf-8"))
				.andExpect(status().isOk());

		verify(userRepository).findById("2");
	}



}

