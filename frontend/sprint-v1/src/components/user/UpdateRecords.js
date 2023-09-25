import React, { useState, useContext } from 'react';
import api from '../../api/axiosConfig';
import UserContext from '../../UserContext';
import '../../UpdateRecords.css';

const UpdateRecord = () => {
  const { user } = useContext(UserContext);
  const [date, setDate] = useState('');
  const [activities, setActivities] = useState(null);
  const [selectedActivity, setSelectedActivity] = useState(null);
  const [duration, setDuration] = useState('');

  const [newDate, setNewDate] = useState('');
  const [newActivity, setNewActivity] = useState('');

  const handleSearch = async (e) => {
    e.preventDefault();

    if (!user) {
      console.log('User not logged in');
      return;
    }

    await fetchActivities();
  };

//   const handleUpdate = async (e) => {
//     e.preventDefault();

//     if (!selectedActivity) return;

//     try {
//       const updatedActivity = { ...selectedActivity, date: newDate, activity: newActivity, duration };
//       const response = await api.put(`/api/v1/activities/update/${selectedActivity._id.$oid}`, updatedActivity);
//       console.log('Activity updated:', response.data);

//       // Refresh activities list
//       handleSearch(e);
//     } catch (error) {
//       console.error('Error updating activity:', error);
//     }
//   };

    const handleUpdate = async (e) => {
    e.preventDefault();
  
    if (!selectedActivity) return;
  
    try {
        const updatedActivity = {
            ...selectedActivity,
            date: newDate || selectedActivity.date,
            summary: [
              {
                activity: newActivity || selectedActivity.summary[0].activity,
                duration: duration || selectedActivity.summary[0].duration,
              },
            ],
          };
          
          if (selectedActivity.calories !== null) {
            updatedActivity.calories = selectedActivity.calories;
          }
          
          if (selectedActivity.lastUpdate !== null) {
            updatedActivity.lastUpdate = selectedActivity.lastUpdate;
          }
          
          if (selectedActivity.segments !== null) {
            updatedActivity.segments = selectedActivity.segments;
          };


        const response = await api.put(
          `/api/v1/activities/update/${selectedActivity._id}`,
          updatedActivity
        );
        console.log('Activity updated:', response.data);
    
        // Refresh activities list
        handleSearch(e);
      } catch (error) {
        console.error('Error updating activity:', error);
      }
  };

  const fetchActivities = async () => {
    if (date) {
      // Format date from YYYY-MM-DD to YYYYMMDD
      const formattedDate = date.replaceAll("-", "");
  
      try {
        const response = await api.get(
          `/api/v1/activities/user/${user.userID}/date/${formattedDate}`
        );
        console.log('Raw response:', response);
        console.log('Activity found:', response.data);

        setActivities(response.data);
        // console.log('Activities set:', activities.date);
        console.log('Activities exist:', activities != null);
        // console.log('Activities length:', activities.summary.map((activity) => activity.duration));


      } catch (error) {
        console.error("Error fetching activities:", error);
      }
    }
  };

  const handleDelete = async (activityId) => {
    try {
      await api.delete(`/api/v1/activities/delete/${activityId}`);
      console.log('Activity deleted:', activityId);
  
      // Refresh activities list
      handleSearch({ preventDefault: () => {} });
    } catch (error) {
      console.error('Error deleting activity:', error);
    }
  };

  const activityTypes = [
    "transport",
    "walking",
    "cycling",
    "airplane",
    "running",
    "ferry",
    "cross_country_skiing",
    "kayaking",
    "train",
    "bus",
  ];


  return (
    <div className="update-record">
    <h2>Update Record</h2>
    <form onSubmit={handleSearch}>
    <div>
        <label htmlFor="date">Date:</label>
        <input
        type="date"
        id="date"
        value={date}
        onChange={(e) => setDate(e.target.value)}
        required
        />
    </div>
    <button type="submit">Search</button>
    </form>
    {activities && activities.length >= 0 && (
    <div>
        <h3>Activities on {date}:</h3>
        <table>
        <thead>
            <tr>
            <th>Activity</th>
            <th>Duration</th>
            <th>Action</th>
            </tr>
        </thead>
        <tbody>
            {activities.map((activity, index) => (
            <tr key={index}>
                <td>{activity.summary.map((activity) => activity.activity)}</td>
                <td>{activity.summary.map((activity) => activity.duration)} minutes</td>
                <td>
                <button onClick={() => setSelectedActivity(activity)}>
                    Modify
                </button>
                </td>
                <td>
                    <button onClick={() => handleDelete(activity._id)}>
                    Delete
                    </button>
                </td>
            </tr>
            ))}
        </tbody>
        </table>
    </div>
    )}

    {selectedActivity && (
    <form onSubmit={handleUpdate}>
        <h3>Update Activity</h3>
        <div>
        <label htmlFor="newDate">New Date:</label>
        <input
                type="date"
                id="newDate"
                value={newDate}
                onChange={(e) => setNewDate(e.target.value)}
            />
            </div>
            <div>
            <label htmlFor="newActivity">New Activity:</label>
            {/* <input
                type="text"
                id="newActivity"
                value={newActivity}
                onChange={(e) => setNewActivity(e.target.value)}
            /> */}
            <select
                id="newActivity"
                value={newActivity}
                onChange={(e) => setNewActivity(e.target.value)}
                required
            >
                <option value="">Select New Activity</option>
                {activityTypes.map((type) => (
                <option key={type} value={type}>
                    {type}
                </option>
                ))}
            </select>

            </div>
            <div>
            <label htmlFor="duration">New Duration:</label>
            <input
                type="number"
                id="duration"
                value={duration}
                onChange={(e) => setDuration(e.target.value)}
                required
            />
            </div>
            <button type="submit">Update</button>
            <button type="button" onClick={() => setSelectedActivity(null)}>
            Cancel
            </button>
        </form>
        )}
    </div>
    );
    };

export default UpdateRecord;
