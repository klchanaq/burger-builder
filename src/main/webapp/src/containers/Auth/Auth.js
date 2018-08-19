import React, { Component } from "react";
import "./Auth.css";

import { connect } from "react-redux";
import { auth } from "../../store/actions/index";

import Input from "../../components/UI/Input/Input";
import Button from "../../components/UI/Button/Button";
import Spinner from "../../components/UI/Spinner/Spinner";

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
    isLoginFormValid: false,
    loginStatus: "Sign-up" // "Sign-up" / "Sign-in"
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
    const {
      email: emailControl,
      password: passwordControl
    } = this.state.controls;

    if (this.state.loginStatus === "Sign-up") {
      console.log("Sign-up Process");
      this.props.onAuth(emailControl.value, passwordControl.value, "Sign-up");
    } else {
      console.log("Sign-in Process");
      this.props.onAuth(emailControl.value, passwordControl.value, "Sign-in");
    }
  };

  loginStatusSwitchedHandler = $event => {
    this.setState(prevState => {
      return {
        loginStatus: prevState.loginStatus === "Sign-up" ? "Sign-in" : "Sign-up"
      };
    });
  };

  render() {
    let formControls = Object.entries(this.state.controls).map(
      ([key, formEl]) => {
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
      }
    );
    if (this.props.loading) {
      formControls = <Spinner show />;
    }

    let errorMsg = this.props.error && (
      <h3 style={{ color: "red" }}>Invalid Login Information.</h3>
    );

    return (
      <div className="Auth">
        <form onSubmit={this.loginHandler}>
          {errorMsg}
          <h3>{this.state.loginStatus}</h3>
          {formControls}
          <Button btnType="Success" disabled={!this.state.isLoginFormValid}>
            Submit
          </Button>
        </form>
        <Button btnType="Danger" clicked={this.loginStatusSwitchedHandler}>
          Switch To{" "}
          {this.state.loginStatus === "Sign-up" ? "Sign-in" : "Sign-up"}
        </Button>
      </div>
    );
  }
}

const mapStateToProps = state => {
  return {
    loading: state.auth.loading,
    error: state.auth.error
  };
};

const mapDispatchToProps = dispatch => {
  return {
    onAuth: (email, password, loginStatus) =>
      dispatch(auth(email, password, loginStatus))
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Auth);
