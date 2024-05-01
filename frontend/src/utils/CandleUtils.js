import {stockData} from './stockData';
import NotAuthError from '../error/NotAuthError';

const arrayData = stockData.split('\n');
arrayData.pop();

export async function makeRandomCandleRange(dataCount, range) {
  let candles = [];
  await fetch(
      `http://localhost:8080/data/random?sum=${dataCount + range}`, {
        method: 'GET',
        credentials: 'include',
      })
      .then(res => {
        if (res.status === 401) throw new NotAuthError('fail login', res.url);
        return res.json();
      })
      .then(body => {
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
      }).catch(e => {
    if (e instanceof NotAuthError) {
      window.location = "http://localhost:3000/login";
      return candles;
    }
    console.log(e);
  })
  return candles.reverse();
}



