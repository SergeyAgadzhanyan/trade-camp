import React from 'react';
import CustomChart from './components/customChart';
import {getNewSeries, makeRandomCandleRange} from './utils/CandleUtils';
import {getChartObject} from './utils/chatObjectUtils';
import {checkByWinningPrice} from './utils/resultUtils';
import Result from './components/result';
import ActionButtons from './components/actionButtons';
import './blocks/main/main.css'

function App() {
  const [rangeForTrading, setRangeForTrading] = React.useState(30);
  const [dataCount, setDateCount] = React.useState(100);
  const [startDate, setStartDate] = React.useState(0);
  const [lastDate, setLastDate] = React.useState(0);
  const [profitPercent, setProfitPercent] = React.useState(5);
  const [losePercent, setLosePercent] = React.useState(3);
  const [options, setOptions] = React.useState({
    options: {},
    series: [],
  });
  const [isShowResult, setIsShowResult] = React.useState(false);
  const [isWin, setIsWin] = React.useState(false);

  React.useEffect(() => {
    const {newStartDate, newSeries} = makeRandomCandleRange(dataCount);
    setOptions({
      options: {}, series: [
        {
          name: 'TSLA',
          data: newSeries,
        }],
    });
    setStartDate(newStartDate);
    setLastDate(newStartDate + dataCount);
  }, []);

  function setNewSeriesWithTradeResult({isWin, index, action}) {
    const countTo = dataCount + index + 1;
    const newSeries = getNewSeries(startDate, countTo);
    const dataOne = newSeries[newSeries.length - index];
    const dataTwo = newSeries[newSeries.length - 1];
    const dateOne = new Date(dataOne.x).toDateString();
    const priceOne = dataOne.y[3];
    const dateTwo = new Date(dataTwo.x).toDateString();
    const priceTwo = dataTwo.y[3];
    setOptions(getChartObject(dateOne, dateTwo, priceOne, priceTwo, newSeries));
    setIsShowResult(true);
    setIsWin(isWin);
  }

  function handleAction(action) {
    const lastPrice = parseFloat(options.series[0].data[dataCount - 1]['y'][3]);
    let winningPrice;
    let losePrice;
    if (action === 'buy') {
      winningPrice = (lastPrice + lastPrice / 100 * profitPercent).toFixed(2);
      losePrice = (lastPrice - lastPrice / 100 * losePercent).toFixed(2);
    } else {
      winningPrice = (lastPrice - lastPrice / 100 * profitPercent).toFixed(2);
      losePrice = (lastPrice + lastPrice / 100 * losePercent).toFixed(2);
    }

    const nextSeries = getNewSeries(lastDate, rangeForTrading);
    for (let index = 0; index < nextSeries.length; index++) {
      const currentPrice = action === 'buy'
          ? nextSeries[index]['y'][2]
          : nextSeries[index]['y'][3];
      const isContinue = checkByWinningPrice({
        currentPrice,
        index,
        winningPrice: parseFloat(winningPrice),
        losePrice: parseFloat(losePrice),
        action,
        resultFunction: setNewSeriesWithTradeResult,
      });
      if (!isContinue) {
        return;
      }
    }
  }

  return (
      <div className='main'>
        <CustomChart series={options.series} isWin={isWin}
                     isShowResult={isShowResult} options={options.options}
                     onClick={handleAction}/>
        {isShowResult ? <Result isWin={isWin}/> : <ActionButtons
            onClick={handleAction}/>
        }
      </div>
  );
}

export default App;
