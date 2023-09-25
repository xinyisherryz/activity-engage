import React from "react";
import api from '../../api/axiosConfig';

const Logout = ({ onLogout }) => {
  const handleLogout = async () => {
    try {
      await api.post("/api/v1/users/logout");
      
      // Remove the JWT token from the client's local storage
      localStorage.removeItem("token");
      
      // Call the onLogout function passed from the parent component
      onLogout();
    } catch (e) {
      console.error(e);
    }
  };

  return (
    <div>
      <button onClick={handleLogout}>Logout</button>
    </div>
  );
};

export default Logout;

// const Logout = ({ onLogout }) => {
//   const handleLogout = async () => {
//     try {
//       await api.post("/api/v1/users/logout");
      
//       // Remove the JWT token from the client's local storage
//       localStorage.removeItem("token");
      
//       // Call the onLogout function passed from the parent component
//       onLogout();
//     } catch (e) {
//       console.error(e);
//     }
//   };

//   return (
//     <div>
//       <button onClick={handleLogout}>Logout</button>
//     </div>
//   );
// };

// export default Logout;