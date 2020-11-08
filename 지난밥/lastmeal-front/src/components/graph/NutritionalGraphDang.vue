<template>
  <d-card class="card-small h-100">
    <!-- Card Header -->
    <d-card-header class="border-bottom">
      <h6 class="m-0">{{ title }}</h6>
      <div style="display: none;">{{this.count}}</div>
      <!-- {{ submitData }} -->
      <div style="display: none;">{{ change }}</div>
      <div class="block-handle"></div>
    </d-card-header>

    <d-card-body class="pt-0">
      <div ref="legend"></div>
      <canvas height="120" ref="canvas" style="max-width: 100% !important;"></canvas>
    </d-card-body>
  </d-card>
</template>

<script>
import Chart from "../../utils/chart";

const defaultChartData = {
  labels: Array.from(new Array(30), (_, i) => (i === 0 ? 1 : i)),
  datasets: [
    {
      label: "권장량",
      fill: "start",
      data: [],
      backgroundColor: "rgba(0,123,255,0)",
      // borderColor: "rgba(0,123,255,1)",
      borderColor: "rgba(0,0,0,1)",
      pointBackgroundColor: "#ffffff",
      pointHoverBackgroundColor: "rgb(0,123,255)",
      borderDash: [3, 3],
      borderWidth: 1,
      pointRadius: 0,
      pointHoverRadius: 3,
    },
    {
      label: "당류",
      fill: "start",
      data: [],
      // backgroundColor: "rgba(255,65,105,0.1)",
      backgroundColor: "rgba(255,255,255,0)",
      borderColor: "rgba(255,65,105,1)",
      pointBackgroundColor: "#ffffff",
      pointHoverBackgroundColor: "rgba(255,65,105,1)",
      // borderDash: [3, 3],
      borderWidth: 1.5,
      pointRadius: 0,
      pointHoverRadius: 3,
      pointBorderColor: "rgba(255,65,105,1)",
    },
  ],
};

export default {
  name: "users-overview",
  props: {
    title: {
      type: String,
      default: "일일 당류 섭취 그래프",
    },
    chartData: {
      type: Object,
      default() {
        return defaultChartData;
      },
    },
    submitStandard: {
      type: Number,
      default: null,
    },
    submitData: {
      type: Array,
      default: null,
    },
    change: {
      type: Boolean,
      default: true,
    },
  },
  data() {
    return {
      dateRange: {
        from: null,
        to: null,
      },
      count: 0,
      act: false,
    };
  },
  methods: {
    retrieveDailyCal() {
      // this.thisChange = this.change;
      this.chartData.datasets[0].data = [];
      this.chartData.datasets[0].data.push(this.submitStandard);
      this.chartData.datasets[1].data = this.submitData;
      // this.chartData.datasets[1].data.push(0);
      for (var i = 1; i <= this.submitData.length; i++) {
        this.chartData.datasets[0].data.push(this.submitStandard);
        // this.chartData.datasets[1].data.push(this.submitData[i]);
      }
    },
  },
  mounted() {
    this.count += 1;
  },
  updated() {
    if (this.change) {
      this.change = false;
      this.retrieveDailyCal();
    }

    const chartOptions = {
      ...{
        responsive: true,
        legend: {
          position: "top",
        },
        elements: {
          line: {
            // A higher value makes the line look skewed at this ratio.
            tension: 0.3,
          },
          point: {
            radius: 0,
          },
        },
        scales: {
          xAxes: [
            {
              gridLines: false,
              ticks: {
                callback(tick, index) {
                  // Jump every 7 values on the X axis labels to avoid clutter.
                  return index % 7 !== 0 ? "" : tick;
                },
              },
            },
          ],
          yAxes: [
            {
              ticks: {
                // suggestedMax: 45,
                stepSize: 10,
                maxTicksLimit: 10,
                callback(tick) {
                  if (tick === 0) {
                    return tick;
                  }
                  // Format the amounts using Ks for thousands.
                  return tick > 9 ? `${(tick / 10)}0` : tick;
                },
              },
            },
            // {
            //   ticks: {
            //     suggestedMax: 45,
            //     callback(tick) {
            //       if (tick === 0) {
            //         return tick;
            //       }
            //       // Format the amounts using Ks for thousands.
            //       return tick > 999 ? `${(tick / 1000).toFixed(1)}K` : tick;
            //     },
            //   },
            //   position: 'right'
            // }
          ],
        },
        // 그래프에 마우스 올리면 보이는 옵션. 근데 켜놓으면 에러뜸
        // hover: {
        //   mode: "nearest",
        //   intersect: false,
        // },
        // tooltips: {
        //   custom: false,
        //   mode: "nearest",
        //   intersect: false,
        // },
      },
      ...this.chartOptions,
    };

    const BlogUsersOverview = new Chart(this.$refs.canvas, {
      type: "LineWithLine",
      data: this.chartData,
      options: chartOptions,
    });

    // They can still be triggered on hover.
    const buoMeta = BlogUsersOverview.getDatasetMeta(0);
    buoMeta.data[0]._model.radius = 0;
    buoMeta.data[this.chartData.datasets[0].data.length - 1]._model.radius = 0;

    // Render the chart.
    BlogUsersOverview.render();
  },
};
</script>

