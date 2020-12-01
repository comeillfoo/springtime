<template>
  <div id="basic">

    <div id="form-container">
      <h4 class="text__title">Форма</h4>
      <form id="result">
        <fieldset ref="x" title="Значение параметра x должно быть целым числом в пределах -4 до 4">
          <label>x</label>
          <select v-model="result.x" required="true">
            <option v-for="value in xValues" >{{ value }}</option>
          </select>
        </fieldset>

        <fieldset ref="y" title="Значение параметра y должно быть действительным число в интервале от -5 до 5">
          <label>y</label>
          <input type="text" placeholder="y in (-5, 5)" v-model="result.y" required="true" />
        </fieldset>

        <fieldset ref="r" title="Значение параметра r должно быть целым числом в пределах от 1 до 4">
          <label>r</label>
          <select v-model="result.r" required="true">
            <option v-for="value in xValues">{{ value }}</option>
          </select>
        </fieldset>

        <fieldset class="separated">
          <button @click.prevent="check" class="btn">проверить</button>
        </fieldset>

      </form>

    </div>

    <div id="area-container">
      <canvas id="area" ref="area" width="500" height="500" @click="">
        Canvas not supported
      </canvas>
    </div>

    <loader v-if="isLoading" />
    <resultscontainer v-bind:results="results" v-else-if="results.length" />
    <p class="empty-results" v-else>результаты отсутствуют</p>

    <div id="close-container">
      <button @click="signout" class="btn">закрыть сессию</button>
    </div>
  </div>
</template>

<script>
  import resultscontainer from '@/components/temp_base/results_container'
  import loader from '@/components/temp_base/loader'

  const baseValues = ['1', '2', '3', '4'];

  export default {
    name: 'basic',
    props: ['token', ],
    components: {
      resultscontainer,
      loader,
    },

    data: function() {
      return {
        rValues: baseValues,
        xValues: ['-4', '-3', '-2', '-1', '0'].concat(baseValues),
        yMinimal: '-5',
        yMaximum: '5',
        result: { x: '', y: '', r: '', },
        results: [],
        isLoading: true
      };
    },
    methods: {
      basedraw: function() {

      },
      redraw: function(radius) {

      },

      testX: function(value) {
        console.log(`x values: ${this.xValues}`);
        return this.xValues.includes(value);
      },

      testY: function(value) {
        const floatRegex = /^[-+]?[0-9]*\.?[0-9]+([eE][-+]?[0-9]+)?$/;
        let float = parseFloat(value);
        console.log(`parsed value: ${float}`);
        const isFloat = floatRegex.test(value);
        console.log(`float?: ${isFloat}`);
        console.log(`not NaN?: ${!isNaN(float)}`);
        const isLess = (float <= this.yMaximum - Number.EPSILON);
        console.log(`less?: ${isLess}`);
        const isMore = (float >= this.yMinimal + Number.EPSILON);
        console.log(`more?: ${isMore}`);
        return isFloat && !isNaN(float) && isLess && isMore;
      },

      testR: function(value) {
        console.log(`r values: ${this.rValues}`);
        return this.rValues.includes(value);
      },

      check: async function(event) {
        let errorMsg = '';
        console.log('--- x testing ---');
        if (!this.testX(this.result.x))
          errorMsg += this.$refs.x.title + ';\n';
        console.log('--- y testing ---');
        if (!this.testY(this.result.y))
          errorMsg += this.$refs.y.title + ';\n';
        console.log('--- r testing ---');
        if (!this.testR(this.result.r))
          errorMsg += this.$refs.r.title + ';\n';

        console.log('=== total testing ===');
        if (errorMsg.length) {
          console.log(`Errors[${errorMsg.length}]: ${errorMsg}`);
          alert(errorMsg);
        } else {
          console.log('provided valid data');
          console.log(`sending ${this.result}`);
          console.log('sending data...');

          let response = await fetch("/create", {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json;charset=utf-8',
              'x-mock-response-code': '201'
            },
            body: JSON.stringify(this.result)
          });

          console.log('request sent checking if response is ok (201)');
          if (response.status === 201)
            console.log('response is ok (201)');
          else
            console.log('bad response');
          console.log(`response status: ${response.status}`);
        }
      },

      signout: function(event) {
        console.log('close current session...');
        this.$emit('update:token', '');
      },

      retrieve: async function(token) {
        console.log('getting results with unique token');

        let response = await fetch("/retrieve", {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json;charset=utf-8',
          },
          body: JSON.stringify(token)
        });

        console.log('check if response is ok');
        if (response.ok) {
          console.log('response is ok');
          console.log('getting the json object');
          let json = response.json();
          this.results = json;
        } else {
          console.log('bad response');
          console.log(`response status: ${response.status}`);
          this.results = [
            {
              date: new Date(),
              time: '0',
              x: '4',
              y: '4',
              r: '4',
              hit: true
            },
            {
              date: new Date(),
              time: '0',
              x: '4',
              y: '4.99999',
              r: '4',
              hit: true
            },
            {
              date: new Date(),
              time: '0',
              x: '4',
              y: '4',
              r: '4',
              hit: true
            }
          ];
        }
        this.isLoading = false;
      },
    },
    mounted() {
      this.retrieve(this.token);
    }
  }
</script>

<style>
  @import url('https://fonts.googleapis.com/css2?family=Lato&display=swap');

  #basic {
    width: 100%;
    margin: 0;
  }

  #area {
    width: 1245px;
  }

  .btn {
    border: 0;
    border-radius: 5px;
    cursor: pointer;
    display: inline-block;
    letter-spacing: .5px;
    margin: 2% 2%;
    padding: 11px 30px;
    background-color: #3e606f;
    color: #eaeaea;
    font-family: Lato, Roboto, "Open Sans", Helvetica, sans-serif;
    text-transform: capitalize;
    font-size: 18px;
  }

  .btn:hover {
    background-color: #5e808f;
  }

  #form-container {
    display: block;
    width: 45%;
    margin: 0 auto;
  }

  #result input, select {
    border: 1px solid #c6c9cc;
    border-radius: 5px;
    color: #555;
    display: block;
    margin: 1% 0 4% 0;
    padding: 1% 2%;
  }

  .separated {
    text-align: center;
  }


  #close-container {
    text-align: center;
  }

  #result label {
    color: #3e606f;
    font-family: Lato, Roboto, "Open Sans", Helvetica, sans-serif;
    text-transform: capitalize;
  }

  #result fieldset {
    border: 1px solid #c6c9cc;
    border-radius: 5px;
    margin: 2% 0;
  }

  .text__title {
    font-size: 24px;
    line-height: 44px;
    color: #3e606f;
    margin: 0;
    font-family: Lato, Roboto, "Open Sans", Helvetica, sans-serif;
  }

  .text__title {
      font-size: 16px;
      line-height: 22px;
    }

    #area {
      width: 320px;
    }

    .btn {
      font-size: 12px;
    }

    .empty-results {
      text-align: center;
      font-size: 24px;
      line-height: 44px;
      color: #3e606f;
      margin: 0;
      font-family: Lato, Roboto, "Open Sans", Helvetica, sans-serif;
    }

  @media only all and (min-width: 643px) and (max-width: 1244px) {

    .text__title {
      font-size: 18px;
      line-height: 32px;
    }

    #area {
      width: 643px;
    }

    .btn {
      font-size: 14px;
    }

    .empty-results {
      font-size: 18px;
      line-height: 32px;
    }
  }

  @media only all and (max-width: 642px) {
    #result {
      display: block;
      margin: 0 auto;
    }

    .empty-results {
      font-size: 12;
      line-height: 22px;
    }
  }
</style>