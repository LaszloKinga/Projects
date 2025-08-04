import React from "react";
import LoginForm from "../loginRegisterComponents/LoginForm";
import RegisterForm from "../loginRegisterComponents/RegisterForm";
import { useAuthForm } from "../../hooks/useAuthForm";

export const LoginRegister = () => {
  const {
    isLogin,
    switchToLogin,
    switchToRegister,
    handleLogin,
    handleRegister,
  } = useAuthForm();

  return (
    <div>
      {isLogin ? (
        <LoginForm onLogin={handleLogin} switchToRegister={switchToRegister} />
      ) : (
        <RegisterForm
          onRegister={handleRegister}
          switchToLogin={switchToLogin}
        />
      )}
    </div>
  );
};
