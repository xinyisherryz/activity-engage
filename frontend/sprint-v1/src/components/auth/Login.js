import React, { useState, useContext  } from "react";
import UserContext from "../../UserContext";
import { useNavigate } from "react-router-dom";
import api from '../../api/axiosConfig';
import jwtDecode from 'jwt-decode';

const Login = () => {
  const { setUser } = useContext(UserContext);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await api.post("/api/v1/users/login", {
        email,
        password,
      });
  
      console.log("Received JWT: " + response.data.token); // add log
  
      // Save the JWT token in the client's local storage
      localStorage.setItem("token", response.data);

      // Decode the JWT token
      const decodedToken = jwtDecode(response.data);
  
      // Set user object
      setUser({ userID: decodedToken.user_id });
  
      // Redirect the user to the dashboard or another page
      navigate("/dashboard");
    } catch (e) {
      console.error(e);
      setError(e);
    }
  };

  return (
    <div>
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button type="submit">Login</button>
      </form>
      {error && <div>{error.message}</div>}
    </div>
  );
};

export default Login;
