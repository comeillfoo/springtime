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
      <canvas id="area" ref="area" width="500" height="500" @click="checkArea">
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
  const maxRadius = Math.max(...baseValues);
  const part = 0.45;

  export default {
    name: 'basic',
    props: ['accessToken', 'refreshToken', ],
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
    watch: {
      result.r: function(value) {
        this.redraw(value);
      },
    },
    methods: {
      translateTo: function(realCoordinate, fieldSize, length, proportion) {
        return length * (realCoordinate - fieldSize / 2) / (fieldSize * proportion);
      },
      translateFrom: function(imagCoordinate, fieldSize, length, proportion) {
        return fieldSize * (imagCoordinate * proportion / length + 0.5);
      },

      drawRectangle(ctx, x, y, radius) {
        ctx.fillRect(x - radius, y, radius, radius / 2);
      },

      drawTriangle(ctx, x, y, radius) {
        ctx.beginPath();
        ctx.moveTo(x, y);
        ctx.lineTo(x, y - radius);
        ctx.lineTo(x + radius / 2, y);
        ctx.closePath();
        ctx.stroke();
        ctx.fill();
      },

      drawQuadrant(ctx, x, y, radius) {
        ctx.beginPath();
        ctx.arc(x, y, radius / 2, 0, Math.PI / 2, false);
        ctx.lineTo(x, y);
        ctx.closePath();
        ctx.stroke();
        ctx.fill();
      },

      drawArea: function(canvas, ctx, x, y, radius) {
        context.strokeStyle = '#3399FF';
        context.fillStyle = '#3399FF';
        console.log(`canvas: ${ canvas }, ctx: ${ ctx }, x: ${ x }, y: ${ y }, radius: ${ radius }`);
        console.log('drawing rectangle');
        drawRectangle(ctx, x, y, radius);
        console.log('drawing triangle');
        drawTriangle(ctx, x, y, radius);
        console.log('drawing quadrant');
        drawQuadrant(ctx, x, y, radius);
      },

      drawVerticalLine: function(ctx, x, y, length) {
        ctx.fillRect(x, y, 1, length);
      },

      drawHorizontalLine: function(ctx, x, y, length) {
        ctx.fillRect(x, y, length, 1);
      },

      drawLines: function(ctx, x, y, length) {
        console.log('draw horizontal line');
        drawHorizontalLine(ctx, 0, y, length);
        console.log('draw vertical line');
        drawVerticalLine(ctx, x, 0, length);
      },
      drawHorizontalArrow: function(ctx, x, y, length) {
        ctx.beginPath();
        ctx.moveTo(x, y);
        ctx.lineTo(x - length, y - length / 2);
        ctx.lineTo(x - length, y + length / 2);
        ctx.closePath();
        ctx.fill();
        ctx.stroke();
      },
      drawVerticalArrow: function(ctx, x, y, length) {
        ctx.beginPath();
        ctx.moveTo(x, y);
        ctx.lineTo(x - length / 2, y + length);
        ctx.lineTo(x + length / 2, y + length);
        ctx.closePath();
        ctx.fill();
        ctx.stroke();
      },
      drawArrows: function(ctx, x, y, length) {
        console.log('drawing horizontal arrow');
        drawHorizontalArrow(ctx, x, 0, length);
        console.log('drawing vertical arrow');
        drawVerticalArrow(ctx, 2 * x, y, length);
      },
      drawHorizontalNotch: function() {

      },
      drawVerticalNotch: function() {

      },
      drawNotches: function(ctx, width, height, start, finish, step, length) {
        for (pos = start; pos <= finish; pos += step) {
          drawHorizontalNotch(ctx, pos, y, length);
          drawVerticalNotch(ctx, x, y, length);
        }
      },
      tempdraw: function(title) {
        let canvas = this.$refs.area;
        let ctx = canvas.getContext('2d');
        let width = canvas.width;
        let height = canvas.height;
        let radius = width * part;
        console.log('drawing working area');
        drawArea(canvas, ctx, width / 2, height / 2, radius);
        console.log('drawing coordinate lines');
        drawLines(ctx, width / 2, height / 2, width);
        console.log('drawing direction arrows');
        drawArrows(ctx, width / 2, height / 2, radius / 100);
        console.log('drawing notches');
        drawNotches();
      },
      basedraw: function(title) {

      },
      redraw: function(radius) {
        if (!radius)
          tempdraw('R');
        else basedraw(radius);
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

      fetchToken: async function(repeat, ...args) {
        console.log(`current access-token expired: ${this.accessToken}`);
        console.log('fetching new one');

        let response = await fetch("/api/refresh/token", {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json;charset=utf-8'
          },
          body: JSON.stringify(this.refreshToken),
        });

        console.log('check if response is ok');
        if (response.ok) {

          console.log('successful fetching new tokens');
          console.log('getting json object');
          let json = response.json();
          if (!json) {

            console.log(`fetched from backend ${ json }`);
            let refreshToken = json.refreshToken;
            console.log(`get refresh token: ${ refreshToken }`);
            let accessToken = json.accessToken;
            console.log(`get access token: ${ accessToken }`);
            this.$emit('update:refreshToken', refreshToken);
            this.$emit('update:accessToken', accessToken);
            repeat = repeat.bind(this);
            repeat(args);

          } else console.log('empty response body');

        } else console.log('bad response');
      },

      fetchResult: async function() {
        console.log('provided valid data');
        console.log(`sending ${this.result}`);
        console.log('sending data...');

        let response = await fetch("/main/app/add", {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json;charset=utf-8',
            'shell_token': this.accessToken,
          },
          body: JSON.stringify(this.result)
        });

        console.log('request sent checking if response is ok (201)');
        if (response.status === 201)
          console.log('response is ok (201)');
        else if (response.status === 403) {
          console.log('access token expired');
          this.fetchToken(this.fetchResult);
        } else
          console.log('bad response');
        console.log(`response status: ${response.status}`);
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
          console.log('fetching new result');
          this.fetchResult();
        }
      },

      checkArea: async function(event) {
        console.log(`current radius: ${this.result.r }`);
        console.log('check if radius set');
        if (!this.result.r) {
          console.log('radius not set');
          alert('Не установлено свойство радиуса области');
        }
        else if (!testR(this.result.r)) {
          console.log('invalid radius value');
          alert(this.$refs.r.title);
        }
        else {
          console.log('valid radius value');
          let area = this.$refs.area;
          const rect = area.getBoundingClientRect();

          console.log('getting x coordinate');
          const realX = event.clientX - rect.left;
          console.log(`mouse x: ${ realX }`);

          console.log('translating to x value');
          const x = this.translateTo(realX, area.width, maxRadius, part);
          console.log(`x translated to: ${ x }`);
          this.result.x = x;

          console.log('getting y coordinate');
          const realY = event.clientY - rect.top;
          console.log(`mouse y: ${ realY }`);

          console.log('translating to y value');
          const y = this.translateTo(realY, area.height, maxRadius, part);
          console.log(`y translated to: ${ y }`);
          this.result.y = y;

          console.log('fetching new result');
          this.fetchResult();
        }
      }

      signout: function(event) {
        console.log('close current session...');
        this.$emit('update:accessToken', '');
        this.$emit('update:refreshToken', '')
      },

      retrieve: async function() {
        console.log('getting results with unique token');

        let response = await fetch("/main/app/dots/all", {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json;charset=utf-8',
            'shell_token': this.accessToken,
          },
          body: JSON.stringify(this.accessToken)
        });

        console.log('check if response is ok');
        if (response.ok) {
          console.log('response is ok');
          console.log('getting the json object');
          let json = response.json();
          this.results = json;
        } else if (response.status === 403) {
          console.log('access token expired');
          this.fetchToken(this.retrieve)
        } else {
          console.log('bad response');
          console.log(`response status: ${response.status}`);
          this.results = [];
        }
        this.isLoading = false;
      },
    },
    mounted() {
      this.retrieve();
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