package cs5500.fireemblem.sprint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {
  @Autowired
  private ActivityRepository activityRepository;

  private static final Logger LOGGER = LoggerFactory.getLogger(ActivityService.class);

  /* Basic CRUD operations */

  // getAll, getSingle, getSingleByDate
  public List<Activity> allActivities() {
    return activityRepository.findAll();
  }

  public Optional<Activity> singleActivity(ObjectId id) {
    return activityRepository.findById(id);
  }

  public Optional<Activity> singleActivityByDate(String date) {
    return activityRepository.findActivityByDate(date);
  }

  // post method
//  public Activity createActivity(Activity activity) {
//    return activityRepository.save(activity);
//  }

  public Activity createActivity(Map<String, Object> payload) {
    LOGGER.info("activityService reached", payload);


    Activity activity = new Activity();
    activity.setUserId(new ObjectId((String) payload.get("userId")));
//    activity.setDate((String) payload.get("date"));

    String originalDate = (String) payload.get("date");
    String formattedDate = originalDate.replace("-", "");
    activity.setDate(formattedDate);

    List<Object> summaries = new ArrayList<>();
    Map<String, Object> summary = new HashMap<>();
    summary.put("activity", (String) payload.get("activity"));
//    summary.put("duration", ((Number) payload.get("duration")).intValue());
    summary.put("duration", ( payload.get("duration")));
    summaries.add(summary);
    activity.setSummary(summaries);

    return activityRepository.save(activity);
  }


  // delete method
  public void deleteActivity(String id) {
    activityRepository.deleteById(new ObjectId(id));
  }

  // update method
  public Optional<Activity> updateActivity(Activity activity, ObjectId id) {
    // The purpose of the Optional class is to provide a better alternative
    // to returning a null value, which can help prevent NullPointerExceptions.
    Optional<Activity> optionalActivity = activityRepository.findById(id);

    if (optionalActivity.isPresent()) {
      Activity prev = optionalActivity.get();

      // Update the fields only if they are not null
      if (activity.getDate() != null) {
        String originalDate = (String) activity.getDate();
        String formattedDate = originalDate.replace("-", "");
        prev.setDate(formattedDate);
      }
      if (activity.getSummary() != null) {
        prev.setSummary(activity.getSummary());
      }
      if (activity.getLastUpdate() != null) {
        prev.setLastUpdate(activity.getLastUpdate());
      }
      if (activity.getSegments() != null) {
        prev.setSegments(activity.getSegments());
      }
      if (activity.getCalories() != null) {
        prev.setCalories(activity.getCalories());
      }

      activityRepository.save(prev);
      return Optional.of(prev);
    } else {
      // return an empty Optional to indicate that no matching object was found
      return Optional.empty();
    }
  }

  /* Advanced operations */

  // get daily activity duration for a specific type of activity
  public List<Map<String, Object>> getActivityDurationsByActivity(String activity) {
    List<Activity> activities = activityRepository.findAll();
    List<Map<String, Object>> durationDatePairs = new ArrayList<>();

    for (Activity a : activities) {
      if (a.getSummary() == null) {
        continue;
      }
      String date = a.getDate();
      List<Object> summaries = a.getSummary();

      for (Object s : summaries) {
        Map<String, Object> summary = (Map<String, Object>) s;

        if (activity.equals(summary.get("activity"))) {
          Map<String, Object> durationDatePair = new HashMap<>();
          durationDatePair.put("date", date);
          durationDatePair.put("duration", summary.get("duration"));
          durationDatePairs.add(durationDatePair);
        }
      }
    }

    // sort the list based on the date
    durationDatePairs.sort(Comparator.comparing(o -> (String) o.get("date")));
    return durationDatePairs;
  }

  // get all types of activities
  public List<String> getAllActivityTypes() {
    List<Activity> activities = activityRepository.findAll();
    List<String> activityTypes = new ArrayList<>();
    for (Activity a : activities) {
      // need to check null value
      if (a.getSummary() == null) {
        continue;
      }

      List<Object> summaries = a.getSummary();
      for (Object s : summaries) {
        Map<String, Object> summary = (Map<String, Object>) s;
        String activityType = (String) summary.get("activity");
        if (!activityTypes.contains(activityType)) {
          activityTypes.add(activityType);
        }
      }
    }
    return activityTypes;
  }

  // get activity types and the sum of each duration
  // The method  returns a list of maps. Each map in the list represents an activity type
  // along with its total duration. The map has two keys:
  //    1. "activity": The value for this key is a String representing the activity type. E.g., walking
  //    2. "totalDuration": The value for this key is an Integer representing the total duration of that activity type. E.g., 23425
  public List<Map<String, Object>> getAllActivityTypesWithDurations() {
    List<Activity> activities = activityRepository.findAll();
    Map<String, Integer> activityTypeDurations = new HashMap<>();

    for (Activity a : activities) {
      if (a.getSummary() == null) {
        continue;
      }

      List<Object> summaries = a.getSummary();
      for (Object s : summaries) {
        Map<String, Object> summary = (Map<String, Object>) s;
        String activityType = (String) summary.get("activity");
        int duration = (int) summary.get("duration");

        activityTypeDurations.put(activityType, activityTypeDurations.getOrDefault(activityType, 0) + duration);
      }
    }

    List<Map<String, Object>> sortedActivityTypeDurations = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : activityTypeDurations.entrySet()) {
      Map<String, Object> activityDuration = new HashMap<>();
      activityDuration.put("activity", entry.getKey());
      activityDuration.put("totalDuration", entry.getValue());
      sortedActivityTypeDurations.add(activityDuration);
    }

    sortedActivityTypeDurations.sort((o1, o2) -> ((Integer) o2.get("totalDuration")).compareTo((Integer) o1.get("totalDuration")));
    return sortedActivityTypeDurations;
  }

  public List<String> getAllLocations() {
    List<Activity> activities = activityRepository.findAll();
    List<String> locationNames = new ArrayList<>();

    for (Activity a : activities) {
      if (a.getSegments() == null) {
        continue;
      }

      List<Object> segments = a.getSegments();
      for (Object s : segments) {
        Map<String, Object> segment = (Map<String, Object>) s;
        String type = (String) segment.get("type");
        if (type.equals("place")) {
          Map<String, Object> place = (Map<String, Object>) segment.get("place");
          String location = (String) place.get("name");
          if (location != null && !locationNames.contains(location)) {
            locationNames.add(location);
          }
        }
      }
    }

    return locationNames;
  }

  public List<Map<String, Object>> getTopVisitedLocations() {
    List<Activity> activities = activityRepository.findAll();
    Map<String, Integer> locationFreqs = new HashMap<>();

    for (Activity a : activities) {
      if (a.getSegments() == null) {
        continue;
      }

      List<Object> segments = a.getSegments();
      for (Object s : segments) {
        Map<String, Object> segment = (Map<String, Object>) s;
        String type = (String) segment.get("type");
        if (type.equals("place")) {
          Map<String, Object> place = (Map<String, Object>) segment.get("place");
          String location = (String) place.get("name");
          if (location != null) {
            locationFreqs.put(location, locationFreqs.getOrDefault(location, 0) + 1);
          }
        }
      }

    }

    List<Map<String, Object>> sortedLocationFreqs = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : locationFreqs.entrySet()) {
      Map<String, Object> locFreq = new HashMap<>();
      locFreq.put("location", entry.getKey());
      locFreq.put("frequency", entry.getValue());
      sortedLocationFreqs.add(locFreq);
    }

    sortedLocationFreqs.sort((o1, o2) -> ((Integer) o2.get("frequency")).compareTo((Integer) o1.get("frequency")));
    List<Map<String, Object>> topTenLocations = sortedLocationFreqs.subList(0, 10);

    return topTenLocations;


  }

//  public List<Map<String, Object>> getSimplifiedActivities() {
//    List<Activity> activities = activityRepository.findAll();
//    List<Map<String, Object>> simplifiedActivities = new ArrayList<>();
//
//    for (Activity a : activities) {
//      if (a.getSummary() == null || a.getSegments() == null) {
//        continue;
//      }
//
//      List<Object> summaries = a.getSummary();
//      List<Object> segments = a.getSegments();
//      String location = null;
//
//      for (Object s : segments) {
//        Map<String, Object> segment = (Map<String, Object>) s;
//        String type = (String) segment.get("type");
//        if (type.equals("place")) {
//          Map<String, Object> place = (Map<String, Object>) segment.get("place");
//          location = (String) place.get("name");
//          break;
//        }
//      }
//
//      for (Object s : summaries) {
//        Map<String, Object> summary = (Map<String, Object>) s;
//        Map<String, Object> simplifiedActivity = new HashMap<>();
//        simplifiedActivity.put("date", a.getDate());
//        simplifiedActivity.put("type", summary.get("activity"));
//        simplifiedActivity.put("duration", summary.get("duration"));
//        simplifiedActivity.put("location", location);
//        simplifiedActivities.add(simplifiedActivity);
//      }
//    }
//
//    return simplifiedActivities;
//  }
private List<Map<String, Object>> simplifyActivities(List<Activity> activities) {
  List<Map<String, Object>> simplifiedActivities = new ArrayList<>();

  for (Activity a : activities) {
    if (a.getSummary() == null || a.getSegments() == null) {
      continue;
    }

    List<Object> summaries = a.getSummary();
    List<Object> segments = a.getSegments();
    String location = null;

    for (Object s : segments) {
      Map<String, Object> segment = (Map<String, Object>) s;
      String type = (String) segment.get("type");
      if (type.equals("place")) {
        Map<String, Object> place = (Map<String, Object>) segment.get("place");
        location = (String) place.get("name");
        break;
      }
    }

    for (Object s : summaries) {
      Map<String, Object> summary = (Map<String, Object>) s;
      Map<String, Object> simplifiedActivity = new HashMap<>();
      simplifiedActivity.put("date", a.getDate());
      simplifiedActivity.put("type", summary.get("activity"));
      simplifiedActivity.put("duration", summary.get("duration"));
      simplifiedActivity.put("location", location);
      simplifiedActivities.add(simplifiedActivity);
    }
  }

  return simplifiedActivities;
}

  public List<Map<String, Object>> getSimplifiedActivities() {
    List<Activity> activities = activityRepository.findAll();
    return simplifyActivities(activities);
  }


//  public List<Activity> getUserActivities(ObjectId userId) {
//    LOGGER.info("Fetching activities for user with ID: {}", userId);
//    List<Activity> activities = activityRepository.findByUserId(userId);
//    LOGGER.info("Found {} activities for user with ID: {}", activities.size(), userId);
//    return activities;
//  }

  public Optional<List<Activity>> getActivitiesByUserId(ObjectId userId) {
    Optional<List<Activity>> activities = activityRepository.findByUserId(userId);
    System.out.println("Activities from repository: " + activities);
    return activities;
  }

  public Optional<List<Activity>> getActivityByUserIdAndDate(ObjectId userId, String date) {
    Optional<List<Activity>> activities = activityRepository.findByUserIdAndDate(userId, date);
    System.out.println("Activities from repository: " + activities);
    return activities;
  }

  public List<Map<String, Object>> getAllActivityTypesWithDurationsByUserId(ObjectId userId) {
    List<Activity> activities = activityRepository.findByUserId(userId).orElse(new ArrayList<>());
    Map<String, Integer> activityTypeDurations = new HashMap<>();

    for (Activity a : activities) {
      if (a.getSummary() == null) {
        continue;
      }

      List<Object> summaries = a.getSummary();
      for (Object s : summaries) {
        Map<String, Object> summary = (Map<String, Object>) s;
        String activityType = (String) summary.get("activity");
        int duration = Integer.parseInt(summary.get("duration").toString());

        activityTypeDurations.put(activityType, activityTypeDurations.getOrDefault(activityType, 0) + duration);
      }
    }

    List<Map<String, Object>> sortedActivityTypeDurations = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : activityTypeDurations.entrySet()) {
      Map<String, Object> activityDuration = new HashMap<>();
      activityDuration.put("activity", entry.getKey());
      activityDuration.put("totalDuration", entry.getValue());
      sortedActivityTypeDurations.add(activityDuration);
    }

    sortedActivityTypeDurations.sort((o1, o2) -> ((Integer) o2.get("totalDuration")).compareTo((Integer) o1.get("totalDuration")));
    return sortedActivityTypeDurations;
  }


}
