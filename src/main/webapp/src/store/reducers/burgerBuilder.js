import {
  ADD_INGREDIENT,
  REMOVE_INGREDIENT,
  SET_INGREDIENTS,
  FETCH_INGREDIENTS_FAILED
} from "../actions/actionTypes";

const INGREDIENT_PRICES = {
  bacon: 0.7,
  cheese: 0.4,
  salad: 0.5,
  meat: 1.3
};

const initialState = {
  ingredients: null,
  totalPrice: 4,
  error: false
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case ADD_INGREDIENT:
      return {
        ...state,
        ingredients: {
          ...state.ingredients,
          [action.ingredientName]: state.ingredients[action.ingredientName] + 1
        },
        totalPrice: state.totalPrice + INGREDIENT_PRICES[action.ingredientName]
      };
    case REMOVE_INGREDIENT:
      return {
        ...state,
        ingredients: {
          ...state.ingredients,
          [action.ingredientName]: state.ingredients[action.ingredientName] - 1
        },
        totalPrice: state.totalPrice - INGREDIENT_PRICES[action.ingredientName]
      };
    case SET_INGREDIENTS: {
      // determine whether the user has saved ingredients changes.
      // if global state ingredients have been initialized, then simply use it, set the ingredients otherwise.
      const initialOrSubsequentIngs = state.ingredients || action.ingredients; 
      const calculatedTotalPrice =
        initialState.totalPrice +
        Object.entries(initialOrSubsequentIngs)
          .map(([key, value]) => {
            return INGREDIENT_PRICES[key] * value;
          })
          .reduce((currentVal, el) => {
            return currentVal + el;
          }, 0);
      return {
        ...state,
        ingredients: initialOrSubsequentIngs,
        totalPrice: calculatedTotalPrice,
        error: false // reset the 'error'
      };
    }
    case FETCH_INGREDIENTS_FAILED:
      return {
        ...state,
        error: true
      };
    default:
      return state;
  }
};

export default reducer;