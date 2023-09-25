//package cs5500.fireemblem.sprint;
//
//import com.sun.source.doctree.SummaryTree;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.bson.types.ObjectId;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//import java.util.List;
//
//@Document(collection = "storyline")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class Activity {
//  @Id
//  private ObjectId _id;
//  private String date;
//  private List<Object> summary;
//  private List<Object> segments;
//  private Integer calories;
//  private String lastUpdate;
//  // other fields
//  private ObjectId userId;
//}

package cs5500.fireemblem.sprint;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.source.doctree.SummaryTree;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "storyline")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
  @Id
  @JsonSerialize(using = ObjectIdSerializer.class)
  private ObjectId _id;
  private String date;
  private List<Object> summary;
  private List<Object> segments;
  private Integer calories;
  private String lastUpdate;
  // other fields

  @JsonSerialize(using = ObjectIdSerializer.class)
  private ObjectId userId;
}
