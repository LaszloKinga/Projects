import React from "react";
import { useLoginForm } from "../../hooks/useLoginForm";

const LoginForm = ({ onLogin, switchToRegister }) => {
  const { formData, onChangeHandler, onSubmitLogin } = useLoginForm(onLogin);

  return (
    <div className="container d-flex justify-content-center align-items-center min-vh-100">
      <div className="col-4">
        <form onSubmit={onSubmitLogin}>
          <div className="form-outline mb-4">
            <label className="form-label">Email</label>
            <input
              type="text"
              name="email"
              className="form-control"
              value={formData.email}
              onChange={onChangeHandler}
            />
          </div>
          <div className="form-outline mb-4">
            <label className="form-label">Password</label>
            <input
              type="password"
              name="password"
              className="form-control"
              value={formData.password}
              onChange={onChangeHandler}
            />
          </div>
          <button type="submit" className="btn btn-primary btn-block mb-4">
            Sign in
          </button>
          <p>
            Don't have an account?{" "}
            <button type="button" onClick={switchToRegister} className="btn btn-link">
              Register here
            </button>
          </p>
        </form>
      </div>
    </div>
  );
};

export default LoginForm;
