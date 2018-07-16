import React, { Component } from "react";
import "./Orders.css";
import Order from "../../components/Order/Order";
import axios from "../../axios-orders";
import withErrorHandler from "../../hoc/withErrorHandler/withErrorHandler";

class Orders extends Component {
  state = {
    orders: [],
    loading: true // initial loading is true becuase we want to start loading first
  };
  componentDidMount() {
    setTimeout(() => {}, 2500, {
      data: [{}, {}]
    });
    // axios
    //   .get("/api/customerOrders")
    //   .then(res => {
    //     const fetchOrders = [];
    //     for (let key in res.data) {
    //       fetchOrders.push({ ...res.data[key], id: key });
    //     }
    //     this.setState({ loading: false, orders: fetchOrders });
    //   })
    //   .catch(err => {
    //     this.setState({ loading: false });
    //   });
  }
  render() {
    return (
      <div className="Orders">
        <Order />
        <Order />
        <Order />
        <Order />
      </div>
    );
  }
}

export default withErrorHandler(Orders);
