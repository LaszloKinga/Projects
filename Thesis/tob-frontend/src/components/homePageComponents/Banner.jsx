import React from "react";
import { Container, Col, Row } from "react-bootstrap";
import { useTypingEffect } from "../../hooks/useTypingEffect";
import headerImg from "../../assets/img/bennerimg.jpeg";
import styles from "../../styles/Banner.module.css";

function Banner() {
  const phrases = ["Let’s play!", "Playtime!", "Game on!"];
  const text = useTypingEffect(phrases, 2000);

  return (
    <section className={styles.banner}>
      <Container>
        <Row className="align-items-center">
          <Col xs={12} md={6} xl={7}>
            <span>
              <h2>Welcome to Tower of Babel!</h2>
            </span>
            <h1>
              {text}
              <span className="wrap"></span>
            </h1>
            <p>
              Think fast, play smart! Challenge the AI in the ultimate Word
              Chain game. Choose your level, earn points, and sharpen your mind
              while having fun!
            </p>
          </Col>
          <Col xs={12} md={6} xl={5}>
            <img src={headerImg} alt="HeaderImg" />
          </Col>
        </Row>
      </Container>
    </section>
  );
}

export default Banner;
