import React, { useState, useEffect } from "react";
import { Nav, Navbar, Container } from "react-bootstrap";
import logo from "../assets/img/TOB1001.png";
import axios from "axios";
import '../styles/NavBar.css';

const NavBar = () => {
  const [activeLink, setActiveLink] = useState("home");
  const [user, setUser] = useState(null); 
  useEffect(() => {
    const path = window.location.pathname;
    if (path === "/") setActiveLink("home");
    else if (path === "/games") setActiveLink("games");
    else if (path === "/profile") setActiveLink("profile");
    else if (path === "/loginRegister") setActiveLink("loginRegister");

   
    axios
      .get("http://localhost:8080/me", { withCredentials: true })
      .then((response) => setUser(response.data))
      .catch((error) => {
        console.error("Hiba a /me végpont lekérésekor:", error);
        setUser(null);
      });
  }, []);

  // Kijelentkezes funkcio
  const handleLogout = async () => {
    try {
      console.log("Kijelentkezés indítása...");

      const response = await axios.post(
        "http://localhost:8080/logout",
        {},
        { withCredentials: true } 
      );

      console.log("Sikeres kijelentkezés:", response.data);

      // Toroljuk a sutit frontend oldalon is
      document.cookie = "jwt=; Path=/; Max-Age=0;";
      localStorage.removeItem("role");

      window.location.href = "/"; // visszairanyitas fooldalra
    } catch (error) {
      console.error("Hiba kijelentkezéskor:", error);
    }
  };

  return (
    <Navbar expand="lg">
      <Container>
        <Navbar.Brand href="/">
          <img src={logo} alt="Logo" />
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="ms-auto">
            <Nav.Link
              href="/"
              className={
                activeLink === "home" ? "active navbar-link" : "navbar-link"
              }
              onClick={() => setActiveLink("home")}
            >
              Home
            </Nav.Link>
            <Nav.Link
              href="/games"
              className={
                activeLink === "games" ? "active navbar-link" : "navbar-link"
              }
              onClick={() => setActiveLink("games")}
            >
              Games
            </Nav.Link>

            {/* Bejelentkezett felhasznalo eseten */}
            {user ? (
              <>
                <Nav.Link
                  href="/profile"
                  className={
                    activeLink === "profile"
                      ? "active navbar-link"
                      : "navbar-link"
                  }
                  onClick={() => setActiveLink("profile")}
                >
                  Profile
                </Nav.Link>
                <Nav.Link onClick={handleLogout} className="navbar-link">
                  Logout
                </Nav.Link>
              </>
            ) : (
              // ha nincs bejelentkezve, akkor a Login gomb jelenik meg
              <Nav.Link
                href="/loginRegister"
                className={
                  activeLink === "loginRegister"
                    ? "active navbar-link"
                    : "navbar-link"
                }
                onClick={() => setActiveLink("loginRegister")}
              >
                Login
              </Nav.Link>
            )}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default NavBar;
