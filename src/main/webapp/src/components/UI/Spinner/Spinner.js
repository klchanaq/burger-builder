import React from "react";
import './Spinner.css';

const spinner = function(props) {
  const { show } = props;
  return show ? <div className="Loader">Loading...</div> : null;
};

export default spinner;
