import React from "react";
import "./Input.css";

const input = function(props) {
  const { label, elementType, elementConfig, value, invalid, touched, changed } = props;

  let inputElement = null;

  // Validty Checking
  let inValidInput = undefined;
  if (invalid && touched) {
    inValidInput = "Input-Invalid";
  }

  switch (elementType) {
    case "input": {
      inputElement = (
        <input
          className={inValidInput}
          {...elementConfig}
          value={value}
          onChange={changed}
        />
      );
      break;
    }
    case "textarea": {
      inputElement = (
        <textarea
          className={inValidInput}
          {...elementConfig}
          value={value}
          onChange={changed}
        />
      );
      break;
    }
    case "select": {
      inputElement = (
        <select className={inValidInput} value={value} onChange={changed}>
          {elementConfig.options.map(option => (
            <option key={option.value} value={option.value}>
              {option.displayValue}
            </option>
          ))}
        </select>
      );
      break;
    }
    default:
      inputElement = (
        <input className={inValidInput} {...elementConfig} value={value} />
      );
  }

  return (
    <div className="Input">
      <label>{label}</label>
      {inputElement}
    </div>
  );
};

export default input;
