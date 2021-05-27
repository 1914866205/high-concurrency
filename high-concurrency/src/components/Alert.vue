<template>
  <v-alert
      dense
      dismissible
      type="success"
      class="infom"
      >{{alertdata}}</v-alert
    >
</template>

<script>
export default {
    props:{
		alertdata:String
	},
}
</script>
