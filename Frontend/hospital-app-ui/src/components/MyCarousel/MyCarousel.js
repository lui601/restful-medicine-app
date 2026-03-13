import React from 'react';
import ReactCardCarousel from 'react-card-carousel';

const MyCarousel = ({ cardData }) => {
  const CARD_STYLE = {
    height: '600px',
    width: '400px',
    textAlign: 'center',
    background: '#52C0F5',
    color: '#FFF',
    fontSize: '25px',
    textTransform: 'uppercase',
    borderRadius: '10px',
    position: 'relative',
    overflow: 'hidden', 
  };

  const IMAGE_STYLE = {
    width: '60%', 
    height: '60%', 
    objectFit: 'cover',
    position: 'absolute',
    top: '20px', 
    left: '20%', 
    borderRadius: '10px',
    zIndex: 0, 
  };
  

  const TEXT_STYLE = {
    position: 'absolute',
    bottom: '10px', 
    width: '100%',
  };

  return (
    <ReactCardCarousel autoplay={true} autoplay_speed={2500}>
      {cardData.map((card, index) => (
        <div key={index} style={CARD_STYLE}>
          <img src={card.imagePath} alt={`Image for ${card.title}`} style={IMAGE_STYLE} />
          <div style={TEXT_STYLE}>{card.title}</div>
        </div>
      ))}
    </ReactCardCarousel>
  );
};

export default MyCarousel;
