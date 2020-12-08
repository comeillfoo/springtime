<template>
	<div id="hat">
		<template v-for="a in authors">
			<author v-bind:name="a.name"
				v-bind:surname="a.surname"
				v-bind:group="a.group"
				v-bind:variant="a.variant"
				v-bind:imageURL="a.imageURL"
				v-bind:profileLink="a.profileLink" />
		</template>
	</div>
</template>

<script>
	import author from '@/components/temp_startup/author'

	export default {
		name: 'hat',
		data: function() {
			return {
				authors: [
					{ 
						name: 'Ленар', 
						surname: 'Ханнанов',
						group: 'P3214',
						variant: '2599',
						imageURL: 'https://avatars0.githubusercontent.com/u/55162739?s=400&u=3c4b18db132cb3f15d9ce43fd39b40ffcc72949c&v=4',
						profileLink: 'https://github.com/Come1LLF00',
					},

					{ 
						name: 'Антон', 
						surname: 'Сушкевич',
						group: 'P3214',
						variant: '2599',
						imageURL: 'https://avatars3.githubusercontent.com/u/58341402?s=400&u=16c83f565161eab46f41869a47fa1e71565ac53d&v=4',
						profileLink: 'https://github.com/Leargy',
					},
				]
			};
		},

		components: {
      author,
		},
	}
</script>

<style>
	#hat {
    width: 100%;
    margin: 4% auto;
	min-height: 50vh;
    }
</style>