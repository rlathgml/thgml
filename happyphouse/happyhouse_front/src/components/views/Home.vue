<template>
  <div class="home">
    <br />
    <h2 class="text-center">HappyHouse</h2>
    <div class="text-right">
      <!-- 버튼 클릭시 movePage 메서드 호출 -->
      <button class="btn btn-primary" @click="movePage">로그아웃</button>
    </div>
    <br />
    <hr />
    <div class="container">
      <h2 class="text-center">{{ getUserId }}님의 관심 주택 입니다.</h2>
      <br />
      <br />
      <div v-if="items.length">
        <table class="table">
          <thead class="thead-dark">
            <tr>
              <th>번호</th>
              <th>동</th>
              <th>아파트 이름</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(housedeal, index) in items" :key="index + '_items'">
              <td>
                <router-link :to="'/apt/detail?no=' + housedeal.no">{{
                  housedeal.no
                }}</router-link>
              </td>
              <td>{{ housedeal.dong }}</td>
              <td>{{ housedeal.aptName }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <br />
      <hr />
      <br />
      <h2 class="text-center">가장 관심을 많이 받은 주택 입니다.</h2>
      <div v-if="interests.length">
        <br />
        <br />
        <table class="table">
          <thead class="thead-dark">
            <tr>
              <th>번호</th>
              <th>동</th>
              <th>아파트 이름</th>
              <th>추천수</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(interest, index) in interests" :key="index + '_inters'">
              <td>
                <router-link :to="'/apt/detail?no=' + interest.no">{{
                  interest.no
                }}</router-link>
              </td>
              <td>{{ interest.dong }}</td>
              <td>{{ interest.aptName }}</td>
              <td>{{ interest.count }}</td>
            </tr>
          </tbody>
        </table>
        <br />
        <br />
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import axios from "axios";

const userStore = "userStore";

export default {
  name: "Home",
  data() {
    return {
      items: [],
      interests: []
    };
  },
  computed: {
    ...mapGetters(userStore, ["getUserId"])
  },
  created() {
    axios
      .get("http://localhost:9999/happyhouse/api/interest/top")
      .then(res => {
        this.interests = res.data;
      })
      .catch(error => {
        console.dir(error);
      });
    if (this.getUserId) {
      axios
        .get(`http://localhost:9999/happyhouse/api/interest/${this.getUserId}`)
        .then(res => {
          this.items = res.data;
        });
    }
  },
  methods: {
    ...mapActions(userStore, ["LOGOUT"]),
    movePage() {
      this.LOGOUT().then(() => {
        this.$router.push("/");
      });
    }
  }
};
</script>

<style></style>
