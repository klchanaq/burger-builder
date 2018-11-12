import React, { Component } from "react";
import "./Layout.css";
import Aux from "../HOC";
import Toolbar from "../../components/Navigation/Toolbar/Toolbar";
import SideDrawer from "../../components/Navigation/SideDrawer/SideDrawer";

import { connect } from 'react-redux';

class Layout extends Component {
  state = {
    showSideDrawer: false
  };

  shouldComponentUpdate(nextProps, nextState) {
    console.log("[Layout] shouldComponentUpdate...", nextProps, nextState);
    return true;
  }
  

  sideDrawerOpenHandler = () => {
    this.setState({
      showSideDrawer: true
    });
  };

  sideDrawerClosedHandler = () => {
    this.setState({
      showSideDrawer: false
    });
  };

  sideDrawerToggleHandler = () => {
    this.setState((prevState, props) => {
      return {
        showSideDrawer: !prevState.showSideDrawer
      };
    });
  };

  render() {
    console.log("[Layout] render()...", this.props);
    return (
      <Aux>
        <Toolbar drawerToggleClicked={this.sideDrawerToggleHandler} isAuth={this.props.isAuthenticated} />
        <SideDrawer
          open={this.state.showSideDrawer}
          closed={this.sideDrawerClosedHandler}
          isAuth={this.props.isAuthenticated}
        />
        <main className="Layout-Content">{this.props.children}</main>
      </Aux>
    );
  }
}

const mapStateToProps = state => {
  return {
    isAuthenticated: state.auth.idToken !== null
  };
};

export default connect(mapStateToProps, null)(Layout);