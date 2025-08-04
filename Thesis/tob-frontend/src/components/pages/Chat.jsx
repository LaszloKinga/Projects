import React from "react";
import { Container, Card, Form, Button } from "react-bootstrap";
import styles from "../../styles/Chat.module.css";
import { useChatLogic } from "../../hooks/useChatLogic";

const Chat = ({ mode = "basic" }) => {
  const {
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
    isLengthMode,
    isWordMode,
  } = useChatLogic(mode);

  return (
    <Container className={styles.chatContainer}>
      <Card className={styles.chatCard}>
        <Card.Title className="text-center mt-2">Chat Room</Card.Title>

        {isTimerMode && timerStarted && (
          <div className="text-center text-muted mb-2">
            Time: <strong>{formatTime(remainingTime)}</strong>
          </div>
        )}

        <div className={styles.chatBox}>
          {messages.map((msg, index) => (
            <div
              key={index}
              className={`d-flex mb-2 ${
                msg.sender === "user"
                  ? "justify-content-end"
                  : "justify-content-start"
              }`}
            >
              <div
                className={`${styles.messageBubble} ${
                  msg.sender === "user" ? styles.userBubble : styles.aiBubble
                }`}
              >
                {msg.text}
              </div>
            </div>
          ))}
        </div>

        <Form className="mt-3 d-flex p-2">
          <Form.Control
            type="text"
            placeholder="Type a message..."
            value={input}
            onChange={(e) => setInput(e.target.value)}
            disabled={remainingTime === 0}
          />
          <Button onClick={sendMessage} variant="primary" className="ms-2">
            Send
          </Button>
        </Form>
      </Card>

      {(isLengthMode || isWordMode) && (
        <div className={styles.scoreBubble}>
          Score: <strong>{score}</strong>
        </div>
      )}

      <div className={styles.resetter}>
        <Button onClick={restartGame}>Restart Game</Button>
      </div>
    </Container>
  );
};

export default Chat;
