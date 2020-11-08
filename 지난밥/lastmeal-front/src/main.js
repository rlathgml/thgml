/* eslint-disable */
import Vue from 'vue';
import ShardsVue from 'shards-vue';
import VueAgile from 'vue-agile'
import VueSplash from 'vue-splash';
import VueCookies from "vue-cookies";

// Styles
import 'bootstrap/dist/css/bootstrap.css';
import '@/scss/shards-dashboards.scss';
import '@/scss/pretty-checkbox.scss';
import '@/assets/scss/date-range.scss';

// Core
import App from './App.vue';
import router from './router';

// Layouts
import Default from '@/layouts/Default.vue';

ShardsVue.install(Vue);

Vue.component('default-layout', Default);

Vue.use(VueAgile)
Vue.use(VueSplash);
Vue.use(VueCookies);

Vue.config.productionTip = false;
Vue.prototype.$eventHub = new Vue();

new Vue({
  router,
  render: h => h(App),
}).$mount('#app');

// window.Kakao.init("59d178a04a9b208a38b21d5eadbf4d31"); 