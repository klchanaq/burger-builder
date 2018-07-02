import React from 'react';
import './Burger.css';
import BurgerIngredient from './BurgerIngredient/BurgerIngredient';

const burger = function (props) {

    // ingredients: {
    //     bacon: 1,
    //     cheese: 2,
    //     salad: 1,
    //     meat: 2,
    // }

    let transformedIngredients =
        Object.keys(props.ingredients) // [ 'bacon', 'cheese', 'salad', 'meat' ]
        .map(igKey => { // igKey = 'bacon' | 'cheese' | 'salad' | 'meat'
            return [...Array(props.ingredients[igKey])].map((_, i) => { // [ 1 ] -> [ <BurgerIngredient /> ]
                return <BurgerIngredient key={igKey + i} type={igKey} />;
            });
        });
    
    // console.log(transformedIngredients);
    
    // result: a 2-D Array of JSX
    // For Example, transformedIngredients = [
    //      [ <BurgerIngredient key={1} type="cheese" />, <BurgerIngredient key={4} type="cheese" />],
    //      [ <BurgerIngredient key={2} type="salad" /> ],
    //      [ <BurgerIngredient key={3} type="bacon" /> ]
    //  ];
    // * Reminder: this is totally fine, React will help you flatten the 2-D Array of JSX.

    // manually flatten the 2-D Array of JSX by Array.prototype.reduce()
    transformedIngredients =
        transformedIngredients.reduce((arr, el) => {
            return arr.concat(el);
        }, []);

    // console.log(transformedIngredients);

    if ( transformedIngredients.length === 0 ) {
        transformedIngredients = <p>Please start adding ingredients</p>
    }

    return (
        <div className="Burger">
            <BurgerIngredient type="bread-top" />
            {transformedIngredients}
            <BurgerIngredient type="bread-bottom" />
        </div>
    );
}

export default burger;