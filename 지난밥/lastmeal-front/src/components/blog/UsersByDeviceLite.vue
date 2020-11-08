<template>
  <div class="card card-small h-100">
    <!-- Card Header -->
    <div class="card-header border-bottom">
      <h6 class="m-0">{{title}}</h6>
    </div>

    <div style="display:none;">{{ submitData }}</div>
    <div style="display:none;">{{ change }}</div>

    <!-- Chart -->
    <div class="card-body d-flex py-0" style="margin: 0 auto;">
      <div ref="legend"></div>
      <canvas height="300" ref="canvas" style="max-width: 100% !important; margin-top:15px;"></canvas>
    </div>
  </div>
</template>

<script>
import Chart from "../../utils/chart";

const defaultChartData = {
  datasets: [
    {
      hoverBorderColor: "#ffffff",
      data: [45.3, 24.2, 7.5, 5.0, 7.0, 6.0, 4.0],
      backgroundColor: [
        "rgba(64,100,176,0.7)",  // 탄 - 파
        "rgba(102,90,166,0.9)",  // 단 - 남
        "rgba(159,66,152,0.6)",  // 지 - 보
        "rgba(247,141,31,0.6)",  // 당류 - 주
        "rgba(238,28,38,0.7)",  // 나트륨 - 빨
        "rgba(251,203,9,0.6)",  // 칼로리 - 노
        "rgba(43,177,80,0.6)",  // 콜레스테롤 - 초
      ],
    },
  ],
  labels: [
    '탄수화물(g)',
    '단백질(g)',
    '지방(g)',
    '당류(g)',
    '나트륨(mg)',
    '칼로리(kcal)',
    '콜레스테롤(mg)'
  ],
};

export default {
  name: "users-by-device",
  components: {
  },
  props: {
    /**
     * The chart options.
     */
    chartOptions: {
      type: Object,
      default() {
        return {};
      },
    },
    /**
     * The chart data.
     */
    chartData: {
      type: Object,
      default() {
        return defaultChartData;
      },
    },
    /**
     * The chart title.
     */
    title: {
      type: String,
      default: "영양 정보 그래프",
    },
    months: ["Tan", "Dan", "Ji", "Dang", "Na", "Cal", "Col"],
    submitData: {
      type: undefined,
      default: null,
    },
    change: {
      type: Boolean,
      default: false,
    }
  },
  data() {
    return {
      updateCount : 0,
      nutritional: {
        tan: 0,
        dan: 0,
        ji: 0,
        dang: 0,
        na: 0,
        cal: 0,
        col: 0,
      },
      ratio: {
        total: 0,
        tan: 0,
        dan: 0,
        ji: 0,
        dang: 0,
        na: 0,
        cal: 0,
        col: 0,
      }
    };
  },
  methods: {    
    retrieveMenuDetail() {
      this.nutritional = {
        tan: 0,
        dan: 0,
        ji: 0,
        dang: 0,
        na: 0,
        cal: 0,
        col: 0,
      }

      this.nutritional.tan = this.submitData['tan']
      this.nutritional.dan = this.submitData['dan']
      this.nutritional.ji = this.submitData['ji']
      this.nutritional.dang = this.submitData['dang']
      this.nutritional.na = this.submitData['na']
      this.nutritional.cal = this.submitData['cal']
      this.nutritional.col = this.submitData['col']

      // 원형그래프 부분
      this.ratio.total = this.nutritional.tan + this.nutritional.dan + this.nutritional.ji 
      + this.nutritional.dang + this.nutritional.na + this.nutritional.cal + this.nutritional.col;
      this.ratio.tan = (this.nutritional.tan * 100 / this.ratio.total).toFixed(1);
      this.ratio.dan = (this.nutritional.dan * 100 / this.ratio.total).toFixed(1);
      this.ratio.ji = (this.nutritional.ji * 100 / this.ratio.total).toFixed(1);
      this.ratio.dang = (this.nutritional.dang * 100 / this.ratio.total).toFixed(1);
      this.ratio.na = (this.nutritional.na * 100 / this.ratio.total).toFixed(1);
      this.ratio.cal = (this.nutritional.cal * 100 / this.ratio.total).toFixed(1);
      this.ratio.col = (this.nutritional.col * 100 / this.ratio.total).toFixed(1);

      this.chartData.datasets[0].data = [
        this.ratio.tan,
        this.ratio.dan,
        this.ratio.ji,
        this.ratio.dang,
        this.ratio.na,
        this.ratio.cal,
        this.ratio.col,
      ]
    },
    pieGraph() {
      const chartConfig = {
        labels: ['T', 'W', 'R', 'B', 'C', 'A', 'D'],
        type: "pie",
        data: this.chartData,
        options: {
          ...{
            legend: {
              position: "bottom",
              labels: {
                padding: 25,
                boxWidth: 20,
              },
            },
            cutoutPercentage: 0,
            tooltips: {
              custom: false,
              mode: "index",
              position: "nearest",
            },
          },
          ...this.chartOptions,
        },
      };

      new Chart(this.$refs.canvas, chartConfig);
    },
  },  
  updated() {
    if(this.change){
      this.change = false;
      this.retrieveMenuDetail(); 
      this.pieGraph();
    }
  },
  mounted() {
    this.retrieveMenuDetail();
    this.pieGraph();
  },

};
</script>

