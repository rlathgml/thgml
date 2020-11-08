<template>
  <section class="section section--demo-2">
    <agile :initial-slide="0">
      <img id="img0" class="slide" :src="menus[0].menuimgpath" />
      <img id="img1" class="slide" :src="menus[1].menuimgpath" />
	  <img id="img2" class="slide" :src="menus[2].menuimgpath" />
	  <img id="img3" class="slide" :src="menus[3].menuimgpath" />
	  <img id="img4" class="slide" :src="menus[4].menuimgpath" />
	  <img id="img5" class="slide" :src="menus[5].menuimgpath" />
	  <img id="img6" class="slide" :src="menus[6].menuimgpath" />
    </agile>
  </section>
</template>

<script>
import axios from "axios";
import http from "../../utils/axios-common";

export default {
  name: "Example2",
  data() {
    return {
      menus: [],
    };
  },
  created() {
    this.nemus = [];
    this.retrieveMenuInfo();
  },
  methods: {
    //메뉴 조회
    retrieveMenuInfo() {
      const memberNo = this.user.memberno;
      const day = this.$route.params.date;

      http
        .get("/menus/daily/" + memberNo + "/" + day + "/")
        .then((res) => {
          var slide = document.getElementById("slide");
          for (var i = 0; i < res.data.length; i++) {
            this.menus.push(res.data[i]);
          }
          for (var i = 7 - res.data.length; i > 0; i--) {
            this.menus.push(new Object({
				eattime : "",
				menuimgpath : "https://teamtech24.com/foodhati/foodhatiAdmin/assets/img/foodimg/default-food-image.jpg",
				menulist : ""
			}));
          }
        })
        .catch((error) => {
        });
    },
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
</style>
