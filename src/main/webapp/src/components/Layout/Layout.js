import React from "react";
import "./Layout.css";
import Aux from "../../hoc/HOC";
import Toolbar from "../Navigation/Toolbar/Toolbar";

const layout = function(props) {
  return (
    <Aux>
      <Toolbar />
      <main className="Layout-Content">{props.children}</main>
    </Aux>
  );
};

export default layout;
