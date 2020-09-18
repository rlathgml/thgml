<template>
  <div>
    삭제중...
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Delete',
  mounted() {
    const params = new URL(document.location).searchParams;

    axios
      .delete(`http://localhost:9999/happyhouse/api/qna/remove/${params.get('qnaNo')}`)
      .then(() => {
        alert('삭제가 완료되었습니다.');
      })
      .catch(() => {
        alert('삭제 처리시 에러가 발생했습니다.');
      })
      .finally(() => {
        this.$router.push('/qna/list');
      });
  },
};
</script>

<style></style>
