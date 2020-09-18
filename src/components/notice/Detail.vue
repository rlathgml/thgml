<template>
  <div class="container">
    <div v-if="error">
      <pre>{{ error }}</pre>
    </div>
    <h1>공지사항</h1>
    <table class="table">
      <tr>
        <th>글번호</th>
        <td>{{ item.no }}</td>
      </tr>
      <tr>
        <th>글제목</th>
        <td>{{ item.title }}</td>
      </tr>
      <tr>
        <th>본문</th>
        <td>{{ item.content }}</td>
      </tr>
      <tr>
        <th>날짜</th>
        <td>{{ getFormatDate(item.regDate) }}</td>
      </tr>
    </table>

    <div>
      <router-link to="/notice/list" class="btn btn-primary">공지사항 목록</router-link>
      <!-- <c:if test="${userinfo.userId == 'admin' }"> -->
      <router-link :to="'/notice/delete?no=' + item.no" class="btn btn-primary"
        >글 삭제</router-link
      >
      <router-link :to="'/notice/update?no=' + item.no" class="btn btn-primary"
        >글 수정</router-link
      >
      <!-- </c:if> -->
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import moment from 'moment';

export default {
  name: 'Detail',
  data() {
    return {
      item: {},
      error: '',
    };
  },
  created() {
    const params = new URL(document.location).searchParams;

    axios
      .get(`http://localhost:9999/happyhouse/api/notice/detail/${params.get('no')}`)
      .then(({ data }) => {
        this.item = data;
      })
      .catch((error) => {
        this.error = error;
      });
  },
  methods: {
    getFormatDate(regtime) {
      return moment(new Date(regtime)).format('YYYY.MM.DD HH:mm:ss');
    },
  },
};
</script>

<style></style>
