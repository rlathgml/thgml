<template>
  <div class="container">
    <div v-if="error">
      <pre>{{ error }}</pre>
    </div>
    <h2>공지사항</h2>
    <input type="text" name="word" v-model="keyword" />
    <button>검색</button>
    <div v-if="items.length">
      <table class="table">
        <thead class="thead-dark">
          <tr>
            <th>글번호</th>
            <th>글제목</th>
            <th>작성자</th>
            <th>작성일</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(notice, index) in items" :key="index + '_items'">
            <td>{{ notice.no }}</td>
            <td>
              <router-link :to="'/notice/detail?no=' + notice.no">{{
                notice.title
              }}</router-link>
            </td>
            <td>{{ notice.writer }}</td>
            <td>{{ notice.reg_date }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <!-- <c:if test="${userinfo.userId == 'admin' }"> -->
    <div>
      <router-link to="/notice/create">공지사항 등록</router-link>
    </div>
    <!-- </c:if> -->
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "List",
  data() {
    return {
      items: [],
      keyword: "",
      error: ""
    };
  },
  created() {
    // const Token = localStorage.getItem('Token');
    // console.log(Token);
    axios
      .get("http://localhost:9999/happyhouse/api/notice/all")
      .then(response => {
        this.items = response.data;
      })
      .catch(error => {
        console.log(error);
      });
  }
};
</script>

<style></style>
