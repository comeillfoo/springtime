<template>
  <div id="startup">
    <hat />
    <loging v-bind:authorized="authorized" v-bind:token="token" />
  </div>
</template>

<script>
  import hat from '@/components/temp_startup/hat'
  import loging from '@/components/temp_startup/loging'

  export default {
    name: 'startup',
  	props: ['authorized', 'token',],
  	components: {
  		hat,
      loging,
  	},
  }
</script>