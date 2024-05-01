import React from 'react';
import user_icon from '../images/user-white.svg';

function Header({clickPopup}) {

  return (
      <div className="header">
        <button className="header__button" onClick={clickPopup}>
          <img className="header__button-icon" src={user_icon}
               alt="burger-button"/>
        </button>
      </div>
  );
}

export default Header;
