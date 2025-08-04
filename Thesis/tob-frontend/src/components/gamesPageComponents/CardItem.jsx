import React from "react";
import { Card } from "react-bootstrap";
import { useNavigate } from "react-router-dom"; 
import styles from '../../styles/Card.module.css';

const CardItem = ({ card }) => {
  const navigate = useNavigate(); 

  const handleClick = () => {
    navigate(`/chat/${card.mode}`);
  };

  return (
    <Card className={styles.card} onClick={handleClick} style={{ cursor: "pointer" }}>
      <Card.Img variant="top" src={card.image} />
      <Card.Body>
        <Card.Title>{card.title}</Card.Title>
        <Card.Text>{card.description}</Card.Text>
      </Card.Body>
    </Card>
  );
};

export default CardItem;
