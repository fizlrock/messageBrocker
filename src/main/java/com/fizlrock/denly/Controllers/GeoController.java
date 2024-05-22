package com.fizlrock.denly.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fizlrock.denly.Domain.Friendship;
import com.fizlrock.denly.Domain.User;
import com.fizlrock.denly.Repository.FriendshipRepository;
import com.fizlrock.denly.Repository.UserRepository;

import lombok.AllArgsConstructor;

@RestController("/")
@AllArgsConstructor
public class GeoController {

  private final UserRepository userRepo;
  private final FriendshipRepository friendRepo;

  /**
   * Метод регистрирует нового пользователя
   * 
   * @param username - имя пользователя
   * @param password
   */

  @PostMapping("user/new")
  public ResponseEntity<String> register(
      @RequestParam(required = true, name = "username") String username) {

    var user = userRepo.findByUsername(username);
    if (user.isPresent())
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Change you username. This already used.");
    else {
      userRepo.save(new User(username, "123456789"));
      return ResponseEntity.ok("User created");
    }
  }

  /**
   * Отправить запрос на дружбу
   * 
   * @param username        - отправитель запроса
   * @param friend_username - получатель запроса
   * @return
   */
  @PostMapping("friend/new")
  public ResponseEntity<String> sendFriendRequest(
      @RequestParam(required = true, name = "username") String username,
      @RequestParam(required = true, name = "friend") String friend_username) {

    var sender = userRepo.findByUsername(username);
    var receiver = userRepo.findByUsername(friend_username);

    if (sender.isEmpty())
      return getUserNotFoundResponce(username);
    if (receiver.isEmpty())
      return getUserNotFoundResponce(friend_username);

    var lastRequest = friendRepo.findBySenderAndReceiver(sender.get(), receiver.get());
    if (lastRequest.isPresent())
      return ResponseEntity.badRequest().body("Запрос на дружбу уже отправлен");

    var frRequest = new Friendship(sender.get(), receiver.get());

    friendRepo.save(frRequest);

    return ResponseEntity.ok("Запрос на дружбу отправлен");

  }

  private ResponseEntity<String> getUserNotFoundResponce(String username) {
    return ResponseEntity.badRequest()
        .body(String.format("Пользователь %s не найден", username));
  }

  /**
   * Подтвердить запрос на дружбу
   * 
   * @param username - пользователь, которому был отправлен запрос
   * @param friend   - пользователь, дружба с которым подтвержается
   */
  void confirmFriendRequest(
      @RequestParam(name = "username", required = true) String username,
      @RequestParam(name = "friend", required = true) String friend) {

  }

}
