package com.polytechcloud.polytechcloud;

import com.polytechcloud.polytechcloud.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PolytechCloudApplicationTests {

	@Test
	public void test() {
		assertEquals(1, 1);
	}
}

