<template>
  <div v-if="this.$cookies.get('auth-token') != null" :class="['main-navbar', 'bg-white', stickyTop ? 'sticky-top' : '']">
    <d-navbar type="light" class="align-items-stretch flex-md-nowrap p-0">
      <router-link to="/">
        <div class="d-table m-auto" style="position: static !important; color: #ffffff">
          <img id="main-logo" class="d-inline-block align-top mr-1" src="@/assets/images/favicon3.png" alt="지난밥">
            <font class="logo">지난밥</font>
        </div>
      </router-link>
      <router-link to="/book">
        <img id="search-book" style="position: relative; top: 15px; right: 100px; width: 30px; height: 30px;" src="@/assets/images/nav/book4.png" alt="검색사전">
      </router-link>
      <div style="position: absolute; top: 0; right: 0;">
        <navbar-nav />
        <navbar-toggle />
      </div>
    </d-navbar>
  </div>
</template>

<script>
import NavbarNav from './NavbarNav.vue';
import NavbarToggle from './NavbarToggle.vue';

export default {
  props: {
    stickyTop: {
      type: Boolean,
      default: true,
    },
  },
  components: {
    NavbarNav,
    NavbarToggle,
  },
  data() {
    return {
      window: {
        width: 0,
        height: 0,
      },
      widthData: 0,
    }
  },
  created() {
    window.addEventListener('resize', this.handleResize);
    this.handleResize();
  },
  watch: {
    destroyed() {
      window.removeEventListener('resize', this.handleResize);
    },
  },
  computed: {
    sendWidth() {
      return this.widthData
    }
  },
  methods: {
    // 현재 보이는 창 화면 계산
    handleResize() {
      this.window.width = window.innerWidth;
      this.window.height = window.innerHeight;
      this.widthCal();
    },
    // 넓이 계산해서 style --widthSize 안에 넣어주기
    widthCal() {
      const suffix = 'px';
      if (this.window.width > 992) {
        this.widthData = Number(this.window.width / 3);
      } else if (this.window.width > 768) {
        this.widthData = Number(this.window.width / 2);
      } else {
        this.widthData = Number(this.window.width);
      }
      document.documentElement.style.setProperty('--widthSize', this.widthData + suffix);
    },
  }
};
</script>

<style lang="scss">
// 가상 선택자, 우선 순위가 제일 높음
:root {
    --widthSize: 400px;
}
  .main-navbar {
    position: fixed;
    width: var(--widthSize);
    .dropdown-menu {
      display: block;
    }
  }

  .logo{
    color: rgb(255,192,0);
    font-family: 'BMDOHYEON';
  }
  #main-logo{
    margin: 0.25rem;
  }

</style>
