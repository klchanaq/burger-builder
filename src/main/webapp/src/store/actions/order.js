import {
  PURCHASE_INIT,
  PURCHASE_BURGER_START,
  PURCHASE_BURGER_SUCCESS,
  PURCHASE_BURGER_FAIL,
  FETCH_ORDERS_START,
  FETCH_ORDERS_SUCCESS,
  FETCH_ORDERS_FAIL
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
  return (dispatch, getStore) => {
    dispatch(purchaseBurgerStart());
    // React Complete Guide 317+ : Add idToken for protected resources as RequestParams ( ?auth=xyz ).
    const idToken = getStore().auth.idToken || localStorage.getItem("idToken");
    const localId = getStore().auth.localId || localStorage.getItem("localId");
    axios({
          url: "/api/customerOrders",
          method: "POST",
          headers: { Authorization: `Bearer ${idToken}` },
          data: newOrderData
      })
      .then(res => {
        console.log("[BurgerBuilder] ", res);
        dispatch(purchaseBurgerSuccess(res.data.id, res.data.customer.id, newOrderData));
        // this.setState({ loading: false });
      })
      .catch(err => {
        console.error("[BurgerBuilder] ", err);
        dispatch(purchaseBurgerFail(err));
        // this.setState({ loading: false });
      });
  };
};

const fetchOrdersSuccess = orders => {
  return {
    type: FETCH_ORDERS_SUCCESS,
    orders: orders
  };
};

const fetchOrdersFail = error => {
  return {
    type: FETCH_ORDERS_FAIL,
    error: error
  };
};

const fetchOrdersStart = () => {
  return {
    type: FETCH_ORDERS_START
  };
};

export const fetchOrders = () => {
  return (dispatch, getStore) => {
    dispatch(fetchOrdersStart());
    /* setTimeout(
      randomNum => {
        if (randomNum > 0.2) {
          const response = { data: getStore().order.orders };
          const fetchedOrders = response.data;
          dispatch(fetchOrdersSuccess(fetchedOrders));
        } else {
          const err = new Error("Failed to Fetch Orders.");
          dispatch(fetchOrdersFail(err));
        }
      },
      500,
      Math.random()
    ); */

    // React Complete Guide 317+ : Add idToken for protected resources as RequestParams ( ?auth=xyz ).
    const idToken = getStore().auth.idToken || localStorage.getItem("idToken");
    const localId = getStore().auth.localId || localStorage.getItem("localId");
    axios({
      url: "/api/customerOrders",
      method: "GET",
      headers: { Authorization: `Bearer ${idToken}` },
      params: {
        customerId: localId
      }
      /* paramsSerializer: function(params) { 
        return Qs.stringify(params, { arrayFormat: "repeat" }); // most common pattern
        //return Qs.stringify(params);
        //return Qs.stringify(params, { arrayFormat: "brackets" });
        //return new URLSearchParams(params);
      } */
    })
      .then(res => {
        const fetchedOrders = res.data;
        dispatch(fetchOrdersSuccess(fetchedOrders));
      })
      .catch(err => {
        dispatch(fetchOrdersFail(err));
      });
  };
};