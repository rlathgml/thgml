<template>
  <div class="total-area">
    <div class="pt-3">
      <!-- Card Header -->
      <div class="text-center">
        
        <!-- 시간 조절 -->
        <div class="mb-4">
          <h4 class="title">섭취 시간</h4>
          <div v-for="(type, idx) in types" :key="type" class="mb-1">
            <d-form-input :id="`type-${type}`" v-model="typeTime[idx]" :type="type" style="font-size: 0.85rem;"></d-form-input>
          </div>
        </div>

        <!-- User Avatar -->
        <div class="mb-4">
          <h4 class="title">식단 이미지</h4>
          <img :src="MenuImg" :alt="MenuImage" :width="sendImageWidth" class="image-view" />
        </div>

        <!-- 메뉴 목록 -->
        <div class="mb-0">
          <h4 class="title">{{ title }}</h4>
          <div style="display: none;">{{ test }}</div>
          <d-container class="menu-view pt-3">
            <d-row>
              <d-col cols="6">
                <ul>
                  <li style="height: 40px; line-height: 40px;" v-for="menu in Menus" :key="menu[0] + 'key'">{{ menu[0] }}</li>
                </ul>
              </d-col>
              <d-col cols="6">
                <div v-for="(menu, index) in Menus" :key="menu[0] + 'key'">
                  <NumberInputSpinner
                    :min="0"
                    :max="10"
                    :step="1"
                    :integerOnly="true"
                    v-model="Menus[index][1]"
                  />
                </div>
              </d-col>
            </d-row>
          </d-container>
        </div>

        <!-- 총 칼로리 -->
        <div class="my-4">
          <h4 class="title">분석</h4>
          <d-list-group-item style="border-radius: 10px;">
            <div class="progress-wrapper">
              <strong class="text-muted d-block mb-2">{{ userDetails.performanceReportTitle }}</strong>
              <d-progress class="progress-sm">
                <span class="progress-value">{{ userDetails.performanceReportValue }} Cal</span>
                <d-progress-bar :max="userDetails.maxCal" :value="userDetails.performanceReportValue" />
              </d-progress>
            </div>
          </d-list-group-item>
        </div>
      </div>
    </div>
    
    <div class="update-menu" style="margin: 10px;">
      <d-button type="submit" class="btn-warning" @click="goCreate" style="font-weight: bold; font-size: 1rem;">식단 기록하기</d-button>
    </div>

  </div>
</template>

<script>
import NumberInputSpinner from "vue-number-input-spinner";
import http from "../../utils/axios-common";

const defaultUserDetails = {
  name: "Sierra Brooks",
  maxCal: 3000,
  performanceReportTitle: "Total Calorie",
  performanceReportValue: 0,
  performanceReporttan: 0,
  performanceReportdan: 0,
  performanceReportji: 0,
  performanceReportdang: 0,
  performanceReportna: 0,
  performanceReportcol: 0,
};


export default {
  name: "user-details",
  components: {
    NumberInputSpinner,
  },
  props: {
    /**
     * The user details.
     */
    userDetails: {
      type: Object,
      default() {
        return defaultUserDetails;
      },
    },
    title: {
      type: String,
      default: "식단 메뉴 리스트",
    },
    MenuImage: {
      type: File,
    },
    MenuList: {
      type: Array,
    },
    DateTime: {
      type: String,
      default: '0000-00-00',
    },
  },

  data() {
    return {
      // MenuImg: '',
      Menus: [],
      MenusCal: [],
      MenusLen: 0,
      TotalCal: 0,
      count: 0,
      now: '',
      user: this.$cookies.get("auth-token"),
      windowWidth: 0,
      imageWidthData: 0,
      types: [
        'date',
        'time',
      ],
      typeTime: [
        '2020-10-05',
        '15:47:48',
      ]
    };
  },
  computed: {
    // 이부분 잘돌아가게 고치자
    test() {
      defaultUserDetails.performanceReportValue = 0;
      defaultUserDetails.performanceReporttan = 0;
      defaultUserDetails.performanceReportdan = 0;
      defaultUserDetails.performanceReportji = 0;
      defaultUserDetails.performanceReportdang = 0;
      defaultUserDetails.performanceReportna = 0;
      defaultUserDetails.performanceReportcol = 0;
      for (let i = 0; i < this.Menus.length; i++) {
        this.Test(i);
      }

      return 0;
    },
    // 보이는 화면 넓이 보내주기
    sendImageWidth() {
      return this.imageWidthData
    },
  },
  updated() {
    // this.count++;
    // if (this.count % 4 == 1) {
      // this.getCal();
    // }
  },
  created() {
    this.MenuImg = URL.createObjectURL(this.MenuImage);
    this.Menus = this.MenuList;
    this.MenuSLen = this.Menus.length;
    window.addEventListener('resize', this.handleResize);
    this.handleResize();
    this.getTimeStamp();
    this.typeTime = this.now.split(' ');
    if (this.DateTime != '0000-00-00') {
      this.typeTime[0] = this.DateTime;
    }
  },
  watch: {
    destroyed() {
      window.removeEventListener('resize', this.handleResize);
    },
  },

  methods: {
    getCal() {
      // TotalCdefaultUserDetailsal.performanceReportValue = 5;
      defaultUserDetails.performanceReportValue = 0;
      for (let i = 0; i < this.Menus.length; i++) {
        this.Test(i); 
      }
    },
    Test(num) {
      http
        .get("/menus/detail/" + this.Menus[num][0] + "/")
        .then((res) => {
          defaultUserDetails.performanceReporttan += res.data.tan * this.Menus[num][1];
          defaultUserDetails.performanceReportdan += res.data.dan * this.Menus[num][1];
          defaultUserDetails.performanceReportji += res.data.ji * this.Menus[num][1];
          defaultUserDetails.performanceReportdang += res.data.dang * this.Menus[num][1];
          defaultUserDetails.performanceReportna += res.data.na * this.Menus[num][1];
          defaultUserDetails.performanceReportcol += res.data.col * this.Menus[num][1];
          defaultUserDetails.performanceReportValue += res.data.cal * this.Menus[num][1];
        })
        .catch((error) => {
        });
    },
    // 현재 보이는 창 화면 계산
    handleResize() {
      this.windowWidth = window.innerWidth;
      this.imageWidth();
    },
    // 넓이 계산해서 image width 안에 넣어주기
    imageWidth() {
      if (this.windowWidth > 992) {
        this.imageWidthData = Number(this.windowWidth / 3).toFixed(0) - 66;
      } else if (this.windowWidth > 768) {
        this.imageWidthData = Number(this.windowWidth / 2).toFixed(0) - 66;
      } else {
        this.imageWidthData = Number(this.windowWidth).toFixed(0) - 66;
      }
    },
    goCreate() {
     // 메뉴 리스트 -> 문자열 변환
      var menulist = this.Menus.join('|');

      var eattime = this.typeTime[0] + ' ' + this.typeTime[1];

      var formData = new FormData();
      formData.append('memberno', this.user.memberno);
      formData.append('menuimgpath', this.MenuImage);
      formData.append('menulist', menulist)
      formData.append('eattime', eattime);
      formData.append('cal', this.userDetails.performanceReportValue);
      formData.append('tan', this.userDetails.performanceReporttan);
      formData.append('dan', this.userDetails.performanceReportdan);
      formData.append('ji', this.userDetails.performanceReportji);
      formData.append('dang', this.userDetails.performanceReportdang);
      formData.append('na', this.userDetails.performanceReportna);
      formData.append('col', this.userDetails.performanceReportcol);
    
      http
      .post('menus/create/', formData)
      .then((res) => {
        this.$router.push("detail/" + eattime.substring(0,10));
      })
    },
    getTimeStamp() {
      var d = new Date();
      var s =
        this.leadingZeros(d.getFullYear(), 4) + '-' +
        this.leadingZeros(d.getMonth() + 1, 2) + '-' +
        this.leadingZeros(d.getDate(), 2) + ' ' +

        this.leadingZeros(d.getHours(), 2) + ':' +
        this.leadingZeros(d.getMinutes(), 2) + ':' +
        this.leadingZeros(d.getSeconds(), 2);
    
      this.now = s;
    },
    leadingZeros(n, digits) {
      var zero = '';
      n = n.toString();

      if (n.length < digits) {
        for (var i = 0; i < digits - n.length; i++)
          zero += '0';
      }
      return zero + n;
    },
  },
};
</script>

<style lang="scss" scoped>

.title{
  color: #ffffff;
  text-align: left;
  font-size: 1rem;
  font-weight: bold;
}

.update-menu {
  position: fixed;
  bottom: 20px;
  left: 50%;
  transform: translate(-50%, 0%);
}

.total-area {
  margin-bottom: 80px;
}

.image-view {
  background-color: #fff;
  border-radius: 10px;
  padding: 10px;
}

.menu-view {
  background-color: #fff;
  border-radius: 10px;
}
</style>
