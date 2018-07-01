import React, { Component } from "react";
import "./App.css";
import Layout from "./components/Layout/Layout";
import BurgerBuilder from "./containers/BurgerBuilder/BurgerBuilder";

/* show how to import lodash */
import _ from "lodash";

/* show how to import lodash function(s) */
import _isNil from "lodash/isNil";

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
    this.showLodashExamples();
    return (
      <div>
        <Layout>
          <BurgerBuilder />
        </Layout>
      </div>
    );
  }
}

export default App;
