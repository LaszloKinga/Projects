import axios from "axios";

export const fetchLeaderboard = (gameName, limit = 5) => {
  return axios.get("http://localhost:8080/api/scores/leaderboard", {
    params: { gameName, limit },
    withCredentials: true,
  });
};
