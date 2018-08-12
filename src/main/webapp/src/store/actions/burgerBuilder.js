import {
  ADD_INGREDIENT,
  REMOVE_INGREDIENT,
  SET_INGREDIENTS,
  FETCH_INGREDIENTS_FAILED
} from "./actionTypes";
import axios from "../../axios-orders";

export const addIngredient = ingredientName => {
  return {
    type: ADD_INGREDIENT,
    ingredientName: ingredientName
  };
};

export const removeIngredient = ingredientName => {
  return {
    type: REMOVE_INGREDIENT,
    ingredientName: ingredientName
  };
};

const setIngredients = ingredients => {
  return {
    type: SET_INGREDIENTS,
    ingredients: ingredients
  };
};

const fetchIngredientsFailed = () => ({
  type: FETCH_INGREDIENTS_FAILED
});

export const initializeIngredients = () => {
  return function(dispatch) {
    setTimeout(
      randomNum => {
        if (randomNum > 0.2) {
          const _ingredients = {
            bacon: 0,
            cheese: 0,
            salad: 0,
            meat: 0
          };
          dispatch(setIngredients(_ingredients));
        } else {
          dispatch(fetchIngredientsFailed());
        }
      },
      500,
      Math.random()
    );

    // axios
    //   .get("/api/customerOrders/" + 1)
    //   .then(response => {
    //     console.log("[BurgerBuilder] response = ", response);
    //     const _ingredients  = response.data.ingredients;
    //     const _totalPrice = response.data.price;
    //     this.setState({
    //       ingredients: _ingredients,
    //       totalPrice: _totalPrice,
    //       purchasable: this.updatePurchaseState(_ingredients)
    //     });
    //   })
    //   .catch(err => {
    //     console.error("[BurgerBuilder] error = ", err);
    //     this.setState({
    //       error: true
    //     });
    //   });
  };
};
