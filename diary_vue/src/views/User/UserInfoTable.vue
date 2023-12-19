<template>
  <div>
    <div class="userInfoDiv row">
      <div class="col-4 singleInfoDiv">
        <div class="labelText">UID</div>
        <div :class="isSmallScreen ? 'infoText-small' : 'infoText'">{{userInfo.id}}</div>
      </div>
      <div class="col-4 singleInfoDiv">
        <div class="labelText">名称</div>
        <div :class="isSmallScreen ? 'infoText-small' : 'infoText'">{{ userInfo.username }}</div>
      </div>
      <div class="col-4 singleInfoDiv">
        <div class="labelText">邮箱</div>
        <div :class="isSmallScreen ? 'infoText-small' : 'infoText'">{{userInfo.email ? userInfo.email : "邮箱尚未设置"}}</div>
      </div>
      <div class="col-4 singleInfoDiv">
        <div class="labelText">注册时间</div>
        <div class="infoText">{{userInfo.createdate}}</div>
      </div>
      <div class="col-4 singleInfoDiv">
        <div class="labelText">最近一次修改时间</div>
        <div class="infoText">{{userInfo.updatedate}}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "UserInfoTable",
  data() {
    return{
      userInfo: {
        "username": "temp",
        "avatar": "",
        "email": "xxxxx@xx.com",
      },
    }
  },
  methods:{

  },
  mounted() {
    this.getUserInfoByLocal()
  }
}
</script>

<style scoped>

.userInfoDiv {

}

.labelText {
  color: rgba(255,255,255,0.8);
  font-size: 24px;
}

.infoText-small {
  margin: 0 auto;
  width: 120px;
  /*height: 54px;*/
  text-align: center;
  font-size: 18px;
  overflow-wrap: break-word;
}

.infoText {
  margin: 0 auto;
  text-align: center;
  font-size: 18px;
}

.singleInfoDiv {
  margin-top: 2rem;
  text-align: center;
}

</style>