import {
  PURCHASE_INIT,
  PURCHASE_BURGER_START,
  PURCHASE_BURGER_SUCCESS,
  PURCHASE_BURGER_FAIL,
  FETCH_ORDERS_START,
  FETCH_ORDERS_SUCCESS,
  FETCH_ORDERS_FAIL
} from "../actions/actionTypes";

const initialState = {
  orders: [
    {
      id: 1,
      ingredients: { bacon: 1, cheese: 2, salad: 1, meat: 3 },
      price: 9.5,
      deliveryMethod: "NOT_SPECIFIC",
      customer: {
        id: 1,
        name: "hkcustomer",
        email: "hkcustomer@gmail.com",
        address: { country: "HK", street: "HK Street", zipCode: null }
      }
    },
    {
      id: 2,
      ingredients: { bacon: 0, cheese: 0, salad: 0, meat: 5 },
      price: 11.5,
      deliveryMethod: "FASTEST",
      customer: 1
    },
    {
      id: 3,
      ingredients: { bacon: 5, cheese: 0, salad: 1, meat: 0 },
      price: 10.4,
      deliveryMethod: "NORMAL",
      customer: {
        id: 2,
        name: "germanCustomer",
        email: "germanCustomer@gmail.com",
        address: {
          country: "Germany",
          street: "Germany Street",
          zipCode: null
        }
      }
    },
    {
      id: 4,
      ingredients: { bacon: 0, cheese: 2, salad: 3, meat: 0 },
      price: 7.5,
      deliveryMethod: "NOT_SPECIFIC",
      customer: 2
    }
  ],
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
    case FETCH_ORDERS_START:
      return {
        ...state,
        loading: true
      };
    case FETCH_ORDERS_SUCCESS:
      return {
        ...state,
        loading: false,
        orders: action.orders
      };
    case FETCH_ORDERS_FAIL:
      return {
        ...state,
        loading: false
      };
    default:
      return state;
  }
};

export default reducer;
