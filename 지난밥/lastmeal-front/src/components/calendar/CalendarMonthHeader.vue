<template>
  <div class="single-date-picker__calendar-month-header">
    <div
      class="single-date-picker__arrow left"
      @click="toggleMonth(-1)"
    >
      <i class="material-icons">
        keyboard_arrow_left
      </i>
    </div>
    <div class="single-date-picker__year">
      {{ year }}년 {{ fullMonth }}
    </div>
    <div
      class="single-date-picker__arrow right"
      @click="toggleMonth(1)"
    >
      <i class="material-icons">
        keyboard_arrow_right
      </i>
    </div>
  </div>
</template>

<script>
const MONTHS = [
  '1월',
  '2월',
  '3월',
  '4월',
  '5월',
  '6월',
  '7월',
  '8월',
  '9월',
  '10월',
  '11월',
  '12월',
];
export default {
  props: {
    year: {
      type: Number,
      default: () => 1970,
    },
    month: {
      type: Number,
      default: () => 0,
    },
  },
  computed: {
    fullMonth() {
      return MONTHS[this.month];
    },
  },
  methods: {
    toggleMonth(direction) {
      this.$emit('toggleMonth', direction);
    },
  },
};
</script>

<style lang="scss" scoped>
.single-date-picker__calendar-month-header {
  padding: 15px;
  background-image: linear-gradient(to right, #bfcde8, #94c8d6);
  display: flex;
  justify-content: space-between;
  border-radius: 10px 10px 0 0;
  align-items: center;
  color: #77505e;

  .single-date-picker__year {
    font-weight: 900;
    font-size: 20px;
    text-transform: capitalize;
  }
  .single-date-picker__arrow {
    cursor: pointer;
  }
}
</style>
