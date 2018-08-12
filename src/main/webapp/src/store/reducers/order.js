import {
  PURCHASE_INIT,
  PURCHASE_BURGER_START,
  PURCHASE_BURGER_SUCCESS,
  PURCHASE_BURGER_FAIL
} from "../actions/actionTypes";

const initialState = {
  orders: [],
  loading: false, // will be used for showing Spinner, executed before any order action
  purchased: false
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case PURCHASE_INIT:
      return {
        ...state,
        purchased: false
      };
    case PURCHASE_BURGER_START:
      return {
        ...state,
        loading: true
      };
    case PURCHASE_BURGER_SUCCESS: {
      const { orderId, customerId, orderData } = action;
      const newOrder = {
        id: orderId,
        ...orderData,
        customer: {
          id: customerId,
          ...orderData.customer
        }
      };
      return {
        ...state,
        orders: [...state.orders, newOrder],
        loading: false,
        purchased: true
      };
    }
    case PURCHASE_BURGER_FAIL:
      return {
        ...state,
        loading: false
      };
    default:
      return state;
  }
};

export default reducer;
