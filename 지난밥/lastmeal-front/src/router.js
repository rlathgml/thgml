import Vue from 'vue';
import Router from 'vue-router';

import PersonalBlog from './views/PersonalBlog.vue';
import UserProfileLite from './views/UserProfileLite.vue';
import AddNewPost from './views/AddNewPost.vue';
import Errors from './views/Errors.vue';
import ComponentsOverview from './views/ComponentsOverview.vue';
import Tables from './views/Tables.vue';
import BlogPosts from './views/BlogPosts.vue';

import SingleDatePicker from 'vue-single-date-picker';
import CalendarView from './views/CalendarView.vue';
import Detail from './views/Detail.vue';
import Analysis from './views/ImageAnalysis.vue';
import Login from './components/layout/Login.vue';
import PureVueChart from 'pure-vue-chart';
import WeekReport from './views/WeekReportView.vue';
import BookSearch from './views/BookSearch.vue';

// @import '~@jiayingy/vue-single-date-picker/dist/vue-single-date-picker.css';
// @import '~vue-single-date-picker/dist/vue-single-date-picker.css';

Vue.use(Router);

export default new Router({
  mode: 'history',
  linkActiveClass: 'active',
  linkExactActiveClass: 'exact-active',
  scrollBehavior() {
    return { x: 0, y: 0 };
  },
  data(){
    return {
      isLoggedIn: this.$cookies.isKey("auth-token"),
    }
  },
  components: {
    SingleDatePicker,
    PureVueChart,
  },
  routes: [
    {
      path: '/',
      redirect: '/main',
      beforeEnter(to, from, next) {
        if (Vue.$cookies.isKey("auth-token")) {
          next("/main");
        } else {
          next("/login");
        }
      },
    },
    {
      path: '/logout',      
      redirect: '/login',
    },
    {
      path: '/main',
      name: 'main',
      component: CalendarView,
      beforeEnter(to, from, next) {
        if (Vue.$cookies.isKey("auth-token")) {
          next();
        } else {
          Swal.fire({
            icon: 'warning',
            title: '로그인을 해주세요',
            showConfirmButton: false,
            timer: 1500
          })
          next("/login");
        }
      },
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
    },
    {
      path: '/detail/:date',
      name: 'detail',
      component: Detail,
      beforeEnter(to, from, next) {
        if (Vue.$cookies.isKey("auth-token")) {
          next();
        } else {
          Swal.fire({
            icon: 'warning',
            title: '로그인을 해주세요',
            showConfirmButton: false,
            timer: 1500
          })
          next("/login");
        }
      },
    },
    {
      path: '/analysis',
      name: 'analysis',
      component: Analysis,
      props: true,
      beforeEnter(to, from, next) {
        if (Vue.$cookies.isKey("auth-token")) {
          next();
        } else {
          Swal.fire({
            icon: 'warning',
            title: '로그인을 해주세요',
            showConfirmButton: false,
            timer: 1500
          })
          next("/login");
        }
      },
    },
    {
      path: '/blog-overview',
      name: 'blog-overview',
      component: PersonalBlog,
      beforeEnter(to, from, next) {
        if (Vue.$cookies.isKey("auth-token")) {
          next();
        } else {
          Swal.fire({
            icon: 'warning',
            title: '로그인을 해주세요',
            showConfirmButton: false,
            timer: 1500
          })
          next("/login");
        }
      },
    },
    {
      path: '/user-profile-lite',
      name: 'user-profile-lite',
      component: UserProfileLite,
      beforeEnter(to, from, next) {
        if (Vue.$cookies.isKey("auth-token")) {
          next();
        } else {
          Swal.fire({
            icon: 'warning',
            title: '로그인을 해주세요',
            showConfirmButton: false,
            timer: 1500
          })
          next("/login");
        }
      },
    },
    {
      path: '/add-new-post',
      name: 'add-new-post',
      component: AddNewPost,
      beforeEnter(to, from, next) {
        if (Vue.$cookies.isKey("auth-token")) {
          next();
        } else {
          Swal.fire({
            icon: 'warning',
            title: '로그인을 해주세요',
            showConfirmButton: false,
            timer: 1500
          })
          next("/login");
        }
      },
    },
    {
      path: '/errors',
      name: 'errors',
      component: Errors,
    },
    {
      path: '/components-overview',
      name: 'components-overview',
      component: ComponentsOverview,
      beforeEnter(to, from, next) {
        if (Vue.$cookies.isKey("auth-token")) {
          next();
        } else {
          Swal.fire({
            icon: 'warning',
            title: '로그인을 해주세요',
            showConfirmButton: false,
            timer: 1500
          })
          next("/login");
        }
      },
    },
    {
      path: '/tables',
      name: 'tables',
      component: Tables,
      beforeEnter(to, from, next) {
        if (Vue.$cookies.isKey("auth-token")) {
          next();
        } else {
          Swal.fire({
            icon: 'warning',
            title: '로그인을 해주세요',
            showConfirmButton: false,
            timer: 1500
          })
          next("/login");
        }
      },
    },
    {
      path: '/blog-posts',
      name: 'blog-posts',
      component: BlogPosts,
      beforeEnter(to, from, next) {
        if (Vue.$cookies.isKey("auth-token")) {
          next();
        } else {
          Swal.fire({
            icon: 'warning',
            title: '로그인을 해주세요',
            showConfirmButton: false,
            timer: 1500
          })
          next("/login");
        }
      },
    }, {
      path: '/book',
      name: 'BookSearch',
      component: BookSearch,
      beforeEnter(to, from, next) {
        if (Vue.$cookies.isKey("auth-token")) {
          next();
        } else {
          Swal.fire({
            icon: 'warning',
            title: '로그인을 해주세요',
            showConfirmButton: false,
            timer: 1500
          })
          next("/login");
        }
      },
    }, {
      path: '/report',
      name: 'WeekReport',
      component: WeekReport,
      props: true,
      beforeEnter(to, from, next) {
        if (Vue.$cookies.isKey("auth-token")) {
          next();
        } else {
          Swal.fire({
            icon: 'warning',
            title: '로그인을 해주세요',
            showConfirmButton: false,
            timer: 1500
          })
          next("/login");
        }
      },
    }, {
      path: '*',
      redirect: '/errors',
    },
  ],
});

