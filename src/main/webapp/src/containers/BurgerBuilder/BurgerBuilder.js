import React, { Component } from 'react';
import './BurgerBuilder.css';
import Aux from '../../hoc/HOC';
import Burger from '../../components/Burger/Burger';

class BurgerBuilder extends Component {

    /*
        constructor(props) {
            super(props);
            this.state = {
                ingredients: {
                    bacon: 1,
                    salad: 1,
                    meat: 2,
                    cheese: 2
                }
            }
        }
    */

   state = {
       ingredients: {
           bacon: 2,
           cheese: 1,
           salad: 2,
           meat: 1,
       }
   }

    render() {
        return (
            <Aux>
                <Burger ingredients={this.state.ingredients} />
                <div>Build Controls</div>
            </Aux>
        );
    }
}

export default BurgerBuilder;