<template>
  <div class="i-header unselectable">
    <div class="header-button">
      <span class="link" @click="$router.push('/')">Documents</span>
    </div>
    <div class="header-button">
      <span class="link" @click="$router.push('/queue')">Queue</span>
    </div>
    <div class="header-button">
      <span class="link" @click="$router.push('/cache')">Cache</span>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss">
@import "../styles/colors.scss";
.i-header {
  background-color: $purple;
  color: lighten($purple, 50%);
}

.header-button {
  padding: 1em;
  position: relative;
  float: left;
  top: 50%;
  transform: translateY(-50%);
  font-size: 15px;
}

.link {
  cursor: pointer;
  &:hover {
    color: white;
  }
}
</style>

