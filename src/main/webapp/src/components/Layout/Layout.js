import React from 'react';
import './Layout.css';
import Aux from '../../hoc/HOC'

const layout = function(props) {
    return (
        <Aux>
           <div>Toolbar, SiderDrawer, Backdrop</div>
           <main className="Layout-Content">{props.children}</main>  
        </Aux>
    );
}

export default layout;
