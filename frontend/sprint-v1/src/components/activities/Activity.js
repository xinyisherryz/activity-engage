import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import api from '../../api/axiosConfig';
import { Container, Row, Col } from 'react-bootstrap';
import '../../Activity.css';
// import '../../ActivityStyles.css';

const Activity = () => {
  const [activity, setActivity] = useState({});
  const { date } = useParams();

  useEffect(() => {
    getActivityByDate();
  }, []);

  const getActivityByDate = async () => {
    try {
      const response = await api.get(`/api/v1/activities/date/${date}`);
      setActivity(response.data);
      console.log(response.data);
    } catch (e) {
      console.error(e);
    }
  };

  return (
    <Container className="activity-container">
      <Row>
        <Col>
          <h3>Activity Details</h3>
        </Col>
      </Row>
      {activity && (
        <>
          <Row>
            <Col>
              <strong>Date:</strong> {activity.date}
            </Col>
          </Row>
          <Row>
            <Col>
              <strong>Last Update:</strong> {activity.lastUpdate}
            </Col>
          </Row>
          <Row>
            <Col>
              <strong>Calories:</strong> {activity.calories}
            </Col>
          </Row>
          <Row>
            <Col>
              <strong>Summary:</strong>
              {activity.summary &&
                activity.summary.map((s, index) => (
                  <div key={index}>
                    Activity: {s.activity} | Duration: {s.duration}
                  </div>
                ))}
            </Col>
          </Row>
          <Row>
            <Col>
              <strong>Segments:</strong>
              {activity.segments &&
                activity.segments.map((s, index) => (
                  <div key={index}>
                    Type: {s.type} | {s.type === "place" ? "Place Name: " + s.place.name : "Activity: " + s.activity}
                  </div>
                ))}
            </Col>
          </Row>
        </>
      )}
    </Container>
  );
};

export default Activity;
