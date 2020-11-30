<template>
  <div id="startup">
    <hat />
    <loging @update:token="update" />
  </div>
</template>

<script>
  import hat from '@/components/temp_startup/hat'
  import loging from '@/components/temp_startup/loging'

  export default {
    name: 'startup',
  	components: {
  		hat,
      loging,
  	},
    methods: {
      update: function(value) {
        this.$emit('update:token', value);
      }
    }
  }
</script>