<template></template>

<script>
export default {
  created() {
    this.$router.replace({
      path: "goods",
      query: { goodsId: this.$route.query.goodsId },
    });
  },
};
</script>
