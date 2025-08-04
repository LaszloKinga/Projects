import { useEffect, useState } from "react";
import { fetchLeaderboard } from "../api/leaderboardApi";

export const useLeaderboard = (initialGame = "WORD_COUNT", limit = 5) => {
  const [game, setGame] = useState(initialGame);
  const [scores, setScores] = useState([]);

  useEffect(() => {
    fetchLeaderboard(game, limit)
      .then((res) => setScores(res.data))
      .catch((err) => console.error("Leaderboard fetch error:", err));
  }, [game, limit]);

  return { game, setGame, scores };
};
