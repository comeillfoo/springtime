<template>
	<div id="logo">
    <h3>dots among the area</h3>
  </div>
</template>

<style>
  @import url('https://fonts.googleapis.com/css2?family=Satisfy&display=swap');
  @import url('https://fonts.googleapis.com/css2?family=Lato&display=swap');

  #logo {
    box-shadow: 0 0 20px rgba(0,0,0,.5);
    width: 100%;
    background-color: #fff;
    font-style: italic;
    text-align: center;
    color: #2a2a2a;
    font-family: Satisfy, Lato, "Open Sans", Roboto, sans-serif;
  }

  #logo h3 {
    font-size: 50px;
    padding-left: 2%;
    margin: 0;
    text-decoration: underline;
  }

  @media only all and (min-width: 1245px) {

    #logo {
      text-align: left;
    }

  }

  @media only all and (min-width: 643px) and (max-width: 1244px) {

      #logo {
          text-align: center;
      }
  }

  @media only all and (max-width: 642px) {

      #logo {
          text-align: center;
      }
  }
</style>

