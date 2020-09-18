<template>
  <div class="container">
    <div v-if="error">
      <pre>{{ error }}</pre>
    </div>
    <table class="table table-bordered  ">
      <tr>
        <th>번호</th>
        <td>{{ item.qnaNo }}</td>
      </tr>
      <tr>
        <th>글쓴이</th>
        <td>{{ item.qnaUserid }}</td>
      </tr>
      <tr>
        <th>제목</th>
        <td>{{ item.qnaTitle }}</td>
      </tr>
      <tr>
        <th>날짜</th>
        <td>{{ getFormatDate(item.qnaDatetime) }}</td>
      </tr>
      <tr>
        <td colspan="2">
          {{ item.qnaContent }}
        </td>
      </tr>
    </table>

    <br />
    <div class="text-center">
      <router-link to="/qna/list" class="btn btn-primary">목록</router-link>
      <router-link :to="'/qna/delete?qnaNo=' + item.qnaNo" class="btn btn-primary"
        >삭제</router-link
      >
      <router-link :to="'/qna/update?qnaNo=' + item.qnaNo" class="btn btn-primary"
        >수정</router-link
      >
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import moment from 'moment';

export default {
  name: 'Read',
  data() {
    return {
      item: {},
      error: '',
    };
  },
  created() {
    const params = new URL(document.location).searchParams;

    axios
      .get(`http://localhost:9999/happyhouse/api/qna/${params.get('qnaNo')}`)
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
