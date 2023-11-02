import Chart from 'react-apexcharts';
import React from 'react';


const CustomChart = ({options, series}) => {
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
