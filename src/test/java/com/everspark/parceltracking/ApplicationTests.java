package com.everspark.parceltracking;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void mainMethod_ShouldRunApplicationWithoutExceptions() {
		Application.main(new String[]{});
	}

}
