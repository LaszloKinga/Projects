import axios from "axios";

export const login = async (email, password) => {
  const response = await axios.post(
    "http://localhost:8080/login",
    { email, password },
    { withCredentials: true }
  );
  return response;
};

export const register = async (user) => {
  await axios.post("http://localhost:8080/register", user);
};
