package com.fizlrock.denly.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fizlrock.denly.Domain.User;
import com.fizlrock.denly.Repository.UserRepository;

import lombok.AllArgsConstructor;

@RestController("/")
@AllArgsConstructor
public class GeoController {

  private final UserRepository userRepo;

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
      var new_user = userRepo.save(new User(username, "123456789"));
      return ResponseEntity.ok("User created");
    }
  }

  /**
   * Отправить запрос на дружбу
   * 
   * @param username        - отправитель запроса
   * @param friend_username - получатель запроса
   */
  void sendFriendRequest(
      @RequestParam(required = true, name = "username") String username,
      @RequestParam(required = true, name = "friend") String friend_username) {
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
