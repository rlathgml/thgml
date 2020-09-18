import axios from "axios";
const path = "http://localhost:9999/happyhouse/api/";
const userStore = {
  namespaced: true,
  state: {
    accessToken: null,
    userId: ""
  },
  getters: {
    getToken(state) {
      return state.accessToken;
    },
    getUserId(state) {
      return state.userId;
    }
  },
  mutations: {
    LOGIN(state, payload) {
      state.accessToken = payload;
      state.userId = payload.user.userId;
    },
    LOGOUT(state) {
      state.accessToken = null;
    }
  },
  actions: {
    LOGIN(context, payload) {
      return axios.post(`${path}/user/signin`, payload).then(res => {
        context.commit("LOGIN", res.data);
        axios.defaults.headers.common["Authorization"] = res.data.accessToken;
      });
    },
    LOGOUT(context) {
      axios.defaults.headers.common["Authorization"] = undefined;
      context.commit("LOGOUT");
    }
  }
};

export default userStore;
