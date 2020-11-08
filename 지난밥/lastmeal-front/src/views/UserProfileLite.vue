<template>
  <d-container fluid class="main px-4">

    <!-- Page Header -->
    <d-row no-gutters class="page-header">
      <d-col sm="12" class="text-center mb-4">
        <span class="text-uppercase page-subtitle" style="color: #ffffff;">User Profile</span>
        <h3 class="page-title" style="color: #ffffff;">마이페이지</h3>
      </d-col>
    </d-row>

    <!-- Content -->
    <d-row>
      <d-col sm="12">
        <upl-user-details />
      </d-col>
      <d-col sm="12">
        <upl-user-condition-details />
      </d-col>
    </d-row>
  </d-container>
</template>

<script>
import UserDetails from '@/components/user-profile-lite/UserDetails.vue';
import UserAccountDetails from '@/components/user-profile-lite/UserAccountDetails.vue';
import UserConditionDetails from '@/components/user-profile-lite/UserConditionDetails.vue';
export default {
  name: 'user-profile-lite',
  components: {
    uplUserDetails: UserDetails,
    uplUserAccountDetails: UserAccountDetails,
    uplUserConditionDetails: UserConditionDetails,
  },
};
</script>

<style scoped>
.main{
  margin-top: 75px;
}
</style>
