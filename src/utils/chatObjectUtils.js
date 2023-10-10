export function getChartObject(dateOne, dateTwo, priceOne, priceTwo, newSeries){
  return {
    options: {
      xaxis: {
        type: 'datetime',
      },

      annotations: {

        points: [
          {
            x: dateOne,
            y: priceOne,
            marker: {
              size: 8,
              fillColor: '#fff',
              strokeColor: 'red',
              radius: 2,
              cssClass: 'apexcharts-custom-class',
            },
            label: {
              borderColor: '#FF4560',
              offsetY: 0,
              style: {
                color: '#fff',
                background: '#FF4560',
              },

              text: 'Point Annotation',
            },
          },
          {
            x: dateTwo,
            y: priceTwo,
            marker: {
              size: 8,
              fillColor: '#fff',
              strokeColor: 'red',
              radius: 2,
              cssClass: 'apexcharts-custom-class',
            },
            label: {
              borderColor: '#FF4560',
              offsetY: 0,
              style: {
                color: '#fff',
                background: '#FF4560',
              },

              text: 'Point Annotation',
            },
          },
        ],
      },

    },
    series: [
      {
        name: 'series-1',
        data: newSeries,
      },
    ],
  }
}
