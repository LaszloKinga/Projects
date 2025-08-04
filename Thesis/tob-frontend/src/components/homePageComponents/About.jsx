import React from "react";
import { Container, Row, Col } from "react-bootstrap";
import Carousel from "react-multi-carousel";
import "react-multi-carousel/lib/styles.css";
import { useCarouselResponsive } from "../../hooks/useCarouselResponsive";
import styles from '../../styles/About.module.css';

import img1 from "../../assets/img/creativity.jpeg";
import img2 from "../../assets/img/memoryDev.jpeg";
import img3 from "../../assets/img/quickThink.jpeg";
import img4 from "../../assets/img/wordAssoc.jpeg";

import ImageFeatureCard from "../../components/ImageFeatureCard";

const About = () => {
  const responsive = useCarouselResponsive();

  const features = [
    { image: img1, alt: "Creativity", title: "Creativity" },
    { image: img2, alt: "Quick Thinking", title: "Quick Thinking" },
    { image: img3, alt: "Memory", title: "Memory Development" },
    { image: img4, alt: "Association", title: "Word Association Skills" },
  ];

  return (
    <section className={styles.skill} >
      <Container>
        <Row>
          <Col xs={12}>
          <section className={styles.skill}>
          <div className={styles.skillbx}>
          
              <h2>About the game</h2>
              <p>
                On this website, you can play against artificial intelligence!
                In the Word Chain game, the AI is your opponent. The goal of the
                game is to come up with a meaningful word that starts with the
                last letter of the given word...
              </p>
              <Carousel
                responsive={responsive}
                infinite
                className={`${styles["owl-carousel"]} ${styles["owl-theme"]} ${styles["skill-slider"]}`}
              >
                {features.map((item, idx) => (
                  <ImageFeatureCard
                    key={idx}
                    image={item.image}
                    alt={item.alt}
                    title={item.title}
                  />
                ))}
              </Carousel>
            </div>
            </section>
          </Col>
        </Row>
      </Container>
    </section>
  );
};

export default About;
