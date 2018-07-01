import React from "react";
import './Logo.css';
import burgerLogo from "../../assets/images/burger-logo.png";

const logo = function(props) {
  const overrideStyles = {
    height: props.height,
    marginBottom: props.marginBottom
  };
  return (
    <div className="Logo" style={overrideStyles}>
      <img src={burgerLogo} alt="MyBurger" />
    </div>
  );
};

export default logo;
