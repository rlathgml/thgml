<template>
  <div id="single-date-picker" class="main single-date-picker__calendar-view">
    <UserUpdate 
      v-if="showModal" 
      @showModal="setData" 
      :msg="'정확한 진단을 위해 추가 정보를 입력해주세요.'"
    />
    <div class="calendar-back">
      <CalendarMonthHeader :year="year" :month="month" @toggleMonth="toggleMonth" />
      <CalendarMonth
        :dates-per-week="datesPerWeek"
        :is-today="isToday"
        :is-selected="isSelected"
        :is-good="isGood"
        :is-normal="isNormal"
        :is-bad="isBad"
        :week-start-day="weekStartDay"
        @selectDate="selectDate"
      />
    </div>
    <!-- <div class="graphs"> -->
      <h4 class="title">오늘의 조언</h4>
      <!-- 조언 ex) 이러다 고혈압 걸리겠어요~!  -->
      <div class="advise">
        <h4> {{ advise1 }} </h4>
        <h6> {{ advise2 }} </h6>
        <!-- 주간 리포트 부분 -->
        <div style="margin-top: 10px;">
          <button class="btn btn-primary" @click="goWeekReport">주간 리포트 확인하기</button>
        </div>
      </div>

      <h4 class="title" style="margin-top: 20px">이번 달 섭취 </h4>
      <!-- 일일 그래프 출력 부분 -->
      <GraphCal class="graph" :submitStandard="sendData.recommand[0]" :submitData="sendDayCal" :change="true" />
      <GraphTan class="graph" :submitStandard="sendData.recommand[1]" :submitData="sendDayTan" :change="true" />
      <GraphDan class="graph" :submitStandard="sendData.recommand[2]" :submitData="sendDayDan" :change="true" />
      <GraphJi class="graph" :submitStandard="sendData.recommand[3]" :submitData="sendDayJi" :change="true" />
      <GraphDang class="graph" :submitStandard="sendData.recommand[4]" :submitData="sendDayDang" :change="true" />
      <GraphNa class="graph" :submitStandard="sendData.recommand[5]" :submitData="sendDayNa" :change="true" />
      <GraphCol class="graph" :submitStandard="sendData.recommand[6]" :submitData="sendDayCol" :change="true" />

      <!-- 카메라 오른쪽 아래 고정 -->
      <input
        id="searchImage"
        type="file"
        ref="imagefile"
        style="display:none;"
        accept="image/*"
        @change="setImage()"/>
      <label for="searchImage">
        <fab position="bottom-right" bg-color="#778899"></fab>
      </label>

      <div v-if="loadingOn" class="loading-view">
        <div class="loading-message">
          <img class="loadingimg" src="../assets/images/loading.gif"/>
          <font class="loadingfont">이미지 분석중</font>
          <img class="loadingimg" src="../assets/images/loading.gif"/>
        </div>
      </div>
    </div>
  <!-- </div> -->
</template>

<script>
import CalendarMonthHeader from "../components/calendar/CalendarMonthHeader";
import CalendarMonth from "../components/calendar/CalendarMonth";
import GraphCal from "@/components/graph/NutritionalGraphCal.vue";
import GraphTan from "@/components/graph/NutritionalGraphTan.vue";
import GraphDan from "@/components/graph/NutritionalGraphDan.vue";
import GraphJi from "@/components/graph/NutritionalGraphJi.vue";
import GraphDang from "@/components/graph/NutritionalGraphDang.vue";
import GraphNa from "@/components/graph/NutritionalGraphNa.vue";
import GraphCol from "@/components/graph/NutritionalGraphCol.vue";
import http from "../utils/axios-common";
import Kakao from "./KakaoLogin.vue";
import fab from '@/components/common/vue-fab.vue';
import UserUpdate from "@/components/user-profile-lite/UserUpdate.vue";

const NUM_DAYS_IN_WEEK = 7;
export default {
  name: "CalendarView",
  components: {
    CalendarMonthHeader,
    CalendarMonth,
    Kakao,
    GraphCal,
    GraphTan,
    GraphDan,
    GraphJi,
    GraphDang,
    GraphNa,
    GraphCol,
    fab,
    UserUpdate,
  },
  props: {
    date: {
      type: Object,
      default: () => null,
    },
    firstDayOfWeek: {
      type: Number,
      default: 0,
      validator: (value) => value >= 0 && value <= 6,
    },
  },
  data() {
    return {
      act: false,
      MenuImage: '',
      recommandCal: 0,
      // 날짜 계산 부분
      year: 0,
      month: 0,
      todayDate: 0,
      todayYear: 0,
      todayMonth: 0,
      selectedDate: null,
      weekStartDay: 0,
      // 권장 칼로리 섭취 여부
      goodDay: [],
      normalDay: [],
      badDay: [],
      // 일별 영양성분 합산 list
      DayCal: [],
      DayNa: [],
      DayTan: [],
      DayDan: [],
      DayJi: [],
      DayDang: [],
      DayCol: [],
      isChange: true,
      nutritional: {
        tan: 0,
        dan: 0,
        ji: 0,
        dang: 0,
        na: 0,
        cal: 0,
        col: 0,
      },
      loadingOn: false,
      user: this.$cookies.get("auth-token"),
      showModal: false,
      // 다른 곳으로 보낼 Data
      sendData: {
        // nutriNum: [
        //   '일일 칼로리 섭취 그래프',
        //   '일일 탄수화물 섭취 그래프',
        //   '일일 단백질 섭취 그래프',
        //   '일일 지방 섭취 그래프',
        //   '일일 당류 섭취 그래프',
        //   '일일 나트륨 섭취 그래프',
        //   '일일 콜레스테롤 섭취 그래프',
        // ],
        nutriData: [
          this.DayCal,
          this.DayTan,
          this.DayDan,
          this.DayJi,
          this.DayDang,
          this.DayNa,
          this.DayCol,
        ],
        // 일일 권장량 순서대로 Cal, tan, dan, ji, dang, na, col// 일일 권장량 순서대로 Cal, tan, dan, ji, dang, na, col
        recommand: [
          this.recommandCal,
          this.recommandCal * 0.55,
          this.recommandCal * 0.25,
          this.recommandCal * 0.2,
          50,
          2000,
          300,
        ],
      },
      advise1: '',
      advise2: '',
    };
  },
  computed: {
    numDays() {
      return new Date(this.year, this.month + 1, 0).getDate();
    },
    firstDay() {
      return new Date(this.year, this.month, 1).getDay() - this.weekStartDay;
    },
    lastDay() {
      return new Date(this.year, this.month + 1, 0).getDay();
    },
    numDaysInFirstWeek() {
      return NUM_DAYS_IN_WEEK - this.firstDay;
    },
    numDaysInLastWeek() {
      return this.lastDay + 1;
    },
    numWeeks() {
      const daysLeft =
        this.numDays - this.numDaysInFirstWeek - this.numDaysInLastWeek;
      return daysLeft / 7 + 2;
    },
    numFullWeeks() {
      return this.numWeeks - 2;
    },
    datesInFirstWeek() {
      return this.generateDatesInWeek(
        1,
        this.firstDay,
        this.numDaysInFirstWeek
      );
    },
    datesInLastWeek() {
      return this.generateDatesInWeek(
        this.numDays - this.numDaysInLastWeek + 1,
        0,
        this.numDaysInLastWeek
      );
    },
    datesInMiddleWeeks() {
      const startDates = [];
      for (let i = 0; i < this.numFullWeeks; i += 1) {
        startDates[i] = this.datesInFirstWeek[6] + 1 + i * 7;
      }
      return startDates.map((startDate) =>
        this.generateDatesInWeek(startDate, 0, 7)
      );
    },
    datesPerWeek() {
      return [
        this.datesInFirstWeek,
        ...this.datesInMiddleWeeks,
        this.datesInLastWeek,
      ];
    },
    isCurrentMonth() {
      return this.todayMonth === this.month;
    },
    isCurrentYear() {
      return this.todayYear === this.year;
    },
    isToday() {
      return this.isCurrentMonth && this.isCurrentYear ? this.todayDate : 0;
    },
    isSelected() {
      if (this.selectedDate) {
        this.showDetail(this.selectedDate);
        return this.selectedDate.year === this.year &&
          this.selectedDate.month === this.month
          ? this.selectedDate.date
          : 0;
      }
      return 0;
    }, 
    // Detail 페이지로 넘기는 부분
    showDetail(date) {
      const detailDate =
        date.selectedDate.year +
        "-" +
        (date.selectedDate.month + 1 < 10
          ? "0" + (date.selectedDate.month + 1)
          : date.selectedDate.month + 1) +
        "-" +
        (date.selectedDate.date < 10
          ? "0" + date.selectedDate.date
          : date.selectedDate.date);
      this.goDetail(detailDate);
    },
    isGood() {
      return this.goodDay;
    },
    isNormal() {
      // return new Date(2020, 9 - 1, 3).getDate();
      return this.normalDay;
    },
    isBad() {
      // return new Date(2020, 9 - 1, 4).getDate();
      return this.badDay;
    },
    // 필요한 영양성분 보내주기
    sendDayCal() {
      return this.DayCal;
    },
    sendDayNa() {
      return this.DayNa;
    },
    sendDayTan() {
      return this.DayTan;
    },
    sendDayDan() {
      return this.DayDan;
    },
    sendDayJi() {
      return this.DayJi;
    },
    sendDayDang() {
      return this.DayDang;
    },
    sendDayCol() {
      return this.DayCol;
    },
  },
  watch: {
    date(val) {
      this.setDate(val);
    },
  },
  created() {
    //권장 칼로리 계산
    this.calCal();

    const date = new Date();
    if (this.date) {
      this.setDate(this.date);
    } else {
      this.year = date.getFullYear();
      this.month = date.getMonth();
    }
    this.todayDate = date.getDate();
    this.todayYear = date.getFullYear();
    this.todayMonth = date.getMonth();
    this.weekStartDay =
      this.firstDayOfWeek >= 0 && this.firstDayOfWeek <= 6
        ? this.firstDayOfWeek
        : 0;

    this.DayCal = [];
    this.DayNa = [];
    this.DayTan = [];
    this.DayDan = [];
    this.DayJi = [];
    this.DayDang = [];
    this.DayCol = [];
    this.DayCal.push(0);
    this.DayNa.push(0);
    this.DayTan.push(0);
    this.DayDan.push(0);
    this.DayJi.push(0);
    this.DayDang.push(0);
    this.DayCol.push(0);
    for (var i = 1; i < this.todayDate; i++) {
      this.DayCal.push(0);
      this.DayNa.push(0);
      this.DayTan.push(0);
      this.DayDan.push(0);
      this.DayJi.push(0);
      this.DayDang.push(0);
      this.DayCol.push(0);
      this.retrieveDayInfo(i);
    }
    
    // 조언 불러오기
    this.getRecommend();
    // 회원정보수정모달
    this.userUpdate();
    
  },
  methods: {
    setData(data){
      this.showModal = data;
      this.user = this.$cookies.get("auth-token");
    },
    // 개인정보 없으면 회원정보수정 모달 띄우게
    userUpdate(){
      if(this.user.age == null && this.user.height == null && this.user.weight == null){
        this.showModal = true;
      }
    },
    // 일일 권장 칼로리 계산 부분
    calCal(){
      if (this.user.age >= 20) {
        // 체중 대비 백분율 (PIBW) 적용
        if (this.user.gender == "여") {
          this.recommandCal = ((this.user.height * this.user.height) * 0.0001 * 21 * 35);
        } else {
          this.recommandCal = ((this.user.height * this.user.height) * 0.0001 * 22 * 35);
        }
      } else if (this.user.age <= 3) {
        this.recommandCal = 1200;
      } else if (this.user.age <= 6) {
        this.recommandCal = 1600;
      } else if (this.user.age <= 9) {
        this.recommandCal = 1800;
      } else {
        if (this.user.gender == "여") {
          if (this.user.age <= 12) {
            this.recommandCal = 2000;
          } else {
            this.recommandCal = 2100;
          }
        } else {
          if (this.user.age <= 12) {
            this.recommandCal = 2200;
          } else if (this.user.age <= 15) {
            this.recommandCal = 2500;
          } else {
            this.recommandCal = 2700;
          }
        }
      } 
      // 일일 영양성분 권장량 집어넣는 부분
      this.sendData.recommand[0] = Number(this.recommandCal).toFixed(1);
      this.sendData.recommand[1] = Number(this.recommandCal * 0.55).toFixed(1);
      this.sendData.recommand[2] = Number(this.recommandCal * 0.25).toFixed(1);
      this.sendData.recommand[3] = Number(this.recommandCal * 0.2).toFixed(1);

      window.localStorage.setItem("calorie", JSON.stringify(this.sendData.recommand));
    },
    // 디테일 페이지로 넘기기
    goDetail(detailDate) {
      this.$router.push(`/detail/${detailDate}`);
    },
    // 주간 리포트 페이지로 넘기기
    goWeekReport() {
      http
        .get("/menus/report/" + this.user.memberno + "/")
        .then((res) => {
          this.$router.push({ name: 'WeekReport', params: { 'recommandData': this.sendData.recommand, 'submitData': res.data }})
        })
        .catch((err) => {
        })
    },

    // 조언 보내기
    getRecommend() {
      var formData = new FormData();
      formData.append("recommendData", this.sendData.recommand)

      http
        .post("/menus/recommend/" + this.user.memberno + "/", formData)
        .then((res) => {
          this.advise1 = res.data.text1;
          this.advise2 = res.data.text2;
        })
        .catch((err) => {
        })
    },

    // 이미지 받아오기
    setImage() {
      this.loadingOn = true;
      this.MenuImage = this.$refs.imagefile.files[0];

      var formData = new FormData();
      formData.append("Image", this.MenuImage);
      formData.append("member", this.user.email);

      http
      .post('menus/image/', formData)
      .then((res) => {
        var MenuList = res.data.menulist;
        this.loadingOn = false;
        this.$router.push({ name: 'analysis', params: { 'MenuImage': this.MenuImage, 'MenuList': MenuList }});
      })
      
    },

    dayCondition(day) {
      if ((this.DayCal[day] <= this.recommandCal * 1.15) && (this.DayCal[day] >= this.recommandCal * 0.85)) {
        this.goodDay.push(day);
      } else if ((this.DayCal[day] <= this.recommandCal * 1.3) && (this.DayCal[day] >= this.recommandCal * 0.7)) {
        this.normalDay.push(day);
      } else {
        this.badDay.push(day);
      }
    },
    generateDatesInWeek(startDate, startDay, numDays) {
      const datesInWeek = new Array(7).fill(0);
      for (let i = 0; i < numDays; i += 1) {
        datesInWeek[startDay + i] = startDate + i;
      }
      return datesInWeek;
    },
    toggleMonth(direction) {
      let newMonth = this.month + Number(direction);

      let newYear = this.year;

      if (newMonth < 0) {
        newMonth = 11;
        newYear -= 1;
      }
      if (newMonth > 11) {
        newMonth = 0;
        newYear += 1;
      }
      if (newYear >= 1970) {
        this.month = newMonth;
        this.year = newYear;
      }
      // 여기에
      let YM =
        newYear +
        "-" +
        (newMonth + 1 < 10 ? "0" + (newMonth + 1) : newMonth + 1);

      this.goodDay = [];
      this.normalDay = [];
      this.badDay = [];
      this.DayCal = [];
      this.DayNa = [];
      this.DayTan = [];
      this.DayDan = [];
      this.DayJi = [];
      this.DayDang = [];
      this.DayCol = [];
      this.DayCal.push(0);
      this.DayNa.push(0);
      this.DayTan.push(0);
      this.DayDan.push(0);
      this.DayJi.push(0);
      this.DayDang.push(0);
      this.DayCol.push(0);
      if (this.todayYear < newYear) {
      } else if (this.todayYear == newYear) {
        if (this.todayMonth < newMonth) {
        } else if (this.todayMonth == newMonth) {
          for (var i = 1; i < this.todayDate; i++) {
            this.DayCal.push(0);
            this.DayNa.push(0);
            this.DayTan.push(0);
            this.DayDan.push(0);
            this.DayJi.push(0);
            this.DayDang.push(0);
            this.DayCol.push(0);
            this.retrieveDayInfo(i);
          }
        } else {

          let monthLength = 0;
          if (this.newMonth + 1 == 2) {
            monthLength = 28;
          } else if (
            this.newMonth + 1 == 1 ||
            this.newMonth + 1 == 3 ||
            this.newMonth + 1 == 5 ||
            this.newMonth + 1 == 7 ||
            this.newMonth + 1 == 8 ||
            this.newMonth + 1 == 10 ||
            this.newMonth + 1 == 12
          ) {
            monthLength = 31;
          } else {
            monthLength = 30;
          }

          for (var i = 1; i <= monthLength; i++) {
            this.DayCal.push(0);
            this.DayNa.push(0);
            this.DayTan.push(0);
            this.DayDan.push(0);
            this.DayJi.push(0);
            this.DayDang.push(0);
            this.DayCol.push(0);
            this.retrievePrevDayInfo(i, YM);
          }
        }
      } else {
        let monthLength = 0;
        if (this.newMonth + 1 == 2) {
          monthLength = 28;
        } else if (
          this.newMonth + 1 == 1 ||
          this.newMonth + 1 == 3 ||
          this.newMonth + 1 == 5 ||
          this.newMonth + 1 == 7 ||
          this.newMonth + 1 == 8 ||
          this.newMonth + 1 == 10 ||
          this.newMonth + 1 == 12
        ) {
          monthLength = 31;
        } else {
          monthLength = 30;
        }


        for (var i = 1; i <= monthLength; i++) {
          this.DayCal.push(0);
          this.DayNa.push(0);
          this.DayTan.push(0);
          this.DayDan.push(0);
          this.DayJi.push(0);
          this.DayDang.push(0);
          this.DayCol.push(0);
          this.retrievePrevDayInfo(i, YM);
        }
      }
    },
    selectDate(date) {
      if (date) {
        this.selectedDate = {
          year: this.year,
          month: this.month,
          date,
        };
        this.$emit("selectDate", this.selectedDate);
      }
    },
    setDate(date) {
      this.year = date.year;
      this.month = date.month;
      this.selectedDate = this.date;
    },
    // 현재 달
    retrieveDayInfo(day) {
      const dayday =
        this.todayYear +
        "-" +
        (this.todayMonth + 1 < 10
          ? "0" + (this.todayMonth + 1)
          : this.todayMonth + 1) +
        "-" +
        (day < 10 ? "0" + day : day);
      //멤버 번호
      const memberNo = this.user.memberno;

      const totalCal = 0;
      http
        .get("/menus/daily/" + memberNo + "/" + dayday + "/")
        .then((res) => {
          for (var i = 0; i < res.data.length; i++) {
            this.DayCal[day] += res.data[i].cal;
            this.DayNa[day] += res.data[i].na;
            this.DayTan[day] += res.data[i].tan;
            this.DayDan[day] += res.data[i].dan;
            this.DayJi[day] += res.data[i].ji;
            this.DayDang[day] += res.data[i].dang;
            this.DayCol[day] += res.data[i].col;
            this.dayCondition(day);
          }
        })
        .catch((error) => {
        });
    },
    // 이전 달
    retrievePrevDayInfo(day, YM) {
      //멤버 번호
      const memberNo = this.user.memberno;
      let dayday = YM + "-" + (day < 10 ? "0" + day : day);
      
      const totalCal = 0;
      http
        .get("/menus/daily/" + memberNo + "/" + dayday + "/")
        .then((res) => {
          for (var i = 0; i < res.data.length; i++) {
            this.DayCal[day] += res.data[i].cal;
            this.DayNa[day] += res.data[i].na;
            this.DayTan[day] += res.data[i].tan;
            this.DayDan[day] += res.data[i].dan;
            this.DayJi[day] += res.data[i].ji;
            this.DayDang[day] += res.data[i].dang;
            this.DayCol[day] += res.data[i].col;
            this.dayCondition(day);
          }
        })
        .catch((error) => {
        });
    },
    sendDayData(num) {
      return this.sendData.nutriData[num];
    },
  },
};
</script>

<style lang="scss" scoped>
#single-date-picker {
  font-family: "Lato", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
.single-date-picker__calendar-view {
  max-width: 90%;
  border-radius: 10px;
  color: #0d1e44;
}
.calendar-back {
  background-color: white;
  border-radius: 10px;
  margin-top: 10px;
  margin-bottom: 20px;
}
.graph {
  margin-top: 10px;
}
.advise{
  background-color: white;
  vertical-align: middle;
  border-radius: 0.225rem;
  margin: auto;
  padding: 20px 15px;
  margin-bottom: 12px;
}
.advise > h4, h6{
  margin-bottom: 0;
  font-family: 'BMDOHYEON';
  // color: #ffc000;
}
.graphs{
  background: rgb(240, 240, 240);
  // background: rgb(228, 228, 228);
  border-radius: 15px;
}
.title{
  font-family: 'BMDOHYEON';
  color: #ffc000;
  text-align: left;
}
.loading-view {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, .8);
}
.loading-message {
  position: fixed;
  z-index: 9999;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: #fff;
  width: 220px;
  height: 35px;
  display: table;
}
.loadingimg{
  width:30px;
}
.loadingfont{
  font-size:22px; 
  margin:0 5px; 
  display: table-cell; 
  vertical-align: middle;
}
.main{
  margin: 75px auto 0;
}
</style>
