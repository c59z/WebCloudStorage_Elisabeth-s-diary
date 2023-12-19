<template>
  <div id="App" class="container-fluid">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
  components: {

  },
}
</script>

<style>
@import "@/assets/css/global.css";


* {

}

#App {
  padding: 0;
  margin: 0;
}

.el-message{
  display: none;
}

</style>
