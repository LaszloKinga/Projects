import { useState } from "react";
import { useUserScores } from "../../hooks/useUserScores";
import { formatDate } from "../../utils/formatDate";

const UserScores = ({ userId }) => {
  const [page, setPage] = useState(0);
  const pageSize = 5;

  const { scores, loading, totalPages } = useUserScores(userId, page, pageSize);
  if (loading) {
    return <p>Loading scores...</p>;
  }

  return (
    <div className="container">
      <h3>User Scores</h3>
      <table className="table table-striped">
        <thead>
          <tr>
            <th>Game Mode</th>
            <th>Score</th>
            <th>Date</th>
          </tr>
        </thead>
        <tbody>
          {scores.map((score) => (
            <tr key={score.id}>
              <td>{score.game}</td>
              <td>{score.score}</td>
              <td>{formatDate(score.playedAt)}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <div className="d-flex justify-content-between mt-3">
        <button
          className="btn btn-secondary"
          disabled={page === 0}
          onClick={() => setPage((prev) => prev - 1)}
        >
          Previous
        </button>
        <span>Page {page + 1} of {totalPages}</span>
        <button
          className="btn btn-secondary"
          disabled={page >= totalPages - 1}
          onClick={() => setPage((prev) => prev + 1)}
        >
          Next
        </button>
      </div>
    </div>
  );
};

export default UserScores;
