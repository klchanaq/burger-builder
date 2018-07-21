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
        value: "",
        validation: {
          required: true
        },
        valid: false,
        touched: false
      },
      street: {
        label: "Street",
        elementType: "input",
        elementConfig: {
          type: "text",
          placeholder: "Street"
        },
        value: "",
        validation: {
          required: true
        },
        valid: false,
        touched: false
      },
      zipCode: {
        label: "Zip Code",
        elementType: "input",
        elementConfig: {
          type: "text",
          placeholder: "ZIP Code"
        },
        value: "",
        validation: {
          required: true,
          minLength: 5,
          maxLength: 5,
          isNumeric: true
        },
        valid: false,
        touched: false
      },
      country: {
        label: "Country",
        elementType: "input",
        elementConfig: {
          type: "text",
          placeholder: "Country"
        },
        value: "",
        validation: {
          required: true
        },
        valid: false,
        touched: false
      },
      email: {
        label: "E-mail",
        elementType: "input",
        elementConfig: {
          type: "email",
          placeholder: "Your E-Mail"
        },
        value: "",
        validation: {
          required: true,
          isEmail: true
        },
        valid: false,
        touched: false
      },
      deliveryMethod: {
        label: "Delivery Method",
        elementType: "select",
        elementConfig: {
          options: [
            { value: "NOT_SPECIFIC", displayValue: "Not Specific" },
            { value: "NORMAL", displayValue: "Normal" },
            { value: "FASTEST", displayValue: "Fastest" }
          ]
        },
        value: "NOT_SPECIFIC",
        validation: {},
        valid: true
      }
    },
    isOrderFormValid: false,
    loading: false
  };

  /* 
  the default value of parameter 'rules' will be resolved as rules = [arguments] || {}
  it is a good practice when you want to aviod the error "the xxx property is undefined"
     call to checkValidity('a', { required: true }) // rules = { required: true } || {}
     call to checkValidity('a', { })// rules = {} || {}
     call to checkValidity('a') // rules = undefined || {}
     call to checkValidity('a', undefined) // rules = undefined || {}
  all of the usages of checkValidity() function will not throw error
  */
   /*
   if no default value:
     call to checkValidity('a', { required: true }) // rules = { required: true }
     call to checkValidity('a', {})// rules = {}
     call to checkValidity('a') // rules = undefined
     call to checkValidity('a', undefined) // rules = undefined
   when you want to access the property of 'undefined' i.e. rules.required, error will throw
   */

  checkValidity = function(value, rules = {}) {
    let isValid = true; // true at this moment in order to solve the common validation gotcha
    if (rules.required) {
      // check the input value : string
      const _trimmedValue = value.trim();
      isValid = _trimmedValue !== "" && isValid;
    }
    if (rules.minLength) {
      isValid = value.length >= rules.minLength && isValid;
    }

    if (rules.maxLength) {
      isValid = value.length <= rules.maxLength && isValid;
    }
    if (rules.isEmail) {
      const pattern = /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/;
      isValid = pattern.test(value) && isValid;
    }

    if (rules.isNumeric) {
      const pattern = /^\d+$/;
      isValid = pattern.test(value) && isValid;
    }

    return isValid;
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
    updatedFormElement.valid = this.checkValidity(
      updatedFormElement.value,
      updatedFormElement.validation
    );
    updatedFormElement.touched = true;
    updatedOrderForm[inputIdentifier] = updatedFormElement;

    // Object.values(updatedOrderForm).map(({ valid }) => {
    //   isOrderFormValid = valid && isOrderFormValid;
    // });
    const isOrderFormValid = Object.values(updatedOrderForm)
      .map(orderFormElement => orderFormElement.valid)
      .reduce((currentFormValidity, el) => {
        return currentFormValidity && el;
      }, true);

    this.setState({
      orderForm: updatedOrderForm,
      isOrderFormValid: isOrderFormValid
    });
  };

  orderHandler = $event => {
    $event.preventDefault();
    this.setState({ loading: true });
    const { orderForm } = this.state;
    const order = {
      ingredients: this.props.ingredients,
      price: this.props.price,
      customer: {
        name: orderForm.name.value,
        address: {
          street: orderForm.street.value,
          zipCode: orderForm.zipCode.value,
          country: orderForm.country.value
        },
        email: orderForm.email.value
      },
      deliveryMethod: orderForm.deliveryMethod.value
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
      // we need to use onSubmit instead of onClick on button
      // becuase we want to have basic validation checking by HTML form e.g. email format
      <form onSubmit={this.orderHandler}>
        {Object.entries(this.state.orderForm).map(([key, formEl]) => {
          return (
            <Input
              key={key}
              label={formEl.label}
              elementType={formEl.elementType}
              elementConfig={formEl.elementConfig}
              value={formEl.value}
              invalid={!formEl.valid}
              touched={formEl.touched}
              changed={e => this.inputChangedHandler(e, key)}
            />
          );
        })}
        <Button btnType="Success" disabled={!this.state.isOrderFormValid}>
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
