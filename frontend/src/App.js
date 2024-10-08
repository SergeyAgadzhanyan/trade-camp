import React from 'react';
import CustomChart from './components/customChart';
import {makeRandomCandleRange} from './utils/CandleUtils';
import {calculateDiff, getLastTradeOrDefault} from './utils/UserUtils';
import {getChartObject} from './utils/chatObjectUtils';
import {checkByWinningPrice} from './utils/resultUtils';
import Result from './components/result';
import ActionButtons from './components/actionButtons';
import ProfilePopup from './components/profilePopup';
import Header from './components/header';
import UserApi from './utils/UserApi';

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
  const [isOpenProfilePopup, setIsOpenProfilePopup] = React.useState(false);
  const [isPositiveBalance, setIsPositiveBalance] = React.useState(true);
  const [balance, setBalance] = React.useState(1000);
  const [balanceDiff, setBalanceDiff] = React.useState(50);

  React.useEffect(() => {
    renderChart();
  }, []);

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
    getLastTradeOrDefault().then(r => {
      setIsPositiveBalance(r.isPositive);
      setBalance(r.balance);
      setBalanceDiff(r.balanceDiff);
    });
  }

  function setNewSeriesWithTradeResult({isWin, index, action, tradeResult}) {

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
    sendTradeResult({
      name: 'APPL',
      startDate: new Date(dateOne).toISOString(),
      endDate: new Date(dateTwo).toISOString(),
      currency: 'USD',
      score: tradeResult,
      operation: action.toUpperCase(),
    });

  }

  function sendTradeResult({
    name,
    startDate,
    endDate,
    currency,
    score,
    operation,
  }) {
    UserApi.sendTradeResult(
        {name, startDate, endDate, currency, score, operation}).then(result => {
      if (result.ok) {
        result.json().then(result => {
          let lastScore = result.score;
          let diff = calculateDiff(result.startScore, lastScore);
          setBalance(lastScore);
          setBalanceDiff(diff);
          setIsPositiveBalance(diff > 0 || diff === 0);
        });
      }
    });
  }

  function restart() {
    setIsShowResult(false);
    renderChart();
  }

  function toggleOpenPopup() {
    setIsOpenProfilePopup(!isOpenProfilePopup);
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
        startPrice: lastPrice,
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
      <>
        <Header clickPopup={toggleOpenPopup}/>
        <div className="main">
          <CustomChart series={options.series} isWin={isWin}
                       isShowResult={isShowResult} options={options.options}
                       onClick={handleAction}/>
          <div className="user">
            <div className="user__balance">
              <div className="user__balance-total">
                <p className={'user__balance-title'}>Balance</p>
                <p className={`user__balance-value ${isPositiveBalance
                    ? 'user__balance-value_positive'
                    : 'user__balance-value_negative'}`}>{balance}$</p>
              </div>
              <div className="user__balance-diff">
                <p className={`user__balance-diff-title ${isPositiveBalance
                    ? 'user__balance-diff-title-positive'
                    : 'user__balance-diff-title-negative'}`}>
                  {isPositiveBalance ?? `+`} {balanceDiff}%</p>
              </div>
            </div>
            {isShowResult ? <Result isWin={isWin} handleRestart={restart}/> :
                <ActionButtons
                    onClick={handleAction}
                    profitState={{profitPercent, setProfitPercent}}
                    loseState={{losePercent, setLosePercent}}
                />
            }
          </div>
        </div>
        <ProfilePopup isOpen={isOpenProfilePopup}/>
      </>);
}

export default App;
