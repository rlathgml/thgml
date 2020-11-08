<template>
  <d-container fluid class="main-content-container px-4">
    <!-- Page Header -->
    <d-row no-gutters class="page-header py-4">
      <d-col col class="text-center mb-4 mb-sm-0">
        <span class="text-uppercase page-subtitle" style="color: #ffffff;">Overview</span>
        <h3 class="page-title" style="color: #ffffff; display: none;">Menu Analysis</h3>
      </d-col>
    </d-row>

    <!-- Content -->
    <d-row>
      <d-col>
        <upl-menu :MenuImage="MenuImage" :MenuList="MenuList" :DateTime="DateTime" />
      </d-col>
    </d-row>
  </d-container>
</template>

<script>
import Menu from '@/components/menu/menu.vue';

export default {
  name: 'user-profile-lite',
  props: ['MenuImage', 'MenuList' , 'DateTime'],
  components: {
    uplMenu: Menu,
  },
};
</script>

