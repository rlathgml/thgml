<template>
  <div class="container">
    <h1>공지사항 수정</h1>
    <div v-if="error">
      <pre>{{ error }}</pre>
    </div>
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
    </div>
    <div class="text-right">
      <button class="btn btn-primary" @click="checkHandler">수정</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Update',
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
      !this.writer && ((msg = '작성자를 입력해주세요'), (err = false), this.$refs.writer.focus());
      err && !this.title && ((msg = '제목 입력해주세요'), (err = false), this.$refs.title.focus());
      err &&
        !this.content &&
        ((msg = '내용 입력해주세요'), (err = false), this.$refs.content.focus());

      if (!err) alert(msg);
      else this.updateHandler();
    },
    updateHandler() {
      axios
        .put(`http://localhost:9999/happyhouse/api/notice/modify`, {
          no: this.no,
          title: this.title,
          content: this.content,
        })
        .then((response) => {
          if (response.data === 'success') alert('수정 완료');
          else alert('수정 처리 시 문제가 발생했습니다.');
        })
        .catch((error) => {
          this.error = error;
        })
        .finally(() => {
          this.$router.push('/notice/list');
        });
    },
  },
  created() {
    const params = new URL(document.location).searchParams;

    axios
      .get(`http://localhost:9999/happyhouse/api/notice/detail/${params.get('no')}`)
      .then(({ data }) => {
        this.no = data.qnaNo;
        this.regtime = data.regDate;
        this.writer = data.writer;
        this.title = data.title;
        this.content = data.content;
      })
      .catch((error) => {
        this.error = error;
      });
  },
};
</script>

<style></style>
