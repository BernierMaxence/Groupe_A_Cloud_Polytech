package com.polytechcloud.polytechcloud;

import com.polytechcloud.polytechcloud.controller.BasicController;
import com.polytechcloud.polytechcloud.repository.UserRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)

public class PolytechCloudApplicationTests {

	private MockMvc mockMvc;

	@Mock
	private UserRepository userRepository;

	// Using @InjectMock because userRepository is @Autowired in BasicController and userRepository is @Mock
	@InjectMocks
	private BasicController basicController;

	@Before
	public void setup() {
		// Init mocked elements
		MockitoAnnotations.initMocks(this);

		//standAlone setup initializes MockMvc without loading Spring configuration
		// --> will mock dependencies withing the controller out using Mockito.
		mockMvc = MockMvcBuilders.standaloneSetup(basicController).build();

	}



}

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


