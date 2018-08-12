import React, { Component } from "react";
import "./Checkout.css";
import { connect } from "react-redux";
import CheckoutSummary from "../../components/Order/CheckoutSummary/CheckoutSummary";
import { Route, Redirect } from "react-router-dom";
import ContactData from "./ContactData/ContactData";
import { purchaseInit } from "../../store/actions/index";

class Checkout extends Component {
  // state = {
  //   ingredients: null,
  //   totalPrice: 0
  // };

  checkoutCancelledHandler = () => {
    this.props.history.goBack();
  };

  checkoutContinuedHandler = () => {
    this.props.history.replace(this.props.match.path + "/contact-data");
  };

  testButtonClick = () => {
    this.props.onInitPurchase();
    // the result would be the same you thought, which is [Checkout.js] > [checkoutSummary] > [burger] > [ContactData]
    // However, you cannot see the update on console.log becuase "Redux" use "ShouldComponentUpdate" to check the mapStateToProps to see whether required states have changed
    // But you can modify the mapStateToProps on both Checkout & ContactData to let them require the changed props
  };

  componentWillMount() {
    /*
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
    */
  }

  render() {
    console.log("[Checkout] render()...", this.props);
    const isIngsExisted = this.props.ings;
    if (!isIngsExisted) {
      return <Redirect to="/" />;
    }
    if (this.props.purchased) {
      return <Redirect to="/" />;
    }
    return (
      <div className="Checkout">
        <button onClick={this.testButtonClick}>Test Batch Update</button>
        <CheckoutSummary
          ingredients={this.props.ings}
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
        {/* <Route
          path={this.props.match.path + "/contact-data"}
          render={props => (
            <ContactData
              ingredients={this.state.ingredients}
              price={this.state.totalPrice}
              {...props}
            />
          )}
        /> */}
        <Route
          path={this.props.match.path + "/contact-data"}
          component={ContactData}
        />
      </div>
    );
  }
}

const mapStateToProps = state => {
  return {
    ings: state.burgerBuilder.ingredients,
    purchased: state.order.purchased
  };
};

export default connect(mapStateToProps)(Checkout);
