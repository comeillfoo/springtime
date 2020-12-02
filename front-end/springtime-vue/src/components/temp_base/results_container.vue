<template>
  <div id="resultscontainer">
    <table id="results">
      <thead class="table__head">
        <tr>
          <th>дата</th>
          <th>время</th>
          <th>x</th>
          <th>y</th>
          <th>r</th>
          <th>результат</th>
        </tr>
      </thead>

      <tbody class="table__body">
        <tr v-for="result in results">
          <td><div class="scrollable">{{ result.date.toLocaleString('ru', dateOptions) }}</div></td>
          <td><div class="scrollable">{{ result.time }}</div></td>
          <td><div class="scrollable">{{ result.x }}</div></td>
          <td><div class="scrollable special-width">{{ result.y }}</div></td>
          <td><div class="scrollable">{{ result.r }}</div></td>
          <td><div class="scrollable">{{ result.hit? 'yes' : 'no' }}</div></td>
        </tr>
      </tbody>
      
    </table>
  </div>
</template>

<script>
	export default {
    name: 'resultscontainer',
    props: ['results', ],
    data: function() {
      return { 
        dateOptions: {
          localeMatcher: 'best fit',
          hour12: false,
          formatMatcher: 'best fit',
          year: 'numeric',
          month: 'short',
          day: 'numeric',
          hour: '2-digit',
          minute: '2-digit',
          second: '2-digit',
        },
      };
    }
  }
</script>

<style>
  @import url('https://fonts.googleapis.com/css2?family=Inconsolata&display=swap');
  @import url('https://fonts.googleapis.com/css2?family=Anton&display=swap');

  #resultscontainer {
    text-align: center;
    display: block;
    width: 95%;
    font-family: Inconsolata, "Anonymous Pro", "Ubuntu Mono", "Lucida Console", Consolas, "Courier New", Courier, monospace;
    font-size: 18px;
    margin: 2% auto;
    border: 1px solid #3e606f;
    border-radius: 5px;
  }

  #results {
    width: 100%;
    border-collapse: collapse;
  }

  .table__head tr {
    background-color: #3e606f;
    border-bottom: 1px solid #3e606f;
    color: #eaeaea;
  }

  .table__head th {
    padding: 16px;
    text-align: left;
    text-transform: capitalize;
    padding-left: 20px;
  }

  .table__body tr {
    border-bottom: 1px solid #3e606f;
  }

  .table__body tr:nth-child(even) {
    background-color: #eaeaea;
  }

  .table__body tr:nth-child(odd) {
    background-color: #fefefe;
  }


  .table__body tr:hover {
    background-color: #bababa;
  }

  .scrollable {
    overflow-x: auto;
    padding: 16px;
    text-align: left;
  }

  @media only all and (min-width: 1245px) {
    .special-width {
      width: 128px;
    }

    .table__head th {
      font-size: 16px;
    }

    .table__body td {
      font-size: 16px;
    }
  }

  @media only all and (min-width: 643px) and (max-width: 1244px) {
    .special-width {
      width: 64px;
    }

    .table__head th {
      font-size: 12px;
    }

    .table__body td {
      font-size: 12px;
    }
  }

  @media only all and (max-width: 642px) {
    .special-width {
      width: 32px;
    }

    .table__head th {
      font-size: 8px;
    }

    .table__body td {
      font-size: 8px;
    }
  }
</style>