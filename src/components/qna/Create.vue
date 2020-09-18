<template>
  <div class="container">
    <div v-if="error">
      <pre>{{ error }}</pre>
    </div>
    <div class="form-group">
      <label for="writer">작성자</label>
      <!-- data 속성과 연결 -->
      <input
        type="text"
        class="form-control"
        id="writer"
        ref="writer"
        placeholder="작성자를 입력하세요"
        v-model="writer"
      />
    </div>
    <div class="form-group">
      <label for="title">제목</label>

      <!-- data 속성과 연결 -->
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

      <!-- data 속성과 연결 -->
      <textarea
        type="text"
        class="form-control"
        id="contnet"
        ref="content"
        placeholder="내용을 입력하세요"
        v-model="content"
      ></textarea>
    </div>
    <div class="text-right">
      <button class="btn btn-primary" @click="checkHandler">등록</button>
      <button class="btn btn-primary" @click="moveList">목록</button>
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
      writer: '',
      title: '',
      content: '',
      error: '',
    };
  },
  methods: {
    checkHandler() {
      let err = true;
      let msg = '';
      /* 자바스크립트 특징 - if-else 뿐만 아니라 논리연산자 형식으로도 할수있음*/
      !this.writer && ((msg = '작성자를 입력해주세요'), (err = false));
      err && !this.title && ((msg = '제목을 입력해주세요'), (err = false));
      err && !this.content && ((msg = '내용을 입력해주세요'), (err = false));

      if (!err) alert(msg);
      else {
        this.createHandler();
      }
    },

    // createHandler 메서드 정의
    createHandler() {
      axios
        .post('http://localhost:9999/happyhouse/api/qna/add', {
          qnaUserid: this.writer,
          qnaTitle: this.title,
          qnaContent: this.content,
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
      this.$router.push('/qna/list');
    },
  },
};
</script>

<style></style>
