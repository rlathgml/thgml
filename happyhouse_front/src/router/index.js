import Vue from "vue";
import VueRouter from "vue-router";
import store from "../store/index.js";

import Home from "@/components/views/Home.vue";

import QnAIndex from "@/components/qna/Index.vue";
import List from "@/components/qna/List.vue";
import Create from "@/components/qna/Create.vue";
import Update from "@/components/qna/Update.vue";
import Delete from "@/components/qna/Delete.vue";
import Read from "@/components/qna/Read.vue";
import Map from "@/components/Map.vue";

import AptIndex from "@/components/apt/Index.vue";
import AptList from "@/components/apt/List.vue";
import AptDetail from "@/components/apt/Detail.vue";

import NoticeIndex from "@/components/notice/Index.vue";
import NoticeList from "@/components/notice/List.vue";
import NoticeCreate from "@/components/notice/Create.vue";
import NoticeDetail from "@/components/notice/Detail.vue";
import NoticeUpdate from "@/components/notice/Update.vue";
import NoticeDelete from "@/components/notice/Delete.vue";

import InfoIndex from "@/components/around/Index.vue";
import ShopList from "@/components/around/ShopList.vue";

import UserIndex from "@/components/user/Index.vue";
import Login from "@/components/user/Login.vue";
import Join from "@/components/user/UserJoin.vue";
Vue.use(VueRouter); // 전역에서 사용할 수 있게 설정, $router 처럼

const requireAuth = () => (to, from, next) => {
  if (store.state.userStore.accessToken) {
    return next();
  }
  next("/user/login");
};

export default new VueRouter({
  mode: "history",
  routes: [
    {
      path: "/",
      name: "index",
      redirect: "/user/login"
    },
    {
      path: "/home",
      name: "home",
      component: Home,
      beforeEnter: requireAuth()
    },
    {
      path: "/user",
      name: "userindex",
      component: UserIndex,
      children: [
        {
          path: "login",
          name: "userlogin",
          component: Login
        },
        {
          path: "userjoin",
          name: "userjoin",
          component: Join
        }
      ]
    },
    {
      path: "/qna",
      name: "qnaindex",
      component: QnAIndex,
      children: [
        {
          path: "list",
          name: "qnalist",
          component: List
        },
        {
          path: "create",
          name: "qnacreate",
          component: Create,
          beforeEnter: requireAuth()
        },
        {
          path: "delete",
          name: "qnadelete",
          component: Delete,
          beforeEnter: requireAuth()
        },
        {
          path: "update",
          name: "qnaupdate",
          component: Update,
          beforeEnter: requireAuth()
        },
        {
          path: "read",
          name: "qnaread",
          component: Read,
          beforeEnter: requireAuth()
        }
      ]
    },

    {
      path: "/apt",
      name: "aptindex",
      component: AptIndex,
      children: [
        {
          path: "list",
          name: "aptlist",
          component: AptList
        },
        {
          path: "detail",
          name: "aptdetail",
          component: AptDetail,
          beforeEnter: requireAuth()
        }
      ]
    },

    {
      path: "/notice",
      name: "noticeindex",
      component: NoticeIndex,
      children: [
        {
          path: "list",
          name: "noticelist",
          component: NoticeList
        },
        {
          path: "create",
          name: "noticecreate",
          component: NoticeCreate,
          beforeEnter: requireAuth()
        },
        {
          path: "detail",
          name: "noticedetail",
          component: NoticeDetail,
          beforeEnter: requireAuth()
        },
        {
          path: "update",
          name: "noticeupdate",
          component: NoticeUpdate,
          beforeEnter: requireAuth()
        },
        {
          path: "delete",
          name: "noticedelete",
          component: NoticeDelete,
          beforeEnter: requireAuth()
        }
      ]
    },
    {
      path: "/info",
      name: "infoindex",
      component: InfoIndex,
      children: [
        {
          path: "shoplist",
          name: "shoplist",
          component: ShopList
        },
        {
          path: "map",
          name: "map",
          component: Map
        }
      ]
    }
  ]
});
