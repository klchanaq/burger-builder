import React from "react";
import "./Order.css";

const order = function(props) {
  let { ingredients, price } = props;
  let ingredientOutputs = Object.entries(ingredients).map(([key, val]) => {
    return (
      <label className="Order-IngreidentOutput" key={key}>
        {key}: {val}
      </label>
    );
  });
  return (
    <div className="Order">
      <p>Ingredients: {ingredientOutputs}</p>
      <p>
        Price: <strong>{price}</strong>
      </p>
    </div>
  );
};

export default order;
