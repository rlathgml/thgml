<template>
  <div class="container">
    <h1>QnA</h1>
    <div v-if="items.length">
      <table class="table table-bordered table-condensed">
        <colgroup>
          <!-- 아래의 코드를 이해한다. -->
          <col :style="{ width: '10%' }" />
          <col :style="{ width: '50%' }" />
          <col :style="{ width: '15%' }" />
          <col :style="{ width: '25%' }" />
        </colgroup>
        <tr>
          <th>번호</th>
          <th>제목</th>
          <th>작성자</th>
          <th>날짜</th>
        </tr>
        <tr v-for="(qna, index) in items" :key="index + '_items'">
          <td>{{ qna.qnaNo }}</td>
          <td>
            <router-link :to="'/qna/read?qnaNo=' + qna.qnaNo">{{
              qna.qnaTitle
            }}</router-link>
          </td>
          <td>{{ qna.qnaUserid }}</td>
          <td>{{ getFormatDate(qna.qnaDatetime) }}</td>
        </tr>
      </table>
    </div>
    <!-- 게시글이 없을 경우 "게시글이 없습니다."를 화면에 보여준다.-->
    <div v-else class="text-center">게시글이 없습니다.</div>
    <div class="text-right">
      <!-- 버튼 클릭시 movePage 메서드 호출 -->
      <button class="btn btn-primary" @click="movePage">등록</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import moment from "moment";

export default {
  name: "List",
  data: function() {
    return {
      items: [],
      error: ""
    };
  },
  created() {
    axios
      .get("http://localhost:9999/happyhouse/api/qna/all")
      .then(response => {
        this.items = response.data;
      })
      .catch(error => {
        console.log(error);
      });
  },
  methods: {
    getFormatDate(regtime) {
      return moment(new Date(regtime)).format("YYYY.MM.DD");
    },
    movePage() {
      this.$router.push("create");
      console.dir(this.$route);
    }
  }
};
</script>

<style></style>
