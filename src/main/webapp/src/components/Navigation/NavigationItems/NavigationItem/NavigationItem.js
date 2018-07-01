import React from "react";
import "./NavigationItem.css";

const navigationItem = function(props) {
  return (
    <li className="NavigationItem">
      <a href={props.link} className={props.active ? "active" : null}>
        {props.children}
      </a>
    </li>
  );
};

export default navigationItem;
