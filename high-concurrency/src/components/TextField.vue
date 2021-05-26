<template>
  <v-text-field
    solo
    height="45"
    :type="type"
    :rules="rules"
    :label="label"
    class="input"
  ></v-text-field>
</template>

<script>
export default {
  props: {
    rules:Array,
    label:String,
    type:String
  },
};
</script>

<style>
</style>