<template>
  <v-text-field
    solo
    height="45"
    :type="type"
    :rules="rules"
    :label="label"
    class="input"
    v-model="value"
    @input="sendValue"
  ></v-text-field>
</template>

<script>
export default {
  data() {
    return {
      value:this.dValue
    }
  },
  props: {
    rules:Array,
    label:String,
    type:String,
    dValue:String
  },
  methods:{
    sendValue(event) {
      this.value = event;
      this.$emit('valueChange',this.value)
    }
  }
};
</script>

<style>
</style>