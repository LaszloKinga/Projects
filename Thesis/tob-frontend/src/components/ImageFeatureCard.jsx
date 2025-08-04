import React from "react";
import PropTypes from "prop-types";
import styles from '../styles/About.module.css';

const ImageFeatureCard = ({ image, alt, title }) => {
  return (
    <div className="item">
      <img className={styles.item} src={image} alt={alt} />
      <h5>{title}</h5>
    </div>
  );
};

ImageFeatureCard.propTypes = {
  image: PropTypes.string.isRequired,
  alt: PropTypes.string.isRequired,
  title: PropTypes.string.isRequired,
};

export default ImageFeatureCard;
