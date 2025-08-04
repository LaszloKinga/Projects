import React, { useState, useEffect } from "react";
import axios from "axios";
import profilePicture from "../../assets/img/profilePicture.jpeg";
import "@fortawesome/fontawesome-free/css/all.min.css";
import styles from "../../styles/ProfileCard.module.css";


const ProfileCard = () => {
  console.log("ProfileCard rendered");

  
  axios.defaults.withCredentials = true;
  
  const [user, setUser] = useState(null);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true); 

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/users/me", { withCredentials: true })
      .then((response) => {
        console.log("User data:", response.data); 
        setUser(response.data);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching user data:", error); 
        setError("Hiba történt a felhasználói adatok betöltésekor.");
        setLoading(false);
      });
  }, []);

  if (loading) {
    return <div>Loading...</div>; 
  }

  if (error) {
    return <div className="alert alert-danger">{error}</div>;
  }

  if (!user) {
    return <div>Nincs felhasználó adata.</div>; 
  }

  const renderRoleIcon = (role) => {
    switch (role) {
      case "ADMIN":
        return <i className="fas fa-user-shield"></i>;
      case "USER":
        return <i className="fas fa-user"></i>;
      default:
        return <i className="fas fa-user-circle"></i>;
    }
  };

  return (
    <div className={`${styles.profileCard} d-flex align-items-center shadow-sm p-3`}>
      <img
        src={profilePicture}
        alt="Profile"
        className={`${styles.profilePictureStyle} rounded-circle`}
      />
      <div className="ms-4">
        <h4>
          {user.firstName} {user.lastName}
        </h4>
        <p>
          <i className="fas fa-user-circle"></i> {user.userName} <br />
          <i className="fas fa-envelope"></i> {user.email} <br />
          {renderRoleIcon(user.role)} {user.role || "N/A"} <br />
        </p>
      </div>
    </div>
  );
};

export default ProfileCard;
