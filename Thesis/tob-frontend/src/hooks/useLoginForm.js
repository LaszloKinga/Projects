import { useState } from "react";

export const useLoginForm = (onLogin) => {
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  const onChangeHandler = (event) => {
    const { name, value } = event.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const onSubmitLogin = (e) => {
    e.preventDefault();
    onLogin(e, formData.email, formData.password);
  };

  return {
    formData,
    onChangeHandler,
    onSubmitLogin,
  };
};
