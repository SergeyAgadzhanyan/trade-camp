import arr_g_up from '../images/arr_g_up.svg';
import arr_g_down from '../images/arr_g_down.svg';
import arr_r_up from '../images/arr_r_up.svg';
import arr_r_down from '../images/arr_r_down.svg';

export function getChartObject({
  dateOne, dateTwo, priceOne, priceTwo, newSeries, isWin, action,
}) {
  let arrow;
  const minusCount = 2;
  const plusCount = 2;
  const priceOneMax = priceOne[1] + plusCount;
  const priceOneMin = priceOne[2] - minusCount;
  const priceTwoMax = priceTwo[1] + plusCount;
  const priceTwoMin = priceTwo[2] - minusCount;
  if (isWin) arrow = action === 'buy'
      ? arr_g_up
      : arr_g_down; else arrow = action === 'buy' ? arr_r_up : arr_r_down;
  return {
    options: {
      xaxis: {
        type: 'datetime',
      },

      annotations: {

        points: [
          {
            x: dateOne,
            y: action === 'buy' ? priceOneMin : priceOneMax,
            marker: {
              size: 0,
            },
            image: {
              path: arrow, width: 40, height: 40, offsetX: 0, offsetY: 0,
            },

          }, {
            x: dateTwo,
            y: action === 'buy' ? priceTwoMin : priceTwoMax,
            marker: {
              size: 0,
            },
            image: {
              path: arrow, width: 40, height: 40, offsetX: 0, offsetY: 0,
            },

          }],
      },

    }, series: [
      {
        name: 'series-1', data: newSeries,
      }],
  };
}
