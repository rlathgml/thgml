<template>
  <div class="card card-small h-100">
    <!-- Card Header -->
    <div class="card-header border-bottom">
      <h6 class="m-0">{{ title }}</h6>
    </div>

    <!-- Card -->
    <div class="card-body d-flex py-0" style="margin: 0 auto;">
      <div style="display: none;"> {{ submitData }}</div>
      <ul>
        <li v-for="menu in menuList" :key="menu[0] + 'key'" class="mt-3">{{ menu[0] }}<span class="mx-2">{{ menu[1] }}개</span></li>
      </ul>
    </div>
  </div>
</template>

<script>

export default {
  name: "menu-listview",
  props: {
    /**
     * The chart title.
     */
    title: {
      type: String,
      default: "식사 메뉴",
    },
    submitData: {
      type: undefined,
      default: null,
    },
    change: {
      type: Boolean,
      default: false,
    }
  },
  data() {
    return {
      menuList: [],
    };
  },
  methods: {
    receiveMenu() {
      this.menuList = this.submitData;
    },

  },  
  updated() {
    if(this.change){
      this.change = false;
      this.receiveMenu(); 
    }
  },

};
</script>
