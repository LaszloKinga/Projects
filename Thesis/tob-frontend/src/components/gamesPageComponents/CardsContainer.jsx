import React from "react";
import { Row, Col } from "react-bootstrap"; 
import CardItem from "../gamesPageComponents/CardItem";  
import styles from '../../styles/Card.module.css';


const CardsContainer = ({ cardsData }) => { 
  return (
    <div className={styles.centeredContainer}>
      <Row className={`${styles.row} justify-content-center`}>
        {cardsData.map((card, index) => (
          <Col xs={12} md={4} xl={3} key={index}>
            <CardItem card={card} /> 
          </Col>
        ))}
      </Row>
    </div>
  );
};

export default CardsContainer;
