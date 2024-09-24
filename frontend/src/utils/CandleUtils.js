import {stockData} from './stockData';
import StockApi from './StockApi';
import NotAuthError from '../error/NotAuthError';

const arrayData = stockData.split('\n');
arrayData.pop();

export async function makeRandomCandleRange(dataCount, range) {
  let candles = [];
  try {

    const jsonR = await StockApi.randomCandleRange({dataCount, range});
    const body = await jsonR.json();

    body.forEach(e => {
      const open = parseFloat(e.open.substring(1));
      const high = parseFloat(e.high.substring(1));
      const low = parseFloat(e.low.substring(1));
      const close = parseFloat(e.close.substring(1));
      candles.push({
        x: new Date(e.date).toDateString(),
        y: [open, high, low, close],
      });
    });
    return candles;
  } catch (e) {
    if (e instanceof NotAuthError) {
      window.location = 'http://localhost:3000/login';
    } else {
      console.error(e);
    }
  }
}



