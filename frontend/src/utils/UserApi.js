import Api from './Api.js';
import Text from './text';

class UserApi extends Api {
  sendTradeResult({name, startDate, endDate, currency, score, operation}) {
    return fetch(Text.TradeResult, {
      method: 'POST',
      body: JSON.stringify({
        stockName: name,
        startDate,
        endDate,
        currency,
        tradeResult: score,
        operation,
      }),
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
    }).then(this._checkResponse);

  }

  getLastTrade() {
    return fetch(Text.LastTrade, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
    }).then(this._checkResponse);

  }

  getCurrentUser() {
    return fetch(Text.CurrentUser, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
    }).then(this._checkResponse);
  }
}

export default new UserApi();


