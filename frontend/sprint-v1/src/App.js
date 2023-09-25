import './App.css';
import api from './api/axiosConfig';
import React, { useState, useEffect } from 'react';
import Layout from './components/Layout';
import { Routes, Route } from "react-router-dom";
import Home from './components/home/Home';
import Header from './components/header/Header';
import ActivitiesList from './components/activities/ActivitiesList';
import Activity from './components/activities/Activity';
import Dashboard from './components/dashboard/Dashboard';
import Login from './components/auth/Login';
import SignUp from './components/auth/SignUp';
import AddRecord from './components/user/AddRecords';
import UpdateRecord from './components/user/UpdateRecords';
import UserContext from "./UserContext";
import jwtDecode from 'jwt-decode';



function App() {

  const [activities, setActivities] = useState();

  // const getActivities = async () => {
    
  //   try {

  //     const response = await api.get("/api/v1/activities");

  //     console.log(response.data)

  //     setActivities(response.data);

  //   } catch(e) {
  //     console.log(e);
  //   }

  // }

  // useEffect(() => {
  //   getActivities();
  // }, [])

  const [user, setUser] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const decodedToken = jwtDecode(token);
        setUser({ userID: decodedToken.user_id });
      } catch (error) {
        console.error('Error decoding token:', error);
      }
    }
  }, []);
  

  return (
    <UserContext.Provider value={{ user, setUser }}> {/* 使用 UserContext.Provider 传递 user 和 setUser */}
    <div className="App">
      <Header />
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route path="/" element={<Home />} /> 
          <Route path="/activitieslist" element={<ActivitiesList />} />
          <Route path="/activity/:date" element={<Activity />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/addrecord" element={<AddRecord />} />
          <Route path="/updaterecord" element={<UpdateRecord />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
        </Route>
      </Routes>
    </div>
    </UserContext.Provider>
  );
  
}

export default App;
