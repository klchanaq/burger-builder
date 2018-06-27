import React from "react";
import "./BuildControl.css";

const buildControl = function(props) {
  return (
    <div className="BuildControl">
      {/* <p>BuildControl works!</p> */}
      <div className="Label">{props.label}</div>
      <button
        className="Less"
        onClick={props.removed}
        disabled={props.disabled}
      >
        Less
      </button>
      <button className="More" onClick={props.added}>
        More
      </button>
    </div>
  );
};

export default buildControl;
