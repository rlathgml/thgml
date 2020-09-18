<template>
  <div class="container">
    <div v-if="error">
      <pre>{{ error }}</pre>
    </div>
    <h1>공지사항 글쓰기</h1>
    <div class="form-group">
      <label for="title">제목</label>
      <input
        type="text"
        class="form-control"
        id="title"
        ref="title"
        placeholder="제목을 입력하세요"
        v-model="title"
      />
    </div>
    <div class="form-group">
      <label for="content">내용</label>
      <textarea
        type="text"
        class="form-control"
        id="contnet"
        ref="content"
        placeholder="내용을 입력하세요"
        v-model="content"
      ></textarea>
      <button class="btn btn-primary" @click="checkHandler">등록하기</button>
      <button type="reset" class="btn btn-primary">취소</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Create',
  data() {
    return {
      no: '',
      writer: 'admin',
      title: '',
      content: '',
      error: '',
    };
  },
  methods: {
    checkHandler() {
      let err = true;
      let msg = '';
      !this.title && ((msg = '제목을 입력해주세요'), (err = false));
      err && !this.content && ((msg = '내용을 입력해주세요'), (err = false));

      if (!err) alert(msg);
      else {
        this.createHandler();
      }
    },
    createHandler() {
      axios
        .post('http://localhost:9999/happyhouse/api/notice/add', {
          writer: this.writer,
          title: this.title,
          content: this.content,
        })
        .then(() => {
          alert('등록 완료');
          this.moveList();
        })
        .catch((error) => {
          this.error = error;
        })
        .finally(() => {});
    },
    // 목록 페이지로 이동하는 moveList 메서드 정의
    moveList() {
      this.$router.push('/notice/list');
    },
  },
};
</script>

<style></style>
