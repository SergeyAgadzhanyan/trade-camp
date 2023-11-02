
export function checkByWinningPrice({
  currentPrice,
  index,
  winningPrice,
  losePrice,
  action,
    resultFunction
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
    resultFunction({isWin, index, action});
  }

  return isContinue;
}
