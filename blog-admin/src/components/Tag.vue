<template>
  <div class="tags">
    <el-tag
        v-for="item in items"
        :key="item.label"
        :type="item.type"
        effect="dark">
      {{ item.label }}
    </el-tag>
  </div>
</template>

<script>
export default {
  name: "Tag",
  data() {
    return {}
  }
}
</script>

<style scoped>

</style>