<template>
  <td style="width:50px; height:20px">
    <div
      style="margin:auto; text-align:center;"
      v-show="date"
      class="single-date-picker__date"
      :class="{'single-date-picker__today': isToday,
               'single-date-picker__selected': isSelected,
               'single-date-picker__good': this.isGood.includes(this.date),
               'single-date-picker__normal': this.isNormal.includes(this.date),
               'single-date-picker__bad': this.isBad.includes(this.date)}"
      @click="selectDate"
    >
    {{ date }}
    </div>
  </td>
</template>

<script>
export default {
  props: {
    date: {
      type: Number,
      default: () => 1,
    },
    isToday: {
      type: Boolean,
      default: false,
    },
    isSelected: {
      type: Boolean,
      default: false,
    },
    isGood: {
      type: Array,
      default: () => [],
    },
    isNormal: {
      type: Array,
      default: () => [],
    },
    isBad: {
      type: Array,
      default: () => [],
    },
  },
  methods: {
    selectDate() {
      this.$emit('selectDate', this.date);
    },
  },
};
</script>

<style>
  .single-date-picker__date {
    font-size: 14px;
    height: 30px;
    width: 30px;
    display: table;
    vertical-align: middle;
    box-sizing: border-box;
    cursor: default;
    text-align: right;
    line-height: 30px;
  }
  .single-date-picker__selected {
    border-radius: 50%;
    border: 2px solid pink;
    background-image: linear-gradient(to bottom right, #eed5d7, #eed5d7);
  }

  .single-date-picker__good {
    border-radius: 50%;
    background-image: linear-gradient(to bottom right, #60c716, #60c716);
  }

  .single-date-picker__normal {
    border-radius: 50%;
    background-image: linear-gradient(to bottom right, #ffc800, #ffc800);
  }

  .single-date-picker__bad {
    border-radius: 50%;
    background-image: linear-gradient(to bottom right, #db3d16, #db3d16);
  }

  .single-date-picker__today {
    border-radius: 50%;
    background: rgba(255,192,203, 0.4);
  }
</style>
