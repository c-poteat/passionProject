import React from 'react';
import './RecipeTile.css';

// functional component
export default function RecipeTile({ recipe }) {
  // accepting value as a prop
  return (
    <div>
      <img className="recipeTile__img" src={recipe['recipe']['image']} />
      <p className="recipeTile__name">{recipe['recipe']['label']}</p>
    </div>
  );
}
