import React from 'react';
import CustomChart from './components/customChart';
import {makeRandomCandleRange} from './utils/CandleUtils';
import {getChartObject} from './utils/chatObjectUtils';
import {checkByWinningPrice} from './utils/resultUtils';
import Result from './components/result';
import ActionButtons from './components/actionButtons';

function App() {
  const [rangeForTrading, setRangeForTrading] = React.useState(30);
  const [dataCount, setDateCount] = React.useState(100);
  const [profitPercent, setProfitPercent] = React.useState(5);
  const [losePercent, setLosePercent] = React.useState(3);
  const [shortSeries, setShortSeries] = React.useState([]);
  const [fullSeries, setFullSeries] = React.useState([]);
  const [options, setOptions] = React.useState({
    options: {}, series: [],
  });
  const [isShowResult, setIsShowResult] = React.useState(false);
  const [isWin, setIsWin] = React.useState(false);

  React.useEffect(() => renderChart(), []);

  function renderChart() {
    makeRandomCandleRange(dataCount, rangeForTrading).then(([...fullS]) => {
      const shortS = fullS.slice(0, fullS.length - rangeForTrading + 1);
      setOptions({
        options: {
          xaxis: {
            type: 'datetime',
          },
          annotations: {
            points: [],
          },
        }, series: [
          {
            name: 'TSLA', data: shortS,
          }],
      });
      setShortSeries(shortS);
      setFullSeries(fullS);
    });
  }

  function setNewSeriesWithTradeResult({isWin, index, action}) {

    // const countTo = dataCount + index;
    // const newSeries = getNewSeries(startDate, countTo);
    const dataOne = shortSeries[shortSeries.length - 1];
    const dataTwo = fullSeries[index];
    const dateOne = new Date(dataOne.x).toDateString();
    const priceOne = dataOne.y;
    const dateTwo = new Date(dataTwo.x).toDateString();
    const priceTwo = dataTwo.y;
    setOptions(getChartObject(
        {
          dateOne,
          dateTwo,
          priceOne,
          priceTwo,
          series: fullSeries.slice(0, index + 1),
          isWin,
          action,
        }));
    setIsShowResult(true);
    setIsWin(isWin);

  }

  function restart() {
    setIsShowResult(false);
    renderChart();
  }

  function handleAction(action) {
    const lastPrice = shortSeries[shortSeries.length - 1]['y'][3];
    let winningPrice;
    let losePrice;
    if (action === 'buy') {
      winningPrice = (lastPrice + lastPrice / 100 * profitPercent).toFixed(2);
      losePrice = (lastPrice - lastPrice / 100 * losePercent).toFixed(2);
    } else {
      winningPrice = (lastPrice - lastPrice / 100 * profitPercent).toFixed(2);
      losePrice = (lastPrice + lastPrice / 100 * losePercent).toFixed(2);
    }

    for (let index = shortSeries.length; index < fullSeries.length; index++) {
      const currentPrice = action === 'buy'
          ? fullSeries[index]['y'][1]
          : fullSeries[index]['y'][2];
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

      <div className="main">


        <CustomChart series={options.series} isWin={isWin}
                     isShowResult={isShowResult} options={options.options}
                     onClick={handleAction}/>
        {isShowResult ? <Result isWin={isWin} handleRestart={restart}/> :
            <ActionButtons
                onClick={handleAction}
                profitState={{profitPercent, setProfitPercent}}
                loseState={{losePercent, setLosePercent}}
            />
        }
      </div>);
}

export default App;
