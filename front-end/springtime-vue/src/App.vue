<template>
  <div id="app">
    <logo />
    <basic :access="accessTokenName" :refresh="refreshTokenName" v-if="isAuthorized" />
    <startup :access="accessTokenName" :refresh="refreshTokenName" v-else />
  </div>
</template>

<script>
  import saveState from 'vue-save-state'
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
        accessTokenName: 'accessToken',
        refreshTokenName: 'refreshToken',
      };
    },
    computed: {
      access: function() {
        return this.$session.get(this.accessTokenName);
      },
      isAuthorized: function() {
        return !((this.access === undefined) || (this.access === null) || (this.access === ''));
      },
    },
  }
</script>

<style>
  *, *:before, *:after {
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box; 
  }

  #wrap {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #efefef;
    margin: 0 auto;
    padding: 0;
  }

  @media only all and (min-width: 1245px) {
    #wrap {
      width: 1245px;
      font-size: 16px;
    }

  }

  @media only all and (min-width: 643px) and (max-width: 1244px) {
    #wrap {
      width: 643px;
      font-size: 12px;
    }
  }

  @media only all and (max-width: 642px) {
    #wrap {
      width: 320px;
      font-size: 8px;
    }
  }
</style>
