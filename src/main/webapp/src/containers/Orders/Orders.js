import React, { Component } from "react";
import "./Orders.css";
import Order from "../../components/Order/Order";
import Spinner from "../../components/UI/Spinner/Spinner";
import axios from "../../axios-orders";
import withErrorHandler from "../../hoc/withErrorHandler/withErrorHandler";
import { connect } from "react-redux";
import { fetchOrders } from "../../store/actions/index";

class Orders extends Component {
  // state = {
  //  orders: [],
  //  loading: true // initial loading is true becuase we want to start loading first
  // };
  componentDidMount() {
    this.props.onFetchOrders();
  }
  render() {
    console.log("[Orders] render()...", this.props);
    let orders = <Spinner show />;
    if (!this.props.loading) {
      orders = this.props.orders.map((order, i) => {
        // as mentioned, the index of list to be key cannot be trusted
        return (
          <Order
            key={order.id}
            ingredients={order.ingredients}
            price={order.price.toFixed(2)}
          />
        );
      });
    }
    return <div className="Orders">{orders}</div>;
  }
}

const mapStateToProps = state => {
  return {
    orders: state.order.orders,
    loading: state.order.loading
  };
};

const mapDispatchToProps = dispatch => {
  return {
    // onFetchOrders name in order to synchronize with the action name
    onFetchOrders: () => dispatch(fetchOrders())
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withErrorHandler(Orders, axios));
