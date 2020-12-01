<template>
  <div id="loging">
	  <form id="login-form">
      <fieldset>
        <label>логин</label>
        <input type="text" placeholder="имя пользователя" title="имя пользователя" v-model="user.login" required="true" autocomplete="username">
      </fieldset>

      <fieldset>
        <label>пароль</label>
        <input type="password" placeholder="пароль" title="пароль" v-model="user.password" required="true" autocomplete="current-password">
      </fieldset>

      <fieldset class="separated">
        <button @click="signin" title="Кнопка для входа в существующий аккаунт" class="btn">Войти</button>
        <button @click="signup" title="Кнопка для создания нового аккаунта" class="btn">Зарегистрироваться</button>
      </fieldset>
    </form>
  </div>
</template>

<script>
  export default {
    name: 'loging',
    data: function() {
      return { 
        user: {
          login: '',
          password: '',
        },
      };
    },


    methods: {
      signin: async function(event) {
        console.log('sign in account:');
        console.log(`user: ${this.user}`);

        let response = await fetch("/sign_in", {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json;charset=utf-8',
          },
          body: JSON.stringify(this.user)
        });

        console.log('check if response is ok (200)');
        if (response.ok) {
          console.log('response 200; get token');
          let json = response.json();
          console.log(`response body: ${json}`);
          if (!json)
            console.log('bad data: expected { token }');
          else {
            let token = json.token;
            console.log(`get user token: ${token}`);
            console.log('invokating token change');
            this.$emit('update:token', token);
          }
        }
      },

      signup: async function(event) {
        console.log('sign up new account:');
        console.log(`user: ${this.user}`);

        let response = await fetch('/register', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json;charset=utf-8',
            'x-mock-response-code': '201'
          },
          body: JSON.stringify(this.user)
        });

        console.log('sent request')
        console.log('check if status 201');

        if (response.status === 201) {
          console.log('user created');
          let json = response.json();
          console.log(`response body: ${json}`);
          if (!json)
            console.log('bad data: expected { token }');
          else {
            let token = json.token;
            console.log(`get user token: ${token}`);
            console.log('invokating token change');
            this.$emit('update:token', token);
          }
        }
      },
    }
  }

</script>

<style>
  @import url('https://fonts.googleapis.com/css2?family=Lato&display=swap');

  #loging {
    display: block;
    width: 45%;
    margin: 0 auto;
  }

  #login-form input {
    border: 1px solid #c6c9cc;
    border-radius: 5px;
    color: #555;
    display: block;
    margin: 1% 0 4% 0;
    padding: 1% 2%;
  }

  #login-form label {
    color: #3e606f;
    font-family: Lato, Roboto, "Open Sans", Helvetica, sans-serif;
    text-transform: capitalize;
  }

  #login-form fieldset {
    border: 1px solid #c6c9cc;
    border-radius: 5px;
    margin: 2% 0;
  }

  .btn {
    border: 0;
    border-radius: 5px;
    cursor: pointer;
    display: inline-block;
    letter-spacing: .5px;
    margin: 2% 2%;
    padding: 2% 5%;
    background-color: #3e606f;
    color: #eaeaea;
  }

  .btn:hover {
    background-color: #5e808f;
  }

  .separated {
    text-align: center;
  }

  @media only all and (max-width: 642px) {
    #loging {
      display: block;
      width: 90%;
    }
  }
</style>