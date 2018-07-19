import React, { Component } from "react";
import "./ContactData.css";
import Button from "../../../components/UI/Button/Button";
import Spinner from "../../../components/UI/Spinner/Spinner";
import axios from "../../../axios-orders";
import Input from "../../../components/UI/Input/Input";

class ContactData extends Component {
  state = {
    orderForm: {
      name: {
        label: "Name",
        elementType: "input",
        elementConfig: {
          type: "text",
          placeholder: "Your Name"
        },
        value: ""
      },
      street: {
        label: "Street",
        elementType: "input",
        elementConfig: {
          type: "text",
          placeholder: "Street"
        },
        value: ""
      },
      zipCode: {
        label: "Zip Code",
        elementType: "input",
        elementConfig: {
          type: "text",
          placeholder: "ZIP Code"
        },
        value: ""
      },
      country: {
        label: "Country",
        elementType: "input",
        elementConfig: {
          type: "text",
          placeholder: "Country"
        },
        value: ""
      },
      email: {
        label: "E-mail",
        elementType: "input",
        elementConfig: {
          type: "email",
          placeholder: "Your E-Mail"
        },
        value: ""
      },
      deliveryMethod: {
        label: "Delivery Method",
        elementType: "select",
        elementConfig: {
          options: [
            { value: "FATEST", displayValue: "Fastest" },
            { value: "NORMAL", displayValue: "Normal" },
            { value: "NOT_SPECIFIC", displayValue: "Not Specific" }
          ]
        },
        value: ""
      }
    },
    loading: false
  };

  inputChangedHandler = ($event, inputIdentifier) => {
    const updatedOrderForm = {
      ...this.state.orderForm
    };
    // clone the first-level inner object is totally enough, since "value" is right there
    const updatedFormElement = {
      ...updatedOrderForm[inputIdentifier]
    };
    updatedFormElement.value = $event.target.value;
    updatedOrderForm[inputIdentifier] = updatedFormElement;
    this.setState({ orderForm: updatedOrderForm });
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
        {Object.entries(this.state.orderForm).map(([key, formEl]) => {
          return (
            <Input
              key={key}
              label={formEl.label}
              elementType={formEl.elementType}
              elementConfig={formEl.elementConfig}
              value={formEl.value}
              changed={e => this.inputChangedHandler(e, key)}
            />
          );
        })}
        <Button btnType="Success" clicked={null}>
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
