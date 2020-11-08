<template>
  <div>
      <a id="login-form-btn" @click=loginFormWithKakao(1)>  
        <img
          class="kakao"
          src="//k.kakaocdn.net/14/dn/btqCn0WEmI3/nijroPfbpCa4at5EIsjyf0/o.jpg"
          width="222"
        />
      </a>
      <a>
        <img
          class="kakao kakaojoin"
          src="../assets/images/login/kakaojoin.png" @click=loginFormWithKakao(2)
          width="222"
        />
      </a>
      
      <Toast v-if="loginToast2" :message="'로그인 성공'"/>
  </div>
</template>

<script>
import axios from "axios";
import http from "../utils/axios-common.js";

export default {
  methods: {
    logout() {
      window.$cookies.remove("auth-token");
    },
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
              if(res1.data.message == "NOAGREE"){
                Swal.fire({
                  icon: 'warning',
                  title: '선택항목에 모두 동의해 주세요.',
                  showConfirmButton: true,
                  timer: 1500
                })
               
                Kakao.API.request({
                  url: '/v1/user/unlink',
                  success: function(response) {
                    return;
                  },
                  fail: function(error) {
                  },
                });
              }
              else if (res1.data.message == "SUCCESS") {
                let user = res1.data.member;
                this.loginToast = true;
                window.$cookies.set("auth-token", res1.data.member);
                Swal.fire({
                  icon: 'success',
                  title: '로그인 되었습니다.',
                  showConfirmButton: false,
                  timer: 1500
                })
                window.location.href = "/main";
              } else if(res1.data.message == "JOIN") {
                Swal.fire({
                  icon: 'warning',
                  title: '가입되지 않은 계정입니다.',
                  showConfirmButton: false,
                  timer: 1500
                });
              } else if(res1.data.message == "GO LOGIN") {
                Swal.fire({
                  icon: 'warning',
                  title: '로그인을 해주세요.',
                  showConfirmButton: false,
                  timer: 1500
                })
              } else if(res1.data.message == "JOIN SUCCESS") {
                Swal.fire({
                  icon: 'success',
                  title: '회원가입 성공',
                  text: '로그인을 해주세요',
                  showConfirmButton: false,
                  timer: 1500
                })
              }
            })
            .catch((err) => {
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
.kakao{
  margin-top: 0.4rem;
}
.kakaojoin{
  border-radius: 6px;
}
</style>