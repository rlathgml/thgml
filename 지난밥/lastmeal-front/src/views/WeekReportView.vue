<template>
  <div id="single-date-picker" class="main single-data-picker__report-view">
    <div class="report-view">
      <span style="font-weight: bold; font-size:1.2rem;">{{ user.nickname }}</span>님의<br/>
      주간<span style="font-weight: lighter; font-size:0.9rem; color: rgba(255,255,255,0.5);">({{ sendData.day[0] }}-{{ sendData.day[6] }})</span> 리포트
    </div>

    <!-- 리포트 분석 -->
    <div class="chart-view" style="padding: 20px; font-size:0.9rem;">

      <div>최근 일주일 동안 <span style="font-weight: bold; font-size:1rem;">{{ submitData.count }}일</span>의 식단을 기록했습니다.</div>
      <div v-if="noticeOn" class="notice-view">(식단 기록이 부족하여 정확한 분석이 힘듭니다)</div>
      <hr style="border-color: rgb(214, 212, 212);">

      <div>
        <span style="font-weight: bold; font-size: 0.95rem; color: #3366cc;">권장량 대비 섭취율</span>
        <div class="container" style="margin-top: 10px;">

          <div class="row">
            <div class="col-6">
              최대
            </div>
            <div class="col-6">
              최소
            </div>
          </div>

          <!-- 식단 기록이 있을 때 -->
          <div v-if="!countZero" class="row">
            <div class="col-6">
              {{ numbers.name[sendData.MaxMin[0]] }} ({{ sendData.MaxMin[2] }}%)
            </div>
            <div class="col-6">
              {{ numbers.name[sendData.MaxMin[1]] }} ({{ sendData.MaxMin[3] }}%)
            </div>
          </div>

          <!-- 식단 기록이 없을 때 -->
          <div v-else class="row">
            <div class="col-6">
              알 수 없음
            </div>
            <div class="col-6">
              알 수 없음
            </div>
          </div>

        </div>
        
      </div>
    </div>
    
    <!-- 리포트 분석 - 섭취 주의-->
    <div class="chart-view" style="padding: 20px; font-size:0.9rem;">
      <!-- 식단 기록이 있을 때 -->
      <div v-if="!countZero">
        <div v-if="reportOn">
          아래 영양 성분들의 섭취에 주의하세요
          <hr style="border-color: rgb(214, 212, 212);">
          <div v-if="MaxOn" class="my-2">
            <div style="font-weight: bold;">과다 섭취</div>
            <span v-for="num in sendData.MaxNum" class="mx-1" style="font-size:0.8rem;">{{ numbers.name[num] }}</span><br>
          </div>
          <div v-if="MinOn" class="my-2">
            <div style="font-weight: bold;">과소 섭취</div>
            <span v-for="num in sendData.MinNum" class="mx-1" style="font-size:0.8rem;">{{ numbers.name[num] }}</span><br>
          </div>
        </div>
        <div v-else>
          모든 영양 성분들의 섭취가 양호합니다.<br>
          잘 드시고 계시는군요? 이대로 유지해주세요!<br>
        </div>
      </div>
      <!-- 식단 기록이 없을 때 -->
      <div v-else>
        주의 해야 할 영양 성분을 알 수 없습니다.<br>
        분석을 위해 식단 기록을 먼저 해주세요.
      </div>
    </div>

    <!-- 차트 부분 -->
    <div v-if="!countZero">
      <div v-for="(name, index) in numbers.name" :key="name + 'key'" class="chart-view">
        <!-- chart header -->
        <br>
        <h6>{{ name }} 섭취 현황</h6>
        <hr style="border-color: rgb(214, 212, 212);">
        <!-- chart body -->
        <pure-vue-chart
          :points="sendData.nutrientData[index]"
          :height="150"
          :width="sendWidth"
          :show-y-axis="true"
          :show-x-axis="true"
          :show-values="true"
          :use-month-labels="true"
          :names="sendData.day"
          :common="recommandData[index]"
        />
        <hr style="border-color: rgb(214, 212, 212);">
        <!-- chart footer -->
        <div style="color: #6a6a6a; font-size: 0.8rem;">
          일주일 <span style="font-weight: bold; color: #3366cc;">평균 {{ name }}</span> 섭취량은 
          <span style="font-weight: bold; color: #3366cc;">{{ sendData.average[index] }}{{ sendData.unit[index] }}</span> 입니다<br>
          권장 섭취량 {{ recommandData[index] }}{{ sendData.unit[index] }}의 
          <span style="font-weight: bold; color: #db3d16;">{{ sendData.percent[index] }}%</span>를 섭취했습니다
        </div>
        <br>
      </div>
    </div>

  </div>
</template>

<script>
import PureVueChart from "@/components/chart/vueChart";

export default {
  name: 'WeekReport',
  
  components: {
    PureVueChart,
  },

  props: {
    recommandData: {
      type: Array
    },
    submitData: {
      type: Object
    },
  },

  data() {
    return {
      user: this.$cookies.get("auth-token"),
      numbers: {
        'name': [
          '칼로리', '탄수화물', '단백질', '지방', '당류', '나트륨', '콜레스테롤',
        ],
      },
      noticeOn: false,
      MaxOn: false,
      MinOn: false,
      countZero: false,
      sendData: {
        'day': [],
        // cal - tan - dan - ji - dang - na - col 순
        'nutrientData': [
          [], [], [], [], [], [], []
        ],
        // 평균 섭취량 계산을 위한 영양성분 별 합산
        'sum': [
          0, 0, 0, 0, 0, 0, 0
        ],
        // 평균 섭취량
        'average': [
          0, 0, 0, 0, 0, 0, 0
        ],
        // 권장량 대비 평균 섭취량의 퍼센트
        'percent': [
          0, 0, 0, 0, 0, 0, 0
        ],
        // 단위
        'unit': [
          'kcal', 'g', 'g', 'g', 'g', 'mg', 'mg',
        ],
        'MaxMin': [0, 0, 0, 0],
        'MaxNum': [],
        'MinNum': [],
      },
      windowWidth: 0,
      widthData: 0,
    }
  },

  created() {
    this.calculValue();
    if (this.submitData.count <= 3) {
      this.noticeOn = true;
      if (this.submitData.count == 0) {
        this.countZero = true;
      }
    }
    window.addEventListener('resize', this.handleResize);
    this.handleResize();
  },

  watch: {
    destroyed() {
      window.removeEventListener('resize', this.handleResize);
    },
  },

  computed: {
    reportOn() {
      if (this.MaxOn == true || this.MinOn == true) {
        return true
      } else {
        return false
      }
    },
    
    sendWidth() {
      return this.widthData
    },
  },

  methods: {
    // 영양성분 별로 분리해서 리스트 안에 집어넣는 함수
    calculValue() {
      for (let i = 0; i < 7; i++) {
        this.sendData.day = this.sendData.day.concat(this.submitData.menus[i].day.substring(5,10).replace('-', '/'));
        this.sendData.nutrientData[1] = this.sendData.nutrientData[1].concat(this.submitData.menus[i].tan);
        this.sendData.nutrientData[2] = this.sendData.nutrientData[2].concat(this.submitData.menus[i].dan);
        this.sendData.nutrientData[3] = this.sendData.nutrientData[3].concat(this.submitData.menus[i].ji);
        this.sendData.nutrientData[4] = this.sendData.nutrientData[4].concat(this.submitData.menus[i].dang);
        this.sendData.nutrientData[5] = this.sendData.nutrientData[5].concat(this.submitData.menus[i].na);
        this.sendData.nutrientData[0]= this.sendData.nutrientData[0].concat(this.submitData.menus[i].cal);
        this.sendData.nutrientData[6] = this.sendData.nutrientData[6].concat(this.submitData.menus[i].col);

        // 해당 영양성분의 일주일 총 합을 구한다
        this.sendData.sum[0] += this.submitData.menus[i].cal;
        this.sendData.sum[1] += this.submitData.menus[i].tan;
        this.sendData.sum[2] += this.submitData.menus[i].dan;
        this.sendData.sum[3] += this.submitData.menus[i].ji;
        this.sendData.sum[4] += this.submitData.menus[i].dang;
        this.sendData.sum[5] += this.submitData.menus[i].na;
        this.sendData.sum[6] += this.submitData.menus[i].col;
      }
      
      // 일주일 섭취량의 평균 값 및 최대, 최소값 구하기
      for (let j = 0; j < 7; j++) {
        // 평균 구하기
        this.sendData.average[j] = (this.sendData.sum[j] / this.submitData.count).toFixed(1);
        this.sendData.percent[j] = ((((this.sendData.average[j] - this.recommandData[j]) / this.recommandData[j]) * 100) + 100).toFixed(1);
        if (Number(this.sendData.percent[j]) > 120) {
          this.sendData.MaxNum.push(j);
          this.MaxOn = true;
        } else if (Number(this.sendData.percent[j]) < 80) {
          this.sendData.MinNum.push(j);
          this.MinOn = true;
        } 

        // 최대, 최소값 구하기
        if (j == 0) {
          this.sendData.MaxMin[2] = Number(this.sendData.percent[j]);
          this.sendData.MaxMin[3] = Number(this.sendData.percent[j]);
        }

        // 최대값
        if (Number(this.sendData.percent[j]) > this.sendData.MaxMin[2]) {
          this.sendData.MaxMin[2] = Number(this.sendData.percent[j]);
          this.sendData.MaxMin[0] = j;
        }
        // 최소값
        if (Number(this.sendData.percent[j]) < this.sendData.MaxMin[3]) {
          this.sendData.MaxMin[3] = Number(this.sendData.percent[j]);
          this.sendData.MaxMin[1] = j;
        }
      }
    },

    // 현재 보이는 창 화면 계산
    handleResize() {
      this.windowWidth = window.innerWidth;
      this.widthCal();
    },

    // 넓이 계산하기
    widthCal() {
      const suffix = 'px';
      if (this.windowWidth > 992) {
        this.widthData = Number(this.windowWidth / 3).toFixed(0) - 100;
      } else if (this.windowWidth > 768) {
        this.widthData = Number(this.windowWidth / 2).toFixed(0) - 100;
      } else {
        this.widthData = Number(this.windowWidth).toFixed(0) - 100;
      }
    },

  },
}
</script>

<style lang="scss" scoped>
#single-date-picker {
  font-family: "Lato", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

.single-data-picker__report-view {
  max-width: 90%;
  border-radius: 10px;
}

.chart-view {
  background-color: rgba(255,255,255,1);
  border-radius: 10px;
  margin-top: 10px;
  margin-bottom: 20px;
}

.report-view {
  // background-color: rgba(255,255,255,0.8);
  color: white;
  border-radius: 10px;
  font-size: 1rem;
  margin-top: 10px;
  margin-bottom: 20px;
}

.notice-view {
  color:#c0c2c2;
  font-size: 0.8rem;
  margin-top: 5px;
}
.main{
  margin: 75px auto 0;
}

</style>