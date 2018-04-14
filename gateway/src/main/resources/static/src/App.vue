<template>
  <div class="i-simple-container">
    <headerbar></headerbar>
    <div class="i-main-content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import headerbar from "./components/Headerbar.vue";

export default {
  components: { headerbar },
};
</script>

<style lang="scss">
@import "./styles/basics.scss";
@import "./styles/layout.scss";
</style>
