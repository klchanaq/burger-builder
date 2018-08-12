import React, { Component } from "react";
import "./Auth.css";
import Input from "../../components/UI/Input/Input";
import Button from "../../components/UI/Button/Button";

function checkValidity(value, rules = {}) {
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
}

class Auth extends Component {
  state = {
    controls: {
      email: {
        label: "E-mail",
        elementType: "input",
        elementConfig: {
          type: "email",
          placeholder: "E-mail Address"
        },
        value: "",
        validation: {
          required: true,
          isEmail: true
        },
        valid: false,
        touched: false
      },
      password: {
        label: "Password",
        elementType: "input",
        elementConfig: {
          type: "password",
          placeholder: "Password"
        },
        value: "",
        validation: {
          required: true,
          minLength: 6
        },
        valid: false,
        touched: false
      }
    },
    isLoginFormValid: false
  };

  inputChangedHandler = ($event, controlName) => {
    const updatedControls = {
      ...this.state.controls,
      [controlName]: {
        ...this.state.controls[controlName],
        value: $event.target.value,
        valid: checkValidity(
          $event.target.value,
          this.state.controls[controlName].validation
        ),
        touched: true
      }
    };

    const isLoginFormValid = Object.values(updatedControls)
      .map(loginFormElement => loginFormElement.valid)
      .reduce((currentFormValidity, el) => {
        return currentFormValidity && el;
      }, true);

    this.setState({
      controls: updatedControls,
      isLoginFormValid: isLoginFormValid
    });
  };

  loginHandler = $event => {
    $event.preventDefault();
  };

  render() {
    return (
      <div className="Auth">
        <form onSubmit={this.loginHandler}>
          {Object.entries(this.state.controls).map(([key, formEl]) => {
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
          <Button btnType="Success" disabled={!this.state.isLoginFormValid}>
            Submit
          </Button>
        </form>
      </div>
    );
  }
}

export default Auth;
