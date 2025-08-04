import React from "react";
import { useAdminRegisterForm } from "../../hooks/useAdminRegisterForm";
import { useAdminAuth } from "../../hooks/useAdminAuth";

const AdminRegisterPage = ({ onRegister }) => {
  const { formData, onChangeHandler, onSubmitRegister } = useAdminRegisterForm(onRegister);
  const isAdmin = useAdminAuth();

  if (!isAdmin) {
    return <div>Nincs jogosultságod a felhasználó hozzáadásához.</div>;
  }

  return (
    <div className="container d-flex justify-content-center align-items-center min-vh-100">
      <div className="col-4">
        <form onSubmit={onSubmitRegister}>
          {["firstName", "lastName", "userName", "email", "password"].map((field) => (
            <div className="form-outline mb-4" key={field}>
              <label className="form-label">{field.replace(/([A-Z])/g, " $1")}</label>
              <input
                type={field === "password" ? "password" : "text"}
                name={field}
                className="form-control"
                value={formData[field]}
                onChange={onChangeHandler}
              />
            </div>
          ))}
          <button type="submit" className="btn btn-primary btn-block mb-3">
            Register
          </button>
        </form>
      </div>
    </div>
  );
};

export default AdminRegisterPage;
