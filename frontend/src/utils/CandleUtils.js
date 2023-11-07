import {stockData} from './stockData';
import NotAuthError from '../error/NotAuthError';

const arrayData = stockData.split('\n');
arrayData.pop();

export async function makeRandomCandleRange(dataCount, range) {

  return [
    ...await fetch(
        `http://localhost:8080/data/random?sum=${dataCount + range}`)
        .then(res => {
          if (res.redirected) throw new NotAuthError('fail login', res.url);
          return res.json();
        })
        .then(body => {
          return body.map(e => {
            const open = parseFloat(e.open.substring(1));
            const high = parseFloat(e.high.substring(1));
            const low = parseFloat(e.low.substring(1));
            const close = parseFloat(e.close.substring(1));
            return {
              x: new Date(e.date).toDateString(),
              y: [open, high, low, close],
            };
          });
        }).catch(e => {
          if (e instanceof NotAuthError) {

            return window.location = e.redirectedUrl;
          }
          console.log(e);
        })].reverse();

}



