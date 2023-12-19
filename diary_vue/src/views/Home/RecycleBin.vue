<template>
  <div>
    <!-- TODO 移动端未实现删除/还原功能   -->
    <div v-if="isNull" class="nullTipDiv">
      <div>
        <img style="width: 20rem;height: 20rem;" src="@/assets/svg/trash3.svg">
      </div>
      <span
          style="display: inline-block;color: white;font-size: 24px;margin-top: 2rem">回收站里什么都没有</span>
    </div>
    <div v-else class="tableArea">
      <div v-show="!isSmallScreen" class="menuOptions row">
        <div class="col">
          <button data-bs-toggle="modal" data-bs-target="#btnDeleteAll" type="button" class="btn btn-outline-danger">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-trash"
                 viewBox="0 0 16 16">
              <path
                  d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
              <path fill-rule="evenodd"
                    d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
            </svg>
            清空回收站
          </button>
          <button data-bs-toggle="modal" data-bs-target="#btnDeleteSelectedFiles" type="button"
                  class="btn btn-outline-warning">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-trash3"
                 viewBox="0 0 16 16">
              <path
                  d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
            </svg>
            删除所选内容
          </button>
          <button @click="getCloudFiles('', 0)" data-bs-toggle="modal" data-bs-target="#btnMoveSelectedFiles" type="button"
                  class="btn btn-outline-info">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                 class="bi bi-arrow-counterclockwise" viewBox="0 0 16 16">
              <path fill-rule="evenodd" d="M8 3a5 5 0 1 1-4.546 2.914.5.5 0 0 0-.908-.417A6 6 0 1 0 8 2v1z"/>
              <path
                  d="M8 4.466V.534a.25.25 0 0 0-.41-.192L5.23 2.308a.25.25 0 0 0 0 .384l2.36 1.966A.25.25 0 0 0 8 4.466z"/>
            </svg>
            还原所选内容
          </button>
        </div>
      </div>
      <hr class="myHr">
      <table class="table table-dark">
        <thead v-if="isSmallScreen">
        <tr class="th_color">
          <th style="padding-left: 1rem" scope="col" class="col-7">名称</th>
        </tr>
        </thead>
        <thead v-else>
        <tr class="th_color">
          <th style="padding-left: 1rem" scope="col" class="col-7">名称</th>
          <th scope="col" class="col-3">大小</th>
          <th scope="col" class="col">修改日期</th>
        </tr>
        </thead>
        <tbody v-if="isSmallScreen">
        <!--文件夹 TODO 多选文件夹后面再写-->
        <tr v-for="folder in folderInfoList" :key="folder.folder_name">
          <td style="padding-left: 1rem" class="td_color">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                 class="bi bi-folder-fill" viewBox="0 0 16 16">
              <path
                  d="M9.828 3h3.982a2 2 0 0 1 1.992 2.181l-.637 7A2 2 0 0 1 13.174 14H2.825a2 2 0 0 1-1.991-1.819l-.637-7a1.99 1.99 0 0 1 .342-1.31L.5 3a2 2 0 0 1 2-2h3.672a2 2 0 0 1 1.414.586l.828.828A2 2 0 0 0 9.828 3zm-8.322.12C1.72 3.042 1.95 3 2.19 3h5.396l-.707-.707A1 1 0 0 0 6.172 2H2.5a1 1 0 0 0-1 .981l.006.139z"/>
            </svg>
            <router-link
                :to="{ path: '/main', query: { path: currentPath ? currentPath+'/'+folder.folder_name : '/'+folder.folder_name} }">
              <span class="fileNameSpan">{{ folder.folder_name }}</span>
            </router-link>
          </td>
        </tr>
        <tr v-for="file in fileInfoList"
            :key="file.file_name">
          <td style="padding-left: 1rem" class="td_color">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                 class="bi bi-book-half" viewBox="0 0 16 16">
              <path
                  d="M8.5 2.687c.654-.689 1.782-.886 3.112-.752 1.234.124 2.503.523 3.388.893v9.923c-.918-.35-2.107-.692-3.287-.81-1.094-.111-2.278-.039-3.213.492V2.687zM8 1.783C7.015.936 5.587.81 4.287.94c-1.514.153-3.042.672-3.994 1.105A.5.5 0 0 0 0 2.5v11a.5.5 0 0 0 .707.455c.882-.4 2.303-.881 3.68-1.02 1.409-.142 2.59.087 3.223.877a.5.5 0 0 0 .78 0c.633-.79 1.814-1.019 3.222-.877 1.378.139 2.8.62 3.681 1.02A.5.5 0 0 0 16 13.5v-11a.5.5 0 0 0-.293-.455c-.952-.433-2.48-.952-3.994-1.105C10.413.809 8.985.936 8 1.783z"/>
            </svg>
            <span class="fileNameSpan">{{ file.file_name }}</span>
          </td>
        </tr>
        </tbody>
        <tbody v-else>

        <tr
            v-for="folder in folderInfoList"
            :key="folder.folder_name"
            @click="selectCurrentIndex(folder.folder_name)"
            :class=" {'selectedCurrentRow': selectedList.includes(folder.folder_name)}"
        >
          <td style="padding-left: 1rem" class="td_color">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                 class="bi bi-folder-fill" viewBox="0 0 16 16">
              <path
                  d="M9.828 3h3.982a2 2 0 0 1 1.992 2.181l-.637 7A2 2 0 0 1 13.174 14H2.825a2 2 0 0 1-1.991-1.819l-.637-7a1.99 1.99 0 0 1 .342-1.31L.5 3a2 2 0 0 1 2-2h3.672a2 2 0 0 1 1.414.586l.828.828A2 2 0 0 0 9.828 3zm-8.322.12C1.72 3.042 1.95 3 2.19 3h5.396l-.707-.707A1 1 0 0 0 6.172 2H2.5a1 1 0 0 0-1 .981l.006.139z"/>
            </svg>
            <span class="fileNameSpan">{{ folder.folder_name }}</span>
          </td>
          <td></td>
          <td class="td_color">{{ folder.modified_date }}</td>
        </tr>
        <!--文件-->
        <tr
            v-for="file in fileInfoList"
            :key="file.file_name"
            @click="selectCurrentIndex(file.file_name)"
            :class=" {'selectedCurrentRow': selectedList.includes(file.file_name)}"
        >
          <td style="padding-left: 1rem" class="td_color">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                 class="bi bi-book-half" viewBox="0 0 16 16">
              <path
                  d="M8.5 2.687c.654-.689 1.782-.886 3.112-.752 1.234.124 2.503.523 3.388.893v9.923c-.918-.35-2.107-.692-3.287-.81-1.094-.111-2.278-.039-3.213.492V2.687zM8 1.783C7.015.936 5.587.81 4.287.94c-1.514.153-3.042.672-3.994 1.105A.5.5 0 0 0 0 2.5v11a.5.5 0 0 0 .707.455c.882-.4 2.303-.881 3.68-1.02 1.409-.142 2.59.087 3.223.877a.5.5 0 0 0 .78 0c.633-.79 1.814-1.019 3.222-.877 1.378.139 2.8.62 3.681 1.02A.5.5 0 0 0 16 13.5v-11a.5.5 0 0 0-.293-.455c-.952-.433-2.48-.952-3.994-1.105C10.413.809 8.985.936 8 1.783z"/>
            </svg>
            <span class="fileNameSpan">{{ file.file_name }}</span>
          </td>
          <td class="td_color">{{ file.file_size }}</td>
          <td class="td_color">{{ file.modified_date }}</td>
        </tr>
        </tbody>
      </table>
    </div>
    <div class="modal fade" id="btnDeleteAll" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
         aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="staticBackdropLabel">Tip</h1>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            确定要清空回收站中的所有内容吗?
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
            <button @click="deleteAll" type="button" class="btn btn-primary" data-bs-dismiss="modal">确认</button>
          </div>
        </div>
      </div>
    </div>
    <div class="modal fade" id="btnDeleteSelectedFiles" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
         aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="staticBackdropLabel">Tip</h1>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body d-flex align-items-center justify-content-center">
            <div>
              你确定要将下列文件移动到回收站吗？
              <ul style="color: #aaa;margin-top: 1rem;" v-for="file in this.selectedList" :key="file">
                {{ file }}
              </ul>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
            <button @click="deleteSelectedFiles" type="button" class="btn btn-primary" data-bs-dismiss="modal">确认
            </button>
          </div>
        </div>
      </div>
    </div>
    <div class="modal fade" id="btnMoveSelectedFiles" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
         aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="staticBackdropLabel">移动到</h1>
          </div>
          <div class="modal-body">
            <div class="tableAreaMove">

              <div id="my-breadcrumb" class="col">
                <div @click="returnPage" style="margin-bottom: 0.5rem;cursor:pointer;">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                       class="bi bi-chevron-bar-left" viewBox="0 0 16 16">
                    <path fill-rule="evenodd"
                          d="M11.854 3.646a.5.5 0 0 1 0 .708L8.207 8l3.647 3.646a.5.5 0 0 1-.708.708l-4-4a.5.5 0 0 1 0-.708l4-4a.5.5 0 0 1 .708 0zM4.5 1a.5.5 0 0 0-.5.5v13a.5.5 0 0 0 1 0v-13a.5.5 0 0 0-.5-.5z"/>
                  </svg>
                  <!-- TODO 如何返回上一级目录？ -->
                  <span style="vertical-align: middle;">
                    上一级
                  </span>
                </div>
                <div class="breadcrumb">
                  <span>当前路径:&nbsp;&nbsp;</span>
                  <span>{{this.targetPath}}</span>
                </div>
              </div>

              <!-- TODO 选择文件要移动到哪个目录 -->
              <table class="table table-dark">
                <thead>
                <tr class="th_color">
                  <th style="padding-left: 1rem" scope="col" class="col">名称</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="folder in moveFolderInfoList" :key="folder.folder_name">
                  <td style="padding-left: 1rem" class="td_color">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                         class="bi bi-folder-fill" viewBox="0 0 16 16">
                      <path
                          d="M9.828 3h3.982a2 2 0 0 1 1.992 2.181l-.637 7A2 2 0 0 1 13.174 14H2.825a2 2 0 0 1-1.991-1.819l-.637-7a1.99 1.99 0 0 1 .342-1.31L.5 3a2 2 0 0 1 2-2h3.672a2 2 0 0 1 1.414.586l.828.828A2 2 0 0 0 9.828 3zm-8.322.12C1.72 3.042 1.95 3 2.19 3h5.396l-.707-.707A1 1 0 0 0 6.172 2H2.5a1 1 0 0 0-1 .981l.006.139z"/>
                    </svg>
                    <span
                        @click="getCloudFiles(folder.folder_name, 0)"
                        class="fileNameSpan"
                    >{{ folder.folder_name }}</span>
                  </td>
                </tr>
                </tbody>
              </table>

            </div>
          </div>
          <div class="modal-footer">
            <button @click="cancelSelectFiles" type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
            <button @click="moveSelectedFiles" type="button" class="btn btn-primary" data-bs-dismiss="modal">确认
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "RecycleBin",
  data() {
    return {
      isNull: false,
      currentPath: undefined,
      selectedList: [],
      deleteDto: {
        "deleteList": [],
        "folderPath": ""
      },
      folderInfoList: [],
      fileInfoList: [],
      breadcrumbList: ["",],
      moveFolderInfoList: [],
      targetPath: "",
      moveDto :{
        "fileList": [],
        "sourcePath":"/",
        "targetPath":"/"
      },
    }
  },
  methods: {
    // 获取用户回收站里的文件
    getRecycleBinFiles() {
      // this.isLoading = true
      this.request.get("/Cloud/getRecycleBinFiles").then(res => {
        if (res.code === 200) {
          this.selectedList = []
          this.fileInfoList = res.data.fileList
          this.folderInfoList = res.data.folderList
          this.isLoading = false
          this.isNull = this.fileInfoList.length === 0 && this.folderInfoList.length === 0;
        }
      })
    },
    // 获取用户目录的文件夹
    getCloudFiles(currentPath, option) {
      this.isLoading = true
      if (option === undefined || option === 0){
        if (this.targetPath === null || this.targetPath === "" || this.targetPath === undefined) {
          this.targetPath = "/"
        } else {
          this.targetPath += "/" + currentPath
        }
      }else{
        this.targetPath = currentPath
      }
      const _this = this
      this.targetPath = this.removeSpecialCharacters(this.targetPath)
      this.currentPath = encodeURIComponent(this.targetPath)
      this.request.get("/Cloud/getFilesByPath?path=" + this.currentPath).then(res => {
        if (res.code === 200) {
          this.moveDto.fileList = this.selectedList
          this.moveDto.targetPath = this.targetPath
          this.moveFolderInfoList = res.data.folderList;
        }
      })
    },
    // 取消移动文件
    cancelSelectFiles(){
      this.targetPath = ""
      this.getCloudFiles("/",1)
    },
    // 清除字符串中连续的/
    removeSpecialCharacters(str){
      return str.replace(/\/{2,}/g, '/')
    },
    // 清除回收站中所有的文件
    deleteAll() {
      this.request.delete("/Cloud/RecycleBin/deleteAll").then(res => {
        this.$message({
          message: res.msg,
          type: 'success',
          center: true
        });
      }).catch(err => {
        this.$message({
          message: err.msg,
          type: 'error',
          center: true
        });
      })
      location.reload()
    },
    // 清除用户选择的内容
    deleteSelectedFiles() {
      this.deleteDto.deleteList = this.selectedList
      this.request.post("/Cloud/RecycleBin/deleteFiles", this.deleteDto).then(res => {
        console.log(res)
        if (res.code === 200) {
          this.$message({
            message: res.msg,
            type: 'success',
            center: true
          });
          location.reload()
        }
      }).catch(err => {
        this.$message({
          message: err.msg,
          type: 'error',
          center: true
        });
      })
    },
    // 返回上级目录
    returnPage() {
      console.log("返回上级")
      if (this.targetPath !== '/'){
        let lastIndex = this.targetPath.lastIndexOf('/')
        if (lastIndex !== -1){
          this.targetPath = this.targetPath.substring(0,lastIndex)
        }
      }
      this.getCloudFiles(this.targetPath, 1)
    },
    // 移动所选择的文件
    moveSelectedFiles(){
      this.moveDto.targetPath += "/"
      console.log("要移动的文件: "+this.moveDto.fileList)
      console.log("要移动到哪里: "+this.moveDto.targetPath)
      const _this = this
      this.request.put("/Cloud/RecycleBin/reduce",this.moveDto).then(res => {
        if (res.code === 200) {
          this.$message({
            message: res.msg,
            type: 'success',
            center: true
          });
          location.reload()
        }
      })
    },
    // 点击单选要操作的内容
    selectCurrentIndex(file_name) {
      // 如果已经选择了这个列表就取消选择这个列表
      if (this.selectedList.includes(file_name)) {
        // 之前已经选过了
        let index = this.selectedList.indexOf(file_name);
        this.selectedList.splice(index, 1)
      } else {
        // 没选择
        this.selectedList.push(file_name)
      }
      console.log("你选择了: " + this.selectedList)
    },
  },
  mounted() {
    this.getRecycleBinFiles()
  },
  watch: {
    targetPath: function (newStr){
      if (newStr === ""){
        this.targetPath = "/"
      }
    }
  },
}
</script>

<style scoped>
.tableArea, .tableAreaMove {
  max-height: 78vh;
  overflow: hidden;
}

a {
  color: white;
  text-decoration: none;
}

ol {
  float: left;
  margin: 0;
}


.breadcrumb > li + li:before {
  color: #CCCCCC;
  content: " > ";
  padding: 0 5px;
}

.breadcrumb > li > a {
  border-radius: 5px;
  padding: 8px 15px;
}

.breadcrumb > li > a:hover {
  background-color: rgba(255, 255, 255, 0.2);
}


.table-dark {
  --bs-table-bg: #303030;
  --bs-table-hover-bg: #404040;
}

.th_color {
  font-style: normal;
}

.td_color {
  color: rgba(255, 255, 255, 0.7);
  max-width: 1000px !important;
}

.table {
  user-select: none;
}

table > tbody > tr:hover {
  --bs-table-bg: rgba(213, 203, 203, 0.2);
}

.fileNameSpan {
  cursor: pointer;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 1000px !important;
  margin-left: 1rem;
}

.menuOptions {
  padding: 0 1rem;
  height: 48px;
}

.myHr {
  margin: 0;
  padding: 0;
}

.btn-outline-info, .btn-outline-danger, .btn-outline-warning {
  margin-top: 4px;
  margin-right: 2rem;
}

.modal-content {
  background-color: #424242;
}


/**
选中列表
 */
.selectedCurrentRow {
  --bs-table-bg: #394853;
}

.selectedCurrentRow:hover {
  --bs-table-bg: #394853;
  background-color: #394853;
}

.nullTipDiv {
  margin-top: 5rem;
  width: 100%;
  text-align: center;
}

a {
  color: white;
  text-decoration: none;
}

.breadcrumb-item {
  color: #fff;
}


ol {
  float: left;
  margin: 0;
}


#my-breadcrumb {
  display: inline-block;
  margin: 0.5rem 0;
}

.breadcrumb > li + li:before {
  color: #CCCCCC;
  content: " > ";
  padding: 0 5px;
}

.breadcrumb > li > a {
  border-radius: 5px;
  padding: 8px 15px;
}

.breadcrumb > li > a:hover {
  background-color: rgba(255, 255, 255, 0.2);
}


</style>
