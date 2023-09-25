import React, { useState, useEffect,useContext } from 'react';
import api from '../../api/axiosConfig';
import { Container, Row, Col } from 'react-bootstrap';
import UserContext from '../../UserContext';
import '../../Dashboard.css';

const Dashboard = () => {
  const [topVisitedLocations, setTopVisitedLocations] = useState([]);
  const [activityTypesWithDurations, setActivityTypesWithDurations] = useState([]);
  const { user } = useContext(UserContext);


  const getTopVisitedLocations = async () => {
    try {
      const response = await api.get('/api/v1/activities/locations/top10');
      setTopVisitedLocations(response.data);
    } catch (e) {
      console.error(e);
    }
  };

  const getAllActivityTypesWithDurations = async () => {
    try {
      const response = await api.get('/api/v1/activities/activitydurations');
      setActivityTypesWithDurations(response.data);
    } catch (e) {
      console.error(e);
    }
  };

  const getAllActivityTypesWithDurationsByUser = async () => {
    try {
      const response = await api.get(`/api/v1/activities/activitydurations/user/${user.userID}`);
      setActivityTypesWithDurations(response.data);
    } catch (e) {
      console.error(e);
    }
  };
  

  useEffect(() => {
    getTopVisitedLocations();
    if (user) {
      getAllActivityTypesWithDurationsByUser();
    } else {
      getAllActivityTypesWithDurations();
    }
  }, [user]);


  return (
    <Container>
      <Row>
      {!user && (
          <Col>
            <h2>Top Visited Locations</h2>
            <ul className="dashboard-list">
              {topVisitedLocations.map((location, index) => (
                <li key={index}>
                  {location.location} - {location.frequency}
                </li>
              ))}
            </ul>
          </Col>
        )}
        <Col>
          <h2>Activity Types with Durations</h2>
          <ul className="dashboard-list">
            {activityTypesWithDurations.map((activity, index) => (
              <li key={index}>
                {activity.activity} - {activity.totalDuration}
              </li>
            ))}
          </ul>
        </Col>
      </Row>
    </Container>
  );
};

export default Dashboard;
