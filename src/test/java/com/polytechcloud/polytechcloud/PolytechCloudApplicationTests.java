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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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


	//.andDo(MockMvcResultHandlers.print())

	/* Tests GET */
	@Test
	public void testGetAllUsers_NoContent() throws Exception {

		when(userRepository.findAll()).thenReturn(new ArrayList<>());
		mockMvc.perform(get("/user").characterEncoding("utf-8"))
				.andExpect(status().isNoContent());

		verify(userRepository).findAll();
	}

	/*@Test
	public void testGetAllUsers_NoError() throws Exception {
		when(userRepository.findAll()).thenReturn()
	}*/

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

//MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
//      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
//

	/* private User user1;
	private User user2;
	private User user3;

	@Before
	public void prepareTests() {
		user1 = new User(1, "Aaaa", "Bbbb", Date.from(Instant.now()), 12.4, 45.7);
		user2 = new User(2, "Ccccc", "Ddddd", Date.from(Instant.now()), 46.6, 67.7);
		user3 = new User("Eeee", "Fffff", Date.from(Instant.now()), 10.9, 23.6);

		assertNotNull(user1);
		assertNotNull(user2);
		assertNotNull(user3);
	} */




	/*@Test
	public void testDeserialize() throws Exception {
		String content = "{\"make\":\"Ford\",\"model\":\"Focus\"}";
		assertThat(this.json.parse(content))
				.isEqualTo(new VehicleDetails("Ford", "Focus"));
		assertThat(this.json.parseObject(content).getMake()).isEqualTo("Ford");
	}*/


