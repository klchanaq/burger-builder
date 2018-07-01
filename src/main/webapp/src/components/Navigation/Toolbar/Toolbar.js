import React from "react";
import "./Toolbar.css";
import NavigationItems from "../NavigationItems/NavigationItems";

const toolbar = function(props) {
  return (
    <header className="Toolbar">
      <div>Menu</div>
      <div>Logo</div>
      <nav>
        <NavigationItems />
      </nav>
    </header>
  );
};

export default toolbar;
