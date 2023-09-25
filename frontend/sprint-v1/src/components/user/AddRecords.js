// AddRecord.js

import React, { useState, useContext } from "react";
import UserContext from "../../UserContext";
import { useNavigate } from "react-router-dom";
import api from "../../api/axiosConfig";
import '../../AddRecords.css';

const AddRecord = () => {
  const { user } = useContext(UserContext);
  const navigate = useNavigate();
  const [date, setDate] = useState("");
  const [calories, setCalories] = useState("");
  const [activity, setActivity] = useState("");
  const [duration, setDuration] = useState("");

//   const handleSubmit = (e) => {
//     e.preventDefault();
//     if (user) {
//       // add userId
//       console.log("Form submitted:", { userID: user.userID, date, calories, activity, duration });
//       // submit form
//     } else {
//       console.log("User not logged in");
//     }

//     // navigate back to home page
//     navigate("/");
//   };

const handleSubmit = async (e) => {
    e.preventDefault();
    if (user) {
      // Add userId
      console.log("Form submitted:", {
        userId: user.userID,
        date,
        calories,
        activity,
        duration,
      });
      // Submit form
      try {
        const response = await api.post("/api/v1/activities/post", {
          userId: user.userID,
          date,
          calories,
          activity,
          duration,
        });
        console.log("Activity created:", response.data);
      } catch (error) {
        console.error("Error creating activity:", error);
      }
    } else {
      console.log("User not logged in");
    }

    // Navigate back to home page
    navigate("/");
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
    <div className="add-record">
      <h2>Add Record</h2>
      <form onSubmit={handleSubmit}>
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
        <div>
          <label htmlFor="calories">Calories:</label>
          <input
            type="number"
            id="calories"
            value={calories}
            onChange={(e) => setCalories(e.target.value)}
            required
          />
        </div>
        <div>
            <label htmlFor="activity">Activity:</label>
            <select
                id="activity"
                value={activity}
                onChange={(e) => setActivity(e.target.value)}
                required
            >
                <option value="">Select activity</option>
                {activityTypes.map((type) => (
                <option key={type} value={type}>
                    {type}
                </option>
                ))}
            </select>
            </div>
        <div>
          <label htmlFor="duration">Duration:</label>
          <input
            type="number"
            id="duration"
            value={duration}
            onChange={(e) => setDuration(e.target.value)}
            required
          />
        </div>
        <button type="submit">Submit</button>
      </form>
    </div>
  );
};

export default AddRecord;
