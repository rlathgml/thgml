<template>
  <component :is="layout" class="main">
    <router-view />
  </component>
</template>

<script>
import http from './utils/axios-common.js';

export default {
  data() {
    return {
      message: '',
      isLoggedIn: false,  
      testData: 20   
    };
  },
  methods: {
  },
  computed: {
    layout() {
      return `${this.$route.meta.layout || 'default'}-layout`;
    },
    
    
  },
};
</script>
<style>
  @font-face { 
    font-family: 'BMDOHYEON'; 
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/BMDOHYEON.woff') format('woff');
    font-weight: normal; 
    font-style: normal;
  }
</style>