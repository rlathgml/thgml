<template>
  <d-container fluid>
    <d-row>
      <!-- Main Sidebar -->
      <!-- <main-sidebar :items="sidebarItems" /> -->

      <d-col class="main-content offset-lg-4 offset-md-3 p-0" tag="main" lg="4" md="6" sm="12">

        <!-- Main Navbar -->
        <main-navbar />

        <!-- Content -->
        <div class="default-wrapper">
          <slot />
        </div>

        <!-- Main Footer -->
        <main-footer />
      </d-col>
      <d-col class="main-content offset-md-3 offset-lg-4">
      </d-col>
    </d-row>
  </d-container>
</template>

<script>
import getSidebarItems from '@/data/sidebar-nav-items';

// Main layout components
import MainNavbar from '@/components/layout/MainNavbar/MainNavbar.vue';
import MainSidebar from '@/components/layout/MainSidebar/MainSidebar.vue';
import MainFooter from '@/components/layout/MainFooter/MainFooter.vue';

export default {
  name: 'analytics',
  components: {
    MainNavbar,
    MainSidebar,
    MainFooter,
  },
  data() {
    return {
      sidebarItems: getSidebarItems(),
    };
  },
};
</script>

<style>
.default-wrapper{
  width: 100% !important; 
}
</style>

