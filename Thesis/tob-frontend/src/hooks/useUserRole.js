import { useState, useEffect } from "react";
import { cardsDataUser } from "../data/cardsData";
import { cardsDataGuest } from "../data/cardsDataGuest";

export const useUserRole = () => {
  const [role, setRole] = useState("guest");

  useEffect(() => {
    const storedRole = localStorage.getItem("role");
    if (storedRole) {
      setRole(storedRole);
    }
  }, []);

  const cardsData =
    role === "ADMIN" || role === "USER" ? cardsDataUser : cardsDataGuest;

  return { role, cardsData };
};
