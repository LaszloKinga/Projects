import { useState } from "react";
import { login, register } from "../api/authApi";

export const useAuthForm = () => {
  const [isLogin, setIsLogin] = useState(true);

  const switchToRegister = () => setIsLogin(false);
  const switchToLogin = () => setIsLogin(true);

  const handleLogin = async (e, email, password) => {
    e.preventDefault();
    try {
      const response = await login(email, password);
      localStorage.setItem("role", response.data.role);
      window.location.href = "/profile";
    } catch (error) {
      console.error("Login error:", error);
      alert("Invalid login credentials!");
    }
  };

  const handleRegister = async (
    e,
    firstName,
    lastName,
    userName,
    email,
    password
  ) => {
    e.preventDefault();
    try {
      await register({ firstName, lastName, userName, email, password });
      alert("Registration successful!");
      setIsLogin(true);
    } catch (error) {
      console.error("Registration failed:", error);
      alert("Registration failed!");
    }
  };

  return {
    isLogin,
    switchToLogin,
    switchToRegister,
    handleLogin,
    handleRegister,
  };
};
