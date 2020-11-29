<template>
  <div id="basic">

    <div id="form-container">

      <form id="result">

        <fieldset title="Значение параметра x должно быть целым числом в пределах -4 до 4">
          <label>x</label>
          <select v-model="result.x" required="true">
            <option v-for="value in xValues" >{{ value }}</option>
          </select>
        </fieldset>

        <fieldset title="Значение параметра y должно быть действительным число в интервале от -5 до 5">
          <label>y</label>
          <input type="text" placeholder="y&in;(-5, 5)" v-model="result.y" required="true" />
        </fieldset>

        <fieldset title="Значение параметра r должно быть целым числом в пределах от 1 до 4">
          <label>r</label>
          <select v-model="result.r" required="true">
            <option v-for="value in rValues" @change="redraw(value, $event);">{{ value }}</option>
          </select>
        </fieldset>

        <fieldset>
          <button @click="check">проверить</button>
        </fieldset>

      </form>

    </div>

    <div id="area-container">
      <canvas id="area" width="500" height="500" @click="">
        Canvas not supported
      </canvas>
    </div>

    <resultscontainer v-bind:token="token" />

    <div id="close-container">
      <button @click="signout">закрыть сессию</button>
    </div>
  </div>
</template>

<script>
  import resultscontainer from '@/components/temp_base/results_container'

  const baseValues = ['1', '2', '3', '4'];

  export default {
    name: 'basic',
    props: ['authorized', 'token',],
    components: {
      resultscontainer,
    },

    data: function() {
      return {
        rValues: baseValues,
        xValues: ['-4', '-3', '-2', '-1', '0'].concat(baseValues),
        yMinimal: '-5',
        yMaximum: '5',
        result: { x: '', y: '', r: '', },
      };
    },

    methods: {
      redraw: function(radius, event) {

      },

      check: function(event) {

      },

      signout: function(event) {
        this.authorized = false;
        this.token = '';
      },
    },
  }
</script>