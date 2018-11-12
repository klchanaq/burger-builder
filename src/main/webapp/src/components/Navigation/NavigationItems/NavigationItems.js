import React from "react";
import "./NavigationItems.css";
import NavigationItem from "./NavigationItem/NavigationItem";

const navigationItems = function(props) {
  let authenticateRouteOrLogoutRoute = props.isAuth ? (
    <NavigationItem link="/logout">Logout</NavigationItem>
  ) : (
    <NavigationItem link="/auth">Authenticate</NavigationItem>
  );
  return (
    <ul className="NavigationItems">
      <NavigationItem link="/" exact>
        Home
      </NavigationItem>
      <NavigationItem link="/burgerBuilder">Burger Builder</NavigationItem>
      {props.isAuth && <NavigationItem link="/orders">Orders</NavigationItem>}
      {authenticateRouteOrLogoutRoute}
      {/* <NavigationItem link="/checkout">Checkout</NavigationItem> */}
    </ul>
  );
};

export default navigationItems;