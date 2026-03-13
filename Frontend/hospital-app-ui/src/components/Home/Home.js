import React from "react";
import MyCarousel from "../MyCarousel/MyCarousel";
import "./Home.css";

export default function Home() {
  const cardData = [
    { title: "Cardiology", imagePath: "/cardiology.png" },
    { title: "Neurology", imagePath: "/neurology.png" },
    { title: "Dermatology", imagePath: "/dermatology.png" },
    { title: "Pediatrics", imagePath: "/pediatrics.png" },
    { title: "Physiotherapy", imagePath: "/physiotherapy.png" },
    { title: "Orthopedics", imagePath: "/Orthopedics.png" },
    { title: "Stomatology", imagePath: "/stomatology1.png" },
    { title: "Pneumology", imagePath: "/Pneumology.png" },
  ];

  return (
    <div className="home-wrapper">
      <h1>Welcome to the Home Page</h1>
      <MyCarousel cardData={cardData} />
    </div>
  );
}
