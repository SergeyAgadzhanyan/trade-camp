export function checkByWinningPrice({
  startPrice,
  currentPrice,
  index,
  winningPrice,
  losePrice,
  action,
  resultFunction,
}) {
  let isWin = true;
  let isContinue = true;
  if (action === 'buy' && currentPrice >= winningPrice) {
    isContinue = false;
  }
  if (action === 'buy' && currentPrice <= losePrice) {
    isWin = false;
    isContinue = false;
  }
  if (action === 'sell' && currentPrice <= winningPrice) {
    isContinue = false;
  }
  if (action === 'sell' && currentPrice >= losePrice) {
    isWin = false;
    isContinue = false;
  }
  if (!isContinue) {
    let tradeResult = Math.abs(currentPrice - startPrice) * (isWin ? 1 : -1);
    resultFunction({isWin, index, action, tradeResult});
  }

  return isContinue;
}
