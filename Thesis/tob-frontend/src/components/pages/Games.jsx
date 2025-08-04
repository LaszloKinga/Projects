import React from "react";
import CardsContainer from "../gamesPageComponents/CardsContainer";
import { useUserRole } from "../../hooks/useUserRole";

export const Games = () => {
  const { cardsData } = useUserRole();

  return (
    <div>
      <CardsContainer cardsData={cardsData} />
    </div>
  );
};
