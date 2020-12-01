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
