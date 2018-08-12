import React, { Component } from "react";
import "./App.css";
import Layout from "./hoc/Layout/Layout";
import BurgerBuilder from "./containers/BurgerBuilder/BurgerBuilder";
import Orders from "./containers/Orders/Orders";
import Auth from "./containers/Auth/Auth";
import { Route, Switch, Redirect } from "react-router-dom";

/* show how to import lodash */
import _ from "lodash";

/* show how to import lodash function(s) */
import _isNil from "lodash/isNil";
import Checkout from "./containers/Checkout/Checkout";

class App extends Component {
  showLodashExamples = () => {
    /* 
      _.isNil(null); // => true
      _.isNil(void 0); // => true
      _.isNil(NaN); // => false
    */
    console.log("_isNil('Example')", _isNil("Example"));
    /* 
      _.chunk(['a', 'b', 'c', 'd'], 2); // => [['a', 'b'], ['c', 'd']]
      _.chunk(['a', 'b', 'c', 'd'], 3); // => [['a', 'b', 'c'], ['d']]
    */
    console.log(
      "_.chunk(['a', 'b', 'c', 'd'], 3)",
      _.chunk(["a", "b", "c", "d"], 3)
    );
  };

  render() {
    console.log("[App] render()...", this.props);
    this.showLodashExamples();
    return (
      <div>
        <Layout>
          <Switch>
            <Route path="/burgerBuilder" exact component={BurgerBuilder} />
            <Route path="/burgerBuilder/checkout" component={Checkout} />
            <Route path="/orders" component={Orders} />
            <Route path="/auth" component={Auth} />
            {/* <Redirect from="/" to="/burgerBuilder" /> */}
            <Route path="*" render={() => <p>Home Page</p>} />
          </Switch>
          {/* <BurgerBuilder />
          <Checkout /> */}
        </Layout>
      </div>
    );
  }
}

export default App;
