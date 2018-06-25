import React, { Component } from 'react';
// import logo from './logo.svg';
import './App.css';
import Layout from './components/Layout/Layout'

// <div className="App">
//   <header className="App-header">
//     <img src={logo} className="App-logo" alt="logo" />
//     <h1 className="App-title">Welcome to React</h1>
//   </header>
//   <p className="App-intro">
//     To get started, edit <code>src/App.js</code> and save to reload.
//   </p>
// </div>

class App extends Component {
  render() {
    return (
      <div>
        <Layout>
          <p1>Test</p1>
        </Layout>
      </div>
    );
  }
}

export default App;
