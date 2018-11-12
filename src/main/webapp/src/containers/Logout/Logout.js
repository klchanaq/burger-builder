import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import { connect } from "react-redux";
import { logout } from "../../store/actions";

class Logout extends Component {
  shouldComponentUpdate(nextProps, nextState) {
    console.log("[Logout] shouldComponentUpdate...", nextProps, nextState);
    return true;
  }
  componentDidMount() {
    // setTimeout( () => this.props.logout(), 5000);
    this.props.logout();
  }
  render() {
    console.log("[Logout] render...");
    return <Redirect to="/" />;
  }
}

const mapDispatchToProps = dispatch => {
  return {
    logout: () => dispatch(logout())
  };
};

export default connect(
  null,
  mapDispatchToProps
)(Logout);