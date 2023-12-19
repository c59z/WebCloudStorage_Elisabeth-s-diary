<template>
  <div>
    <!--移动端-->
    <div v-if="isSmallScreen" style="margin-top: 1rem">
      <div class="mb-3 row row-small">
        <label for="staticEmail" class="col-3 col-form-label">用户名</label>
        <div class="col-9">
          <input style="color: white" type="text" readonly class="form-control-plaintext" id="staticEmail" :value="userInfoChange.username">
        </div>
      </div>
      <div class="mb-3 row row-small">
        <label for="inputPassword" class="col-3 col-form-label">旧密码</label>
        <div class="col-9">
          <input placeholder="请输入旧密码:" type="password" class="form-control" id="inputOldPassword" :value="userInfoChange.passwordOld">
        </div>
      </div>
      <div class="mb-3 row row-small">
        <label for="inputPassword" class="col-3 col-form-label">新密码</label>
        <div class="col-9">
          <input placeholder="请输入新密码:" type="password" class="form-control" id="inputNewPassword" :value="userInfoChange.passwordNew">
        </div>
      </div>
      <div class="mb-3 row row-small">
        <label for="inputPassword" class="col-3 col-form-label">确认密码</label>
        <div class="col-9">
          <input placeholder="请输入确认密码:" type="password" class="form-control" id="inputConfirmPassword" :value="userInfoChange.confirm">
        </div>
      </div>
      <div class="row row-small mx-auto">
        <button @click="CancelChange" type="button" class="btn btn-outline-light col-4">取消修改</button>
        <span class="col-4"></span>
        <button @click="changePwd" type="button" class="btn btn-outline-light col-4">确认修改</button>
      </div>
    </div>
    <!--大屏-->
    <div v-else style="margin-top: 1rem">
      <div class="mb-3 row row-full">
        <label for="staticEmail" class="col-sm-2 col-form-label">用户名</label>
        <div class="col-sm-10">
          <input style="color: white" type="text" readonly class="form-control-plaintext" id="staticEmail" v-model="userInfoChange.username">
        </div>
      </div>
      <div class="mb-3 row row-full">
        <label for="inputPassword" class="col-sm-2 col-form-label">旧密码</label>
        <div class="col-sm-10">
          <input  placeholder="请输入旧密码:" type="password" class="form-control" id="inputOldPassword" v-model="userInfoChange.passwordOld">
        </div>
      </div>
      <div class="mb-3 row row-full">
        <label for="inputPassword" class="col-sm-2 col-form-label">新密码</label>
        <div class="col-sm-10">
          <input placeholder="请输入新密码:" type="password" class="form-control" id="inputNewPassword" v-model="userInfoChange.passwordNew">
        </div>
      </div>
      <div class="mb-3 row row-full">
        <label for="inputPassword" class="col-sm-2 col-form-label">确认密码</label>
        <div class="col-sm-10">
          <input placeholder="请输入确认密码:" type="password" class="form-control" id="inputConfirmPassword" v-model="userInfoChange.confirm">
        </div>
      </div>
      <div class="row row-full mx-auto">
        <button @click="CancelChange" type="button" class="btn btn-outline-light col-4">取消修改</button>
        <span class="col-4"></span>
        <button @click="changePwd" type="button" class="btn btn-outline-light col-4">确认修改</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ChangeUserInfo",
  data() {
    return {
      userInfoChange:{
        username:"",
        passwordOld:"",
        passwordNew:"",
        confirm:""
      }
    }
  },
  methods: {
    CancelChange(){
      if (this.$router.currentRoute.path !== "/userCenter/userInfoTable"){
        this.$router.push('/userCenter/userInfoTable')
      }
    },
    changePwd(){
      const _this = this
      this.request.put("/userCenter/changePwd",this.userInfoChange).then(res => {
        if(res.code === 200){
          _this.$message({
            message: res.msg,
            type: 'success',
            center:true
          });
        this.userLogout()
        }else if(res.code === 400){
          _this.$message({
            message: res.msg,
            type: 'warning',
            center:true
          });
        }
      })
    },
  },
  mounted() {
    this.userInfoChange.username = JSON.parse(window.localStorage.getItem("userInfo")).username
  }
}
</script>

<style scoped>

.row-small {
  padding: 0 1rem;
}

.row-full {
  padding: 0 11rem;
}

.btn-outline-light {
  display: inline-block;
}


</style>