<template>
  <div id="resultscontainer">
    <table id="results">
      <thead>
        <tr>
          <th>дата</th>
          <th>время</th>
          <th>x</th>
          <th>y</th>
          <th>r</th>
          <th>результат</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="result in results">
          <td>{{ result.date }}</td>
          <td>{{ result.time }}</td>
          <td>{{ result.x }}</td>
          <td>{{ result.y }}</td>
          <td>{{ result.r }}</td>
          <td>{{ result.hit? 'yes' : 'no' }}</td>
        </tr>
      </tbody>
      
    </table>
  </div>
</template>

<script>
	export default {
    name: 'resultscontainer',
    props: [ 'token', ],

    data: function() {
      return {
        results: [],
      };
    },

    methods: {

      retrieve: function(token) {
        this.results = [
          {
            date: Date.now(),
            time: '21:39',
            x: '4',
            y: '3',
            r: '4',
            hit: 'false',
          },
        ];
      },

    },

    mount() {
      retrieve(token);
    },
  }
</script>