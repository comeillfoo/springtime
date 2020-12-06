<template>
  <div id="loging">
      <div id="login-form">
      <fieldset>
        <label>логин</label>
        <input type="text" placeholder="dissya" title="имя пользователя" v-model="user.login" required="true" autocomplete="username">
      </fieldset>

      <fieldset>
        <label>пароль</label>
        <input type="password" placeholder="qwerty" title="пароль" v-model="user.password" required="true" autocomplete="current-password">
      </fieldset>

      <fieldset class="separated">
        <button @click="signin" title="Кнопка для входа в существующий аккаунт" class="btn">Войти</button>
        <button @click="signup" title="Кнопка для создания нового аккаунта" class="btn">Зарегистрироваться</button>
      </fieldset>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'loging',
    props: ['access', 'refresh'],
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

        this.$session.set(this.access, '12345');
        this.$session.set(this.refresh, '12345');
        window.location.reload();
        return;

        console.log('sign in account:');
        console.log(`user: ${this.user}`);

        let response = await fetch("api/aunt/sign_in", {
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
            console.log('bad data: expected { accessToken, refreshToken }');
          else {
            let accessToken = json.accessToken;
            let refreshToken = json.refreshToken;
            console.log(`get user access-token: ${accessToken}`);
            console.log(`get user refresh-token: ${refreshToken}`);
            console.log('invokating tokens change');
            this.$session.set(this.access, accessToken);
            this.$session.set(this.refresh, refreshToken);
            window.location.reload();
          }
        }
      },

      signup: async function(event) {
        console.log('sign up new account:');
        console.log(`user: ${this.user}`);

        let response = await fetch('api/aunt/register', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json;charset=utf-8',
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
            console.log('bad data: expected { accessToken, refreshToken }');
          else {
            let accessToken = json.accessToken;
            let refreshToken = json.refreshToken;
            console.log(`get user access-token: ${accessToken}`);
            console.log(`get user refresh-token: ${refreshToken}`);
            console.log('invokating tokens change');
            this.$session.set(this.access, accessToken);
            this.$session.set(this.refresh, refreshToken);
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

//TODO: расположение формы ввода слева а графика справа