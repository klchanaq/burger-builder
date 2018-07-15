import React, { Component } from "react";
import "./Checkout.css";
import CheckoutSummary from "../../components/Order/CheckoutSummary/CheckoutSummary";

class Checkout extends Component {
  state = {
    ingredients: {
      bacon: 1,
      cheese: 1,
      meat: 1,
      salad: 1
    }
  };

  checkoutCancelledHandler = () => {
    this.props.history.goBack();
  };

  checkoutContinuedHandler = () => {
    this.props.history.replace("/checkout/contact-data");
  };

  componentDidMount() {
    const querySearchParams = new URLSearchParams(this.props.location.search);
    const _ingredients = {};
    for (const searchParam of querySearchParams.entries()) {
      // ['salad', '1']
      _ingredients[searchParam[0]] = +searchParam[1];
    }
    console.log(_ingredients);
    this.setState({
      ingredients: _ingredients
    });
  }

  render() {
    console.log("[Checkout] render()...");
    console.log("[Checkout] props = ", this.props);
    return (
      <div className="Checkout">
        <CheckoutSummary
          ingredients={this.state.ingredients}
          checkoutCancelled={this.checkoutCancelledHandler}
          checkoutContinued={this.checkoutContinuedHandler}
        />
      </div>
    );
  }
}

export default Checkout;
