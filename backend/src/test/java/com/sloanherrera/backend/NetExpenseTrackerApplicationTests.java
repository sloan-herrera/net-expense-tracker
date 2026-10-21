package com.sloanherrera.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Verifies that the Spring Boot application context loads successfully using the 
 * local configuration.
 */

@SpringBootTest
@ActiveProfiles("local") // Ensure local profile is active for testing.
class NetExpenseTrackerApplicationTests {

	@Test
	void contextLoads() {
	}

}
