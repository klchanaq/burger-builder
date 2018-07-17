import React, { Component } from "react";
import "./Checkout.css";
import CheckoutSummary from "../../components/Order/CheckoutSummary/CheckoutSummary";
import { Route } from "react-router-dom";
import ContactData from "./ContactData/ContactData";

class Checkout extends Component {
  state = {
    ingredients: null,
    totalPrice: 0
  };

  checkoutCancelledHandler = () => {
    this.props.history.goBack();
  };

  checkoutContinuedHandler = () => {
    this.props.history.replace("/checkout/contact-data");
  };

  componentWillMount() {
    const querySearchParams = new URLSearchParams(this.props.location.search);
    const _ingredients = {};
    let _totalPrice = 0;
    for (const searchParam of querySearchParams.entries()) {
      // ['salad', '1']
      if (searchParam[0] === "totalPrice") {
        _totalPrice = searchParam[1];
      } else {
        _ingredients[searchParam[0]] = +searchParam[1];
      }
    }
    console.log(_ingredients);
    this.setState({
      ingredients: _ingredients,
      totalPrice: _totalPrice
    });
  }

  render() {
    console.log("[Checkout] render()...", this.props);
    return (
      <div className="Checkout">
        <CheckoutSummary
          ingredients={this.state.ingredients}
          checkoutCancelled={this.checkoutCancelledHandler}
          checkoutContinued={this.checkoutContinuedHandler}
        />
        {/* 
        both this.props.match.url and this.props.match.path are okay
        We would not use the following <Route /> format, since it does not allow
        us to pass "props" to the Component rendered by <Route />
        we need to use another format having the prop "render"
        <Route
          path={this.props.match.path + "/contact-data"}
          component={ContactData}
        /> */}
        {/* Althernative to withRouter() HOC, Route will pass the "Route props" to the render()'s arguments */}
        <Route
          path={this.props.match.path + "/contact-data"}
          render={props => (
            <ContactData
              ingredients={this.state.ingredients}
              price={this.state.totalPrice}
              {...props}
            />
          )}
        />
      </div>
    );
  }
}

export default Checkout;
