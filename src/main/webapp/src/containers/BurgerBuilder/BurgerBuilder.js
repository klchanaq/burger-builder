import React, { Component } from "react";
import "./BurgerBuilder.css";
import Aux from "../../hoc/HOC";
import Burger from "../../components/Burger/Burger";
import BuildControls from "../../components/Burger/BuildControls/BuildControls";
import Modal from "../../components/UI/Modal/Modal";
import OrderSummary from "../../components/Burger/OrderSummary/OrderSummary";
import axios from "../../axios-orders";
import Spinner from "../../components/UI/Spinner/Spinner";
import withErrorHandler from "../../hoc/withErrorHandler/withErrorHandler";
import { connect } from "react-redux";
import {
  addIngredient,
  removeIngredient,
  initializeIngredients
} from "../../store/actions/index";

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
    purchasing: false
    // ingredients: null,
    // totalPrice: 4,
    // purchasable: false,
    // loading: false,
    // error: false
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
    return sum > 0;
  }

  // addIngredientHandler = type => {
  //   const oldCount = this.props.ings[type];
  //   const updatedCount = oldCount + 1;
  //   const updatedIngredients = {
  //     ...this.props.ings
  //   };
  //   updatedIngredients[type] = updatedCount;
  //   const priceAddition = INGREDIENT_PRICES[type];
  //   const oldPrice = this.props.totalPrice;
  //   const newPrice = oldPrice + priceAddition;
  //     // Warm Reminder: Actually, there are three different ways to use setState();
  //     // #1 this.setState( { newState } );
  //     // #2 this.setState( { newState }, callbackFunction );
  //     //    // callbackFunction having the format () => { return void; } will execute immediately after setState()
  //     // #3 this.setState( (prevState, prevProps) => { return { newState }; });
  //     //    Reference: https://stackoverflow.com/questions/45619297/prevstate-in-this-setstate-a-copy-or-a-reference
  //     //    i.   prevState is exactly the same as this.state ( same reference prevState === this.state ),
  //     //         but using prevState will be better because setState() will be exeuted in queue order,  prevState will use the "last state" just before mutation, but this.state will be affected in the queue order
  //     //    ii.  prevState should be immutable as well just like this.state! Use const newState = { ...prevState } first!
  //     //    iii. Like props, try not to modify the second argument of setState fucntion "prevProps"
  //     // })
  //   this.setState({
  //     ingredients: updatedIngredients,
  //     totalPrice: newPrice,
  //     purchasable: this.updatePurchaseState(updatedIngredients)
  //   });
  // };

  // removeIngredientHandler = type => {
  //   const oldCount = this.props.ings[type];
  //   if (oldCount <= 0) {
  //     return;
  //   }
  //   const updatedCount = oldCount - 1;
  //   const updatedIngredients = {
  //     ...this.props.ings
  //   };
  //   updatedIngredients[type] = updatedCount;
  //   const priceDeduction = INGREDIENT_PRICES[type];
  //   const oldPrice = this.props.totalPrice;
  //   const newPrice = oldPrice - priceDeduction;
  //   this.setState({
  //     ingredients: updatedIngredients,
  //     totalPrice: newPrice,
  //     purchasable: this.updatePurchaseState(updatedIngredients)
  //   });
  // };

  purchaseHandler = () => {
    this.setState({ purchasing: true });
  };

  purchaseCancelHandler = () => {
    this.setState({ purchasing: false });
  };

  purchaseContinueHandler = () => {
    // alert("You continue!");
    // const queryParams = [];
    // for (const [key, val] of Object.entries(this.props.ings)) {
    // // queryParams.push(key+"="+val); // okay as well but not recommended
    //   queryParams.push(encodeURIComponent(key) + "=" + encodeURIComponent(val));
    // }
    // Add Price Param
    // queryParams.push(
    //   encodeURIComponent("totalPrice") +
    //     "=" +
    //     encodeURIComponent(this.props.totalPrice)
    // );
    // const queryString = queryParams.join("&");
    // this.props.history.push({
    //   pathname: "/checkout",
    //   search: "?" + queryString
    // });
    // this.props.history.push("/checkout");
    this.props.history.push("/checkout");
  };

  componentDidMount() {
    this.props.onIngsInitialized();
  }

  render() {
    console.log("[BurgerBuilder] render()...", this.props);
    const disabledInfo = {
      ...this.props.ings
    };
    for (let key in disabledInfo) {
      if (disabledInfo.hasOwnProperty(key)) {
        disabledInfo[key] = disabledInfo[key] <= 0;
      }
    }
    let orderSummary = null;
    let burger = this.props.error ? (
      <p>Ingredients cannot be fetched.</p>
    ) : (
      <Spinner show />
    );
    if (this.props.ings) {
      burger = (
        <Aux>
          <Burger ingredients={this.props.ings} />
          <BuildControls
            // ingredientAdded={this.addIngredientHandler}
            // ingredientRemoved={this.removeIngredientHandler}
            ingredientAdded={this.props.onIngAdded}
            ingredientRemoved={this.props.onIngRemoved}
            disabled={disabledInfo}
            purchasable={this.updatePurchaseState(this.props.ings)}
            ordered={this.purchaseHandler}
            price={this.props.totalPrice}
          />
        </Aux>
      );
      orderSummary = (
        <OrderSummary
          ingredients={this.props.ings}
          price={this.props.totalPrice}
          purchaseCancelled={this.purchaseCancelHandler}
          purchaseContinued={this.purchaseContinueHandler}
        />
      );
    }
    // if (this.state.loading) { orderSummary = <Spinner show />; }
    return (
      <Aux>
        <Modal
          show={this.state.purchasing}
          modalClosed={this.purchaseCancelHandler}
        >
          {orderSummary}
        </Modal>
        {burger}
      </Aux>
    );
  }
}

const mapStateToProps = state => {
  return {
    ings: state.burgerBuilder.ingredients,
    totalPrice: state.burgerBuilder.totalPrice,
    error: state.burgerBuilder.error
  };
};

const mapDispatchToProps = dispatch => {
  return {
    onIngAdded: ingredientName => dispatch(addIngredient(ingredientName)),
    onIngRemoved: ingredientName => dispatch(removeIngredient(ingredientName)),
    onIngsInitialized: () => dispatch(initializeIngredients())
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withErrorHandler(BurgerBuilder, axios));
