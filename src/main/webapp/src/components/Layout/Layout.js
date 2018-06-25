import React from 'react';
import './Layout.css';
import Aux from '../../hoc/HOC/HOC'

const layout = function(props) {
    return (
        <Aux>
           <div>Toolbar, SiderDrawer, Backdrop</div>
           <main>{props.children}</main>  
        </Aux>
    );
}

export default layout;
