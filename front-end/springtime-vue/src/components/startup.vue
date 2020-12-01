<template>
  <div id="startup">
    <hat />
    <loging @update:accessToken="updateAccess" @update:refreshToken="updateRefresh" />
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
      updateAccess: function(value) {
        this.$emit('update:accessToken', value);
      },
      updateRefresh: function(value) {
        this.$emit('update:refreshToken', value);
      },
    }
  }
</script>
