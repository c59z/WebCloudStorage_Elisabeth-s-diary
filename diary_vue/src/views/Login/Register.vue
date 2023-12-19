<template>
  <div>

    <div class="box">
      <div class="left"></div>
      <!-- Register -->
      <div class="right">
        <h4><img src="../../assets/img/register.png" alt=""></h4>
        <div class="register">
          <input v-model.trim="user.username" class="form-control" type="text" placeholder="用户名" aria-label="请输入用户名">
          <input v-model.trim="user.password" class="form-control" type="password" placeholder="密码" aria-label="请输入密码">
          <input v-model.trim="user.confirm" class="form-control" type="password" placeholder="确认密码" aria-label="请输入确认密码">

          <input @click="userRegister" class="submit" type="button" value="注册账户" >
        </div>
        <div class="fn">
          <router-link to="/login">
            <a href="#">返回登录</a>
          </router-link>
<!--          <router-link to="/forgetPwd">-->
<!--            <a href="#">忘记密码</a>-->
<!--          </router-link>-->
        </div>
      </div>
    </div>
  </div>

</template>

<script>



export default {
  name: "Register",
  data() {
    return {
      user: {
        username:"",
        password:"",
        confirm:"",
        checkNum:""
      },
    }
  },
  methods: {
    userRegister(){
      if(this.user.username.length < 6 || this.user.password < 6){
        this.$message({
          message: '注册失败,用户名和密码长度不能小于6位',
          type: 'warning',
          center:true
        });
        return;
      }else if(this.user.confirm !== this.user.password){
        this.$message({
          message: '注册失败,两次密码不一致',
          type: 'warning',
          center:true
        });
        return;
      }
      this.request.post("/user/register",this.user).then(res => {
        this.$message({
          message: "注册成功！欢迎使用~",
          type: 'success',
          center:true
        });
        this.$router.push("/login")
      })
    },
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

.right .submit {
  width: 60%;
  height: 3rem;
  color: #666;
  background-image: linear-gradient(120deg, #8ec5fc 0%,#e0c3fc  100%);
  font-size: 1rem;
  border: none;
  border-radius: 0.5rem;
  margin: 3rem 0 0 50%;
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

.alert {
  top: 1rem;
  left: calc(50% - 35rem/2);
  position: fixed;
  line-height: 3rem;
  text-align: center;
  width: 35rem;
  height: 3rem;
}

</style>