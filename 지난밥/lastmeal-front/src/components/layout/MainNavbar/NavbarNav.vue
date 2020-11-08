<template>
  <d-navbar-nav class="flex-row">
    <li class="nav-item dropdown" v-if="isLoggedIn">
      <a class="nav-link dropdown-toggle text-nowrap px-3" v-d-toggle.user-actions style="color: rgba(255,255,255,0.5);">
        <img
          class="user-avatar rounded-circle mr-2"
          :src= this.user.profilepath
          alt="User Avatar"
          style="height: 40px; width: 40px;"
        />
      </a>
      <d-collapse id="user-actions" class="dropdown-menu-small">
        <d-dropdown-item>
          <i class="material-icons">&#xE7FD;</i>
          <router-link to="/user-profile-lite">프로필</router-link>
        </d-dropdown-item>
        <d-dropdown-divider />
        <d-dropdown-item class="text-danger">
          <i class="material-icons text-danger" style="float: left; margin-top: 3px;">&#xE879;</i>
          <div @click="logout">로그아웃</div>
        </d-dropdown-item>
      </d-collapse>
    </li>
  </d-navbar-nav>
</template>

<script>
export default {
  data() {
    return {
      isLoggedIn: this.$cookies.isKey("auth-token"),
      user: this.$cookies.get("auth-token")
    }
  },
  methods: {
    logout() {
      this.$cookies.remove("auth-token");
      this.$router.go("/");
    },
  },
};
</script>

<style>
.nav-link:hover {
  cursor: pointer;
}
.dropdown-menu-small{
  background: white;
  border-bottom-right-radius: 5px;
  border-bottom-left-radius: 5px;
}
</style>
