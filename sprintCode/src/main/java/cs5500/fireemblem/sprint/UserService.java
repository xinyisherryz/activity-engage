package cs5500.fireemblem.sprint;

import io.jsonwebtoken.SignatureAlgorithm;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;



@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
  private static final long EXPIRATION_TIME = 86400000; // 24 hours




  public List<User> allUsers() {
    return userRepository.findAll();
  }

  public Optional<User> singleUser(ObjectId id) {
    return userRepository.findById(id);
  }

//  public User createUser(User user) {
//    return userRepository.save(user);
//  }

  public void deleteUser(String id) {
    userRepository.deleteById(new ObjectId(id));
  }

  public Optional<User> updateUser(User user, String id) {
    Optional<User> optionalUser = userRepository.findById(new ObjectId(id));

    if (optionalUser.isPresent()) {
      User prev = optionalUser.get();

      // Update the fields only if they are not null
      if (user.getUsername() != null) {
        prev.setUsername(user.getUsername());
      }
      if (user.getEmail() != null) {
        prev.setEmail(user.getEmail());
      }
      if (user.getPassword() != null) {
        prev.setPassword(user.getPassword());
      }

      userRepository.save(prev);
      return Optional.of(prev);
    } else {
      return Optional.empty();
    }
  }

  // auth
  public User createUser(User user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

//  public String loginUser(String email, String password) {
//    System.out.println("Authenticating user"); // add log
//    User user = userRepository.findByEmail(email);
//
//    if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
//      // Generate JWT token
//      return Jwts.builder()
//          .setSubject(email)
//          .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//          .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
//          .compact();
//    } else {
//      System.out.println("Authentication failed"); // add log
//      return null;
//    }
//  }

  public String loginUser(String email, String password) {
    System.out.println("Authenticating user"); // add log
    User user = userRepository.findByEmail(email);

    if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
      // Generate JWT token
      return Jwts.builder()
          .setSubject(email)
          .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
          .claim("user_id", user.getId()) // add userId to jwt payload
          .signWith(SECRET_KEY)
          .compact();
    } else {
      System.out.println("Authentication failed"); // add log
      return null;
    }
  }

  public void logoutUser() {
    // no functionality in the backend as it will be handled by the frontend
    System.out.println("User has logged out.");
  }


}
