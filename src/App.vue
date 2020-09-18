<template>
  <div>
    <nav-header class="nav"></nav-header>
    <!-- <h2 class="text-center">Vue를 이용한 QnA 게시판</h2> -->
    <router-view></router-view>
  </div>
</template>

<script>
import NavHeader from "@/components/NavHeader.vue";
// @->src 가르킴

export default {
  name: "App",
  components: {
    NavHeader
  }
};
</script>

<style>
.nav {
  width: 100%;
} /* 
.content {
  float: left;
} */
</style>
