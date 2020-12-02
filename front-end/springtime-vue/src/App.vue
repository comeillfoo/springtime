<template>
  <div id="app">
    <logo />
    <basic :accessToken="sessionStorage.accessToken" @update:accessToken="updateAccess" :refreshToken="sessionStorage.refreshToken" @update:refreshToken="updateRefresh" v-if="isAuthorized" />
    <startup @update:accessToken="updateAccess" @update:refreshToken="updateRefresh" v-else />
  </div>
</template>

<script>
  import saveState from 'vue-save-state'
  import logo from '@/components/logo'
  import startup from '@/components/startup'
  import basic from '@/components/basic'

  export default {
    mixins: [saveState],
    name: 'app',
    components: {
      logo,
      startup,
      basic,
    },
    data: function() {
      return {
        sessionStorage: {
          accessToken: '',
          refreshToken: '',
        },
      }
    },
    methods: {
      updateAccess(value) {
        this.sessionStorage.accessToken = value;
      },
      updateRefresh(value) {
        this.sessionStorage.refreshToken = value;
      },
      getSaveStateConfig: function() {
        return { 
          'cacheKey': 'app',
          'saveProperties': ['sessionStorage'],
        };
      },
    },
    computed: {
      isAuthorized: function() {
        return !((this.sessionStorage.accessToken === undefined) || (this.sessionStorage.accessToken === null) || (this.sessionStorage.accessToken === ''));
      }
    }
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
