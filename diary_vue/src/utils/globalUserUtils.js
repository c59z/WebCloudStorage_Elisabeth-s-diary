export default {
    data() {
        return {

        };
    },
    methods: {
        // 从sessionStorage中获取userInfo
        getUserInfoByLocal(){
            this.userInfo = JSON.parse(window.localStorage.getItem("userInfo"))
        },
        getUserStorageInfo(){
            // 获取后端传来的"用户已经使用的大小" TODO 到时候后端把值计算完成拼接字符串 再传过来就行了
            this.request.get("/Cloud/getUsedStorage").then(res => {
                this.usedStorage.usedStorageSpace = res.data.usedStorage
                this.usedStorage.storageSpace = res.data.size
                this.usedStorage.occupancy = res.data.percentage
            })
            this.usedStorage.occupancy = 0.148

            // 把值转为style样式中的width: *%
            this.widthValue = `${this.usedStorage.occupancy * 100}%`
        },
        // 用户退出
        userLogout() {
            const _this = this
            this.request.post("/user/logout").then(res => {
                _this.$store.commit("REMOVE_INFO")
                _this.$router.push("/login")
                this.$message({
                    message: res.msg,
                    type: 'success',
                    center:true
                });
            })
        },
    },
    mounted() {

    },
    beforeDestroy() {


    }
};
