package cs5500.fireemblem.sprint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  @Id
  private ObjectId _id;
  private String username;
  private String email;
  private String password;

  public String getId() {
    return _id.toHexString();
  }
}
