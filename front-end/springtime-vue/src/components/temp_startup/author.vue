<template>
  <div id="author">
    <a v-bind:href="profileLink">
      <div class="author-logo">
        <img v-bind:src="imageURL" width="100%" />
      </div>

      <div class="author-info">
        <h3 class="text__title" >{{ name }} {{ surname }}</h3>
        <h4 class="text__subtitle" >группа: {{ group }} <br/> вариант: {{ variant }}</h4>
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
    width: auto;
    margin: 1% 100px;
    display: inline-block;
  }

  div.author-info {
    width: 45%;
    text-align: left;
    color: grey;
    margin: 3%;
    vertical-align: top;
    font-family: Satisfy,Lato,Open Sans,Roboto,sans-serif;
    display: inline-block;
  }

  div.author-logo {
    display: inline-block;
    max-width: 100%;
    width: 30%;
    margin: 10px;
    vertical-align: center;
  }

  div.author-logo img {
    border: solid 2px #555;
    border-radius: 25%;
  }

  .text__title {
    font-weight: 700;
    font-size: 25px;
    line-height: 44px;
    color: #2a2a2a;
    margin: 0;
  }

  .text__subtitle {
    color: #2a2a2a;
    font-size: 20px;
    font-weight: 400;
    margin: 0;
    font-style: italic;
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