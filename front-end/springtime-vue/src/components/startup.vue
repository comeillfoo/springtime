<template>
  <div id="startup">
    <hat />
    <loging :access="access" :refresh="refresh" />
  </div>
</template>

<script>
  import hat from '@/components/temp_startup/hat'
  import loging from '@/components/temp_startup/loging'

  export default {
    name: 'startup',
    props: ['access', 'refresh'],
  	components: {
  		hat,
      loging,
  	},
  }
</script>
