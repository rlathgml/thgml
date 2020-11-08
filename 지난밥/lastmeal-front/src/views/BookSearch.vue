<template>
  <d-container fluid class="main px-4">
    <!-- 검색 위치 -->
    <d-row class="mb-4">
      <input type="text" name="searchBook" class="input-form" v-model="text" placeholder="음식 이름을 넣어주세요." @keyup.enter="searchName" autofocus />
      <input type="button" name="searchBook" value="검색" @click="searchName" />
    </d-row>

    <!-- 검색한 결과 -->
    <div v-if="receiveOn">
      <d-row class="mb-3 text-center">
        <d-col>
          <h4 class="title">{{ researchText }} 영양성분</h4>
        </d-col>
      </d-row>
      
      <!-- 서치가 된 경우 -->
      <div v-if="searchOk">
        <d-row style="margin-top: 10px;">
          <!-- Users by Device (lite) -->
          <d-col class="mb-4">
            <bo-users-by-device :submitData="sendMenu" :change="true"/>
          </d-col>
        </d-row>

        <d-row>
          <!-- Top Referrals -->
          <d-col class="mb-4">
            <!-- <bo-top-referrals :submitData="sendMenu" :change="true"/> -->
            <d-card class="card-small">

              <!-- Card Header -->
              <d-card-header class="border-bottom">
                <h6 class="m-0">{{ title }}</h6>
                <div class="block-handle"></div>
              </d-card-header>

              <d-card-body class="p-0">

                <!-- Top Referrals List Group -->
                <d-list-group flush class="list-group-small">
                  <d-list-group-item v-for="(item, idx) in referralData" :key="idx" class="d-flex px-3">
                    <span class="text-semibold text-fiord-blue">{{ item.title }}</span>
                    <span v-if="idx != 4 && idx != 6 && idx != 5" class="ml-auto text-right text-semibold text-reagent-gray">{{ item.value }} g</span>
                    <span v-if="idx == 4 || idx == 6" class="ml-auto text-right text-semibold text-reagent-gray">{{ item.value }} mg</span>
                    <span v-if="idx == 5" class="ml-auto text-right text-semibold text-reagent-gray">{{ item.value }} kcal</span>
                  </d-list-group-item>
                </d-list-group>

              </d-card-body>

            </d-card>

          </d-col>
        </d-row>
      </div>

      <!-- 서치가 안 된 경우 -->
      <div v-else>
        <d-row>
          <d-col class="message-text">
            {{ message }}
          </d-col>
        </d-row>
      </div>

    </div>

  </d-container>
</template>

<script>
import UsersByDevice from "@/components/blog/UsersByDeviceLite.vue";
import http from "../utils/axios-common";

const defaultTopReferrals = [{
  title: '탄수화물',
  value: '0',
}, {
  title: '단백질',
  value: '0',
}, {
  title: '지방',
  value: '0',
}, {
  title: '당류',
  value: '0',
}, {
  title: '나트륨',
  value: '0',
}, {
  title: '칼로리',
  value: '0',
}, {
  title: '콜레스테롤',
  value: '0',
},
];

export default {

  components: {
    boUsersByDevice: UsersByDevice,
  },

  props: {
    /**
       * The component's title.
       */
    title: {
      type: String,
      default: '상세 영양 정보',
    },
    /**
       * The referral datasets.
       */
    referralData: {
      type: Array,
      default() {
        return defaultTopReferrals;
      },
    },
  },

  data() {
    return {
      text: '',
      researchText: '',
      receiveOn: false,
      searchOk: true,
      message: '',
      menu: [],
      widthData: 0,
    }
  },
  
  created() {
    window.addEventListener('resize', this.handleResize);
    this.handleResize();
  },

  watch: {
    destroyed() {
      window.removeEventListener('resize', this.handleResize);
    },
  },

  computed: {
    sendMenu() {
      return this.menu
    }
  },

  methods: {
    searchName() {
      this.searchOk = true;
      this.receiveOn = false;
      this.menu = [];
      this.researchText = this.text;
      this.text = '';

      http
      .get('menus/detail/' + this.researchText + '/')
      .then((res) => {
        this.menu = res.data;
        this.retrieveMenuDetail(this.menu);
        if (res.data['data'] === null) {
          this.searchOk = false;
          this.message = res.data['message'];
        }
        this.receiveOn = true;
      })
      .catch((err) => {
      })
    },

    retrieveMenuDetail(submitData) {
      
      defaultTopReferrals[0].value = submitData['tan'];
      defaultTopReferrals[1].value = submitData['dan'];
      defaultTopReferrals[2].value = submitData['ji'];
      defaultTopReferrals[3].value = submitData['dang'];
      defaultTopReferrals[4].value = submitData['na'];
      defaultTopReferrals[5].value = submitData['cal'];
      defaultTopReferrals[6].value = submitData['col'];

    },
    
    // 현재 보이는 창 화면 계산
    handleResize() {
      this.widthCal(window.innerWidth);
    },
    // 넓이 계산해서 style --widthSize 안에 넣어주기
    widthCal(windowWidth) {
      const suffix = 'px';
      if (windowWidth > 992) {
        this.widthData = Number(windowWidth / 3) - 106;
      } else if (windowWidth > 768) {
        this.widthData = Number(windowWidth / 2) - 106;
      } else {
        this.widthData = Number(windowWidth) - 102;
      }
      document.documentElement.style.setProperty('--inputSize', this.widthData + suffix);
    },

  },

}
</script>

<style lang="scss" scoped>
:root {
    --inputSize: 200px;
}
  input{
    margin:0;
  }
  input[type="text"]{
    margin-left: 16px;
    width: var(--inputSize);
    height: 40px;
    border: none;
    font-size: 1em;
    padding-left: 5px;
    font-style: oblique;
    display: inline;
    outline: none;
    box-sizing: border-box;
    color: black;
    outline: none;
  }

  input[type=button]{
    width: 60px;
    height: 40px;
    border: none;
    background-color: #ffc800;
    font-size: 1em;
    color: #000;
    font-weight: bold;
    outline: none;
    display: inline;
    margin-left: -10px;
    box-sizing: border-box;
    outline: none;
    border-radius: 0px;
  }

.title {
  color: #ffffff;
  text-align: center;
  font-size: 1rem;
  font-weight: bold;
}

.message-text {
  color: #ffffff;
  text-align: center;
  font-size: 0.9rem;
}

.main{
  margin-top: 75px;
}
</style>