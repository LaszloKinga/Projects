import axios from "axios";

export const fetchUserScores = (userId, page = 0, size = 5) =>
    axios.get(`http://localhost:8080/api/scores/${userId}`, {
      params: { page, size },
      withCredentials: true,
    });
