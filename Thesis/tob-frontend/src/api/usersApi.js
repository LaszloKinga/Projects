import axios from "axios";

const config = {
  headers: {
    Authorization: `Bearer ${localStorage.getItem("auth_token")}`,
  },
};

export const fetchCurrentUser = () =>
  axios.get("http://localhost:8080/api/users/me", { withCredentials: true });

export const fetchAllUsers = () =>
  axios.get("http://localhost:8080/api/users", config);

export const createUser = (user) =>
  axios.post("http://localhost:8080/api/users", user, config);

export const deleteUser = (userId) =>
  axios.delete(`http://localhost:8080/api/users/${userId}`, config);

export const updateUser = (user) =>
  axios.put(`http://localhost:8080/api/users/${user.id}`, user, config);