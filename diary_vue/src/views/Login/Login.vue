<template>
  <div class="box">
      <div class="left"></div>
      <!-- 第一个板块 -->
      <div class="right">
        <h4><img src="../../assets/img/login.png" alt=""></h4>
        <div class="register">
          <input v-model="user.username" class="form-control" type="text" placeholder="用户名" aria-label="请输入用户名">
          <input v-model="user.password" class="form-control" type="password" placeholder="密码" aria-label="请输入密码">
<!--          <input v-model="user.checkNum" class="form-control let_check" type="text" placeholder="验证码" aria-label="请输入验证码">-->
<!--          <img class="img_check_code" src="../../assets/temp/checkCode01.png" alt="" srcset="">-->
<!--          <div class="form-check">-->
<!--            <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault">-->
<!--            <label class="form-check-label" for="flexCheckDefault">-->
<!--              记住密码-->
<!--            </label>-->
<!--          </div>-->
          <button @click="userLogin" class="submit" >用户登录</button>
        </div>
        <div class="fn">
          <router-link to="/register">
            <a href="#">注册账号</a>
          </router-link>
<!--          <router-link to="/forgetPwd">-->
<!--            <a href="#">忘记密码</a>-->
<!--          </router-link>-->
        </div>
      </div>
    </div>
</template>

<script>


export default {
  name: "Login",
  methods: {
    // 向后端发送登录请求
    userLogin(){
      // 登录成功后
      const _this = this
      this.request.post("/user/login",this.user).then(res => {
        if(res.code === 200){
          this.$message({
            message: res.msg,
            type: 'success',
            center:true
          });
          const token = res.data.token
          const userInfo = res.data.userInfo
          userInfo.avatar = "data:image/jpeg;base64,"+userInfo.avatar
          _this.$store.commit('SET_TOKEN', token)
          _this.$store.commit('SET_USERINFO', userInfo)
          this.$router.push("/main")
        }else if(res.code === 400) {
          this.$message({
            message: res.msg,
            type: 'error',
            center:true
          });
        }
      })
    },
  },
  data(){
    return {
      user:{

      },
    }
  },
  mounted() {

  }
}
</script>

<style scoped>

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

@keyframes show{
  0%{
    opacity: 0;
  }

  100%{
    opacity: 1;
  }
}

@keyframes infoshow{
  0%{
    opacity: 0;
    top: 100%;
  }

  50% {
    opacity: 1;
    top: 90%;
  }

  100%{
    opacity: 0;
    top: 100%;
  }
}

.box {
  animation: show 1s ease 300ms forwards;
}


html::before {

}

::selection {
  /*color: #fff;*/
  /*background-color: rgb(144,129,241);*/
}

.box {
  opacity: 0;
  display: flex;
  overflow: hidden;
  width: 55rem;
  height: 35rem;
  background-color: rgba(255,255,255,70%);
  border-radius: 1.5rem;
  margin: 5% auto;
}

.box .left {
  position: relative;
  width: 35%;
  height: 100%;
}

.box .left::before {
  content: '';
  position: absolute;
  width: 100%;
  height: 100%;
  background-image: url(../../assets/img/89478080.jpg);
  background-size: cover;
  opacity: 70%;
}

.box .right {
  display: flex;
  width: 65%;
  flex-direction: column;
  align-items: center;
}

.box .right h4{
  color: rgb(144,129,241);
  font-size: 1rem;
  margin-top: 1rem;
}

.box .right .register {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

.form-control {
  outline: none;
  width: 80%;
  height: 2.5rem;
  margin-top: 1rem;
  padding: 0.5rem;
  border: rgb(94, 70, 252) 1px solid;
  color: rgb(94, 70, 252);
  background-color: rgba(0,0,0,0);
}

.let_check {
  width: 53.5%;
}

.box .right .register .form-check {
  display: inline-block;
  margin-left: 3rem;
  outline: none;
  width: 80%;
  height: 1rem;
  font-size: 1rem;
  margin-top: 1rem;
  border: none;
  color: rgb(94, 70, 252);
  background-color: rgba(0,0,0,0);
}

.form-check-input {
  margin-top: 0.2rem;
}

.right .submit {
  width: 60%;
  height: 3rem;
  color: #666;
  background-image: linear-gradient(120deg, #8ec5fc 0%,#e0c3fc  100%);
  font-size: 1rem;
  border: none;
  border-radius: 0.5rem;
  margin: 2rem 0 0 50%;
  transform: translateX(-50%);
}

.right .submit:hover {
  box-shadow: 0 0 2rem -0.5rem rgb(0 0 0 / 15%);
}

.right .fn {
  display: flex;
  justify-content: space-between;
  margin-top: 0.5rem;
  width: 23%;
}

.right .fn a {
  font-size: 1rem;
  margin-top: 1rem;
  padding: 0.5rem 1rem;
  color: #666;
  text-decoration: none;
}

.right .fn a:hover {
  color: rgb(144,129,241);
}
.img_check_code {
  width: 120px;
  height: 40px;
  margin-top: 1rem;
  margin-left: 2rem;
}




</style>