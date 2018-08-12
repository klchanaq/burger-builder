import React from "react";
import "./CheckoutSummary.css";
import Burger from "../../Burger/Burger";
import Button from "../../UI/Button/Button";

const checkoutSummary = function(props) {
  console.log("[checkoutSummary] render()...");
  return (
    <div className="CheckoutSummary">
      <h1>Wow...! It looks tasty!</h1>
      <div>
        <Burger ingredients={props.ingredients} />
        <Button btnType="Danger" clicked={props.checkoutCancelled}>
          Cancel
        </Button>
        <Button btnType="Success" clicked={props.checkoutContinued}>
          Continue
        </Button>
      </div>
    </div>
  );
};

export default checkoutSummary;
