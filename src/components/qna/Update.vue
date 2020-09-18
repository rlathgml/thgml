<template>
  <div class="container">
    <div v-if="error">
      <pre>{{ error }}</pre>
    </div>
    <div class="form-group">
      <label for="writer">작성자</label>
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
        .put(`http://localhost:9999/happyhouse/api/qna/modify`, {
          qnaNo: this.no,
          qnaUserid: this.writer,
          qnaTitle: this.title,
          qnaContent: this.content,
        })
        .then((response) => {
          if (response.data === 'success') alert('수정 완료');
          else alert('수정 처리 시 문제가 발생했습니다.');
        })
        .catch((error) => {
          this.error = error;
        })
        .finally(() => {
          this.$router.push('/qna/list');
        });
    },
  },
  created() {
    const params = new URL(document.location).searchParams;

    axios
      .get(`http://localhost:9999/happyhouse/api/qna/${params.get('qnaNo')}`)
      .then(({ data }) => {
        this.no = data.qnaNo;
        this.regtime = data.qnaDatetime;
        this.writer = data.qnaUserid;
        this.title = data.qnaTitle;
        this.content = data.qnaContent;
      })
      .catch((error) => {
        this.error = error;
      });
  },
};
</script>

<style></style>
