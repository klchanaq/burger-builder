import {
  PURCHASE_INIT,
  PURCHASE_BURGER_START,
  PURCHASE_BURGER_SUCCESS,
  PURCHASE_BURGER_FAIL
} from "./actionTypes";
import axios from "../../axios-orders";

var fakedNewOrderId = 10;
var fakedCustomerId = 5;

const purchaseBurgerSuccess = (orderId, customerId, orderData) => {
  return {
    type: PURCHASE_BURGER_SUCCESS,
    orderId: orderId,
    customerId: customerId,
    orderData: orderData
  };
};

const purchaseBurgerFail = error => {
  return {
    type: PURCHASE_BURGER_FAIL,
    error: error
  };
};

const purchaseBurgerStart = () => {
  return {
    type: PURCHASE_BURGER_START
  };
};

export const purchaseInit = () => {
  return {
    type: PURCHASE_INIT
  };
};

export const purchaseBurger = newOrderData => {
  return dispatch => {
    dispatch(purchaseBurgerStart());
    setTimeout(
      randomNum => {
        if (randomNum > 0.2) {
          dispatch(
            purchaseBurgerSuccess(
              fakedNewOrderId++,
              fakedCustomerId++,
              newOrderData
            )
          );
        } else {
          const err = new Error("Failed to POST New Order Data.");
          dispatch(purchaseBurgerFail(err));
        }
      },
      500,
      Math.random()
    );

    // axios
    //   .post("/api/customerOrders", order)
    //   .then(response => {
    //     console.log("[BurgerBuilder] ", response);
    //     this.setState({ loading: false });
    //   })
    //   .catch(error => {
    //     console.error("[BurgerBuilder] ", error);
    //     this.setState({ loading: false });
    //   });
  };
};
