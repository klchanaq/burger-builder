import React from "react";
import "./Order.css";

const order = function(props) {
  let { ingredients, price, address } = props;
  let ingredientOutputs = Object.entries(ingredients).map(([key, val]) => {
    return (
      <label className="Order-IngreidentOutput" key={key}>
        {key}: {val}
      </label>
    );
  });
  // { variable && JSX + Variable }, shorthand of variable ? JSX + Variable : null;
  // { variable || JSX + Variable }, shorthand of variable ? variable: (JSX + Variable) ? (JSX + Variable) : null;
  return (
    <div className="Order">
      <p>Ingredients: {ingredientOutputs}</p>
      <p>
        Price: <strong>{price}</strong>
      </p>
      <p>
        Address
      </p>
      { address.street && <p>Street: {address.street}</p>} 
      { address.country && <p>Country: {address.country}</p>}
      { address.zipCode && <p>ZipCode: {address.zipCode}</p>}
      </div>
  );
};

export default order;