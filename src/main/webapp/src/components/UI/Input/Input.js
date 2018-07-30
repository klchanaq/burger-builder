import React from "react";
import "./Input.css";

const input = function(props) {
  const {
    label,
    elementType,
    elementConfig,
    value,
    invalid,
    touched,
    changed
  } = props;

  let inputElement = null;

  // Validty Checking
  let invalidInput = undefined;
  if (invalid && touched) {
    invalidInput = "Input-Invalid";
  }

  switch (elementType) {
    case "input": {
      inputElement = (
        <input
          className={invalidInput}
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
          className={invalidInput}
          {...elementConfig}
          value={value}
          onChange={changed}
        />
      );
      break;
    }
    case "select": {
      inputElement = (
        <select className={invalidInput} value={value} onChange={changed}>
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
        <input
          className={invalidInput}
          {...elementConfig}
          value={value}
          onChange={changed}
        />
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
