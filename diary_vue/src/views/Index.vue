<template>
  <!--- TODO 分页-->
  <!--- TODO 搜索框 -->
  <!--- TODO 鼠标右键-->
  <div id="root">
    <!--  头部Header  -->
    <Header id="my-header"></Header>
    <!--  左侧导航栏ASide  -->
    <div  style="display: inline-block;float:left;">
      <div class="collapse collapse-horizontal collapse show"  id="collapseWidthExample">
        <div class="card card-body" style="width: 160px;">
          <Aside id="my-aside" class="row"></Aside>
        </div>
      </div>
    </div>
    <!--  主界面Main  -->
<!--    <Main id="my-main" class="row"></Main>-->
    <router-view id="my-main" class="row"></router-view>
  </div>
</template>

<script>
import "@/assets/css/animation.css"

import header from "@/views/Home/Header";
import main from "@/views/Home/Main";
import Aside from "@/views/Home/Aside";

export default {
  name: "Index",
  components: {
    Aside,
    "Header":header,
    // "Main": main,
  },
  data() {
    return {

    }
  },
}
</script>

<style scoped>
@import "@/assets/css/darkMode.css";

#root {
  width: 100%;
}

#my-header {
  height: 58px;
}

#my-aside {
  overflow: hidden;
  position: relative;
  float: left;
  display: inline-block;
  background-color: #424242;
  height: calc(100vh - 58px);
}

#my-main {
  /*display: inline-block;*/
  height: calc(100vh - 58px);
  background-color: #303030;
  overflow: hidden; /*TODO 临时解决错位问题*/
}

.card-body {
  padding: 0;
  border: none;
}

.row {
  margin: 0;
}



</style>