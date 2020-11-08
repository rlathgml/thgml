<template>
  <div class="wrapper">
    <div style="position: absolute; z-index: 5; left:50%; transform: translate(-111px, 0%)">
    <div class="night">
      <div class="shooting_star"></div>
      <div class="shooting_star"></div>
      <div class="shooting_star"></div>
      <div class="shooting_star"></div>
      <div class="shooting_star"></div>
      <div class="shooting_star"></div>
      <div class="shooting_star"></div>
      <div class="shooting_star"></div>
      <div class="shooting_star"></div>
      <div class="shooting_star"></div>
      <div class="shooting_star"></div>
      <div class="shooting_star"></div>
      <div class="shooting_star"></div>
      <div class="shooting_star"></div>
      <div class="shooting_star"></div>
      <div class="shooting_star"></div>
    </div>
      <div style="margin-top: 300px; ">
        <h1 style="text-align: center;">지난밥</h1>
        <kakao />
      </div>
    </div>
    <img src="../../assets/images/login/1.png" class="img"/>
  </div>
</template>

<script>
import YourLogo from "../../assets/images/Logo.png";
import Kakao from "../../views/KakaoLogin.vue";

export default {
  name: "YourMagnificentVueComponent",
  data() {
    return {
      childData: 20
    }
  },
  components: {
    Kakao,
  },
  computed: {
    logo() {
      return YourLogo;
    },
  },
  methods: {
    loginFormWithKakao(requestType) {
      Kakao.Auth.loginForm({
        success: function (res) {
          http
            .post("accounts/account/kakao/", {
              requestType: requestType,
              access_token: res.access_token,
              expires_in: res.expires_in,
              refresh_token: res.refresh_token,
              refresh_token_expires_in: res.refresh_token_expires_in,
              scope: res.scope,
              token_type: res.token_type,
            })
            .then((res1) => {
              console(res1.data);
            });
        },
        fail: function (err) {
          
        },
      });
    },
  },
};
</script>

<style>
  @font-face { 
    font-family: 'BMDOHYEON'; 
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/BMDOHYEON.woff') format('woff');
    font-weight: normal; 
    font-style: normal;
     }
  h1 {
    font-family: 'BMDOHYEON';
    font-weight: 900; 
    color: rgb(255, 192, 0);
  }
 .container-fluid{
    overflow: hidden;
  }
  .default-wrapper{
    margin-top: 0 !important;
  }
</style>

<style lang="scss">


  .wrapper{
    // background: linear-gradient(to bottom, #020369, #4676D1);
    height: 100vh;
  }
  .img{
    position: absolute;
    margin-top: 72vh;
    width: 100%;
    height: 50vw;
  }
  $shooting-time: 3000ms;

.night {
  position: relative;
  width: 100%;
  height: 100%;
  transform: rotateZ(45deg);
}

.shooting_star {
  position: absolute;
  left: 50%;
  top: 50%;
  // width: 100px;
  height: 2px;
  background: linear-gradient(-45deg, rgba(95, 145, 255, 1), rgba(0, 0, 255, 0));
  border-radius: 999px;
  filter: drop-shadow(0 0 6px rgba(105, 155, 255, 1));
  animation:
    tail $shooting-time ease-in-out infinite,
    shooting $shooting-time ease-in-out infinite;
  
  &::before {
    content: '';
    position: absolute;
    top: calc(50% - 1px);
    right: 0;
    // width: 30px;
    height: 2px;
    background: linear-gradient(-45deg, rgba(0, 0, 255, 0), rgba(95, 145, 255, 1), rgba(0, 0, 255, 0));
    transform: translateX(50%) rotateZ(45deg);
    border-radius: 100%;
    animation: shining $shooting-time ease-in-out infinite;
  }

  &::after {
    @extend .shooting_star::before;
    transform: translateX(50%) rotateZ(-45deg);
  }
  
  @for $i from 1 through 20 {
    &:nth-child(#{$i}) {
      $delay: random(9999) + 0ms;
      top: calc(50% - #{random(400) - 200px});
      left: calc(50% - #{random(300) + 0px});
      animation-delay: $delay;
      // opacity: random(50) / 100 + 0.5;
      
      &::before,
      &::after {
        animation-delay: $delay;
      }
    }
  }
}

@keyframes tail {
  0% {
    width: 0;
  }
  
  30% {
    width: 100px;
  }
  
  100% {
    width: 0;
  }
}

@keyframes shining {
  0% {
    width: 0;
  }
  
  50% {
    width: 30px;
  }
  
  100% {
    width: 0;
  }
}

@keyframes shooting {
  0% {
    transform: translateX(0);
  }
  
  100% {
    transform: translateX(300px);
  }
}

@keyframes sky {
  0% {
    transform: rotate(45deg);
  }
  
  100% {
    transform: rotate(45 + 360deg);
  }
}

</style>