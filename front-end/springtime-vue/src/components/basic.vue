<template>
  <div id="basic">

    <div id="top-container">

        <div id="area-container" class="inlines__align--center">
          <div id="area-subcontainer">
            <canvas id="area" ref="area" width="600" height="600" @click="checkArea">
              Canvas not supported
            </canvas>
          </div>
        </div>

        <div id="form-container">
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
            <fieldset class="inlines__align--center">
              <button @click.prevent="check" class="btn">проверить</button>
            </fieldset>
          </form>
        </div>

    </div>

    <loader v-if="isLoading" /><resultscontainer v-bind:results="results" v-else-if="results.length" />
    <p class="empty-results" v-else>результаты отсутствуют</p>

<!--    <div id="close-container" class="inlines__align&#45;&#45;center">-->
<!--      <button @click="signout" class="btn">закрыть сессию</button>-->
<!--    </div>-->

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
    components: {
      resultscontainer,
      loader,
    },
    props: ['access', 'refresh',],
    data: function() {
      return {
        rValues: baseValues,
        xValues: ['-4', '-3', '-2', '-1', '0'].concat(baseValues),
        yMinimal: '-5',
        yMaximum: '5',
        result: { x: '', y: '', r: '', },
        results: [],
        isLoading: true,
        queries: {
          add: '/main/app/add',
          refresh: '/api/refresh/token',
          retrieve: '/main/app/dots/all',
        },
        counter: 4,
      };
    },
    computed: {
      radius: function() {
        return this.result.r;
      },
      isNotChecked: function() {
        return !(this.result.x !== undefined && this.result.x !== null && this.result.x.length > 0 && this.result.y !== undefined && this.result.y !== null && this.result.y.length > 0 && this.result.r !== undefined && this.result.r !== null && this.result.r.length > 0);
      }
    },
    methods: {
      translateTo: function(realCoordinate, fieldSize, length, proportion) {
        console.log(`length: ${ length }, real coordinate: ${ realCoordinate }, field size: ${ fieldSize }, proportion: ${ proportion }`);
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
        ctx.strokeStyle = '#3399FF';
        ctx.fillStyle = '#3399FF';
        console.log(`canvas: ${ canvas }, ctx: ${ ctx }, x: ${ x }, y: ${ y }, radius: ${ radius }`);
        console.log('drawing rectangle');
        this.drawRectangle(ctx, x, y, radius);
        console.log('drawing triangle');
        this.drawTriangle(ctx, x, y, radius);
        console.log('drawing quadrant');
        this.drawQuadrant(ctx, x, y, radius);
      },

      drawVerticalLine: function(ctx, x, y, length) {
        ctx.fillRect(x, y, 2, length);
      },

      drawHorizontalLine: function(ctx, x, y, length) {
        ctx.fillRect(x, y, length, 2);
      },

      drawLines: function(ctx, x, y, length) {
        ctx.strokeStyle = '#000000';
        ctx.fillStyle = '#000000';
        console.log('draw horizontal line');
        this.drawHorizontalLine(ctx, 0, y, length);
        console.log('draw vertical line');
        this.drawVerticalLine(ctx, x, 0, length);
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

      drawSignedHorizontalArrow: function(ctx, x, y, length, text) {
        ctx.fillText(text, x - length, y - length);
        this.drawHorizontalArrow(ctx, x, y, length);
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

      drawSignedVerticalArrow: function(ctx, x, y, length, text) {
        ctx.fillText(text, x + length, y + length);
        this.drawVerticalArrow(ctx, x, y, length);
      },

      drawArrows: function(ctx, x, y, length) {
        ctx.strokeStyle = '#000000';
        ctx.fillStyle = '#000000';
        console.log('drawing horizontal arrow');
        this.drawHorizontalArrow(ctx, x, 0, length);
        console.log('drawing vertical arrow');
        this.drawVerticalArrow(ctx, 2 * x, y, length);
      },

      drawSignedArrows: function(ctx, x, y, length) {
        ctx.strokeStyle = '#000000';
        ctx.fillStyle = '#000000';
        console.log('drawing signed horizontal arrow');
        this.drawSignedHorizontalArrow(ctx, 2 * x, y, length, 'x');
        console.log('drawing signed vertical arrow');
        this.drawSignedVerticalArrow(ctx, x, 0, length, 'y');
      },

      drawSignedHorizontalNotch: function(ctx, x, y, length, text) {
        ctx.strokeStyle = '#000000';
        ctx.fillStyle = '#000000';
        ctx.fillText(text, x + length, y + length / 2);
        this.drawHorizontalLine(ctx, x - length / 2, y, length);
      },

      drawSignedVerticalNotch: function(ctx, x, y, length, text) {
        ctx.strokeStyle = '#000000';
        ctx.fillStyle = '#000000';
        ctx.fillText(text, x - length, y - length);
        this.drawVerticalLine(ctx, x, y - length / 2, length);
      },

      tempdraw: function(title) {
        console.log('drawing template');
        let canvas = this.$refs.area;
        let ctx = canvas.getContext('2d');
        let width = canvas.width;
        let height = canvas.height;
        let radius = width * part;
        console.log(`canvas: (${width}:${height})`);

        const fontName = 'sans-serif';
        const textSize = width / 38;
        ctx.font = `${textSize}px ${fontName}`;

        console.log('clearing canvas');
        ctx.clearRect(0, 0, width, height);

        console.log('drawing working area');
        this.drawArea(canvas, ctx, width / 2, height / 2, radius);

        console.log('drawing coordinate lines');
        this.drawLines(ctx, width / 2, height / 2, width);

        console.log('drawing direction arrows');
        this.drawSignedArrows(ctx, width / 2, height / 2, radius / 20);

        console.log('drawing notches');

        console.log('drawing horizontal notches');
        this.drawSignedHorizontalNotch(ctx, width / 2, height / 2 + radius, radius / 20, '-R');
        this.drawSignedHorizontalNotch(ctx, width / 2, height / 2 + radius / 2, radius / 20, '-R/2');
        this.drawSignedHorizontalNotch(ctx, width / 2, height / 2 - radius / 2, radius / 20, 'R/2');
        this.drawSignedHorizontalNotch(ctx, width / 2, height / 2 - radius, radius / 20, 'R');

        console.log('drawing vertical notches');
        this.drawSignedVerticalNotch(ctx, width / 2 - radius, height / 2, radius / 20, '-R');
        this.drawSignedVerticalNotch(ctx, width / 2 - radius / 2, height / 2, radius / 20, '-R/2');
        this.drawSignedVerticalNotch(ctx, width / 2 + radius / 2, height / 2, radius / 20, 'R/2');
        this.drawSignedVerticalNotch(ctx, width / 2 + radius, height / 2, radius / 20, 'R');

        console.log('template drew');

      },

      basedraw: function(title) {
        console.log('drawing with real radius');
        let canvas = this.$refs.area;
        let ctx = canvas.getContext('2d');
        let width = canvas.width;
        let height = canvas.height;
        let radius = width * part;

        console.log(`canvas: (${width}:${height})`);

        const fontName = 'sans-serif';
        const textSize = width / 38;
        ctx.font = `${textSize}px ${fontName}`;

        console.log('clearing canvas');
        ctx.clearRect(0, 0, width, height);

        console.log('drawing working area');
        this.drawArea(canvas, ctx, width / 2, height / 2, title * radius / maxRadius);

        console.log('drawing coordinate lines');
        this.drawLines(ctx, width / 2, height / 2, width);

        console.log('drawing direction arrows');
        this.drawSignedArrows(ctx, width / 2, height / 2, radius / 20);

        console.log('drawing notches');

        console.log('drawing horizontal notches');
        for (let i = -4; i <= 4; i += 0.5) {
          let start = height / 2 + radius;
          let step = 2 * radius / 8;
          if (i === 0)
            continue;
          this.drawSignedHorizontalNotch(ctx, width / 2, start - (maxRadius + i) * step, radius / 20, i + '');
        }

        console.log('drawing vertical notches');
        for (let i = -4; i <= 4; i += 0.5) {
          let start = width / 2 - radius;
          let step = 2 * radius / 8;
          this.drawSignedVerticalNotch(ctx, start + (maxRadius + i) * step, height / 2, radius / 20, i + '');
        }

        console.log('basic drew');
      },
      redraw: function(radius) {
        if (!radius || radius <= 0)
          this.tempdraw('R');
        else this.basedraw(radius);
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

        console.log('fetching tokens from server...');
        let response = await fetch(this.queries.refresh, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json;charset=utf-8'
          },
          body: JSON.stringify({ refreshToken : this.$session.get(this.refresh)}),
        });

        console.log('check if response is ok');
        if (response.ok) {

          console.log('successful fetching new token');
          console.log('getting json object...');
          let json = await response.json();
          if (json) {
            this.$session.set(this.access, json.accessToken);
            window.location.reload();
            repeat = repeat.bind(this);
            console.log('repeating losed operation...');
            repeat(args);
          } else console.error('empty response body');

        } else {
          console.error(`bad response ${response.status} ${response.statusText}`);

          console.log('redirecting to login-page...');

          this.signout();
        }
      },

      fetchResult: async function() {
        console.log('provided valid data');
        console.log(`new result is ready to send: ${this.result}`);

        console.log('sending data...');
        let response = await fetch(this.queries.add, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json;charset=utf-8',
            'shell_token': this.$session.get(this.access),
          },
          body: JSON.stringify(this.result)
        });

        console.log('request sent -- checking if response is ok (201)');
        if (response.status === 201) {
          console.log('response is ok (201 created)');
          console.log('getting respond object from the server...');
          try {
            let json = await response.json();
            if (json) {
              let isHit = json.hit;
              if (isHit !== undefined && isHit !== null) {
                console.log('query evaluation finished');
                this.results = this.results.concat([{ x: this.result.x, y: this.result.y, r: this.result.r, hit: isHit }]);
              } else throw new Error('bad repond object [field "hit" not provided]');
            } else {
              let err = new Error('bad respond object [provided empty result]');
              err.data = json;
            }
          } catch (e) {
            console.error(e);
            console.error(`bad respond object: ${e.data}`);
          } finally {
            console.log('fetching new result finished');
          }
        } else if (response.status == '403') {

          console.error('access token expired');
          console.log('fetching new token pair...');
          await this.fetchToken(this.fetchResult);
          // console.log('redirecting to login page...');
          // this.signout();

        } else {
          console.error(`bad response ${response.statusText} : ${response.status}`);
          // plug for testing basic drawing
          // this.results = this.results.concat([{ x: this.result.x, y: this.result.y, r: this.result.r, hit: true }]);
        }
        console.log(`response status: ${response.status}`);
      },

      drawDots: function(results) {
        this.redraw(this.radius);

        console.log(`previous results: ${ results }`);
        if (!results.length)
          console.log('no any results in the table');
        else {
          // this.result.r = results[0].r;
          const out = 5;
          let canvas = this.$refs.area;
          let ctx = canvas.getContext('2d');
          let width = canvas.width;
          let height = canvas.height;
          let counter = out;
          for (let i = results.length - 1; i >= 0; --i) {
            console.log(`putting dot: ${results[i]}`);
            console.log(`x: ${ results[i].x }; y: ${ results[i].y }`);
            ctx.fillStyle = results[i].hit? "#000000" : "#cd0000";

            ctx.globalAlpha = 1 - 1 / counter;
            console.log(`counter: ${ counter }; alpha set to: ${ ctx.globalAlpha }`);

            console.log('translating coordinates');
            const realX = this.translateFrom(results[i].x, width, maxRadius, part);
            const realY = this.translateFrom(-results[i].y, height, maxRadius,part);
            console.log(`translated: x: ${ realX } y: ${ realY }`);

            console.log('putting dots');
            ctx.beginPath();
            ctx.arc(realX, realY, width * part / 100, 0, 2*Math.PI, true);
            ctx.fill();
            ctx.stroke();
            ctx.closePath();

            ctx.globalAlpha = 1.0;
            console.log(`counter: ${ counter }; alpha restored to: ${ ctx.globalAlpha }`);
            if (--counter === 0) break;
          }
        }
      },

      drawDot: function(result) {
        console.log(`previous result: ${ result }`);

        console.log('preparing canvas variables...');
        let canvas = this.$refs.area;
        let ctx = canvas.getContext('2d');
        let width = canvas.width;
        let height = canvas.height;

        console.log(`checking if more than ${ this.out } dots drawn`);
        if (!this.counter) {
          console.log('redrawing canvas...');
          this.redraw(result.r);
        }

        console.log(`dot.x: ${ result.x }; dot.y: ${ result.y }`);
        ctx.fillStyle = result.hit? "#000000" : "#cd0000";

        console.log('translating dot coordinates');
        const realX = this.translateFrom(result.x, width, maxRadius, part);
        const realY = this.translateFrom(-result.y, height, maxRadius, part);
        console.log(`dot coordinates translated to (x: ${ realX }; y: ${ realY })`);

        console.log('putting dot...');
        ctx.beginPath();
        ctx.arc(realX, realY, width * part / 100, 0, 2*Math.PI, true);
        ctx.fill();
        ctx.stroke();
        ctx.closePath();
        console.log('dot put -- decrease counter');
        this.counter = (this.counter - 1) % 5;

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
          console.error(`Errors[${errorMsg.length}]: ${errorMsg}`);
          alert(errorMsg);
        } else {
          console.log('fetching new result...');
          await this.fetchResult();
        }
      },

      checkArea: async function(event) {
        console.log(`current radius: ${this.result.r }`);
        console.log('check if radius set');
        if (!this.result.r) {
          console.log('radius not set');
          alert('Не установлено свойство радиуса области');
        }
        else if (!this.testR(this.result.r)) {
          console.log('invalid radius value');
          alert(this.$refs.r.title);
        }
        else {
          console.log('valid radius value');
          let area = this.$refs.area;
          const rect = area.getBoundingClientRect();
          console.log(`canvas: (${area.width}:${area.height})`);

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
          const y = -this.translateTo(realY, area.height, maxRadius, part);
          console.log(`y translated to: ${ y }`);
          this.result.y = y;

          console.log('fetching new result');
          await this.fetchResult();
          // let last = this.results[this.results.length - 1];
          // console.log(`get last element ${ last }`);
          // this.drawDot(last);
          this.drawDots(this.results);
        }
      },

      signout: function(event) {
        console.log('close current session...');
        this.$session.clear();
        window.location.reload();
      },

      retrieve: async function() {
        console.log('getting results with unique token...');

        let response = await fetch(this.queries.retrieve, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json;charset=utf-8',
            'shell_token': this.$session.get(this.access),
          },
          body: JSON.stringify(this.accessToken)
        });

        console.log('check if response is ok');
        if (response.ok) {
          console.log('response is ok');
          console.log('getting the json object...');
          let json = await response.json();
          this.results = json;
        } else if (response.status == '403') {
          console.log('access token expired...');
          await this.fetchToken(this.retrieve);
        } else {
          // plug for testing table
          // this.results = [ {x: 1, y: '4.9999999999999999999999999999999', r: 2, hit: true }];
          console.error(`bad response ${response.status} ${response.statusText}`);
        }
        this.isLoading = false;
      },
      createExitButton: function() {
        let exitButton = document.createElement("BUTTON");
        exitButton.innerHTML = 'Выйти';
        exitButton.setAttribute('class', 'btn logo_btn');
        exitButton.onclick = this.signout;
        let logo = document.getElementById('logo_btn');
        logo.appendChild(exitButton);
      },
    },
    mounted() {
      this.createExitButton();
      this.retrieve();
      this.redraw(this.radius);
    },
    watch: {
      radius: function(value) {
        this.redraw(this.radius);
      },
    }
  }
</script>

<style>
  @import url('https://fonts.googleapis.com/css2?family=Lato&display=swap');
  @import url('https://fonts.googleapis.com/css2?family=Pacifico&display=swap%27');

  #basic {
    width: 100%;
    padding: 0;
  }

  /**
   * Form area container
   */

  #top-container {
    width: 100%;
    min-height: 100vh;
    margin-top: 10vh;
  }
  #area-container {
    background-color: #fff;
    box-shadow: 0 0 10px rgba(0,0,0,.3);
    margin: 0 auto;
    min-width: 600px;
    min-height: 600px;
  }

  #form-container {
    background-color: #fff;
    box-shadow: 0 0 10px rgba(0,0,0,.3);
    width: 30%;
    margin: 10% 2%;
  }

  .btn:hover {
    background-color: #5e808f;
  }

  #result input, select {
    border: 2px solid #c6c9cc;
    border-radius: 3px;
    color: #555;
    display: block;
    padding: 1% 3%;
    min-width: 100%;
    min-height: 30px;
    font-size: 120%;
    margin-top: 10px;
  }

  #result option {
    font-size: 15px;
  }

  #result label {
    color: #3e606f;
    font-family: Lato, Roboto, "Open Sans", Helvetica, sans-serif;
    font-weight: bold;
    text-transform: capitalize;
  }

  #result fieldset {
    border: 0px solid #c6c9cc;
    border-radius: 5px;
    margin: 2% 0;
    width: 100%;
    padding: 3% 10%;
  }

  /**
   * area
   */

  #area-subcontainer {
    border: 1px solid #c6c9cc;
    border-radius: 5px;
  }

  #area-subcontainer {
    width: 100%;
    height: 100%;
  }

  /**
   * typography
   */

  .inlines__align--center {
    text-align: center;
  }

  .empty-results {
    text-align: center;
    font-size: 24px;
    line-height: 44px;
    color: #3e606f;
    margin: 0;
    font-family: Lato, Roboto, "Open Sans", Helvetica, sans-serif;
  }

  #area {
    border: 0px solid #c6c9cc;
  }

  @media only all and (min-width: 1245px) {

    .top-shell {
      width: 35%;
      display: inline-block;

    }

    #area-subcontainer {
      max-height: 600px;
      max-width: 600px;
    }

    #area-container {
      display: inline-block;
      margin: 5% 15% 0 0;
      float: right;
    }

    #form-container {
      display: inline-block;
      float: right;
    }

    #top-container {
      vertical-align: top;
      display: table;
    }

    #result label {
      font-size: 120%;
    }
  }

  @media only all and (min-width: 643px) and (max-width: 1244px) {

    .text__title {
      font-size: 18px;
      line-height: 32px;
    }

    .btn {
      font-size: 120%;
    }

    .empty-results {
      font-size: 18px;
      line-height: 32px;
    }

    #form-container {
      display: block;
      margin: 0 auto;
      width: 60%;
    }


    #area-container {
      max-height: 600px;
      max-width: 600px;
    }

    #area-subcontainer {
      max-height: 600px;
      max-width: 600px;
    }

    #area-container {
      display: block;
      margin: 0 auto;
    }

    #result label {
      font-size: 120%;
    }
  }

  @media only all and (max-width: 642px) {

    .btn {
      font-size: 100%;
    }

    .empty-results {
      font-size: 12px;
      line-height: 22px;
    }

    #form-container {
      display: block;
      margin: 2% auto;
      width: 95%;
    }

    #area-subcontainer {
      width: 100%;
      max-height: 90%;
    }

    #area-container {
      display: block;
      margin: 0 auto;
      min-width: 320px;
      min-height: 320px;
      max-height: 320px;
      max-width: 320px;
    }

    #area {
      max-width: 320px;
      max-height: 320px;
    }

    #top-container {
      width: 95%;
      margin: 8% auto;
    }

    #result label {
      font-size: 85%;
    }
  }
</style>