import React, { Component } from "react";
import "./BurgerBuilder.css";
import Aux from "../../hoc/HOC";
import Burger from "../../components/Burger/Burger";
import BuildControls from "../../components/Burger/BuildControls/BuildControls";
import Modal from "../../components/UI/Modal/Modal";
import OrderSummary from "../../components/Burger/OrderSummary/OrderSummary";
import axios from "../../axios-orders";
import Spinner from "../../components/UI/Spinner/Spinner";

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
    purchasable: false,
    purchasing: false,
    loading: false
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
    /* 
      Warm Reminder: Actually, there are three different ways to use setState();
      #1 this.setState( { newState } );
      #2 this.setState( { newState }, callbackFunction ); 
         // callbackFunction having the format () => { return void; } will execute immediately after setState()
      #3 this.setState( (prevState, prevProps) => { return { newState }; });
         Reference: https://stackoverflow.com/questions/45619297/prevstate-in-this-setstate-a-copy-or-a-reference
         i.   prevState is exactly the same as this.state ( same reference prevState === this.state ), 
              but using prevState will be better because setState() will be exeuted in queue order,  prevState will use the "last state" just before mutation, but this.state will be affected in the queue order 
         ii.  prevState should be immutable as well just like this.state! Use const newState = { ...prevState } first!
         iii. Like props, try not to modify the second argument of setState fucntion "prevProps"
      })
   */
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

  purchaseHandler = () => {
    this.setState({ purchasing: true });
  };

  purchaseCancelHandler = () => {
    this.setState({ purchasing: false });
  };

  purchaseContinueHandler = () => {
    this.setState({ loading: true });
    // alert("You continue!");
    const order = {
      ingredients: this.state.ingredients,
      price: this.state.totalPrice,
      customer: {
        name: "Edward Chan",
        address: {
          street: "Teststreet 1",
          zipCode: "12345",
          country: "China Hong Kong"
        },
        email: "test@test.com"
      },
      deliveryMethod: "fastest"
    };

    setTimeout(() => {
      this.setState({ loading: false, purchasing: false });
    }, 3000);

    // axios
    //   .post("/api/orders")
    //   .then(response => {
    //     console.log(response);
    //   })
    //   .catch(error => {
    //     console.log(error);
    //   });
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
    let orderSummary = (
      <OrderSummary
        ingredients={this.state.ingredients}
        price={this.state.totalPrice}
        purchaseCancelled={this.purchaseCancelHandler}
        purchaseContinued={this.purchaseContinueHandler}
      />
    );
    if (this.state.loading) {
      orderSummary = <Spinner show />;
    }
    return (
      <Aux>
        <Modal
          show={this.state.purchasing}
          modalClosed={this.purchaseCancelHandler}
        >
          {orderSummary}
        </Modal>
        <Burger ingredients={this.state.ingredients} />
        <BuildControls
          ingredientAdded={this.addIngredientHandler}
          ingredientRemoved={this.removeIngredientHandler}
          disabled={disabledInfo}
          purchasable={this.state.purchasable}
          ordered={this.purchaseHandler}
          price={this.state.totalPrice}
        />
      </Aux>
    );
  }
}

export default BurgerBuilder;
