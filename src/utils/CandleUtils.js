import {stockData} from './stockData';

const arrayData = stockData.split('\n');
arrayData.pop();
const candleDates = _makeCandle();

export function makeRandomCandleRange(dataCount) {
  const newStartDate = Math.floor(Math.random() * (candleDates.length - dataCount - 1));
  const newSeries = [...candleDates].splice(newStartDate, dataCount);
  return {newStartDate, newSeries};
}

export function getNewSeries(startDate, countTo) {
  return [...candleDates].splice(startDate, countTo);
}

function _makeCandle() {
  return [...arrayData].map(el => {
    const splitEl = el.split(',');
    const open = parseFloat(splitEl[3].substring(1));
    const high = parseFloat(splitEl[4].substring(1));
    const low = parseFloat(splitEl[5].substring(1));
    const close = parseFloat(splitEl[1].substring(1));
    return {
      x: new Date(splitEl[0]).toDateString(),
      y: [open, high, low, close],
    };
  }).reverse();

}

