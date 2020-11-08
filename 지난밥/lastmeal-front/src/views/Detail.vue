<template>
  <d-container fluid class="main-content-container px-4" style="margin-top:75px;">
    <!-- Page Header -->
    <d-row no-gutters class="page-header">
      <d-col class="text-center mb-4">
        <span
          class="text-uppercase page-subtitle"
          style="color: #ffffff;"
        >{{this.$route.params.date}}</span>
        <h3 class="page-title" style="color: #ffffff;" > {{ menus[time].eattime.substring(11, 16) }}</h3>
        <!-- <h3 class="page-title" style="color: #ffffff;" v-if="(menus[time].eattime.substring(11, 13)-9) >= 0 && (menus[time].eattime.substring(11, 13)-9) < 10"> 0{{ menus[time].eattime.substring(11, 13)-9 }}{{ menus[time].eattime.substring(13, 19) }}</h3>
        <h3 class="page-title" style="color: #ffffff;" v-if="(menus[time].eattime.substring(11, 13)-9) >= 0 && (menus[time].eattime.substring(11, 13)-9) >= 10"> {{ menus[time].eattime.substring(11, 13)-9 }}{{ menus[time].eattime.substring(13, 19) }}</h3>
        <h3 class="page-title" style="color: #ffffff;" v-if="(menus[time].eattime.substring(11, 13)-9) < 0">{{ +menus[time].eattime.substring(11, 13)+15 }}{{ menus[time].eattime.substring(13, 19) }}</h3> -->
      </d-col>
    </d-row>

    <d-row style="margin-top: 10px;">
      <!-- Users by Device (lite) -->
      <d-col class="mb-4">
        <section class="section section--demo-2">
          <agile @after-change="showCurrentSlide($event)" :initial-slide="0">
            <img v-for="n in menus.length" :id="'img' + n-1" class="slide" :src="menus[n-1].menuimgpath" />
          </agile>
        </section>
      </d-col>
    </d-row>
    <!-- data가 존재할 때 -->
    <div v-if="dataExist">
      <!-- 식사 메뉴 목록 -->
      <d-row style="margin-top: 10px;">
        <d-col class="mb-4">
          <MenuListview :submitData="menuList" :change="true"/>
        </d-col>
      </d-row>
      <d-row style="margin-top: 10px;">
        <!-- Users by Device (lite) -->
        <d-col class="mb-4">
          <bo-users-by-device :submitData="menu" :change="true"/>
        </d-col>
      </d-row>

      <d-row>
        <!-- Top Referrals -->
        <d-col class="mb-4">
          <bo-top-referrals :submitData="menu" :change="true"/>
        </d-col>
      </d-row>
    </div>
    <!-- 데이터가 존재하지 않을 때 -->
    <div v-else>
      <d-row style="margin-top: 10px;">
        <d-col col class="text-center mb-4">
          <span
            class="text-uppercase page-subtitle"
            style="color: #ffffff; font-size: 14px;"
          >아직 등록된 식단이 없습니다</br>식단을 등록해주세요</span>
        </d-col>
      </d-row>
      
      <!-- 카메라 / 업로드 -->
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

  </d-container>
</template>

<script>
import SmallStats from "@/components/common/SmallStats.vue";
import TopReferrals from "@/components/common/TopReferrals.vue";
import UsersOverview from "@/components/blog/UsersOverview.vue";
import UsersByDevice from "@/components/blog/UsersByDeviceLite.vue";
import MenuListview from "@/components/blog/MenuListview.vue";
import NewDraft from "@/components/blog/NewDraft.vue";
import Discussions from "@/components/blog/Discussions.vue";
import PureVueChart from "pure-vue-chart";
import DetailImage from "@/components/detail/detailImage.vue";
import http from "../utils/axios-common";
import fab from '@/components/common/vue-fab.vue';

export default {
  components: {
    SmallStats,
    boUsersOverview: UsersOverview,
    boUsersByDevice: UsersByDevice,
    MenuListview,
    boNewDraft: NewDraft,
    boDiscussions: Discussions,
    boTopReferrals: TopReferrals,
    PureVueChart,
    DetailImage,
    fab,
  },
  props: {
    submitDate: {
      type: Number,
    },
  },
  data() {
    return {
      baseUrl: 'https://j3d205.p.ssafy.io',
      MenuImage: '',
      time: 0,
      menus: [],
      menu: [],
      menuList: [],
      dateRange: {
        from: null,
        to: null,
      },
      dataExist: true,
      loadingOn: false,
    };
  },
  methods: {
    retrieveMenuInfo() {
      const memberNo = this.$cookies.get("auth-token").memberno
      const day = this.$route.params.date;

      http
        .get("/menus/daily/" + memberNo + "/" + day + "/",{
        headers: {
          'Access-Control-Allow-Origin': '*',
          'Context-type':'image/jpeg'}})
        .then((res) => {
          var slide = document.getElementById("slide");
          if (res.data.length == 0) {

            this.dataExist = false;
            this.menus.push(
              new Object({
                eattime: "0000-00-00 00:00:00",
                menuimgpath:
                  "https://teamtech24.com/foodhati/foodhatiAdmin/assets/img/foodimg/default-food-image.jpg",
                menulist: "",
              })
            );

          } else {

            this.dataExist = true;
            for (var i = 0; i < res.data.length; i++) {
              res.data[i].menuimgpath = this.baseUrl + res.data[i].menuimgpath;
              this.menus.push(res.data[i]);
            }
          }
        })
        .catch((error) => {
        });
    },
    showCurrentSlide(event) {
      this.time = event.currentSlide;
      this.menu = [];
      this.menu = this.menus[this.time];
      this.menuList = [];
      var tmp = this.menu['menulist'].split('|');
      for (let i = 0; i < tmp.length; i++) {
        this.menuList.push(tmp[i].split(','))
      }
      this.$emit("submitData", this.menu);
      this.$emit("change", true);
    },

    // 카메라
    
    setImage() {
      this.loadingOn = true;
      this.MenuImage = this.$refs.imagefile.files[0];

      var user = this.$cookies.get("auth-token");

      var formData = new FormData();
      formData.append("Image", this.MenuImage);
      formData.append("member", user.email);

      http
      .post('menus/image/', formData)
      .then((res) => {
        this.loadingOn = false;
        var MenuList = res.data.menulist
        this.$router.push({ name: 'analysis', params: { 'MenuImage': this.MenuImage, 'MenuList': MenuList, 'DateTime': this.$route.params.date }});
      })
    },
  },
  created() {
    this.menus = [];
    this.retrieveMenuInfo();
  },
  mounted() {
    
  },
  updated() {
  },
};
</script>

<style>
.section--demo-2 .agile__nav-button {
  background: transparent;
  border: none;
  color: #fff;
  cursor: pointer;
  font-size: 24px;
  height: 100%;
  position: absolute;
  top: 0;
  transition-duration: 0.3s;
  width: 80px;
}
.section--demo-2 .agile__nav-button:hover {
  background-color: rgba(0, 0, 0, 0.5);
  opacity: 1;
}
.section--demo-2 .agile__nav-button--prev {
  left: 0;
}
.section--demo-2 .agile__nav-button--next {
  right: 0;
}
.section--demo-2 .agile__dots {
  bottom: 10px;
  left: 50%;
  position: absolute;
  transform: translateX(-50%);
}
.section--demo-2 .agile__dot {
  margin: 0 10px;
}
.section--demo-2 .agile__dot button {
  background-color: transparent;
  border: 1px solid #fff;
  border-radius: 50%;
  cursor: pointer;
  display: block;
  height: 10px;
  font-size: 0;
  line-height: 0;
  margin: 0;
  padding: 0;
  transition-duration: 0.3s;
  width: 10px;
}
.section--demo-2 .agile__dot--current button,
.section--demo-2 .agile__dot:hover button {
  background-color: #fff;
}
.section--demo-2 .agile .slide {
  display: block;
  object-fit: cover;
}

.section--menu-list {
  background-color: #fff;
  border-radius: 10px;
  font-size: 1rem;
  padding: 10px;
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
</style>