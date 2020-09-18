<template>
  <div class="container">
    <h1>아파트 거래 내역</h1>
    <b-input-group>
      <b-input-group-prepend>
        <b-form-select v-model="keyword">
          <b-form-select-option :value="null" disabled
            >검색</b-form-select-option
          >
          <b-form-select-option value="apt">아파트</b-form-select-option>
          <b-form-select-option value="dong">동</b-form-select-option>
        </b-form-select>
      </b-input-group-prepend>
      <input
        type="text"
        class="form-control"
        aria-label="Text input with dropdown button"
        id="word"
        name="word"
        v-model="word"
      />
      <b-input-group-append>
        <button
          class="btn btn-outline-secondary"
          @click="search(1, pagination.range)"
        >
          검색
        </button>
      </b-input-group-append>
    </b-input-group>

    <br />
    <br />
    <div v-if="items.length">
      <table class="table">
        <thead class="thead-dark">
          <tr>
            <th>번호</th>
            <th>동</th>
            <th>아파트 이름</th>
            <th>거래 금액</th>
            <th v-if="getToken" style="text-align: center">관심 주택</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(housedeal, index) in items" :key="index + '_items'">
            <td>
              <router-link :to="'detail?no=' + housedeal.no">{{
                housedeal.no
              }}</router-link>
            </td>
            <td>{{ housedeal.dong }}</td>
            <td>{{ housedeal.aptName }}</td>
            <td>{{ housedeal.dealAmount }}</td>
            <td v-if="getToken" style="text-align: center">
              <i
                v-if="housedeal.interest"
                class="fas fa-star"
                :id="housedeal.no"
                @click="
                  registInterest(
                    housedeal.no,
                    housedeal.aptName,
                    housedeal.dong,
                    housedeal.interest
                  )
                "
              ></i>
              <i
                v-else
                class="far fa-star"
                :id="housedeal.no"
                @click="
                  registInterest(
                    housedeal.no,
                    housedeal.aptName,
                    housedeal.dong,
                    housedeal.interest
                  )
                "
              ></i>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <br />
    <ul class="pagination">
      <li class="page-item" v-if="pagination.prev">
        <a
          class="page-link"
          href="#"
          @click="
            fn_prev(
              pagination.page,
              pagination.range,
              pagination.rangeSize,
              pagination.keyword,
              pagination.word
            )
          "
          >Previous</a
        >
      </li>
      <li
        class="page-item"
        v-for="(n, index) in range(pagination.startPage, pagination.endPage)"
        :key="index"
      >
        <a
          class="page-link"
          href="#"
          @click="
            fn_pagination(
              n,
              pagination.range,
              pagination.rangeSize,
              pagination.keyword,
              pagination.word
            )
          "
          >{{ n }}</a
        >
      </li>
      <li class="page-item" v-if="pagination.next">
        <a
          class="page-link"
          href="#"
          @click="
            fn_next(
              pagination.page,
              pagination.range,
              pagination.rangeSize,
              pagination.keyword,
              pagination.word
            )
          "
          >Next</a
        >
      </li>
    </ul>
  </div>
</template>

<script>
import axios from "axios";
import { mapGetters } from "vuex";

const path = "http://localhost:9999/happyhouse/api/house/all";
const userStore = "userStore";
export default {
  name: "List",
  data: function() {
    return {
      checked: [],
      items: [],
      keyword: "",
      word: "",
      pagination: {},
      interest: []
    };
  },
  computed: {
    ...mapGetters(userStore, ["getToken", "getUserId"])
  },
  created() {
    // this.get(path);
    axios
      .get("http://localhost:9999/happyhouse/api/house/all")
      .then(response => {
        this.items = response.data.list;
        this.pagination = response.data.pagination;
        axios
          .get(
            `http://localhost:9999/happyhouse/api/interest/${this.getUserId}`
          )
          .then(res => {
            this.interest = res.data;
            for (let i = 0; i < this.items.length; i++) {
              this.$set(this.items[i], "interest", false);
            }
            for (let inObj of this.interest) {
              for (let Obj of this.items) {
                if (inObj.no == Obj.no) {
                  Obj.interest = true;
                }
              }
            }
          });
      })
      .catch(error => {
        console.log(error);
      });
  },
  methods: {
    fn_prev(page, range, rangeSize, keyword, word) {
      console.log(
        page + " " + range + " " + rangeSize + " " + keyword + " " + word
      );
      let _page = (range - 2) * rangeSize + 1;
      let _range = range - 1;
      let url = path;
      url = url + "?page=" + _page;
      url = url + "&range=" + _range;
      if (this.keyword) {
        url = url + "&keyword=" + this.keyword;
      }
      if (this.word) {
        url = url + "&word=" + this.word;
      }
      this.get(url);
    },
    fn_pagination(page, range, rangeSize, keyword, word) {
      console.log(
        page + " " + range + " " + rangeSize + " " + keyword + " " + word
      );
      let url = path;
      url = url + "?page=" + page;
      url = url + "&range=" + range;
      if (this.keyword) {
        url = url + "&keyword=" + this.keyword;
      }
      if (this.word) {
        url = url + "&word=" + this.word;
      }
      this.get(url);
    },
    fn_next(page, range, rangeSize, keyword, word) {
      console.log(
        page + " " + range + " " + rangeSize + " " + keyword + " " + word
      );
      let _page = parseInt(range * rangeSize) + 1;
      let _range = parseInt(range) + 1;
      let url = path;
      url = url + "?page=" + _page;
      url = url + "&range=" + _range;
      if (this.keyword) {
        url = url + "&keyword=" + this.keyword;
      }
      if (this.word) {
        url = url + "&word=" + this.word;
      }
      this.get(url);
    },
    range(start, end) {
      let list = [];
      for (let i = start; i <= end; i++) {
        list.push(i);
      }
      return list;
    },
    search(page, range) {
      let url = path;
      url = url + "?page=" + page;
      url = url + "&range=" + range;
      if (this.keyword) {
        url = url + "&keyword=" + this.keyword;
      }
      if (this.word) {
        url = url + "&word=" + this.word;
      }
      this.get(url);
    },
    get(url) {
      console.log("url : " + url);
      console.log("keyword : " + this.keyword);
      console.log("word : " + this.word);
      axios
        .get(url)
        .then(response => {
          console.log(response);
          this.items = response.data.list;
          this.pagination = response.data.pagination;
        })
        .catch(error => {
          console.log(error);
        });
    },
    registInterest(no, aptName, dong, state) {
      if (state) {
        axios
          .delete(`http://localhost:9999/happyhouse/api/interest/remove/${no}`)
          .then(() => {
            alert("해제 성공");
            document.getElementById(no).setAttribute("class", "far fa-star");
            this.items[no - 1].interest = false;
          })
          .catch(error => {
            console.dir(error);
          });
      } else {
        axios
          .post("http://localhost:9999/happyhouse/api/interest/add", {
            no,
            aptName,
            dong: dong.trim(),
            userId: this.getUserId
          })
          .then(res => {
            if (res.data === "FULL") alert("더이상 등록할 수 없습니다.");
            else {
              alert("등록 성공");
              document.getElementById(no).setAttribute("class", "fas fa-star");
              this.items[no - 1].interest = true;
            }
          })
          .catch(error => {
            console.dir(error);
          });
      }
    }
  }
};
</script>

<style></style>
