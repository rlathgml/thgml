<template>
  <d-card class="card-small">

    <!-- Card Header -->
    <d-card-header class="border-bottom">
      <h6 class="m-0">{{ title }}</h6>
      <div style="display:none;">{{submitData}}</div>
      <div style="display:none;">{{change}}</div>
      <div class="block-handle"></div>
    </d-card-header>

    <d-card-body class="p-0">

      <!-- Top Referrals List Group -->
      <d-list-group flush class="list-group-small">
        <d-list-group-item v-for="(item, idx) in referralData" :key="idx" class="d-flex px-3">
          <span class="text-semibold text-fiord-blue">{{ item.title }}</span>
          <span v-if="idx != 4 && idx != 6 && idx != 5" class="ml-auto text-right text-semibold text-reagent-gray">{{ item.value }} g</span>
          <span v-if="idx == 4 || idx == 6" class="ml-auto text-right text-semibold text-reagent-gray">{{ item.value }} mg</span>
          <span v-if="idx == 5" class="ml-auto text-right text-semibold text-reagent-gray">{{ item.value }} kcal</span>
        </d-list-group-item>
      </d-list-group>

    </d-card-body>

  </d-card>
</template>

<script>
import http from "../../utils/axios-common";

const defaultTopReferrals = [{
  title: '탄수화물',
  value: '0',
}, {
  title: '단백질',
  value: '0',
}, {
  title: '지방',
  value: '0',
}, {
  title: '당류',
  value: '0',
}, {
  title: '나트륨',
  value: '0',
}, {
  title: '칼로리',
  value: '0',
}, {
  title: '콜레스테롤',
  value: '0',
},
];

export default {
  name: 'ao-top-referrals',
  props: {
    /**
       * The component's title.
       */
    title: {
      type: String,
      default: '상세 영양 정보',
    },
    /**
       * The referral datasets.
       */
    referralData: {
      type: Array,
      default() {
        return defaultTopReferrals;
      },
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
      updateCount : 0,
    };
  },
  methods: {    
    retrieveMenuDetail() {
      
      defaultTopReferrals[0].value = this.submitData['tan']
      defaultTopReferrals[1].value = this.submitData['dan'];
      defaultTopReferrals[2].value = this.submitData['ji'];
      defaultTopReferrals[3].value = this.submitData['dang'];
      defaultTopReferrals[4].value = this.submitData['na'];
      defaultTopReferrals[5].value = this.submitData['cal'];
      defaultTopReferrals[6].value = this.submitData['col'];

    },
  },  
  updated() {
    if(this.change){
      this.change = false;
      this.retrieveMenuDetail(); 
    }
  },
};
</script>
