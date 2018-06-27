import React from 'react';
import './Modal.css';

const modal = function(props) {
    return (
        <div className="Modal">
        {props.children}
        </div>
    );
}

export default modal;