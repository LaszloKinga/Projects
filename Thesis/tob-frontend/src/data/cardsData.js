import levelFlower1 from "../assets/img/level1.jpeg";
import levelFlower2 from "../assets/img/level2.jpeg";
import levelFlower3 from "../assets/img/level3.jpeg";

export const cardsDataUser = [
  {
    image: levelFlower1,
    title: "Level 1",
    description:
      "This level encourages you to expand your vocabulary. The rule is simple: just provide a word that starts with the last letter of your opponent's word.",
    mode: "basic",
  },
  {
    image: levelFlower2,
    title: "Level 2",
    description:
      "This level measures time. The goal is to create the longest word chain possible within 2 minutes. Each word counts as one point.",
      mode: "timer-word",
  },
  {
    image: levelFlower3,
    title: "Level 3",
    description:
      "This level measures both time and word length. The goal is to create the longest word chain possible within 2 minutes, where each letter of the word counts as one point. The longer the words, the higher the score!",
      mode: "timer-length",
  },
];
