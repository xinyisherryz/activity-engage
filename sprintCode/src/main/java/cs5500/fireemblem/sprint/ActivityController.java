package cs5500.fireemblem.sprint;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import javax.servlet.http.HttpServletRequest;
import org.bson.types.ObjectId;


@RestController
@RequestMapping("/api/v1/activities")
public class ActivityController {
  @Autowired
  private ActivityService activityService;

  private static final Logger LOGGER =
      LoggerFactory.getLogger(ActivityController.class);

  @GetMapping
  public ResponseEntity<List<Activity>> getAllActivities() {
    return new ResponseEntity<List<Activity>>(activityService.allActivities(), HttpStatus.OK);
  }

  @GetMapping("id/{id}")
  public ResponseEntity<Optional<Activity>> getSingleActivity(@PathVariable ObjectId id) {
    return new ResponseEntity<Optional<Activity>>(activityService.singleActivity(id), HttpStatus.OK);
  }

  @GetMapping("date/{date}")
  public ResponseEntity<Optional<Activity>> getSingleActivityByDate(@PathVariable String date) {
    return new ResponseEntity<Optional<Activity>>(activityService.singleActivityByDate(date), HttpStatus.OK);
  }

  // post
//  @PostMapping("post")
//  public Activity createActivity(@RequestBody Activity activity) {
//    return activityService.createActivity(activity);
//  }

  @PostMapping("/post")
  public Activity createActivity(@RequestBody Map<String, Object> payload) {
    LOGGER.info("Payload received: " + payload);
    LOGGER.info("activityService activated");

    return activityService.createActivity(payload);
  }

  @GetMapping("/test")
  public String test() {
    System.out.println("Test route activated");
    return "Test successful";
  }

  // delete
  @DeleteMapping("/delete/{id}")
  public void deleteActivity(@PathVariable String id) {
    activityService.deleteActivity(id);
  }

  // update
  @PutMapping("update/{id}")
  public Optional<Activity> updateActivity(@RequestBody Activity activity,
      @PathVariable ObjectId id) {
    return activityService.updateActivity(activity, id);
  }



  // get a specific activity's duration in every day.
  @GetMapping("durations/{activity}")
  public ResponseEntity<List<Map<String, Object>>> getActivityDurationsByActivity(@PathVariable String activity) {
    return new ResponseEntity<>(activityService.getActivityDurationsByActivity(activity), HttpStatus.OK);
  }

  // get all types of activities
  @GetMapping("/activitytypes")
  public ResponseEntity<List<String>> getAllActivityTypes() {
    return new ResponseEntity<>(activityService.getAllActivityTypes(), HttpStatus.OK);
  }

  // get activity types and the sum of each duration
  @GetMapping("/activitydurations")
  public ResponseEntity<List<Map<String, Object>>> getAllActivityTypesWithDurations() {
    return new ResponseEntity<>(activityService.getAllActivityTypesWithDurations(), HttpStatus.OK);
  }

  @GetMapping("/locations")
  public ResponseEntity<List<String>> getAllLocations() {
    return new ResponseEntity<>(activityService.getAllLocations(), HttpStatus.OK);
  }

  @GetMapping("/locations/top10")
  public ResponseEntity<List<Map<String, Object>>> getTopVisitedLocations() {
    return new ResponseEntity<>(activityService.getTopVisitedLocations(), HttpStatus.OK);
  }

  @GetMapping("/simplified")
  public ResponseEntity<List<Map<String, Object>>> getSimplifiedActivities() {
    return new ResponseEntity<>(activityService.getSimplifiedActivities(), HttpStatus.OK);
  }

//  @GetMapping("/userActivities")
//  public ResponseEntity<List<Activity>> getUserActivities(HttpServletRequest request) {
//    ObjectId userId = (ObjectId) request.getAttribute("userId");
//    List<Activity> userActivities = activityService.getUserActivities(userId);
//    return new ResponseEntity<>(userActivities, HttpStatus.OK);
//  }

  @GetMapping("user/{userId}")
  public ResponseEntity<Optional<List<Activity>>> getActivitiesByUserId(@PathVariable String userId) {
    ObjectId objectId = new ObjectId(userId);
    return new ResponseEntity<Optional<List<Activity>>>(activityService.getActivitiesByUserId(objectId), HttpStatus.OK);
  }

  @GetMapping("user/{userId}/date/{date}")
  public ResponseEntity<Optional<List<Activity>>> getActivityByUserIdAndDate(@PathVariable String userId, @PathVariable String date) {
    LOGGER.info("GOGOGO");
    ObjectId objectId = new ObjectId(userId);
    return new ResponseEntity<Optional<List<Activity>>>(activityService.getActivityByUserIdAndDate(objectId, date), HttpStatus.OK);
  }

  @GetMapping("/activitydurations/user/{userId}")
  public ResponseEntity<List<Map<String, Object>>> getAllActivityTypesWithDurationsByUserId(@PathVariable("userId") ObjectId userId) {
    return new ResponseEntity<>(activityService.getAllActivityTypesWithDurationsByUserId(userId), HttpStatus.OK);
  }





}
