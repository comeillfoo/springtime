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
          <a id="err_message" ></a>
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
        createMessage: async function(message) {
            let field = document.getElementById('err_message');
            field.innerText = message;
        },
      signin: async function(event) {
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

            json.then(data => {
                console.log(`get user access-token: got`);
                console.log(`get user refresh-token: got`);

                this.$session.set(this.access, data.accessToken);
                this.$session.set(this.refresh, data.refreshToken);

                window.location.reload();
            });
          }
        }else {
            response.json().then(data => {
                this.createMessage(`*${data.description}`);
            });
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

              json.then(data => {
                  console.log(`get user access-token: got`);
                  console.log(`get user refresh-token: got`);

                  this.$session.set(this.access, data.accessToken);
                  this.$session.set(this.refresh, data.refreshToken);

                  window.location.reload();
              });
          }
        }else {
            response.json().then(data => {
                this.createMessage(`*${data.description}`);
            });
        }
      },
    }
  }

</script>

<style>
  @import url('https://fonts.googleapis.com/css2?family=Lato&display=swap');
  @import url('https://fonts.googleapis.com/css2?family=Pacifico&display=swap%27');

  #loging {
    background: #fff;
    display: block;
    width: 40%;
    margin: 0 auto;
  }

  #login-form {
      margin-top: 23vh;
      box-shadow: 0 0 10px rgba(0,0,0,.3);
  }

  #login-form input {
    margin: auto;
    width: 90%;
    border: 1px solid #c6c9cc;
    border-radius: 5px;
    color: #555;
    display: block;
    padding: 1% 2%;
    height: 50px;
  }

  #login-form label {
    padding: 0 5% ;
    color: #3e606f;
    font-family: Satisfy,Lato,Open Sans,Roboto,sans-serif;
    text-transform: capitalize;
    font-size: 120%
  }

  #login-form fieldset {
    padding: 4%;
    border: 1px solid #fff;
    border-radius: 5px;
    margin: 1% 1%;
  }

  #err_message {
      font-size: 100%;
      font-style: italic;
      margin-left: 10%;
      color: #cd1a21
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
    font-family: 'Pacifico', cursive;
    font-size: 18px;
  }

  .btn:hover {
    background-color: #5e808f;
  }

  .separated {
    text-align: center;
  }

  @media only all and (min-width: 643px) and (max-width: 1244px) {
      #login-form {
          display: block;
          height: 100%;
          width: 100%;
      }

      #login-form label {
          font-size: 160%;
      }


      #loging {
          width: 50%;
      }
  }

  @media only all and (max-width: 642px) {
    #loging {
      display: block;
      width: 90%;
    }
  }
</style>

//TODO: расположение формы ввода слева а графика справа