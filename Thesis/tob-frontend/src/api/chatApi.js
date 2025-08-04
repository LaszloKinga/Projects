export const resetBackend = async () => {
    await fetch("http://localhost:8080/api/chat/reset", {
      method: "POST",
      credentials: "include",
    });
  };
  
  export const sendMessageToBackend = async (message) => {
    return await fetch("http://localhost:8080/api/chat/message", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ message }),
      credentials: "include",
    });
  };
  
  export const saveScore = async (score, gameMode) => {
    return await fetch("http://localhost:8080/api/scores", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify({ score, gameMode }),
    });
  };
  