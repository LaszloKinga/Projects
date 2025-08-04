import { useLeaderboard } from "../../hooks/useLeaderboard";

const Leaderboard = () => {
  const { game, setGame, scores } = useLeaderboard();

  return (
    <div className="card p-3">
      <h5 className="mb-3">Top 5 Players - {game.replace("_", " ")}</h5>

      <div className="btn-group mb-3">
        <button
          className={`btn btn-outline-primary ${game === "WORD_COUNT" ? "active" : ""}`}
          onClick={() => setGame("WORD_COUNT")}
        >
          WORD COUNT
        </button>
        <button
          className={`btn btn-outline-primary ${game === "LETTER_COUNT" ? "active" : ""}`}
          onClick={() => setGame("LETTER_COUNT")}
        >
          LETTER COUNT
        </button>
      </div>

      <table className="table table-sm table-striped">
        <thead>
          <tr>
            <th>#</th>
            <th>Player</th>
            <th>Score</th>
          </tr>
        </thead>
        <tbody>
          {scores.map((score, index) => (
            <tr key={score.id}>
              <td>{index + 1}</td>
              <td>{score.userName}</td>
              <td>{score.score}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Leaderboard;
