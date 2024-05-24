package com.fizlrock.denly;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fizlrock.denly.Controllers.GeoController;
import com.fizlrock.denly.Domain.Friendship;
import com.fizlrock.denly.Domain.User;
import com.fizlrock.denly.Domain.Friendship.FriendshipState;
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
				.thenReturn(Optional.empty());

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

	@Test
	void sendFriendRequestToUnknownUser() throws Exception {

		String firstUser = "njenkins", secondUser = "unknownuser";

		Mockito.when(userRepo.findByUsername(firstUser))
				.thenReturn(Optional.ofNullable(new User(firstUser, "astarst")));

		Mockito.when(userRepo.findByUsername(secondUser))
				.thenReturn(Optional.empty());

		mvc.perform(post(String.format("/friend/new?username=%s&friend=%s", firstUser, secondUser)))
				.andExpect(status().isBadRequest());

		verify(userRepo, times(1)).findByUsername(firstUser);
		verify(userRepo, times(1)).findByUsername(secondUser);

		verify(userRepo, never()).save(any());
		verify(friendsRepo, never()).save(any());
	}

	@Test
	void sendFriendRequestFromUnknownUser() throws Exception {

		Mockito.when(userRepo.findByUsername("unknownuser"))
				.thenReturn(Optional.ofNullable(null));

		Mockito.when(userRepo.findByUsername("fizlrock"))
				.thenReturn(Optional.ofNullable(new User("fizlrock", "astarst")));

		mvc.perform(post("/friend/new?username=unknownuser&friend=fizlrock"))
				.andExpect(status().isBadRequest());

		verify(userRepo, times(1)).findByUsername("unknownuser");
		verify(userRepo, times(1)).findByUsername("fizlrock");

		verify(userRepo, never()).save(any());
		verify(friendsRepo, never()).save(any());
	}

	@Test
	void sendFriendRequest() throws Exception {
		String u1 = "firstUser", u2 = "secondUser", dP = "astarstarst";

		Mockito.when(userRepo.findByUsername(u1))
				.thenReturn(Optional.of(new User(u1, dP)));

		Mockito.when(userRepo.findByUsername(u2))
				.thenReturn(Optional.of(new User(u2, dP)));

		String request = String.format("/friend/new?username=%s&friend=%s", u1, u2);

		mvc.perform(post(request))
				.andExpect(status().isOk());

		verify(userRepo).findByUsername(u1);
		verify(userRepo).findByUsername(u2);
		verify(userRepo, never()).save(any());

		var ag = ArgumentCaptor.forClass(Friendship.class);
		verify(friendsRepo).save(ag.capture());

		assertEquals(u1, ag.getValue().getSender().getUsername());
		assertEquals(u2, ag.getValue().getReceiver().getUsername());
		assertEquals(ag.getValue().getState(), FriendshipState.Requested);
	}

	@Test
	void confirmFriendRequest() throws Exception {

		String u1 = "firstUser", u2 = "secondUser", dP = "astarstarst";
		User user1 = new User(u1, dP), user2 = new User(u1, dP);

		Mockito.when(userRepo.findByUsername(u1))
				.thenReturn(Optional.of(user1));

		Mockito.when(userRepo.findByUsername(u2))
				.thenReturn(Optional.of(user2));

		String request = "/friend/confirm?username=firstUser&friend=secondUser";
		mvc.perform(post(request)).andExpect(status().isBadRequest());
		verify(userRepo).findByUsername(u1);
		verify(userRepo).findByUsername(u2);
		verify(userRepo, never()).save(any());

		verify(friendsRepo).findBySenderIdAndReceiverId(user1.getId(), user2.getId());

	}

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
