import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import { resetBackend, sendMessageToBackend, saveScore } from "../api/chatApi";

export const useChatLogic = (mode = "basic") => {
  const location = useLocation();

  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState("");
  const [timerStarted, setTimerStarted] = useState(false);
  const [remainingTime, setRemainingTime] = useState(120);
  const [score, setScore] = useState(0);

  const isTimerMode = mode === "timer-word" || mode === "timer-length";
  const isLengthMode = mode === "timer-length";
  const isWordMode = mode === "timer-word";

  const formatTime = (seconds) => {
    const mins = String(Math.floor(seconds / 60)).padStart(2, "0");
    const secs = String(seconds % 60).padStart(2, "0");
    return `${mins}:${secs}`;
  };

  const restartGame = async () => {
    await resetBackend();
    setMessages([]);
    setInput("");
    setScore(0);
    setRemainingTime(120);
    setTimerStarted(false);
  };

  const sendMessage = async () => {
    if (input.trim() === "") return;

    if (!timerStarted) {
      setTimerStarted(true);
    }

    try {
      const response = await sendMessageToBackend(input);

      if (!response.ok) {
        const errorText = await response.text();
        alert(errorText);
        await resetBackend();
        setMessages([]);
        setInput("");
        setTimerStarted(false);
        setRemainingTime(0);
        setScore(0);
        return;
      }

      const generated = await response.text();

      if (isLengthMode) {
        const wordLength = input.trim().replace(/\s+/g, "").length;
        setScore((prev) => prev + wordLength);
      }

      if (isWordMode) {
        setScore((prev) => prev + 1);
      }

      setMessages((prev) => [
        ...prev,
        { text: input, sender: "user" },
        { text: generated, sender: "ai" },
      ]);
      setInput("");
    } catch (err) {
      console.error("Hiba:", err);
      alert("Unexpected error.");
      await resetBackend();
      setMessages([]);
      setInput("");
      setTimerStarted(false);
      setRemainingTime(0);
      setScore(0);
    }
  };

  useEffect(() => {
    resetBackend();
  }, []);

  useEffect(() => {
    if (isTimerMode && timerStarted && remainingTime > 0) {
      const interval = setInterval(() => {
        setRemainingTime((prev) => prev - 1);
      }, 1000);
      return () => clearInterval(interval);
    }

    if (remainingTime === 0 && timerStarted) {
      const handleTimeUp = async () => {
        if (score > 0) {
          await saveScore(score, isLengthMode ? "LETTER_COUNT" : "WORD_COUNT");
        }

        if (isLengthMode || isWordMode) {
          alert(`Time is up! Your total score is: ${score} points`);
        }

        await resetBackend();
        setMessages([]);
        setInput("");
        setScore(0);
        setTimerStarted(false);
      };
      handleTimeUp();
    }

    return () => {
      resetBackend();
    };
  }, [location.pathname, timerStarted, remainingTime, isTimerMode, score, isLengthMode, isWordMode]);

  return {
    messages,
    input,
    score,
    remainingTime,
    timerStarted,
    formatTime,
    setInput,
    sendMessage,
    restartGame,
    isTimerMode,
    isWordMode,
    isLengthMode,
  };
};
