import React from 'react';

function ProfilePopup({isOpen}) {
  return (
      <div className={`popup ${isOpen && 'popup_opened'}  popup_profile`}>
        Profile popup
      </div>
  );
}

export default ProfilePopup;
