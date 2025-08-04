import { useState, useEffect } from "react";
import { fetchUserScores } from "../api/scoresApi";

export const useUserScores = (userId, page = 0, pageSize = 5) => {
  const [scores, setScores] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    fetchUserScores(userId, page, pageSize)
      .then((res) => {
        setScores(res.data.content);
        setTotalPages(res.data.totalPages);
      })
      .catch((err) => console.error("Score fetch error:", err))
      .finally(() => setLoading(false));
  }, [userId, page, pageSize]);

  return {
    scores,
    totalPages,
    loading,
  };
};
