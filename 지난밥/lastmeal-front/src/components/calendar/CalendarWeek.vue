<template>
  <tr>
    <CalendarDate
      v-for="(date, index) in week"
      :key="index"
      :date="date"
      :is-today="isToday === date"
      :is-selected="isSelected === date"
      :is-good="isGood"
      :is-normal="isNormal"
      :is-bad="isBad"
      @selectDate="selectDate"
    />
  </tr>
</template>

<script>
import CalendarDate from './CalendarDate';

export default {
  components: {
    CalendarDate,
  },
  props: {
    week: {
      type: Array,
      default: () => [],
    },
    isToday: {
      type: Number,
      default: 0,
    },
    isSelected: {
      type: Number,
      default: 0,
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
    selectDate(date) {
      this.$emit('selectDate', date);
    },
  },
};
</script>

<style scoped>
tr {
  color: #614a52;
}
</style>
