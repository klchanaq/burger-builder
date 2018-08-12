import React from "react";
import "./NavigationItems.css";
import NavigationItem from "./NavigationItem/NavigationItem";

const navigationItems = function(props) {
  return (
    <ul className="NavigationItems">
      <NavigationItem link="/" exact>Home</NavigationItem>
      <NavigationItem link="/burgerBuilder">Burger Builder</NavigationItem>
      <NavigationItem link="/orders">Orders</NavigationItem>
      <NavigationItem link="/auth">Authenticate</NavigationItem>
      {/* <NavigationItem link="/checkout">Checkout</NavigationItem> */}
    </ul>
  );
};

export default navigationItems;