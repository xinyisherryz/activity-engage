package cs5500.fireemblem.sprint;

import java.util.Optional;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.Query;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;



@Repository
public interface ActivityRepository extends MongoRepository<Activity, ObjectId> {

  Optional<Activity> findActivityByDate(String date);


  // Custom query to find Activities by "activity" field in the "summary"
//  @Query("{'summary': {$elemMatch: {'activity': ?0}}}")
//  List<Activity> findBySummaryActivity(String activity);

  @Query("{ 'userId': ?0 }")
  Optional<List<Activity>> findByUserId(ObjectId userId);

  Optional<List<Activity>> findByUserIdAndDate(ObjectId userId, String date);


}
