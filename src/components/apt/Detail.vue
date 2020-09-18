<template>
  <div class="container">
    <table class="table" border="1">
      <tr>
        <th colspan="2">상세 정보</th>
      </tr>
      <tr>
        <th rowspan="8">{{ housedeal.no }}</th>
        <td>{{ housedeal.aptName }}</td>
      </tr>
      <tr>
        <td>{{ housedeal.dealAmount }}</td>
      </tr>
      <tr>
        <td v-if="housedeal.rentMoney == null">없음</td>
        <td v-else>{{ housedeal.rentMoney }}</td>
      </tr>
      <tr>
        <td>{{ housedeal.buildYear }}년도</td>
      </tr>
      <tr>
        <td>{{ housedeal.area }}평</td>
      </tr>
      <tr>
        <td>{{ housedeal.dealYear }}년. {{ housedeal.dealMonth }}월. {{ housedeal.dealDay }}일</td>
      </tr>
      <tr>
        <td>{{ housedeal.dong }}</td>
      </tr>
      <tr>
        <td>{{ housedeal.jibun }}</td>
      </tr>
    </table>

    <router-link to="/apt/list" class="btn btn-primary">목록가기</router-link>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Detail',
  data() {
    return {
      housedeal: {},
      error: '',
    };
  },
  created() {
    const params = new URL(document.location).searchParams;

    axios
      .get(`http://localhost:9999/happyhouse/api/house/${params.get('no')}`)
      .then(({ data }) => {
        this.housedeal = data;
      })
      .catch((error) => {
        this.error = error;
      });
  },
};
</script>

<style></style>
