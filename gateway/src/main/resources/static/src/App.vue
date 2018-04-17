<template>
  <div class="i-simple-container">
    <headerbar></headerbar>
    <div class="i-main-content">
      <router-view></router-view>
    </div>
    <chat></chat>
  </div>
</template>

<script>
import headerbar from "./components/Headerbar.vue";
import chat from "./components/Chat.vue";

export default {
  components: { headerbar, chat },
};
</script>

<style lang="scss">
@import "./styles/basics.scss";
@import "./styles/layout.scss";
</style>
