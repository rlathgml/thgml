<template>
  <div class="container">
    <h2>회원가입</h2>
    <form method="POST" id="userJoinForm">
      <div>
        <label>아이디</label>
        <input type="text" name="userId" class="form-control" id="userId" v-model="id" ref="id" />
        <button type="button" id="dupliBtn" @click="dupli">아이디 중복 확인</button>
      </div>
      <div>
        <label>비밀번호</label>
        <input type="password" name="password" class="form-control" id="password" v-model="pw" />
      </div>
      <div>
        <label>이름</label>
        <input type="text" name="userName" class="form-control" id="userName" v-model="name" />
      </div>
      <div>
        <label>주소</label>
        <input type="text" name="address" class="form-control" id="address" v-model="address" />
      </div>
      <div>
        <label>전화번호</label>
        <input
          type="text"
          name="phoneNumber"
          class="form-control"
          id="phoneNumber"
          v-model="phone"
        />
      </div>
      <div class="btnDiv" align="center">
        <button class="btn btn-primary" type="button" @click="userJoin">회원가입</button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'UserJoin',
  data() {
    return {
      id: '',
      pw: '',
      name: '',
      address: '',
      phone: '',
      checkFlag: false,
    };
  },
  methods: {
    dupli() {
      let userId = this.id;
      if (userId == '' || userId == null) {
        alert('아이디를 입력해주세요.');
      } else {
        axios
          .get(`http://localhost:9999/happyhouse/api/user/check/${this.id}`)
          .then(() => {
            alert('사용할 수 있는 아이디 입니다.');
            this.checkFlag = true;
          })
          .catch(() => {
            alert('아이디가 중복 입니다.');
            this.checkFlag = false;
            this.$refs.id.focus();
          })
          .finally(() => {
            console.log(this.checkFlag);
          });
      }
    },
    userJoin() {
      if (this.checkFlag == false) {
        alert('아이디 중복 확인을 하세요.');
        return;
      }

      if (document.getElementById('userName').value == '') {
        alert('이름을 입력하세요.');
        return;
      } else if (document.getElementById('userId').value == '') {
        alert('아이디를 입력하세요.');
        return;
      } else if (document.getElementById('password').value == '') {
        alert('비밀번호를 입력하세요');
        return;
      } else if (document.getElementById('address').value == '') {
        alert('주소를 입력하세요');
        return;
      } else if (document.getElementById('phoneNumber').value == '') {
        alert('전화번호를 입력하세요');
        return;
      } else {
        axios
          .post('http://localhost:9999/happyhouse/api/user/signup', {
            userId: this.id,
            password: this.pw,
            userName: this.name,
            address: this.address,
            phoneNumber: this.phone,
          })
          .then(() => {
            alert('회원가입을 축하합니다.');
            this.$router.push('/home');
          })
          .catch((error) => {
            console.dir(error);
            alert('회원가입 중 에러가 발생하였습니다.');
          });
      }
    },
  },
};
</script>

<style></style>
