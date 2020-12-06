import Vue from 'vue'
import App from './App.vue'
import VueSessionStorage from 'vue-sessionstorage'

Vue.config.productionTip = false
Vue.use(VueSessionStorage)

new Vue({
  render: h => h(App),
}).$mount('#app')
