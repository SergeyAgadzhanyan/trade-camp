import React from 'react';

const ActionButtons = ({onClick}) => (
    <div className={'main__buttons'}>
      <button className={'main__button custom-chart__button_buy'}
              onClick={() => onClick('buy')}>
        Buy
      </button>
      <button className={'main__button main__button_sell'}
              onClick={() => onClick('sell')}>
        Sell
      </button>
    </div>
);

export default ActionButtons;
