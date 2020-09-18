<template>
  <div class="container" align="center">
    <div class="col-lg-6" align="center">
      <form id="loginform" method="post" action>
        <input type="hidden" name="act" id="act" value="login" />
        <div class="form-group" align="left">
          <label for>아이디</label>
          <input
            type="text"
            class="form-control"
            id="userId"
            name="userId"
            placeholder
            value="${saveid}"
            v-model="id"
          />
        </div>
        <div class="form-group" align="left">
          <label for>비밀번호</label>
          <input
            type="password"
            class="form-control"
            id="password"
            name="password"
            placeholder
            onkeydown="javascript:if(event.keyCode == 13) {login();}"
            v-model="pw"
          />
        </div>
        <div class="form-group form-check" align="right">
          <label class="form-check-label">
            <input
              class="form-check-input"
              type="checkbox"
              id="idsave"
              name="idsave"
              value="saveok"
            />
            아이디저장
          </label>
        </div>
        <div class="form-group" align="center">
          <button type="button" class="btn btn-warning" @click="login">
            로그인
          </button>
          <button type="button" class="btn btn-primary" @click="moveJoin">
            회원가입
          </button>
          <button type="button" class="btn btn-primary" @click="moveFindPwd">
            비밀번호찾기
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
//import axios from 'axios';

const userStore = "userStore";

export default {
  name: "Login",
  data() {
    return {
      id: "",
      pw: "",
      idck: ""
    };
  },
  methods: {
    ...mapActions(userStore, ["LOGIN"]),
    login() {
      if (document.getElementById("userId").value == "") {
        alert("아이디를 입력하세요.");
        return;
      } else if (document.getElementById("password").value == "") {
        alert("비밀번호를 입력하세요.");
        return;
      } else {
        const payload = {
          userId: this.id,
          password: this.pw
        };

        this.LOGIN(payload).then(() => {
          alert("Welcome!!!");
          this.$router.push("/home");
        });
      }
    },
    moveJoin() {
      this.$router.push("/user/userjoin");
    },
    moveFindPwd() {
      this.$router.push("/user/findPwd");
    }
  }
};
</script>

<style></style>
