package com.fizlrock.denly.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.fizlrock.denly.Domain.Friendship;
import com.fizlrock.denly.Domain.User;

/**
 * 
 */

public interface FriendshipRepository extends CrudRepository<Friendship, Long> {

  Optional<Friendship> findBySenderIdAndReceiverId(String senderId, String recieverId);

  default Optional<Friendship> findBySenderAndReceiver(User sender, User reciever) {
    return findBySenderIdAndReceiverId(sender.getId(), reciever.getId());
  }

}
