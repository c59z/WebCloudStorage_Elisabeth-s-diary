<template>
  <div>
    <div class="userCenter">

      <!--上传头像-->
      <div @close="clearTempImage" class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h1 class="modal-title fs-5" id="staticBackdropLabel">个性化</h1>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <div class="uploadAvatar">
                <div style="display: inline-block;margin: 0 1rem">
                  <div>当前头像</div>
                  <img v-if="userInfo.avatar === 'data:image/jpeg;base64,null'" class="avatar_upload" src="@/assets/svg/person-circle.svg">
                  <img v-else class="avatar_upload" :src="userInfo.avatar">
                </div>
                <div style="display: inline-block;margin: 0 1rem">
                  <div>新头像</div>
                  <img v-if="this.previewPicture===null" class="avatar_upload" src="@/assets/svg/person-circle.svg">
                  <img v-else class="avatar_upload" :src="this.previewPicture">
                </div>

                <el-upload
                    class="avatar-uploader"
                    action="/api/userCenter/uploadAvatar"
                    :headers="{Token: token}"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload"
                >
                  <div class="input-group mb-3" style="margin: 1rem auto;padding: 0 2rem">
                    <span style="margin-right: 1rem;">上传头像</span>
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-upload" viewBox="0 0 16 16">
                      <path d="M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5z"/>
                      <path d="M7.646 1.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L8.5 2.707V11.5a.5.5 0 0 1-1 0V2.707L5.354 4.854a.5.5 0 1 1-.708-.708l3-3z"/>
                    </svg>
                  </div>
                </el-upload>
              </div>
            </div>
            <div class="modal-footer">
              <button @click="clearTempImage" type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
              <button @click="saveNewAvatar" type="button" class="btn btn-primary">使用新头像</button>
            </div>
          </div>
        </div>
      </div>

      <div  :class="isSmallScreen ? 'centerAreaSmall' : 'centerAreaFull'">
        <!--头像之类的-->
        <!--TODO 点击头像可以弹出头像修改框 -->
        <div class="centerTop">
              <img v-if="userInfo.avatar === 'data:image/jpeg;base64,null'" class="avatar_big" data-bs-toggle="modal" data-bs-target="#staticBackdrop" src="@/assets/svg/person-circle.svg">
              <img v-else class="avatar_big" data-bs-toggle="modal" data-bs-target="#staticBackdrop" :src="userInfo.avatar">
              <div class="userInfoNameSpan">{{ userInfo.username }}</div>
        </div>

        <!--个人信息与修改-->
        <div class="centerBottom">
          <div style="width: auto" class="row">
            <div style="text-align: center" class="btn-group mx-auto" role="group">
              <router-link class="col-6 btnUserOptions" to="/userCenter/userInfoTable">
                <button type="button" style="color: white" class="btn">个人信息</button>
              </router-link>
              <router-link class="col-6 btnUserOptions" to="/userCenter/changeUserInfo">
                <button type="button" style="color: white" class="btn">密码修改</button>
              </router-link>
            </div>
          </div>

          <router-view class=""></router-view>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "UserCenter",
  data(){
    return {
      userInfo: {
        "username": "temp",
        "avatar": "",
        "email": "xxxxx@xx.com",
      },
      AvatarBase64: {
        "avatar": null,
      },
      uploadAvatarUrl: null,
      token: window.localStorage.getItem("token"),

      previewPicture:null,
    }
  },
  methods: {
    // 上传成功时
    handleAvatarSuccess(res, file) {
      this.uploadAvatarUrl = URL.createObjectURL(file.raw);
      if(res.code === 200){
        this.$message.success(res.msg)
        this.AvatarBase64.avatar = res.data.previewPicture
        this.previewPicture ="data:image/jpeg;base64," + this.AvatarBase64.avatar
      }
    },
    // 上传之前
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    clearTempImage(){
      this.previewPicture = null;
    },
    saveNewAvatar(){
      // TODO 将this.previewPicture传给后端，然后存到数据库中,
      if(this.previewPicture === null){
        this.$message.warning("请选择要上传的头像~");
        return
      }
      this.request.put("/userCenter/saveAvatar",this.AvatarBase64).then(res => {
        if (res.code === 200){
          this.$message.success(res.msg);
          // 成功修改头像后，重新获取userInfo
          this.reGetUserInfo()
        }else{
          this.$message.warning(res.msg);
        }
      }).catch(err => {
        this.$message.error(err.msg);
        console.log(err)
      })
    },
    reGetUserInfo(){
      const _this = this
      this.request.get("/userCenter/getUserInfo").then(res => {
        console.log(res)
        const userInfo = res.data.userInfo
        userInfo.avatar = "data:image/jpeg;base64,"+userInfo.avatar
        _this.$store.commit('SET_USERINFO', userInfo)
        window.location.reload()
      })
    },
  },
  mounted() {
    this.getUserInfoByLocal()
  },
}
</script>

<style scoped>

.userCenter {
  padding: 1rem;
  float: left;;
  display: inline-block;
  background-color: #303030;
  max-height: calc(100vh - 50px);
}

.centerAreaFull , .centerAreaSmall {
  margin-top: 5rem;
  position: relative;
  left: 50%;
  transform: translateX(-50%);
  display: inline-block;
  overflow: hidden;
}

.centerAreaFull {
  width: 55rem;
  height: 35rem;
  border-radius: 2rem;
}

.centerAreaSmall {
  width: 28rem;
  height: 40rem;
  border-radius: 2rem;
}

.centerTop {
  text-align: center;
  height: 40%;
  background-color: #424242;
}

.uploadAvatar {
  text-align: center;
  height: 40%;
  background-color: rgba(0,0,0,0);
}

.avatar_big , .avatar_upload {
  margin-top: 1rem;
  text-align: center;
  width: 8rem;
  height: 8rem;
  border-radius: 4rem;
}

.avatar_big {
  cursor: pointer;
  margin-top: 1rem;
}

.btn-close{
  display:none !important;
}


.userInfoNameSpan {
  font-size: 24px;
  margin-top: 1.5rem;
}


.centerBottom{
  height: 60%;
  background-color: rgba(255,255,255,0.3);
}

.row {

}

.btnUserOptions {
  display: inline-block;
  border: none;
  border-radius: 0;
  color: white;
}

.btnUserOptions:hover {
  border: none;
  color: white;
  background-color: rgba(255,255,255,0.3);
}

.btnUserOptions > button:hover {
  border: none;
}

.btnUserOptions > button {
  border: none;
}

.btn-group {
  margin: 0 2rem;
}

.modal-content {
  background-color: #6f6f6f;
}

</style>