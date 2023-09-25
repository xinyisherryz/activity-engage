package cs5500.fireemblem.sprint;

import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
  @Autowired
  private UserService userService;

  @GetMapping
  public ResponseEntity<List<User>> getAllUsers() {
    return new ResponseEntity<List<User>>(userService.allUsers(), HttpStatus.OK);
  }

  @GetMapping("id/{id}")
  public ResponseEntity<Optional<User>> getSingleUser(@PathVariable ObjectId id) {
    return new ResponseEntity<Optional<User>>(userService.singleUser(id), HttpStatus.OK);
  }

  @PostMapping("post")
  public User createUser(@RequestBody User user) {
    return userService.createUser(user);
  }

  @DeleteMapping("/delete/{id}")
  public void deleteUser(@PathVariable String id) {
    userService.deleteUser(id);
  }

  @PutMapping("update/{id}")
  public Optional<User> updateUser(@RequestBody User user,
      @PathVariable String id) {
    return userService.updateUser(user, id);
  }

  // auth
  @PostMapping("login")
  public ResponseEntity<?> loginUser(@RequestBody User user) {
    System.out.println("Login request received"); // add log
    String email = user.getEmail();
    String password = user.getPassword();

    // Authenticate the user and generate a token
    String token = userService.loginUser(email, password);

    if (token != null) {
      // Return the token as a response
      return new ResponseEntity<>(token, HttpStatus.OK);
    } else {
      // Authentication failed
      return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
    }
  }

  @PostMapping("logout")
  public ResponseEntity<?> logoutUser() {
    // will be handled by the frontend
    userService.logoutUser();
    return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
  }

}
