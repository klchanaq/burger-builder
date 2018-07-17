import React, { Component } from "react";
import "./ContactData.css";
import Button from "../../../components/UI/Button/Button";
import Spinner from "../../../components/UI/Spinner/Spinner";
import axios from "../../../axios-orders";

class ContactData extends Component {
  state = {
    name: "",
    email: "",
    address: {
      street: "",
      postalCode: ""
    },
    loading: false
  };

  orderHandler = $event => {
    $event.preventDefault();
    // console.log("[ContactData] this.props", this.props);
    this.setState({ loading: true });
    const order = {
      ingredients: this.props.ingredients,
      price: this.props.price,
      customer: {
        name: "Edward Chan",
        address: {
          street: "Teststreet 1",
          zipCode: "12345",
          country: "China Hong Kong"
        },
        email: "test@test.com"
      },
      deliveryMethod: "fastest"
    };

    setTimeout(() => {
      this.setState({ loading: false });
      this.props.history.push("/");
    }, 3000);

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

  render() {
    console.log("[ContactData] render()...", this.props);
    let form = (
      <form>
        <input type="text" name="name" placeholder="Your Name" />
        <input type="email" name="email" placeholder="Your E-mail" />
        <input type="text" name="street" placeholder="Your Street" />
        <input type="text" name="postalCode" placeholder="Your Postal Code" />
        <Button btnType="Success" clicked={this.orderHandler}>
          Confirm
        </Button>
      </form>
    );
    if (this.state.loading) {
      form = <Spinner show />;
    }
    return (
      <div className="ContactData">
        <h4>Please Enter Your Contact Data.</h4>
        {form}
      </div>
    );
  }
}

export default ContactData;
