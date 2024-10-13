import UserApi from './UserApi';
import NotFoundError from '../error/NotFoundError';

export async function getLastTradeOrDefault() {
  let balance = 0;
  let balanceDiff = 0;
  let isPositive = true;
  const currentUserJson = await UserApi.getCurrentUser();
  const currentUserBody = await currentUserJson.json();

  try {
    const jsonR = await UserApi.getLastTrade();
    const tradeBody = await jsonR.json();
    balance = tradeBody.scoreResult;
    balanceDiff = calculateDiff(currentUserBody.startScore,
        tradeBody.scoreResult);
    isPositive = balanceDiff > 0 || balanceDiff === 0;
  } catch (e) {
    if (e instanceof NotFoundError) {
      balance = currentUserBody.startScore;
    }
  }

  return {
    'balance': balance,
    'balanceDiff': balanceDiff,
    'isPositive': isPositive,
  };
}

export function calculateDiff(startScore, scoreResult) {
  return (scoreResult - startScore) / (startScore / 100).toFixed(2);
}



