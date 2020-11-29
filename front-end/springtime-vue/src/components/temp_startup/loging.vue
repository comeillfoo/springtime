<template>
  <div id="loging">
	  <form id="login-form">
      <fieldset>
        <label>логин</label>
        <input type="text" placeholder="имя пользователя" title="имя пользователя" v-model="user.login" required="true">
      </fieldset>

      <fieldset>
        <label>пароль</label>
        <input type="password" placeholder="пароль" title="пароль" v-model="user.password" required="true">
      </fieldset>

      <fieldset>
        <button @click="signin" title="Кнопка для входа в существующий аккаунт">Войти</button>
        <button @click="signup" title="Кнопка для создания нового аккаунта">Зарегистрироваться</button>
      </fieldset>
    </form>
  </div>
</template>

<script>
  export default {
    name: 'loging',
    props: ['authorized', 'token'],
    data: function() {
      return { 
        user: {
          login: '',
          password: '',
        },
      };
    },


    methods: {
      signin: function(event) {
        props['authorized'] = true;
        props['token'] = '132151516161';
      },

      signup: function(event) {
        props['authorized'] = true;
        props['token'] = '132151516161';
      },
    }
  }

</script>