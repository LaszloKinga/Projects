import { useState } from "react";

export const useRegisterForm = (onRegister) => {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    userName: "",
    email: "",
    password: "",
  });

  const onChangeHandler = (event) => {
    const { name, value } = event.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const onSubmitRegister = (e) => {
    e.preventDefault();
    onRegister(
      e,
      formData.firstName,
      formData.lastName,
      formData.userName,
      formData.email,
      formData.password
    );
  };

  return {
    formData,
    onChangeHandler,
    onSubmitRegister,
  };
};
