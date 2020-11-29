<template>
  <div id="app">
    <logo />
    <basic v-bind:authorized="session.isAuthorized" v-bind:token="session.token" v-if="session.isAuthorized" />
    <startup v-bind:authorized="session.isAuthorized" v-bind:token="session.token" v-else />
  </div>
</template>

<script>
  import logo from '@/components/logo'
  import startup from '@/components/startup'
  import basic from '@/components/basic'

  export default {
    name: 'app',
    data: function() {
      return {
        session: {
          token: '',
          isAuthorized: false,
        },
      };
    },
    components: {
      logo,
      startup,
      basic,
    },
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
