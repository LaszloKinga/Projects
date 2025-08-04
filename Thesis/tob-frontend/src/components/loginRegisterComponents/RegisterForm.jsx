import React from "react";
import { useRegisterForm } from "../../hooks/useRegisterForm";

const RegisterForm = ({ onRegister, switchToLogin }) => {
  const { formData, onChangeHandler, onSubmitRegister } = useRegisterForm(onRegister);

  return (
    <div className="container d-flex justify-content-center align-items-center min-vh-100">
      <div className="col-4">
        <form onSubmit={onSubmitRegister}>
          <div className="form-outline mb-4">
            <label className="form-label">First name</label>
            <input
              type="text"
              name="firstName"
              className="form-control"
              value={formData.firstName}
              onChange={onChangeHandler}
            />
          </div>
          <div className="form-outline mb-4">
            <label className="form-label">Last name</label>
            <input
              type="text"
              name="lastName"
              className="form-control"
              value={formData.lastName}
              onChange={onChangeHandler}
            />
          </div>
          <div className="form-outline mb-4">
            <label className="form-label">User name</label>
            <input
              type="text"
              name="userName"
              className="form-control"
              value={formData.userName}
              onChange={onChangeHandler}
            />
          </div>
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
          <button type="submit" className="btn btn-primary btn-block mb-3">
            Register
          </button>
          <p>
            Already have an account?{" "}
            <button type="button" onClick={switchToLogin} className="btn btn-link">
              Login here
            </button>
          </p>
        </form>
      </div>
    </div>
  );
};

export default RegisterForm;
