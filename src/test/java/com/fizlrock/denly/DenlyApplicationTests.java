package com.fizlrock.denly;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fizlrock.denly.Controllers.GeoController;
import com.fizlrock.denly.Domain.User;
import com.fizlrock.denly.Repository.FriendshipRepository;
import com.fizlrock.denly.Repository.UserRepository;

@WebMvcTest(GeoController.class)
class DenlyApplicationTests {

	// @Autowired
	// private GeoController geoController;

	@Autowired
	MockMvc mvc;

	@MockBean
	UserRepository userRepo;

	@MockBean
	FriendshipRepository friendsRepo;

	@Test
	void testRegisterUser() throws Exception {

		Mockito.when(userRepo.findByUsername("njenkins"))
				.thenReturn(Optional.ofNullable(null));

		mvc.perform(
				post("/user/new?username=njenkins"))
				.andExpect(status().isOk());

		verify(userRepo).findByUsername("njenkins");

		var requestCaptor = ArgumentCaptor.forClass(User.class);

		verify(userRepo).save(requestCaptor.capture());

		assertEquals("njenkins", requestCaptor.getValue().getUsername());

	}

	@Test
	void registerUserWithRepeatUsername() throws Exception {

		Mockito.when(userRepo.findByUsername("njenkins"))
				.thenReturn(Optional.of(new User("njenkins", "arstoiaesntaortsen")));

		mvc.perform(post("/user/new?username=njenkins"))
				.andExpect(status().isBadRequest());

		verify(userRepo).findByUsername("njenkins");
		verify(userRepo, never()).save(any());
	}

	// @Test
	// void testDoubleRegisterUser() {
	// var r1 = geoController.register("Saraarst23");
	// var r2 = geoController.register("Saraarst23");
	// assertTrue(r1.getStatusCode() == HttpStatus.OK);
	// assertTrue(r2.getStatusCode() == HttpStatus.BAD_REQUEST);
	// }

	// @Test
	// void sendFriendRequestFromUnknownUser() {
	// var r = geoController.sendFriendRequest("unknownuser", "unknownuser");
	// assertTrue(r.getStatusCode() == HttpStatus.BAD_REQUEST);
	// }

	// @Test
	// void sendFriendRequestToUnknownUser() {
	// geoController.register("userarst1");
	// var r = geoController.sendFriendRequest("userarst1", "unknownuser");
	// assertTrue(r.getStatusCode() == HttpStatus.BAD_REQUEST);
	// }

	// @Test
	// void sendRepeatedFriendRequest() {
	// geoController.register("userAAAAA1");
	// geoController.register("userAAAAA2");
	// var r = geoController.sendFriendRequest("userAAAAA1", "userAAAAA2");
	// assertTrue(r.getStatusCode() == HttpStatus.OK);
	// var r2 = geoController.sendFriendRequest("userAAAAA1", "userAAAAA2");
	// assertTrue(r2.getStatusCode() == HttpStatus.BAD_REQUEST);
	// }

}
