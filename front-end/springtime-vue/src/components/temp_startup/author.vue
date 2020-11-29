<template>
  <div id="author">
    <a v-bind:href="profileLink">
      <div class="author-info">
        <h3>{{ name }}</h3>
        <h4>{{ surname }}</h4>
        <h5>{{ group }}</h5>
        <h4>{{ variant }}</h4>
      </div>

      <div class="author-logo">
        <img v-bind:src="imageURL" />
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