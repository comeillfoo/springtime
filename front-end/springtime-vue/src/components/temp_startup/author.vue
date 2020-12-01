<template>
  <div id="author">
    <a v-bind:href="profileLink">
      <div class="author-info">
        <h3 class="text__title" >{{ name }} {{ surname }}</h3>
        <h4 class="text__subtitle" >группа: {{ group }} вариант: {{ variant }}</h4>
      </div>

      <div class="author-logo">
        <img v-bind:src="imageURL" width="120" />
      </div>
    </a>
  </div>
</template>

<script>
	export default {
    name: 'author',
		props: [ 'name', 'surname', 'group', 'variant',	'imageURL',	'profileLink' ]
	}
</script>

<style>
  @import url('https://fonts.googleapis.com/css2?family=Lato&display=swap');

  #author {
    width: 45%;
    margin: 0 auto;
    display: inline-block;
    text-align: center;
    text-decoration: none;
  }

  div.author-info {
    display: inline-block;
    width: 45%;
    text-align: left;
    color: gray;
    margin: 0 auto;
    vertical-align: top;
    font-family: Lato, Roboto, "Open Sans", Helvetica, sans-serif;
    text-decoration: none;
  }

  div.author-logo {
    display: inline-block;
    max-width: 45%;
    margin: 0 auto;
    vertical-align: center;
  }

  div.author-logo img {
    border: solid 2px #555;
    border-radius: 25%;
  }

  .text__title {
    font-size: 24px;
    line-height: 44px;
    color: #3e606f;
    margin: 0;
  }

  .text__subtitle {
    color: #a9b2b9;
    font-size: 14px;
    font-weight: 400;
    margin: 0;
  }


  @media only all and (min-width: 1245px) {
  }

  @media only all and (min-width: 643px) and (max-width: 1244px) {
    #author {
      text-decoration: none;
    }

    .text__title {
      font-size: 18px;
      line-height: 32px;
    }

    .text__subtitle {
      color: #a9b2b9;
      font-size: 12px;
      font-weight: 400;
    }
  }

  @media only all and (max-width: 642px) {

    #author {
      width: 90%;
      margin: 6% auto;
      display: block;
      text-decoration: none;
    }

    .text__title {
      font-size: 16px;
      line-height: 22px;
    }

    .text__subtitle {
      font-size: 12px;
      font-weight: 400;
    }
  }
</style>