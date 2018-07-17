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
    setTimeout(
      res => {
        this.setState({
          orders: res.data,
          loading: false
        });
      },
      1500,
      {
        data: [
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
        ]
      }
    );
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
    console.log("[Orders] render()...", this.props);
    let orders = this.state.orders.map((order, i) => {
      // as mentioned, the index of list to be key cannot be trusted
      return (
        <Order
          key={order.id}
          ingredients={order.ingredients}
          price={order.price}
        />
      );
    });
    return <div className="Orders">{orders}</div>;
  }
}

export default withErrorHandler(Orders, axios);
