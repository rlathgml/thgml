<template>
  <div>
    <b-nav tabs fill justified>
      <b-nav-item to="/home" exact exact-active-class="active">HOME</b-nav-item>
      <b-nav-item to="/apt/list" exact exact-active-class="active">아파트</b-nav-item>
      <b-nav-item to="/info/shoplist" exact exact-active-class="active">주변 정보</b-nav-item>
      <b-nav-item to="/notice/list" exact exact-active-class="active">공지사항</b-nav-item>
      <b-nav-item to="/qna/list" exact exact-active-class="active">QnA</b-nav-item>
    </b-nav>
  </div>
</template>

<script>
export default {
  name: "NavHeader"
};
</script>

<style>
</style>
