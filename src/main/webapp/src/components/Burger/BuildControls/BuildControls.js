import React from "react";
import "./BuildControls.css";
import BuildControl from "./BuildControl/BuildControl";

const controls = [
  { label: "Bacon", type: "bacon" },
  { label: "Cheese", type: "cheese" },
  { label: "Salad", type: "salad" },
  { label: "Meat", type: "meat" }
];

const buildControls = function(props) {
  return (
    <div className="BuildControls">
      {/* <p>BuildControls works!</p> */}
      <p className="BuildControls-Price">
        Current Price: {props.price.toFixed(2)}
      </p>
      {controls.map(ctrl => {
        return (
          <BuildControl
            key={ctrl.label}
            label={ctrl.label}
            added={$event => props.ingredientAdded(ctrl.type)}
            removed={$event => props.ingredientRemoved(ctrl.type)}
            disabled={props.disabled[ctrl.type]}
          />
        );
      })}
      <button
        className="OrderButton"
        onClick={null}
        disabled={!props.purchasable}
      >
        Order Now
      </button>
    </div>
  );
};

export default buildControls;
