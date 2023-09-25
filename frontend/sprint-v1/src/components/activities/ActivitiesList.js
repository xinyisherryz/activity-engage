import { useEffect, useState, useContext  } from 'react';
import UserContext from '../../UserContext';
import api from '../../api/axiosConfig';
import { Container, Row, Col, Table } from 'react-bootstrap';
import { Link, useHistory } from 'react-router-dom';
import '../../ActivityStyles.css';


const ActivitiesList = () => {
  const { user } = useContext(UserContext);
  const [activities, setActivities] = useState([]);

  useEffect(() => {
    const fetchActivities = async () => {
      try {
        let response;

        console.log(user)

        if (user) {
          console.log("user exist")
          response = await api.get(`/api/v1/activities/user/${user.userID}`);
          console.log("only records of current user will be displayed")
        } else {
          console.log("no user")
          response = await api.get('/api/v1/activities/simplified');
          console.log("all records will be displayed")
        }

        setActivities(response.data);
      } catch (error) {
        console.error('Error fetching activities:', error);
      }
    };

    fetchActivities();
  }, [user]);

  // const getActivities = async () => {
  //   try {
  //     const response = await api.get('/api/v1/activities/simplified');
  //     setActivities(response.data);
  //   } catch (e) {
  //     console.error(e);
  //   }
  // };

  return (
    <Container>
      <Row>
        <Col>
          <h3>Activities</h3>
        </Col>
      </Row>
      <Row className="mt-4">
        <Col>
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>Date</th>
                <th>Type</th>
                <th>Duration (min)</th>
                {user ? null : <th>Location</th>}
              </tr>
            </thead>
            <tbody>
              {activities.map((activity, index) => (
                <tr key={index}>
                  <td>
                    <Link to={`/activity/${activity.date}`}>{activity.date}</Link>
                  </td>
                  <td>
                    {user && activity.summary
                      ? activity.summary.map((activity) => activity.activity)
                      : activity.type}
                  </td>
                  <td>
                    {user && activity.summary
                      ? activity.summary.map((activity) => activity.duration)
                      : activity.duration}
                  </td>
                  {user ? null : <td>{activity.location}</td>}
                </tr>
              ))}
            </tbody>
          </Table>
        </Col>
      </Row>
    </Container>
  );
  
};

export default ActivitiesList;
