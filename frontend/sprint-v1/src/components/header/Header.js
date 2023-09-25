// import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
// import { faPersonWalking } from "@fortawesome/free-solid-svg-icons";
// import Button from "react-bootstrap/Button";
// import Container from "react-bootstrap/Container"
// import Nav from "react-bootstrap/Nav";
// import Navbar from "react-bootstrap/Navbar";
// import { NavLink, useNavigate } from "react-router-dom";

// const Header = () => {

//   const navigate = useNavigate();
 
// return (
//   <Navbar bg="dark" variant="dark" expand="lg">
//     <Container fluid>
//       <Navbar.Brand href="/" style={{"color":'white'}}>
//       <FontAwesomeIcon icon={faPersonWalking} /> Activity Engage
//       </Navbar.Brand>
//       <Navbar.Toggle aria-controls="navbarScroll" />
//       <Navbar.Collapse id="navbarScroll">
//         <Nav
//             className="me-auto my-2 my-lg-0"
//             style={{maxHeight: '100px'}}
//             navbarScroll
//         >
//         <NavLink className ="nav-link" to="/">Home</NavLink>
//         <NavLink className ="nav-link" to="/activitieslist">Activities List</NavLink>
//         <NavLink className ="nav-link" to="/dashboard">Dashboard</NavLink>        
//         </Nav>

//         <Button
//           variant="outline-info"
//           className="me-2"
//           onClick={() => navigate("/login")}
//         >
//           Login
//         </Button>
//         <Button
//           variant="outline-info"
//           onClick={() => navigate("/signup")}
//         >
//           Sign Up
//         </Button>

//       </Navbar.Collapse>
//     </Container>
//   </Navbar>
//   )
// }

// export default Header




import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPersonWalking } from "@fortawesome/free-solid-svg-icons";
import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import { NavLink, useNavigate } from "react-router-dom";
import React, { useState, useEffect } from "react";

import "../../Header.css";
import Logout from "../auth/Logout";

const Header = () => {
  const navigate = useNavigate();

  const [isLoggedIn, setIsLoggedIn] = useState(false);

  // useEffect(() => {
  //   const token = localStorage.getItem("token");
  //   if (token) {
  //     setIsLoggedIn(true);
  //   } else {
  //     setIsLoggedIn(false);
  //   }
  // }, []);

  useEffect(() => {
    const handleStorageChange = (e) => {
      if (e.key === "token") {
        setIsLoggedIn(!!localStorage.getItem("token"));
      }
    };
  
    // check localStorage' token
    setIsLoggedIn(!!localStorage.getItem("token"));
  
    window.addEventListener("storage", handleStorageChange);
  
    return () => {
      window.removeEventListener("storage", handleStorageChange);
    };
  }, []);

  const onLogout = () => {
    setIsLoggedIn(false);
  };

  return (
    <Navbar expand="lg" className="custom-navbar">
      <Container fluid>
        <Navbar.Brand className= "IconWord"  href="/" style={{ color: "black" } }>
          <FontAwesomeIcon icon={faPersonWalking} /> Activity Engage
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="navbarScroll" />
        <Navbar.Collapse id="navbarScroll">
          <Nav className="me-auto my-2 my-lg-0" style={{ maxHeight: "100px" }} navbarScroll>
            <NavLink className="nav-link custom-nav-link" to="/">
              Home
            </NavLink>
            <NavLink className="nav-link custom-nav-link" to="/activitieslist">
              Activities List
            </NavLink>
            <NavLink className="nav-link custom-nav-link" to="/dashboard">
              Dashboard
            </NavLink>
            {isLoggedIn && (
              <NavLink className="nav-link custom-nav-link" to="/addrecord">
                Add Record
              </NavLink>
            )}
            {
              isLoggedIn && (
                <NavLink className="nav-link custom-nav-link" to="/updaterecord">
                Update Record
              </NavLink>
              )
            }
          </Nav>

          {/* <Button variant="outline-info" className="me-2" onClick={() => navigate("/login")}>
            Login
          </Button>
          <Button variant="outline-info" onClick={() => navigate("/signup")}>
            Sign Up
          </Button> */}
           {!isLoggedIn && (
          <>
            <Button variant="outline-info" className="me-2" onClick={() => navigate("/login")}>
              Login
            </Button>
            <Button variant="outline-info" onClick={() => navigate("/signup")}>
              Sign Up
            </Button>
          </>
        )}
        {isLoggedIn && (
          <Logout onLogout={onLogout} />
        )}
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default Header;


