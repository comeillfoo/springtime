<template>
  <div id="app">
    <logo />
    <basic :token="token" @update:token="update" v-if="isAuthorized" />
    <startup @update:token="update" v-else />
  </div>
</template>

<script>
  import logo from '@/components/logo'
  import startup from '@/components/startup'
  import basic from '@/components/basic'

  export default {
    name: 'app',
    components: {
      logo,
      startup,
      basic,
    },
    data: function() {
      return {
        token: '',
      }
    },
    methods: {
      update(value) {
        this.token = value;
      }
    },
    computed: {
      isAuthorized: function() {
        return !((this.token === undefined) || (this.token === null) || (this.token === ''));
      }
    }
  }
</script>

<style>
  #wrap {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }
</style>
