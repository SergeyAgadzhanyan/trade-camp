import Chart from 'react-apexcharts';
import React from 'react';
import '../blocks/main/__custom-chart/main__custom-chart.css';
import '../blocks/main/__button/custom-chart__button.css';
import '../blocks/main/__button/_buy/main__button_buy.css';
import '../blocks/main/__button/_sell/custom-chart__button_sell.css';
import '../blocks/main/__buttons/main__buttons.css';

const CustomChart = ({options, series, onClick, isWin, isShowResult}) => {
  console.log(isShowResult);
  return (
      <div className={'main__custom-chart'}>
        <div style={{width: '100%'}}>
          <Chart
              options={options}
              series={series}
              type="candlestick"
              width="100%"
          />;
        </div>
      </div>
  );
};
export default CustomChart;
