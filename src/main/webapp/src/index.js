import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import registerServiceWorker from "./registerServiceWorker";

import { createStore, applyMiddleware } from "redux";
import { Provider } from "react-redux";
// import reducer from "./store/reducer";
import burgerBuilderReducer from "./store/reducers/burgerBuilder";
import thunk from "redux-thunk";

import { BrowserRouter } from "react-router-dom";

const store = createStore(burgerBuilderReducer, applyMiddleware(thunk));

const app = (
  <Provider store={store}>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </Provider>
);

ReactDOM.render(app, document.getElementById("root"));
registerServiceWorker();
