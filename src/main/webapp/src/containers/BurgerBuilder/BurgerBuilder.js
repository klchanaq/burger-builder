import React, { Component } from "react";
import "./BurgerBuilder.css";
import Aux from "../../hoc/HOC";
import Burger from "../../components/Burger/Burger";
import BuildControls from "../../components/Burger/BuildControls/BuildControls";
import Modal from "../../components/UI/Modal/Modal";

const INGREDIENT_PRICES = {
  bacon: 0.7,
  cheese: 0.4,
  salad: 0.5,
  meat: 1.3
};

/* 
  function incrementIngsHandler(number) {
    return $event => {
      const updatedIngs = { ...this.state.ingredients };
      for (const key of Object.keys(updatedIngs)) {
        updatedIngs[key] += number;
      }
      this.setState({ ingredients: updatedIngs });
    };
  }
*/

/*
  function addIngredientHandler(type) {
    return $event => {
      const oldCount = this.state.ingredients[type];
      const updatedCount = oldCount + 1;
      const updatedIngredients = {
        ...this.state.ingredients
      };
      updatedIngredients[type] = updatedCount;
      const priceAddition = INGREDIENT_PRICES[type];
      const oldPrice = this.state.totalPrice;
      const newPrice = oldPrice + priceAddition;
      this.setState({
        ingredients: updatedIngredients,
        totalPrice: newPrice,
        purchasable: this.updatePurchaseState(updatedIngredients)
      });
    };
  }
*/

class BurgerBuilder extends Component {
  state = {
    ingredients: {
      bacon: 0,
      cheese: 0,
      salad: 0,
      meat: 0
    },
    totalPrice: 4,
    purchasable: false
  };

  // incrementIngsHandler = incrementIngsHandler.bind(this);
  // addIngredientHandler = addIngredientHandler.bind(this);

  updatePurchaseState(updatedIngredients) {
    const sum = Object.keys(updatedIngredients)
      .map(igKey => {
        return updatedIngredients[igKey];
      })
      .reduce((sum, el) => {
        return sum + el;
      }, 0);
    return sum;
  }

  addIngredientHandler = type => {
    const oldCount = this.state.ingredients[type];
    const updatedCount = oldCount + 1;
    const updatedIngredients = {
      ...this.state.ingredients
    };
    updatedIngredients[type] = updatedCount;
    const priceAddition = INGREDIENT_PRICES[type];
    const oldPrice = this.state.totalPrice;
    const newPrice = oldPrice + priceAddition;
    this.setState({
      ingredients: updatedIngredients,
      totalPrice: newPrice,
      purchasable: this.updatePurchaseState(updatedIngredients)
    });
  };

  removeIngredientHandler = type => {
    const oldCount = this.state.ingredients[type];
    if (oldCount <= 0) {
      return;
    }
    const updatedCount = oldCount - 1;
    const updatedIngredients = {
      ...this.state.ingredients
    };
    updatedIngredients[type] = updatedCount;
    const priceDeduction = INGREDIENT_PRICES[type];
    const oldPrice = this.state.totalPrice;
    const newPrice = oldPrice - priceDeduction;
    this.setState({
      ingredients: updatedIngredients,
      totalPrice: newPrice,
      purchasable: this.updatePurchaseState(updatedIngredients)
    });
  };

  render() {
    const disabledInfo = {
      ...this.state.ingredients
    };
    for (let key in disabledInfo) {
      if (disabledInfo.hasOwnProperty(key)) {
        disabledInfo[key] = disabledInfo[key] <= 0;
      }
    }
    return (
      <Aux>
        <Modal />
        <Burger ingredients={this.state.ingredients} />
        <BuildControls
          ingredientAdded={this.addIngredientHandler}
          ingredientRemoved={this.removeIngredientHandler}
          disabled={disabledInfo}
          purchasable={this.state.purchasable}
          price={this.state.totalPrice}
        />
      </Aux>
    );
  }
}

export default BurgerBuilder;
