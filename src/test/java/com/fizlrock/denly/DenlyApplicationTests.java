package com.fizlrock.denly;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.fizlrock.denly.Controllers.GeoController;

@SpringBootTest
class DenlyApplicationTests {

	@Autowired
	private GeoController geoController;

	@Test
	void contextLoads() {
	}

	@Test
	void testRegisterUser() {
		var responce = geoController.register("Saraarstats");
		assertTrue(responce.getStatusCode() == HttpStatus.OK);
	}

	@Test
	void testDoubleRegisterUser() {
		var r1 = geoController.register("Saraarst23");
		var r2 = geoController.register("Saraarst23");
		assertTrue(r1.getStatusCode() == HttpStatus.OK);
		assertTrue(r2.getStatusCode() == HttpStatus.BAD_REQUEST);
	}

}
