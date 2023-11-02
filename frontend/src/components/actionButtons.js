import React from 'react';

const ActionButtons = ({onClick, profitState, loseState}) => (
    <div className={'main__buttons'}>
      <div className="main__button-row">
        <div className="main__select-group">
          <label  htmlFor="profit-select">Take profit</label>
          <select
              id="profit-select"
              className="form-select"
              value={profitState.profitPercent}
              onChange={e => profitState.setProfitPercent(
                  Number(e.target.value))}
          >
            <option value="2">2%</option>
            <option value="3">3%</option>
            <option value="5">5%</option>
          </select>
        </div>
        <div className="main__select-group">
          <label htmlFor="profit-select">Stop lose</label>
          <select
              id="lose-select"
              className="form-select"
              value={loseState.losePercent}
              onChange={e => loseState.setLosePercent(
                  Number(e.target.value))}
          >
            <option value="2">2%</option>
            <option value="3">3%</option>
            <option value="5">5%</option>
          </select>
        </div>
      </div>

      <div className="main__button-row">
        <button className={'main__button btn btn-outline-success'}
                onClick={() => onClick('buy')}>
          Buy
        </button>
        <button className={'main__button btn btn-outline-danger'}
                onClick={() => onClick('sell')}>
          Sell
        </button>
      </div>
    </div>
);

export default ActionButtons;
