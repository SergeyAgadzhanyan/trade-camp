import Api from './Api';
import Links from './links';

class StockApi extends Api {

  randomCandleRange({dataCount, range}) {
    return fetch(
        `${Links.linkStockRandom}?sum=${dataCount + range}`, {
          method: 'GET',
          credentials: 'include',
        }).then(this._checkResponse);
  }

}

export default new StockApi();
