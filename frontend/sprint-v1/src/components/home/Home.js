// import React from 'react'

// const Home = ({activities}) => {
//   return (
//     <div>
//       Welcome!!
//     </div>
//   )
// }

// export default Home


// first version
// import React from 'react';
// import '../../Home.css';
// import { useNavigate } from 'react-router-dom';
// import backgroundImage from '../../backgroundImage.jpg';

// const Home = ({ activities }) => {
//   const navigate = useNavigate();

//   const handleClick = () => {
//     navigate('/dashboard');
//   };


//   return (
//     <div className="home-container" >
//       <div className="home-glass"></div>
//       <div className="home-content">
//         <header className="home-header">
//           <h1>Fitness Tracker</h1>
//         </header>
//         <section className="home-intro">
//           <p>Welcome to Fitness Tracker! Start tracking your activities and achieve your fitness goals today.</p>
//         </section>
//         <section className="home-cta">
//           <button className="home-btn" onClick={handleClick}>
//             Get Started
//           </button>
//         </section>
//       </div>
//     </div>
//   );
// };

// export default Home;

import React from "react";
import "../../Home.css";

const Home = () => {
  return (
    <div className="home-container">
      <div className="intro-content">
        <h1>Hello!</h1>
        <h2>This is Activity Engage</h2>
        <p>—— A fitness tracking application</p>
      </div>
    </div>
  );
};

export default Home;

