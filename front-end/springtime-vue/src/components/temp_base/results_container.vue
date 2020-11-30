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
          <td>{{ result.date.toLocaleString("ru", options) }}</td>
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
    props: ['results', ],
    data {
    	options: {
    		era: 'long',
  			year: 'numeric',
  			month: 'long',
  			day: 'numeric',
  			weekday: 'long',
  			timezone: 'UTC',
  			hour: 'numeric',
  			minute: 'numeric',
  			second: 'numeric'
		  },
    },
  }
</script>