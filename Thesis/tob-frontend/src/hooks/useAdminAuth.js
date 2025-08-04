export const useAdminAuth = () => {
    const role = localStorage.getItem("role");
    return role === "ADMIN";
  };
  