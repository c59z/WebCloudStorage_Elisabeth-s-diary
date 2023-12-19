<template>
  <div v-if="isSelect">
    <div class="bg-dark optionsMenuBar container-fluid">
      <div class="row myMenuBarRow align-items-center">
        <!-- TODO 移动端时,名称显示不全-->
        <div v-if="this.selectedList.length === 1" class="col-5 myMenuCol align-items-center">
          <div @click="returnHeader" class="returnBtn">
            <div class="my-icon">
              <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor"
                   class="bi bi-caret-left" viewBox="0 0 16 16">
                <path
                    d="M10 12.796V3.204L4.519 8 10 12.796zm-.659.753-5.48-4.796a1 1 0 0 1 0-1.506l5.48-4.796A1 1 0 0 1 11 3.204v9.592a1 1 0 0 1-1.659.753z"/>
              </svg>
            </div>
          </div>
          <span>
            {{ FileTitle }}
          </span>
        </div>
        <div v-else class="col-5 myMenuCol align-items-center">
          <div @click="returnHeader" class="returnBtn">
            <div class="my-icon">
              <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor"
                   class="bi bi-caret-left" viewBox="0 0 16 16">
                <path
                    d="M10 12.796V3.204L4.519 8 10 12.796zm-.659.753-5.48-4.796a1 1 0 0 1 0-1.506l5.48-4.796A1 1 0 0 1 11 3.204v9.592a1 1 0 0 1-1.659.753z"/>
              </svg>
            </div>
          </div>
          {{ this.selectedList.length }}个对象
        </div>
        <div class="col-7 myMenuCol align-items-center">
          <div class="optionsContext">
            <!--删除-->
            <div title="将所选文件移入回收站" class="OptionButton" data-bs-toggle="modal" data-bs-target="#moveTomoveToRecycleBin">
              <div class="my-icon">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-trash"
                     viewBox="0 0 16 16">
                  <path
                      d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
                  <path fill-rule="evenodd"
                        d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
                </svg>
              </div>
            </div>
            <!--移动-->
            <div title="移动当前所选文件" @click="getCloudFiles('/',1)" class="OptionButton" data-bs-toggle="modal" data-bs-target="#btnMoveSelectedFiles">
              <div class="my-icon">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                     class="bi bi-folder-symlink-fill" viewBox="0 0 16 16">
                  <path
                      d="M13.81 3H9.828a2 2 0 0 1-1.414-.586l-.828-.828A2 2 0 0 0 6.172 1H2.5a2 2 0 0 0-2 2l.04.87a1.99 1.99 0 0 0-.342 1.311l.637 7A2 2 0 0 0 2.826 14h10.348a2 2 0 0 0 1.991-1.819l.637-7A2 2 0 0 0 13.81 3zM2.19 3c-.24 0-.47.042-.683.12L1.5 2.98a1 1 0 0 1 1-.98h3.672a1 1 0 0 1 .707.293L7.586 3H2.19zm9.608 5.271-3.182 1.97c-.27.166-.616-.036-.616-.372V9.1s-2.571-.3-4 2.4c.571-4.8 3.143-4.8 4-4.8v-.769c0-.336.346-.538.616-.371l3.182 1.969c.27.166.27.576 0 .742z"/>
                </svg>
              </div>
            </div>
            <!--重命名-->
            <div title="重命名当前文件" v-show="this.selectedList.length <= 1" class="OptionButton" data-bs-toggle="modal" data-bs-target="#renameMode">
              <div class="my-icon">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                     class="bi bi-pencil-square" viewBox="0 0 16 16">
                  <path
                      d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                  <path fill-rule="evenodd"
                        d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                </svg>
              </div>
            </div>
            <!--下载-->
            <div v-show="selectedList.length >= 1" title="下载当前所选文件" @click="downloadFile" class="OptionButton">
              <div class="my-icon">
                <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-cloud-arrow-down" viewBox="0 0 16 16">
                  <path fill-rule="evenodd" d="M7.646 10.854a.5.5 0 0 0 .708 0l2-2a.5.5 0 0 0-.708-.708L8.5 9.293V5.5a.5.5 0 0 0-1 0v3.793L6.354 8.146a.5.5 0 1 0-.708.708l2 2z"/>
                  <path d="M4.406 3.342A5.53 5.53 0 0 1 8 2c2.69 0 4.923 2 5.166 4.579C14.758 6.804 16 8.137 16 9.773 16 11.569 14.502 13 12.687 13H3.781C1.708 13 0 11.366 0 9.318c0-1.763 1.266-3.223 2.942-3.593.143-.863.698-1.723 1.464-2.383zm.653.757c-.757.653-1.153 1.44-1.153 2.056v.448l-.445.049C2.064 6.805 1 7.952 1 9.318 1 10.785 2.23 12 3.781 12h8.906C13.98 12 15 10.988 15 9.773c0-1.216-1.02-2.228-2.313-2.228h-.5v-.5C12.188 4.825 10.328 3 8 3a4.53 4.53 0 0 0-2.941 1.1z"/>
                </svg>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 重命名弹窗 -->
    <div class="modal fade"  id="renameMode" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <div class="modal-title fs-5" id="staticBackdropLabel">重命名</div>
          </div>
          <div class="modal-body d-flex align-items-center justify-content-center">
            <br>
            <div class="input-group mb-3" style="margin-bottom: 0px!important;">
              <span class="input-group-text" id="inputGroup-sizing-default">文件名称</span>
              <input v-model.trim="renameFile" type="text" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
            <button id="renamebtn" @click="rename(renameFile)" type="button" class="btn btn-primary" data-bs-dismiss="modal">确认</button>
          </div>
        </div>
      </div>
    </div>
    <!-- 移动到回收站 -->
    <div class="modal fade"  id="moveTomoveToRecycleBin" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <div class="modal-title fs-5" id="staticBackdropLabel">移入回收站？</div>
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
            <button @click="moveToRecycleBin" type="button" class="btn btn-primary" data-bs-dismiss="modal">确认</button>
          </div>
        </div>
      </div>
    </div>
    <!-- 移动文件 -->
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
  <div v-else>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark" aria-label="Offcanvas navbar large">
      <div class="container-fluid">
        <div class="diary_ico_border" style="float:left;" type="button" data-bs-toggle="collapse"
             data-bs-target="#collapseWidthExample" aria-expanded="false" aria-controls="collapseWidthExample">
          <div class="diary_ico">
            <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-book"
                 viewBox="0 0 16 16">
              <path
                  d="M1 2.828c.885-.37 2.154-.769 3.388-.893 1.33-.134 2.458.063 3.112.752v9.746c-.935-.53-2.12-.603-3.213-.493-1.18.12-2.37.461-3.287.811V2.828zm7.5-.141c.654-.689 1.782-.886 3.112-.752 1.234.124 2.503.523 3.388.893v9.923c-.918-.35-2.107-.692-3.287-.81-1.094-.111-2.278-.039-3.213.492V2.687zM8 1.783C7.015.936 5.587.81 4.287.94c-1.514.153-3.042.672-3.994 1.105A.5.5 0 0 0 0 2.5v11a.5.5 0 0 0 .707.455c.882-.4 2.303-.881 3.68-1.02 1.409-.142 2.59.087 3.223.877a.5.5 0 0 0 .78 0c.633-.79 1.814-1.019 3.222-.877 1.378.139 2.8.62 3.681 1.02A.5.5 0 0 0 16 13.5v-11a.5.5 0 0 0-.293-.455c-.952-.433-2.48-.952-3.994-1.105C10.413.809 8.985.936 8 1.783z"/>
            </svg>
          </div>
        </div>
        <router-link class="navbar-brand" to="/main">
          <a class="navbar-brand" href="#">Elisabeth's diary</a>
        </router-link>
        <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasNavbar2"
                aria-controls="offcanvasNavbar2">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="offcanvas offcanvas-end text-bg-dark" tabindex="-1" id="offcanvasNavbar2"
             aria-labelledby="offcanvasNavbar2Label">
          <div class="offcanvas-header">
            <h5 class="offcanvas-title" id="offcanvasNavbar2Label">Elisabeth's diary</h5>
            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="offcanvas"
                    aria-label="Close"></button>
          </div>
          <!--移动端适配-->
          <div v-if="isSmallScreen" class="offcanvas-body">
            <ul class="navbar-nav justify-content-end flex-grow-1 pe-3">
              <!--头像-->
              <li style="" class="nav-item">
                <button class="btn my-icon switch_btn_area userInfoArea" type="submit">
                  <img v-if="userInfo.avatar === 'data:image/jpeg;base64,null'" class="avatar_big"
                       src="@/assets/svg/person-circle.svg">
                  <img v-else class="avatar_big" :src="userInfo.avatar">
                  <span class="userInfoNameSpan">{{ userInfo.username }}</span>
                  <span class="userInfoNameEmail">{{
                      userInfo.email ? userInfo.email : "邮箱尚未设置&nbsp&nbsp&nbsp&nbsp&nbsp"
                    }}</span>
                </button>
              </li>
              <!--搜索-->
              <form style="align-items: center;" class="d-flex mt-3 mt-md-0" role="search">
                <input @input="searchFiles" class="form-control my-form-search " type="text" placeholder="搜索...">
              </form>
              <div style="width: 60rem"></div>

              <!--个人中心-->
              <li class="nav-item nav-item-small">
                <router-link to="/userCenter">
                  <button class="btn my-icon" type="submit">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                         class="bi bi-person-vcard" viewBox="0 0 16 16">
                      <path
                          d="M5 8a2 2 0 1 0 0-4 2 2 0 0 0 0 4Zm4-2.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5ZM9 8a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4A.5.5 0 0 1 9 8Zm1 2.5a.5.5 0 0 1 .5-.5h3a.5.5 0 0 1 0 1h-3a.5.5 0 0 1-.5-.5Z"/>
                      <path fill-rule="evenodd"
                            d="M2 2a2 2 0 0 0-2 2v8a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V4a2 2 0 0 0-2-2H2ZM1 4a1 1 0 0 1 1-1h12a1 1 0 0 1 1 1v8a1 1 0 0 1-1 1H8.96c.026-.163.04-.33.04-.5C9 10.567 7.21 9 5 9c-2.086 0-3.8 1.398-3.984 3.181A1.006 1.006 0 0 1 1 12V4Z"/>
                    </svg>
                    <span>用户中心</span>
                  </button>
                </router-link>
              </li>
<!--              <li @click="switchDarkOrLight" class="nav-item nav-item-small">-->
<!--                <button v-if="isDark" class="btn my-icon" type="submit">-->
<!--                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"-->
<!--                       class="bi bi-brightness-high" viewBox="0 0 16 16">-->
<!--                    <path-->
<!--                        d="M8 11a3 3 0 1 1 0-6 3 3 0 0 1 0 6zm0 1a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM8 0a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 0zm0 13a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 13zm8-5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2a.5.5 0 0 1 .5.5zM3 8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2A.5.5 0 0 1 3 8zm10.657-5.657a.5.5 0 0 1 0 .707l-1.414 1.415a.5.5 0 1 1-.707-.708l1.414-1.414a.5.5 0 0 1 .707 0zm-9.193 9.193a.5.5 0 0 1 0 .707L3.05 13.657a.5.5 0 0 1-.707-.707l1.414-1.414a.5.5 0 0 1 .707 0zm9.193 2.121a.5.5 0 0 1-.707 0l-1.414-1.414a.5.5 0 0 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .707zM4.464 4.465a.5.5 0 0 1-.707 0L2.343 3.05a.5.5 0 1 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .708z"/>-->
<!--                  </svg>-->
<!--                  <span>日间模式</span>-->
<!--                </button>-->
<!--                <button v-else class="btn my-icon" type="submit">-->
<!--                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"-->
<!--                       class="bi bi-moon-stars" viewBox="0 0 16 16">-->
<!--                    <path-->
<!--                        d="M6 .278a.768.768 0 0 1 .08.858 7.208 7.208 0 0 0-.878 3.46c0 4.021 3.278 7.277 7.318 7.277.527 0 1.04-.055 1.533-.16a.787.787 0 0 1 .81.316.733.733 0 0 1-.031.893A8.349 8.349 0 0 1 8.344 16C3.734 16 0 12.286 0 7.71 0 4.266 2.114 1.312 5.124.06A.752.752 0 0 1 6 .278zM4.858 1.311A7.269 7.269 0 0 0 1.025 7.71c0 4.02 3.279 7.276 7.319 7.276a7.316 7.316 0 0 0 5.205-2.162c-.337.042-.68.063-1.029.063-4.61 0-8.343-3.714-8.343-8.29 0-1.167.242-2.278.681-3.286z"/>-->
<!--                    <path-->
<!--                        d="M10.794 3.148a.217.217 0 0 1 .412 0l.387 1.162c.173.518.579.924 1.097 1.097l1.162.387a.217.217 0 0 1 0 .412l-1.162.387a1.734 1.734 0 0 0-1.097 1.097l-.387 1.162a.217.217 0 0 1-.412 0l-.387-1.162A1.734 1.734 0 0 0 9.31 6.593l-1.162-.387a.217.217 0 0 1 0-.412l1.162-.387a1.734 1.734 0 0 0 1.097-1.097l.387-1.162zM13.863.099a.145.145 0 0 1 .274 0l.258.774c.115.346.386.617.732.732l.774.258a.145.145 0 0 1 0 .274l-.774.258a1.156 1.156 0 0 0-.732.732l-.258.774a.145.145 0 0 1-.274 0l-.258-.774a1.156 1.156 0 0 0-.732-.732l-.774-.258a.145.145 0 0 1 0-.274l.774-.258c.346-.115.617-.386.732-.732L13.863.1z"/>-->
<!--                  </svg>-->
<!--                  <span>夜间模式</span>-->
<!--                </button>-->
<!--              </li>-->
              <!--首页-->
              <li class="nav-item nav-item-small">
                <button @click="retrunMain" class="btn my-icon" type="submit">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-house"
                       viewBox="0 0 16 16">
                    <path fill-rule="evenodd"
                          d="M8.707 1.5a1 1 0 0 0-1.414 0L.646 8.146a.5.5 0 0 0 .708.708L2 8.207V13.5A1.5 1.5 0 0 0 3.5 15h9a1.5 1.5 0 0 0 1.5-1.5V8.207l.646.647a.5.5 0 0 0 .708-.708L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.707 1.5ZM13 7.207l-5-5-5 5V13.5a.5.5 0 0 0 .5.5h9a.5.5 0 0 0 .5-.5V7.207Z"/>
                  </svg>
                  <span>回到首页</span>
                </button>
              </li>
              <!--设置-->
<!--              <li style="margin-right: 2rem;" class="nav-item nav-item-small">-->
<!--                <button class="btn my-icon">-->
<!--                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-gear"-->
<!--                       viewBox="0 0 16 16">-->
<!--                    <path-->
<!--                        d="M8 4.754a3.246 3.246 0 1 0 0 6.492 3.246 3.246 0 0 0 0-6.492zM5.754 8a2.246 2.246 0 1 1 4.492 0 2.246 2.246 0 0 1-4.492 0z"/>-->
<!--                    <path-->
<!--                        d="M9.796 1.343c-.527-1.79-3.065-1.79-3.592 0l-.094.319a.873.873 0 0 1-1.255.52l-.292-.16c-1.64-.892-3.433.902-2.54 2.541l.159.292a.873.873 0 0 1-.52 1.255l-.319.094c-1.79.527-1.79 3.065 0 3.592l.319.094a.873.873 0 0 1 .52 1.255l-.16.292c-.892 1.64.901 3.434 2.541 2.54l.292-.159a.873.873 0 0 1 1.255.52l.094.319c.527 1.79 3.065 1.79 3.592 0l.094-.319a.873.873 0 0 1 1.255-.52l.292.16c1.64.893 3.434-.902 2.54-2.541l-.159-.292a.873.873 0 0 1 .52-1.255l.319-.094c1.79-.527 1.79-3.065 0-3.592l-.319-.094a.873.873 0 0 1-.52-1.255l.16-.292c.893-1.64-.902-3.433-2.541-2.54l-.292.159a.873.873 0 0 1-1.255-.52l-.094-.319zm-2.633.283c.246-.835 1.428-.835 1.674 0l.094.319a1.873 1.873 0 0 0 2.693 1.115l.291-.16c.764-.415 1.6.42 1.184 1.185l-.159.292a1.873 1.873 0 0 0 1.116 2.692l.318.094c.835.246.835 1.428 0 1.674l-.319.094a1.873 1.873 0 0 0-1.115 2.693l.16.291c.415.764-.42 1.6-1.185 1.184l-.291-.159a1.873 1.873 0 0 0-2.693 1.116l-.094.318c-.246.835-1.428.835-1.674 0l-.094-.319a1.873 1.873 0 0 0-2.692-1.115l-.292.16c-.764.415-1.6-.42-1.184-1.185l.159-.291A1.873 1.873 0 0 0 1.945 8.93l-.319-.094c-.835-.246-.835-1.428 0-1.674l.319-.094A1.873 1.873 0 0 0 3.06 4.377l-.16-.292c-.415-.764.42-1.6 1.185-1.184l.292.159a1.873 1.873 0 0 0 2.692-1.115l.094-.319z"/>-->
<!--                  </svg>-->
<!--                  <span>设置</span>-->
<!--                </button>-->
<!--              </li>-->
              <li class="nav-item nav-item-small">
                <button class="btn my-icon" @click="userLogout">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                       class="bi bi-box-arrow-left" viewBox="0 0 16 16">
                    <path fill-rule="evenodd"
                          d="M6 12.5a.5.5 0 0 0 .5.5h8a.5.5 0 0 0 .5-.5v-9a.5.5 0 0 0-.5-.5h-8a.5.5 0 0 0-.5.5v2a.5.5 0 0 1-1 0v-2A1.5 1.5 0 0 1 6.5 2h8A1.5 1.5 0 0 1 16 3.5v9a1.5 1.5 0 0 1-1.5 1.5h-8A1.5 1.5 0 0 1 5 12.5v-2a.5.5 0 0 1 1 0v2z"/>
                    <path fill-rule="evenodd"
                          d="M.146 8.354a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L1.707 7.5H10.5a.5.5 0 0 1 0 1H1.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3z"/>
                  </svg>
                  <span>登出</span>
                </button>
              </li>
            </ul>
          </div>

          <!--todo 大屏幕适配-->
          <div v-else class="offcanvas-body">
            <ul class="navbar-nav justify-content-end flex-grow-1 pe-3">
              <form style="align-items: center;" class="d-flex mt-3 mt-md-0" role="search">
                <input v-model="searchStr" @input="searchFiles" class="form-control my-form-search " type="text" placeholder="搜索...">
              </form>
              <transition name="show">
                <div v-show="isInput" class="searchDialog">
                  <div class="searchDiv row">
                    <button @click="getSearchedFiles" type="button" class="btn btn-secondary">在本地文件中搜索: {{this.searchStr}}</button>
                  </div>
                </div>
              </transition>
              <div style="width: 55rem"></div>
              <!--切换夜间与日间模式-->
<!--              <li style="margin-right: 2rem;" class="nav-item">-->
<!--                <div @click="switchDarkOrLight" class="diary_ico_border_right">-->
<!--                  <div v-if="isDark" class="my-icon">-->
<!--                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"-->
<!--                         class="bi bi-brightness-high" viewBox="0 0 16 16">-->
<!--                      <path-->
<!--                          d="M8 11a3 3 0 1 1 0-6 3 3 0 0 1 0 6zm0 1a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM8 0a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 0zm0 13a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 13zm8-5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2a.5.5 0 0 1 .5.5zM3 8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2A.5.5 0 0 1 3 8zm10.657-5.657a.5.5 0 0 1 0 .707l-1.414 1.415a.5.5 0 1 1-.707-.708l1.414-1.414a.5.5 0 0 1 .707 0zm-9.193 9.193a.5.5 0 0 1 0 .707L3.05 13.657a.5.5 0 0 1-.707-.707l1.414-1.414a.5.5 0 0 1 .707 0zm9.193 2.121a.5.5 0 0 1-.707 0l-1.414-1.414a.5.5 0 0 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .707zM4.464 4.465a.5.5 0 0 1-.707 0L2.343 3.05a.5.5 0 1 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .708z"/>-->
<!--                    </svg>-->
<!--                  </div>-->
<!--                  <div v-else class="my-icon">-->
<!--                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"-->
<!--                         class="bi bi-moon-stars" viewBox="0 0 16 16">-->
<!--                      <path-->
<!--                          d="M6 .278a.768.768 0 0 1 .08.858 7.208 7.208 0 0 0-.878 3.46c0 4.021 3.278 7.277 7.318 7.277.527 0 1.04-.055 1.533-.16a.787.787 0 0 1 .81.316.733.733 0 0 1-.031.893A8.349 8.349 0 0 1 8.344 16C3.734 16 0 12.286 0 7.71 0 4.266 2.114 1.312 5.124.06A.752.752 0 0 1 6 .278zM4.858 1.311A7.269 7.269 0 0 0 1.025 7.71c0 4.02 3.279 7.276 7.319 7.276a7.316 7.316 0 0 0 5.205-2.162c-.337.042-.68.063-1.029.063-4.61 0-8.343-3.714-8.343-8.29 0-1.167.242-2.278.681-3.286z"/>-->
<!--                      <path-->
<!--                          d="M10.794 3.148a.217.217 0 0 1 .412 0l.387 1.162c.173.518.579.924 1.097 1.097l1.162.387a.217.217 0 0 1 0 .412l-1.162.387a1.734 1.734 0 0 0-1.097 1.097l-.387 1.162a.217.217 0 0 1-.412 0l-.387-1.162A1.734 1.734 0 0 0 9.31 6.593l-1.162-.387a.217.217 0 0 1 0-.412l1.162-.387a1.734 1.734 0 0 0 1.097-1.097l.387-1.162zM13.863.099a.145.145 0 0 1 .274 0l.258.774c.115.346.386.617.732.732l.774.258a.145.145 0 0 1 0 .274l-.774.258a1.156 1.156 0 0 0-.732.732l-.258.774a.145.145 0 0 1-.274 0l-.258-.774a1.156 1.156 0 0 0-.732-.732l-.774-.258a.145.145 0 0 1 0-.274l.774-.258c.346-.115.617-.386.732-.732L13.863.1z"/>-->
<!--                    </svg>-->
<!--                  </div>-->
<!--                </div>-->
<!--              </li>-->
              <!--设置-->
<!--              <li style="margin-right: 2rem;" class="nav-item">-->
<!--                <div @click="openSetting" class="diary_ico_border_right">-->
<!--                  <div class="my-icon">-->
<!--                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"-->
<!--                         class="bi bi-gear" viewBox="0 0 16 16">-->
<!--                      <path-->
<!--                          d="M8 4.754a3.246 3.246 0 1 0 0 6.492 3.246 3.246 0 0 0 0-6.492zM5.754 8a2.246 2.246 0 1 1 4.492 0 2.246 2.246 0 0 1-4.492 0z"/>-->
<!--                      <path-->
<!--                          d="M9.796 1.343c-.527-1.79-3.065-1.79-3.592 0l-.094.319a.873.873 0 0 1-1.255.52l-.292-.16c-1.64-.892-3.433.902-2.54 2.541l.159.292a.873.873 0 0 1-.52 1.255l-.319.094c-1.79.527-1.79 3.065 0 3.592l.319.094a.873.873 0 0 1 .52 1.255l-.16.292c-.892 1.64.901 3.434 2.541 2.54l.292-.159a.873.873 0 0 1 1.255.52l.094.319c.527 1.79 3.065 1.79 3.592 0l.094-.319a.873.873 0 0 1 1.255-.52l.292.16c1.64.893 3.434-.902 2.54-2.541l-.159-.292a.873.873 0 0 1 .52-1.255l.319-.094c1.79-.527 1.79-3.065 0-3.592l-.319-.094a.873.873 0 0 1-.52-1.255l.16-.292c.893-1.64-.902-3.433-2.541-2.54l-.292.159a.873.873 0 0 1-1.255-.52l-.094-.319zm-2.633.283c.246-.835 1.428-.835 1.674 0l.094.319a1.873 1.873 0 0 0 2.693 1.115l.291-.16c.764-.415 1.6.42 1.184 1.185l-.159.292a1.873 1.873 0 0 0 1.116 2.692l.318.094c.835.246.835 1.428 0 1.674l-.319.094a1.873 1.873 0 0 0-1.115 2.693l.16.291c.415.764-.42 1.6-1.185 1.184l-.291-.159a1.873 1.873 0 0 0-2.693 1.116l-.094.318c-.246.835-1.428.835-1.674 0l-.094-.319a1.873 1.873 0 0 0-2.692-1.115l-.292.16c-.764.415-1.6-.42-1.184-1.185l.159-.291A1.873 1.873 0 0 0 1.945 8.93l-.319-.094c-.835-.246-.835-1.428 0-1.674l.319-.094A1.873 1.873 0 0 0 3.06 4.377l-.16-.292c-.415-.764.42-1.6 1.185-1.184l.292.159a1.873 1.873 0 0 0 2.692-1.115l.094-.319z"/>-->
<!--                    </svg>-->
<!--                  </div>-->
<!--                </div>-->

<!--              </li>-->
              <!--头像-->
              <li @mouseleave="isOpenUserInfo=false" @mouseenter="isOpenUserInfo=true" style="margin-right: 2rem;"
                  class="nav-item">
                <button class="btn my-icon">
                  <img v-if="userInfo.avatar === 'data:image/jpeg;base64,null'" class="avatar"
                       src="@/assets/svg/person-circle.svg">
                  <img v-else class="avatar" :src="userInfo.avatar">
                </button>

                <transition name="show">
                  <div v-show="isOpenUserInfo" class="userInfoDialog">
                    <div class="userInfoArea">
                      <img v-if="userInfo.avatar === 'data:image/jpeg;base64,null'" class="avatar_big"
                           src="@/assets/svg/person-circle.svg">
                      <img v-else class="avatar_big" :src="userInfo.avatar">
                      <span class="userInfoNameSpan">{{ userInfo.username }}</span>
                      <span class="userInfoNameEmail">{{
                          userInfo.email ? userInfo.email : "邮箱尚未设置&nbsp&nbsp&nbsp&nbsp&nbsp"
                        }}</span>
                    </div>
                    <hr style="margin-top: -2px">
                    <div class="fn">
                      <button @click="toUserCenter">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                             class="bi bi-person-vcard" viewBox="0 0 16 16">
                          <path
                              d="M5 8a2 2 0 1 0 0-4 2 2 0 0 0 0 4Zm4-2.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5ZM9 8a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4A.5.5 0 0 1 9 8Zm1 2.5a.5.5 0 0 1 .5-.5h3a.5.5 0 0 1 0 1h-3a.5.5 0 0 1-.5-.5Z"/>
                          <path fill-rule="evenodd"
                                d="M2 2a2 2 0 0 0-2 2v8a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V4a2 2 0 0 0-2-2H2ZM1 4a1 1 0 0 1 1-1h12a1 1 0 0 1 1 1v8a1 1 0 0 1-1 1H8.96c.026-.163.04-.33.04-.5C9 10.567 7.21 9 5 9c-2.086 0-3.8 1.398-3.984 3.181A1.006 1.006 0 0 1 1 12V4Z"/>
                        </svg>
                        <span style="margin-left: 1rem;">用户中心</span>
                      </button>
                      <button @click="userLogout">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                             class="bi bi-box-arrow-left" viewBox="0 0 16 16">
                          <path fill-rule="evenodd"
                                d="M6 12.5a.5.5 0 0 0 .5.5h8a.5.5 0 0 0 .5-.5v-9a.5.5 0 0 0-.5-.5h-8a.5.5 0 0 0-.5.5v2a.5.5 0 0 1-1 0v-2A1.5 1.5 0 0 1 6.5 2h8A1.5 1.5 0 0 1 16 3.5v9a1.5 1.5 0 0 1-1.5 1.5h-8A1.5 1.5 0 0 1 5 12.5v-2a.5.5 0 0 1 1 0v2z"/>
                          <path fill-rule="evenodd"
                                d="M.146 8.354a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L1.707 7.5H10.5a.5.5 0 0 1 0 1H1.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3z"/>
                        </svg>
                        <span style="margin-left: 1rem;">用户退出</span>
                      </button>
                    </div>
                  </div>
                </transition>

              </li>
            </ul>
          </div>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>
import {EventBus} from "@/utils/EventBus";
import request from "@/utils/request";
import axios from "axios";
import fileDownload from "js-file-download";

export default {
  name: "Header",
  data() {
    return {
      isDark: true,
      isOpenUserInfo: false,
      userInfo: {
        "username": "temp",
        "avatar": "",
        "email": "xxxxx@xx.com",
      },
      selectedList: [],
      folderInfoList: [],
      fileInfoList: [],
      isSelect: false,
      downloadDto: {
        "selectedList": "",
        "currentPath": "/"
      },
      moveDto: {
        "deleteList":[],
        "folderPath":""
      },
      renameDto: {
        folderPath: "/",
        fileNameOld: "",
        fileNameNew: ""
      },
      downloadProgress: 0.00,
      breadcrumbList: ["",],
      moveFolderInfoList: [],
      targetPath: "",
      moveDto :{
        "fileList": [],
        "sourcePath":"/",
        "targetPath":"/"
      },
      searchStr: "",
      isInput: false,
    }
  },
  computed: {
    FileTitle(){
      const str = this.selectedList[0];
      const parts = str.split("/");
      return parts[parts.length - 1];
    }
  },
  methods: {
    searchFiles(){
      if (this.searchStr.length > 0){
        this.isInput = true
      }else{
        this.isInput = false
      }
    },
    getSearchedFiles(){
      let keyword = this.searchStr
      this.searchStr = ""
      this.isInput = false
      // 这里使用eventBus传递要关键字到main组件
      EventBus.$emit("getSearchedFiles",keyword)
    },
    // 切换夜间与日间模式
    switchDarkOrLight() {
      this.isDark = !this.isDark
    },
    // 打开设置
    openSetting() {

    },
    // 把所选文件加入回收站中
    moveToRecycleBin(){
      const _this = this
      if (this.currentPath === "" || this.currentPath === undefined){
        this.moveDto.folderPath = "/"
      }else{
        this.moveDto.folderPath = this.currentPath
      }
      if(this.selectedList.length < 1){
        this.$message({
            message: "未选择文件",
            type: "info",
            center:true
          });
      }else{
        this.moveDto.deleteList = this.selectedList
      }
      this.request.put("/Cloud/toRecycleBin",this.moveDto).then(res => {
        if(res.code === 200){
          this.$message({
            message: "成功将文件移入回收站",
            type: "success",
            center:true
          });
          location.reload()
        }
      })
    },
    // 首页
    retrunMain() {
      if (this.$router.currentRoute.path !== "/main") {
        this.$router.push('/main')
      }
    },
    // 用户中心
    toUserCenter() {
      if (this.$router.currentRoute.path !== "/userCenter") {
        this.$router.push('/userCenter')
      }
    },
    // 重命名
    rename(renameFile){
      if (renameFile !== undefined && renameFile !== "" && renameFile !== null){
        const _this = this
        this.renameDto.fileNameOld = this.selectedList[0]
        this.renameDto.fileNameNew = renameFile
        this.renameDto.folderPath = this.downloadDto.currentPath
        this.request.put("/Cloud/rename",this.renameDto).then(res => {
          if (res.code === 400){
            this.$message({
              message: res.msg,
              type: 'warning',
              center:true
            });
          }else if (res.code === 200){
            location.reload()
          }
        })
        this.returnHeader()
      }else{
        console.log("请输入文件名称")
      }
    },
    // 关闭文件菜单选项
    returnHeader() {
      this.selectedList = []
      this.isSelect = false
      EventBus.$emit("returnListChange", this.selectedList)
    },
    // 下载文件
    async downloadFile() {
      // 如果用户只选择了一个文件就进行分段下载
      if (this.selectedList.length === 1){
        const url = '/api/dowmload/one-file';
        const chunkSize = 1024 * 1024 * 5; // 1MB
        const config = {
          responseType: 'arraybuffer',
          params: this.downloadDto,
          headers: {
            "token": localStorage.getItem("token")
          }
        }
        let start = 0;
        let end = chunkSize - 1;
        let fileSize = 0;
        let chunks = [];
        // 获取文件大小
        const response = await axios.head(url,config);
        console.log("response-----")
        console.log(response)
        console.log("-----")
        fileSize = response.headers['content-length'];
        console.log(`文件大小--------${fileSize}`)
        // 计算块的数量
        const numChunks = Math.ceil(fileSize / chunkSize)
        console.log(`块数量--------${numChunks}`);
        // 如果文件大小小于块大小(说明可以一次就下载完成)
        if (fileSize < chunkSize) {
          end = fileSize - 1;
        }
        // 下载块
        for (let i = 0; i < numChunks; i++) {
          const range = `bytes=${start}-${end}`;
          this.downloadProgress = (i/numChunks*100).toFixed(2)+"%"
          console.log(`目前下载的大小:====${this.downloadProgress}`);
          // TODO 把downloadProgress传到“任务队列”组件中
          const config = {
            params: this.downloadDto,
            headers: {
              Range: range,
              "token": localStorage.getItem("token"),
            },
            responseType: 'arraybuffer'
          };
          const response = await axios.get(url, config);
          chunks.push(response.data);
          start = end + 1;
          end = Math.min(end + chunkSize, fileSize - 1);
        }

        const blob = new Blob(chunks);
        const link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.download = this.selectedList[0];
        link.click();
      }
      // 如果用户选中了多个用户则把用户选中的文件进行打包下载
      else if (this.selectedList.length >= 2){
        console.log("把选中的多个文件打包成zip下载到本地")
        let fileName = ""
        let targetPath = this.downloadDto.currentPath
        if (targetPath === undefined || targetPath === ""){
          targetPath = "/"
        }
        const targeturl = '/api/dowmload/selected-file?currentPath='
            +encodeURIComponent(targetPath)
        +"&selectedList="+encodeURIComponent(this.selectedList);
        const config = {
          responseType: 'blob',
          headers: {
            "token": localStorage.getItem("token")
          }
        }
        this.$message.info("正在下载中...请不要重复操作!")
        axios.get(targeturl, config).then(res => {
          var number = targetPath.lastIndexOf('/');
          fileName = "file.zip"
          fileDownload(res.data, fileName)
        }).catch(err => {
          this.$message.error(err)
        })
      }
    },
    // 获取用户目录的文件夹
    getCloudFiles(currentPath, option) {
      console.log("获取文件夹")
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
      this.moveDto.sourcePath += "/"
      this.moveDto.sourcePath = this.removeSpecialCharacters(this.moveDto.sourcePath)
      this.moveDto.targetPath = this.removeSpecialCharacters(this.moveDto.targetPath)
      console.log("要移动的文件原位置:" +this.moveDto.sourcePath)
      console.log("要移动的文件: "+this.moveDto.fileList)
      console.log("要移动到哪里: "+this.moveDto.targetPath)
      const _this = this
      this.request.put("/Cloud/moveFiles",this.moveDto).then(res => {
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
  },
  mounted() {
    this.getUserInfoByLocal()
    EventBus.$on('selectedListChange', (selectedList, currentPath) => {
      if (selectedList === undefined && currentPath === undefined){
        this.selectedList = []
        this.currentPath = "/"
        this.isSelect = false
        return
      }
      if (selectedList.includes(currentPath)){
        this.selectedList = []
        this.currentPath = "/"
        this.isSelect = false
        return
      }

      // var lastIndex = currentPath.lastIndexOf('/');
      // if (lastIndex !== undefined){
      //   var s = currentPath.substring(lastIndexOf+1);
      //   if (selectedList.includes(s)){
      //     console.log("点击了文件夹")
      //     this.isSelect = false
      //     selectedList = []
      //     return;
      //   }
      // }


      this.currentPath = currentPath
      this.selectedList = selectedList
      if (currentPath === undefined) {
        this.downloadDto.currentPath = "/"
      } else {
        this.downloadDto.currentPath = encodeURIComponent(currentPath)
      }
      const encodedList =encodeURIComponent(selectedList.join(","))
      this.downloadDto.selectedList = encodedList
      // 如果这个值的长度大于0，说明用户选择了某些数据
      if (this.selectedList.length > 0) {
        this.renameFile = this.selectedList[0]
        this.isSelect = true
      } else {
        this.isSelect = false
      }
    })
    EventBus.$on('getMainCurrentPath',(currentPath) => {
      if (currentPath === undefined){
        this.moveDto.sourcePath = "/"
      }else{
        this.moveDto.sourcePath = currentPath
      }
    })
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

.diary_ico {
  text-align: center;
  cursor: pointer;
}


.diary_ico_border {
  border-radius: 25px;
  line-height: 47px;
  text-align: center;
  width: 50px;
  height: 50px;
  margin: 0 1rem;
}

.diary_ico_border:hover, .diary_ico_border_right:hover, .OptionButton:hover, .returnBtn:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.diary_ico_border_right, .OptionButton {
  cursor: pointer;
  line-height: 40px;
  text-align: center;
  border-radius: 24px;
  width: 42px;
  height: 42px;
}

.avatar {
  width: 30px;
  height: 30px;
  border-radius: 15px;;
}

.avatar_big {
  float: left;
  margin: 10px 10px 10px 20px;
  display: inline-block;
  width: 90px;
  height: 90px;
  border-radius: 45px;
}

.userInfoNameEmail, .userInfoNameSpan {
  float: left;
  display: inline-block;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.userInfoNameSpan {
  font-size: 18px;
  color: #fff;
  max-width: 200px;
  margin: 20px 10px 10px 6px;
}

.userInfoNameEmail {
  color: rgba(255, 255, 255, 0.7);
  max-width: 150px;
  margin: 10px 10px 10px 6px;
}

.userInfoArea {
  display: inline-block;
}

.fn a, .fn button {
  font-size: 16px;
  text-align: center;
  display: block;
  width: 100%;
  padding: 0.5rem 1rem;
  color: white;
  text-decoration: none;
}

.fn button {
  background-color: rgba(0, 0, 0, 0);
  border: none;
}


@media (max-width: 768px) {
  .nav-item {
    width: 200px;
  }

  /*.my-icon {*/
  /*  overflow: auto;*/
  /*}*/
  .my-form-search {
    position: relative;
    left: 0rem;
  }
}

.bg-dark {
  background-color: #212121 !important;
}

.my-icon {
  text-align: center;
  align-items: center;
  display: inline-block;
  overflow: hidden;
  background-color: rgba(0, 0, 0, 0);
  border: none;
}

.switch_btn_area:hover {
  cursor: default;
}

.switch_btn_area {
  width: 22rem;
}

.btn span {
  position: relative;
  display: inline-block;
  color: white;
  text-align: center;
  margin: 1rem 0 0 1rem;
  bottom: -3px;
}

.userInfoDialog,.searchDialog {
  z-index: 999;
  width: 300px;
  height: 250px;
  background-color: #424242;
  border-radius: 2rem;
  position: absolute;
  top: 100%; /* 将详细信息div定位在头像下方 */
  right: 0;
  overflow: hidden;
}

.searchDialog {
  width: 15rem;
  height: auto;
  border-radius: 1rem;
  background-color: #fff;
  left: 29%;
}
.searchDiv {

}

.navbar {
  padding: 4px;
}

.nav-item-small {
  margin-right: 2rem;
  width: 100%;
}


.nav-item-small:hover {
  cursor: pointer;
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
}

.optionsMenuBar {
  height: 58px;
  width: 100%;
}

.myMenuBarRow {
}

.myMenuCol {
  display: inline-block;
  white-space: nowrap;
  overflow: hidden;
  font-size: 20px;
  height: 58px;
  line-height: 58px;
}

.optionsContext {
  display: inline-block;
  float: right;
}

.OptionButton, .returnBtn {
  float: right;
  display: inline-block;
  width: 58px;
  height: 58px;
  line-height: 56px;
  border-radius: 29px;
}

.returnBtn {
  cursor: pointer;
  float: left;
  display: flex;
  align-items: center;
  justify-content: center;
}

.myMenuCol > span {
  display: inline-block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 100%;
}

.modal-title {

}

.modal-content {
  background-color: #424242;
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