<template>
  <d-card class="card-small mb-4 pt-3">

    <!-- Card Header -->
    <d-card-header class="border-bottom text-center">

      <!-- User Avatar -->
      <div class="mb-3 mx-auto">
        <!-- <img class="rounded-circle" :src="user.profilepath" width="110"> -->
        <div class="photo" :style="{backgroundImage: 'url(' + user.profilepath + ')'}"></div>
      </div>

      <!-- User Name -->
      <h4 class="mb-0">{{ this.user.nickname }}</h4>

      <!-- User Job Title -->
      <span class="text-muted d-block mb-2">{{ user.age }}세({{ user.gender }})</span>
      <span class="text-muted d-block mb-2">{{ user.height }}cm {{ user.weight }}kg</span>
    </d-card-header>

    <d-list-group flush>

      <!-- User Performance Report -->
      <d-list-group-item class="px-4">
        <div class="progress-wrapper">
          <strong class="text-muted d-block mb-2">권장 칼로리</strong>
          <d-progress class="progress-sm">
            <span class="progress-value">{{ maxCal }} Cal</span>
            <d-progress-bar :max="maxCal" :value="Value" />
          </d-progress>
          <div class="form-group" style="margin: 10px auto;">
            <d-button class="btn-accent" type="submit" style="float: left" @click="showModal=true">회원 정보 수정</d-button>
              <UserUpdate v-if="showModal" @showModal="setData"/>
            <d-button class="btn-danger" type="submit" style="float: right;" @click="deleteUser">회원 탈퇴</d-button>
          </div>
        </div>
      </d-list-group-item>
    </d-list-group>

  </d-card>
</template>
<script>
import Vue from 'vue';
import http from "@/utils/axios-common.js";
import UserUpdate from "@/components/user-profile-lite/UserUpdate.vue"

export default {
  name: 'user-details',
   data() {
    return {
      user: this.$cookies.get("auth-token"),
      showModal: false,
      maxCal: JSON.parse(window.localStorage.getItem("calorie"))[0],
      Value: null,
    }
  },
  components:{
    UserUpdate
  },
  created(){
    this.setValue();
  },
  methods: {
      deleteUser(){
        Swal.fire({
          title: '정말 탈퇴하시겠습니까?',
          text: "모든 개인 정보는 삭제된 후 복구할 수 없습니다.",
          icon: 'warning',
          showCancelButton: true,
          confirmButtonColor: '#d33',
          cancelButtonColor: '#3085d6',
          cancelButtonText: '아니요, 안할래요',
          confirmButtonText: '네, 탈퇴할래요!'
        }).then((result) => {
          if (result.isConfirmed) {
            // DB 삭제
            http 
              .delete(`accounts/memberdelete/${this.user.memberno}/`)
              .then((data) => {
                // 카카오 연결 끊기
                Kakao.API.request({
                  url: '/v1/user/unlink',
                  success: function(response) {
                    // 탈퇴 성공 알림
                    Swal.fire({
                      title: '탈퇴 완료!',
                      text: '회원님의 개인정보가 모두 삭제되었습니다.',
                      icon: 'success',
                      timer: 1500,
                    });
                    // 로그아웃
                    window.$cookies.remove("auth-token");
                    // 메인으로
                    window.location.href = "/";
                  },
                  fail: function(error) {
                  },
                });
              })
              .catch((err) => {
              })

          }
      })
    },
    setData(data){
      this.showModal = data;
      this.user = this.$cookies.get("auth-token");
    },
    setValue(){
      const date = new Date();
      const day = date.getDate();
      const dayday =
        date.getFullYear() +
        "-" +
        (date.getMonth() + 1 < 10
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1) +
        "-" +
        (day < 10 ? "0" + day : day);
      http
        .get("/menus/daily/" + this.user.memberno + "/" + dayday + "/")
        .then((res) => {
          let total = 0;
          for (var i = 0; i < res.data.length; i++) {
            total += res.data[i].cal;
          }
          this.Value = total;
        })
        .catch((error) => {
        });
    }
  },
};
</script>
<style scoped>
.photo{
  width : 200px;
  height : 200px;
  background-size: 100%;
  background-repeat: round;
  border-radius: 200px;
  background-repeat: no-repeat;
  background-position: center;
  margin: auto;
}

</style>