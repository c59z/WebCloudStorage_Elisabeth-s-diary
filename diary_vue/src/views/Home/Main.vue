<template>
  <div>
    <div id="main">
      <div class="my-nav">
        <nav v-if="this.isMain" aria-label="breadcrumb" class="row">
          <!-- 面包屑 -->
          <div v-show="!isSmallScreen" id="my-breadcrumb" class="col-8">
            <!-- 根据breadcrumbList使用for循环创建面包屑 -->
            <ol class="breadcrumb">
              <li @click="breadcrumbOnClick" v-for="(breadcrumb,index) in this.breadcrumbList" class="breadcrumb-item"
                  :key="index">
                <router-link v-if="breadcrumb ===''" :to="{ path: '/main'}" :key="index">
                  <a href="#"> / </a>
                </router-link>
                <router-link v-else :to="{ path: '/main', query: { path: '/'+generateBreadcrumbLink(index) }}"
                             :key="index">
                  <a href="#"> {{ breadcrumb }} </a>
                </router-link>
              </li>
            </ol>
          </div>
          <div class="nav-options col">
            <!--切换视图形式-->
            <!--            <div @click="switchView" class="diary_ico_border_right">-->
            <!--              <div v-if="isListView" class="my-icon">-->
            <!--                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"-->
            <!--                     class="bi bi-border-all" viewBox="0 0 16 16">-->
            <!--                  <path-->
            <!--                      d="M0 0h16v16H0V0zm1 1v6.5h6.5V1H1zm7.5 0v6.5H15V1H8.5zM15 8.5H8.5V15H15V8.5zM7.5 15V8.5H1V15h6.5z"/>-->
            <!--                </svg>-->
            <!--              </div>-->
            <!--              <div v-else class="my-icon">-->
            <!--                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"-->
            <!--                     class="bi bi-card-list" viewBox="0 0 16 16">-->
            <!--                  <path-->
            <!--                      d="M14.5 3a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-.5.5h-13a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h13zm-13-1A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h13a1.5 1.5 0 0 0 1.5-1.5v-9A1.5 1.5 0 0 0 14.5 2h-13z"/>-->
            <!--                  <path-->
            <!--                      d="M5 8a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7A.5.5 0 0 1 5 8zm0-2.5a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zm0 5a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zm-1-5a.5.5 0 1 1-1 0 .5.5 0 0 1 1 0zM4 8a.5.5 0 1 1-1 0 .5.5 0 0 1 1 0zm0 2.5a.5.5 0 1 1-1 0 .5.5 0 0 1 1 0z"/>-->
            <!--                </svg>-->
            <!--              </div>-->
            <!--            </div>-->
            <!--切换显示排序-->
            <div class="diary_ico_border_right">
              <div title="列表排序" class="dropdown-toggle my-icon" data-bs-toggle="dropdown"
                   data-bs-auto-close="outside"
                   aria-expanded="false">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                     class="bi bi-sort-down" viewBox="0 0 16 16">
                  <path
                      d="M3.5 2.5a.5.5 0 0 0-1 0v8.793l-1.146-1.147a.5.5 0 0 0-.708.708l2 1.999.007.007a.497.497 0 0 0 .7-.006l2-2a.5.5 0 0 0-.707-.708L3.5 11.293V2.5zm3.5 1a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zM7.5 6a.5.5 0 0 0 0 1h5a.5.5 0 0 0 0-1h-5zm0 3a.5.5 0 0 0 0 1h3a.5.5 0 0 0 0-1h-3zm0 3a.5.5 0 0 0 0 1h1a.5.5 0 0 0 0-1h-1z"/>
                </svg>
              </div>
              <ul class="dropdown-menu">
                <li>
                  <div @click="FilesListSort(0)"
                       style="color: white"
                       class="dropdown-item">A - Z
                  </div>
                </li>
                <li>
                  <div @click="FilesListSort(1)"
                       style="color: white"
                       class="dropdown-item">Z - A
                  </div>
                </li>
                <li>
                  <div @click="FilesListSort(2)"
                       style="color: white"
                       class="dropdown-item">最早 - 最晚
                  </div>
                </li>
                <li>
                  <div @click="FilesListSort(3)"
                       style="color: white"
                       class="dropdown-item">最晚 - 最早
                  </div>
                </li>
                <li>
                  <div @click="FilesListSort(4)"
                       style="color: white"
                       class="dropdown-item">最大 - 最小
                  </div>
                </li>
                <li>
                  <div @click="FilesListSort(5)"
                       style="color: white"
                       class="dropdown-item">最小 - 最大
                  </div>
                </li>
              </ul>
            </div>
            <!--切换分页页数-->
            <!--              <div @click="changePageCount" class="diary_ico_border_right">-->
            <!--                <div class="my-icon">-->
            <!--                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-filter-circle" viewBox="0 0 16 16">-->
            <!--                    <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>-->
            <!--                    <path d="M7 11.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 0 1h-1a.5.5 0 0 1-.5-.5zm-2-3a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5zm-2-3a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5z"/>-->
            <!--                  </svg>-->
            <!--                </div>-->
            <!--              </div>-->
            <!--批量下载-->
            <div
                data-bs-toggle="modal"
                data-bs-target="#downloadFiles"
                title="将当前目录打包下载"
                class="diary_ico_border_right"
            >
              <div class="my-icon">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                     class="bi bi-cloud-download" viewBox="0 0 16 16">
                  <path
                      d="M4.406 1.342A5.53 5.53 0 0 1 8 0c2.69 0 4.923 2 5.166 4.579C14.758 4.804 16 6.137 16 7.773 16 9.569 14.502 11 12.687 11H10a.5.5 0 0 1 0-1h2.688C13.979 10 15 8.988 15 7.773c0-1.216-1.02-2.228-2.313-2.228h-.5v-.5C12.188 2.825 10.328 1 8 1a4.53 4.53 0 0 0-2.941 1.1c-.757.652-1.153 1.438-1.153 2.055v.448l-.445.049C2.064 4.805 1 5.952 1 7.318 1 8.785 2.23 10 3.781 10H6a.5.5 0 0 1 0 1H3.781C1.708 11 0 9.366 0 7.318c0-1.763 1.266-3.223 2.942-3.593.143-.863.698-1.723 1.464-2.383z"/>
                  <path
                      d="M7.646 15.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 14.293V5.5a.5.5 0 0 0-1 0v8.793l-2.146-2.147a.5.5 0 0 0-.708.708l3 3z"/>
                </svg>
              </div>
            </div>
            <!--上传文件-->
            <div
                data-bs-toggle="modal"
                data-bs-target="#uploadFiles"
                title="将文件上传到此处"
                class="diary_ico_border_right"
            >
              <div class="my-icon">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                     class="bi bi-cloud-upload" viewBox="0 0 16 16">
                  <path fill-rule="evenodd"
                        d="M4.406 1.342A5.53 5.53 0 0 1 8 0c2.69 0 4.923 2 5.166 4.579C14.758 4.804 16 6.137 16 7.773 16 9.569 14.502 11 12.687 11H10a.5.5 0 0 1 0-1h2.688C13.979 10 15 8.988 15 7.773c0-1.216-1.02-2.228-2.313-2.228h-.5v-.5C12.188 2.825 10.328 1 8 1a4.53 4.53 0 0 0-2.941 1.1c-.757.652-1.153 1.438-1.153 2.055v.448l-.445.049C2.064 4.805 1 5.952 1 7.318 1 8.785 2.23 10 3.781 10H6a.5.5 0 0 1 0 1H3.781C1.708 11 0 9.366 0 7.318c0-1.763 1.266-3.223 2.942-3.593.143-.863.698-1.723 1.464-2.383z"/>
                  <path fill-rule="evenodd"
                        d="M7.646 4.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L8.5 5.707V14.5a.5.5 0 0 1-1 0V5.707L5.354 7.854a.5.5 0 1 1-.708-.708l3-3z"/>
                </svg>
              </div>
            </div>
            <div
                data-bs-toggle="modal"
                data-bs-target="#createNewFolder"
                title="在当前位置新建文件夹"
                class="diary_ico_border_right"
            >
              <div class="my-icon">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-folder-plus" viewBox="0 0 16 16">
                  <path d="m.5 3 .04.87a1.99 1.99 0 0 0-.342 1.311l.637 7A2 2 0 0 0 2.826 14H9v-1H2.826a1 1 0 0 1-.995-.91l-.637-7A1 1 0 0 1 2.19 4h11.62a1 1 0 0 1 .996 1.09L14.54 8h1.005l.256-2.819A2 2 0 0 0 13.81 3H9.828a2 2 0 0 1-1.414-.586l-.828-.828A2 2 0 0 0 6.172 1H2.5a2 2 0 0 0-2 2zm5.672-1a1 1 0 0 1 .707.293L7.586 3H2.19c-.24 0-.47.042-.683.12L1.5 2.98a1 1 0 0 1 1-.98h3.672z"/>
                  <path d="M13.5 10a.5.5 0 0 1 .5.5V12h1.5a.5.5 0 1 1 0 1H14v1.5a.5.5 0 1 1-1 0V13h-1.5a.5.5 0 0 1 0-1H13v-1.5a.5.5 0 0 1 .5-.5z"/>
                </svg>
              </div>
            </div>
          </div>
        </nav>
        <div v-else style="height: 47px"></div>
        <hr style="padding: 0;margin: 0;position:relative;">
      </div>
      <!--真正的主内容-->
      <div class="myCloudDevice">
        <div class="tableArea overflow-auto">
          <div v-show="this.searchInfo.length > 0" class="searchText">搜索: {{ this.searchInfo }}</div>
          <!--列表视图-->
          <table
              v-if="isListView"
              class="table table-dark"
          >
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
            <!--文件夹-->
            <tr
                v-for="folder in folderInfoList"
                :key="folder.folder_name"
            >
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
            <!--文件-->
            <tr v-for="file in fileInfoList"
                :key="file.file_name"
                @click="selectCurrentIndex(file.file_name)"
                :class=" {'selectedCurrentRow': selectedList.includes(file.file_name)}">
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
            <!--TODO PC端-->
            <tbody v-else>
            <!--文件夹-->
            <tr
                v-for="folder in folderInfoList"
                :key="folder.folder_name"
                :class=" {'selectedCurrentRow': selectedList.includes(folder.file_path)}"
                @contextmenu.prevent="showContextMenu(folder.file_path,$event)"
            >
              <!--                @contextmenu.prevent="showContextMenu(folder.file_path)"-->
              <td style="padding-left: 1rem" class="td_color">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                     class="bi bi-folder-fill" viewBox="0 0 16 16">
                  <path
                      d="M9.828 3h3.982a2 2 0 0 1 1.992 2.181l-.637 7A2 2 0 0 1 13.174 14H2.825a2 2 0 0 1-1.991-1.819l-.637-7a1.99 1.99 0 0 1 .342-1.31L.5 3a2 2 0 0 1 2-2h3.672a2 2 0 0 1 1.414.586l.828.828A2 2 0 0 0 9.828 3zm-8.322.12C1.72 3.042 1.95 3 2.19 3h5.396l-.707-.707A1 1 0 0 0 6.172 2H2.5a1 1 0 0 0-1 .981l.006.139z"/>
                </svg>
                <router-link :to="{ path: '/main', query: { path: folder.file_path }}">
                  <!--                  <router-link :to="{ path: '/main', query: { path: currentPath ? currentPath+'/'+folder.folder_name : '/'+folder.folder_name} }">-->
                  <span class="fileNameSpan">{{ folder.folder_name }}</span>
                </router-link>
              </td>
              <td></td>
              <td class="td_color">{{ folder.modified_date }}</td>
            </tr>
            <!--文件-->
            <tr
                v-for="file in fileInfoList"
                :key="file.file_path+file.file_name"
                @click="selectCurrentIndex(file.file_path)"
                :class=" {'selectedCurrentRow': selectedList.includes(file.file_path)}"
                @contextmenu.prevent="showContextMenu(file.file_path, $event)"
            >
              <!--                @contextmenu.prevent="showContextMenu(file.file_path)"-->
              <td style="padding-left: 1rem" class="td_color">
                <!--这里对文件名称进行判断，如果文件后缀包含在对应类别中那么就呈现出对应的图标-->
                <!--判断文件是否是文档格式的-->
                <svg v-if="iconTypeDoc.includes(getFileExtension(file.file_name))" xmlns="http://www.w3.org/2000/svg"
                     width="24" height="24" fill="currentColor"
                     class="bi bi-book-half" viewBox="0 0 16 16">
                  <path
                      d="M8.5 2.687c.654-.689 1.782-.886 3.112-.752 1.234.124 2.503.523 3.388.893v9.923c-.918-.35-2.107-.692-3.287-.81-1.094-.111-2.278-.039-3.213.492V2.687zM8 1.783C7.015.936 5.587.81 4.287.94c-1.514.153-3.042.672-3.994 1.105A.5.5 0 0 0 0 2.5v11a.5.5 0 0 0 .707.455c.882-.4 2.303-.881 3.68-1.02 1.409-.142 2.59.087 3.223.877a.5.5 0 0 0 .78 0c.633-.79 1.814-1.019 3.222-.877 1.378.139 2.8.62 3.681 1.02A.5.5 0 0 0 16 13.5v-11a.5.5 0 0 0-.293-.455c-.952-.433-2.48-.952-3.994-1.105C10.413.809 8.985.936 8 1.783z"/>
                </svg>
                <!--判断文件是否是图片格式的-->
                <svg v-else-if="iconTypeImg.includes(getFileExtension(file.file_name))"
                     xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-image"
                     viewBox="0 0 16 16">
                  <path d="M6.002 5.5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0z"/>
                  <path
                      d="M2.002 1a2 2 0 0 0-2 2v10a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V3a2 2 0 0 0-2-2h-12zm12 1a1 1 0 0 1 1 1v6.5l-3.777-1.947a.5.5 0 0 0-.577.093l-3.71 3.71-2.66-1.772a.5.5 0 0 0-.63.062L1.002 12V3a1 1 0 0 1 1-1h12z"/>
                </svg>
                <!--判断文件是否是音频格式的-->
                <svg v-else-if="iconTypeSound.includes(getFileExtension(file.file_name))"
                     xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                     class="bi bi-file-earmark-music" viewBox="0 0 16 16">
                  <path
                      d="M11 6.64a1 1 0 0 0-1.243-.97l-1 .25A1 1 0 0 0 8 6.89v4.306A2.572 2.572 0 0 0 7 11c-.5 0-.974.134-1.338.377-.36.24-.662.628-.662 1.123s.301.883.662 1.123c.364.243.839.377 1.338.377.5 0 .974-.134 1.338-.377.36-.24.662-.628.662-1.123V8.89l2-.5V6.64z"/>
                  <path
                      d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2zM9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5v2z"/>
                </svg>
                <!--判断文件是否是视频格式的-->
                <svg v-else-if="iconTypeVideo.includes(getFileExtension(file.file_name))"
                     xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                     class="bi bi-file-play" viewBox="0 0 16 16">
                  <path
                      d="M6 10.117V5.883a.5.5 0 0 1 .757-.429l3.528 2.117a.5.5 0 0 1 0 .858l-3.528 2.117a.5.5 0 0 1-.757-.43z"/>
                  <path
                      d="M4 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H4zm0 1h8a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1z"/>
                </svg>
                <svg v-else xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                     class="bi bi-file-earmark" viewBox="0 0 16 16">
                  <path
                      d="M14 4.5V14a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2h5.5L14 4.5zm-3 0A1.5 1.5 0 0 1 9.5 3V1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V4.5h-2z"/>
                </svg>
                <span class="fileNameSpan">{{ file.file_name }}</span>
              </td>
              <td class="td_color">{{ file.file_size }}</td>
              <td class="td_color">{{ file.modified_date }}</td>
            </tr>
            </tbody>
          </table>
          <!-- TODO bug 位置有点问题 加载时启用 -->
          <div v-if="isLoading">
            <div class="spinner-grow" style="width: 3rem; height: 3rem;" role="status">
              <span class="visually-hidden">Loading...</span>
            </div>
          </div>

          <!-- 当没有文件和文件夹时,显示提示 -->
          <div v-else v-show="this.folderInfoList.length === 0 && this.fileInfoList.length === 0" class="nullTipDiv">
            <div>
              <img style="width: 20rem;height: 20rem;" src="@/assets/svg/boxes.svg">
            </div>
            <span
                style="display: inline-block;color: white;font-size: 24px;margin-top: 2rem">这里空空如也什么都没有~</span>
          </div>
        </div>
        <ContextMenu ref="contextMenu"
                     :isOpen="isContextMenuOpen"
                     :position="contextMenuPosition"
                     :filePath="MenuFilePath"
        />
        <!--        &lt;!&ndash;分页插件&ndash;&gt;-->
        <!--        <div class="myPagingPlugin">-->
        <!--          <nav aria-label="Page navigation">-->
        <!--            <ul class="pagination justify-content-center">-->
        <!--              <li class="page-item">-->
        <!--                <a class="page-link" href="#" aria-label="Previous">-->
        <!--                  <span aria-hidden="true">&laquo;</span>-->
        <!--                </a>-->
        <!--              </li>-->
        <!--              <li class="page-item"><a class="page-link" href="#">1</a></li>-->
        <!--              <li class="page-item"><a class="page-link" href="#">2</a></li>-->
        <!--              <li class="page-item"><a class="page-link" href="#">3</a></li>-->
        <!--              <li class="page-item">-->
        <!--                <a class="page-link" href="#" aria-label="Next">-->
        <!--                  <span aria-hidden="true">&raquo;</span>-->
        <!--                </a>-->
        <!--              </li>-->
        <!--            </ul>-->
        <!--          </nav>-->
        <!--        </div>-->
      </div>
    </div>
    <!--打包下载确认窗口-->
    <div class="modal fade" id="downloadFiles" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
         aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="staticBackdropLabel">打包下载</h1>
          </div>
          <div class="modal-body">
            确认要把当前目录下的所有文件打包下载吗?
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
            <button @click="Btn_PackAndDownload" type="button" class="btn btn-primary" data-bs-dismiss="modal">确认
            </button>
          </div>
        </div>
      </div>
    </div>
    <!--上传文件窗口-->
    <div class="modal fade" id="uploadFiles" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
         aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="staticBackdropLabel">上传文件到该目录</h1>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <label for="formFileMultiple" class="form-label">请选择要上传的文件</label>
              <input @change="handleFileUpload" class="form-control" type="file" id="formFileMultiple" multiple>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
            <button @click="uploadFile" type="button" class="btn btn-primary" data-bs-dismiss="modal">上传</button>
          </div>
        </div>
      </div>
    </div>
    <!--在当前位置新建文件夹-->
    <div class="modal fade" id="createNewFolder" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
         aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="staticBackdropLabel">新建文件夹</h1>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <label for="formFileMultiple" class="form-label">请选择要上传的文件</label>
              <div class="input-group mb-3">
                <span class="input-group-text" id="inputGroup-sizing-default">文件夹名称</span>
                <input v-model="CreateNewFolderDto.folderName" type="text" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
            <button @click="createNewFolder" type="button" class="btn btn-primary" data-bs-dismiss="modal">创建</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {EventBus} from "@/utils/EventBus";
import fileDownload from 'js-file-download'
import axios from "axios";
import ContextMenu from "@/views/Utils/ContextMenu";
import router from "@/router/router";

export default {
  name: 'Mains',
  props: {
    msg: String
  },
  components: {
    ContextMenu,
  },
  data() {
    return {
      token: window.localStorage.getItem("token"),
      isMain: true,
      isLoading: false,
      currentPath: undefined,
      isListView: true,
      contextMenuVisible: false,
      // 文件信息
      fileInfoList: [],
      folderInfoList: [],
      breadcrumbList: ["",],
      selectedList: [],
      uploadFileList: [],
      searchInfo: "",
      isContextMenuOpen: false,
      contextMenuPosition: {x: 0, y: 0},
      MenuFilePath: "",
      moveDto: {
        "fileList": [],
        "sourcePath": "/",
        "targetPath": "/"
      },
      downloadDto: {
        "selectedList": "",
        "currentPath": "/"
      },
      CreateNewFolderDto: {
        folderName:"",
        folderPath:"",
      },

      iconTypeDoc: ["pdf", "doc", "docx", "ppt", "pptx",
        "xls", "xlsx", "odt", "odp", "ods", "txt", "epub"],
      iconTypeImg: ["bmp", "jpg", "png", "tif", "gif",
        "svg", "psd", "webp", "jpeg"],
      iconTypeSound: ["mp3", "wav", "flac", "aac", "ogg",
        "wma", "m4a", "opus", "mid", "amr"],
      iconTypeVideo: ["mp4", "avi", "mkv", "mov", "wmv",
        "flv", "webm", "3gp", "mpeg", "rm",
        "vob", "ogg"],
    }
  },
  watch: {
    '$route.query.path': function (currentPathNew) {
      this.selectedList = []
      if (this.currentPath === null || this.currentPath === undefined) {
        this.currentPath = ""
        this.currentPath = currentPathNew
      } else if (currentPathNew === undefined) {
        this.currentPath = ""
      } else if (currentPathNew.lastIndexOf('/') === -1) {
        this.currentPath = "/" + currentPathNew
      } else {
        this.currentPath = currentPathNew
      }
      this.getCloudFiles(this.currentPath)
    },
    '$route.query.type': function (type) {
      // type 1: 视频 2:图片 3:音乐 4:文档
      switch (type) {
        case "1":
          this.isMain = false
          // console.log("文件筛选: 视频")
          break;
        case "2":
          this.isMain = false
          // console.log("文件筛选: 图片")
          break;
        case "3":
          this.isMain = false
          // console.log("文件筛选: 音乐")
          break;
        case "4":
          this.isMain = false
          // console.log("文件筛选: 文档")
          break;
        default:
          this.isMain = true
          break;
      }
      // this.getSpecifiedTypeFiles(type)
    },
    currentPath: function (newPath) {
      console.log("newPath" + newPath)
      if (newPath === undefined) {
        // 当前为根目录,只有一个面包屑导航
      } else {
        // /hiten/(C101) [HitenKei (Hiten)] deLIGHTful (オリジナル)
        // 非根目录,进行字符串分割
        this.breadcrumbList = newPath.split("/")
        // 每次url变化时，清空header中的选项
        EventBus.$emit("selectedListChange", this.selectedList, this.breadcrumbList)
        // console.log(this.breadcrumbList)
      }
    }
  },
  methods: {
    // 切换显示方式（列表与表格形式）
    switchView() {
      this.isListView = !this.isListView
    },
    breadcrumbOnClick() {
      this.selectedList = []
    },
    // 打包下载(把当前路径下的所有文件打包成压缩包下载下来)
    Btn_PackAndDownload() {
      let targetPath = ""
      if (this.currentPath === undefined) {
        targetPath = "/"
      } else {
        targetPath = this.currentPath
      }
      console.log("打包下载当前路径下的所有文件: " + targetPath)

      let fileName = ""

      const targeturl = '/api/dowmload/package-file?targetPath=' + encodeURIComponent(targetPath);
      const config = {
        responseType: 'blob',
        headers: {
          "token": localStorage.getItem("token")
        }
      }
      this.$message.info("正在下载中...请不要重复操作!")
      axios.get(targeturl, config).then(res => {
        fileName = "files.zip"
        // var number = targetPath.lastIndexOf('/');
        // if (number !== -1){
        //   fileName = targetPath.substring(number + 1)+".zip";
        // }else{
        //   fileName = "file.zip"
        // }
        fileDownload(res.data, fileName)
      }).catch(err => {
        this.$message.error(err)
      })
    },
    // 获取文件名后缀
    getFileExtension(filename) {
      const ex = filename.split(".");
      return ex.length > 1 ? ex[ex.length - 1].toLowerCase() : "";
    },
    getFileName(filename) {
      const ex = filename.split("/");
      return ex.length > 1 ? ex[ex.length - 1] : "";
    },
    // 获取指定分类下的所有文件
    getSpecifiedTypeFiles(type) {
      this.isLoading = true
      const _this = this
      this.request.get("/Cloud/FileSort/" + type).then(res => {
        if (res.code === 200) {
          this.selectedList = []
          this.fileInfoList = res.data.fileList;
          this.folderInfoList = [];
          this.isLoading = false
        }
      })
    },
    // 获取用户存放的文件
    getCloudFiles(currentPath) {
      this.searchInfo = ""
      this.isLoading = true
      if (currentPath === null || currentPath === "" || currentPath === undefined) {
        currentPath = "/"
      }
      const _this = this
      currentPath = encodeURIComponent(currentPath)
      this.request.get("/Cloud/getFilesByPath?path=" + currentPath).then(res => {
        if (res.code === 200) {
          this.selectedList = []
          this.fileInfoList = res.data.fileList;
          this.folderInfoList = res.data.folderList;
          this.isLoading = false
        }
      })
    },
    // 动态生成面包屑
    generateBreadcrumbLink(index) {
      // 从0到当前索引的子数组
      const subArray = this.breadcrumbList.slice(0, index + 1);
      // 清除多余的空格
      const filteredArray = subArray.filter(item => item !== '');
      // 使用数组元素拼接URL
      const url = filteredArray.join('/');
      // 返回生成的URL
      return url;
    },
    // 上传文件
    async uploadFile() {
      if (this.uploadFileList.length <= 0) {
        this.$message.info("请选择要上传的文件")
        return
      }
      const formData = new FormData();
      for (var i = 0; i < this.uploadFileList.length; i++) {
        formData.append("files", this.uploadFileList[i]);
      }
      let targetPath = ""
      if (this.currentPath === undefined) {
        targetPath = "/"
      } else {
        targetPath = this.currentPath
      }
      formData.append("targetPath", targetPath)

      const res = await axios.post("/api/upload/files", formData, {
        headers: {
          "Content-Type": "multipart/form-data", // 设置正确的Content-Type
          "token": this.token
        }
      })

      console.log(res)
      if (res.status === 200) {
        this.$message.success("上传成功!")
        location.reload()
      } else {
        this.$message.error(res.data.msg)
      }

    },
    // 当选择的文件发送变化时
    handleFileUpload(event) {
      this.uploadFileList = event.target.files
      console.log(this.uploadFileList)
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
      EventBus.$emit("selectedListChange", this.selectedList, this.currentPath)
      EventBus.$emit("getMainCurrentPath", this.currentPath)
    },
    // 文件大小改成同一单位方便比较 字节
    FileSizeToBytes(fileSize) {
      let sizeNum = fileSize.split(" ")
      let sizeValue = parseFloat(sizeNum[0])
      let sizeUnit = sizeNum[1]

      switch (sizeUnit) {
        case "字节":
          return sizeValue
        case "KB":
          return sizeValue * 1024;
        case "MB":
          return sizeValue * 1024 * 1024
        case "GB":
          return sizeValue * 1024 * 1024 * 1024
        default:
          return 0
      }

    },
    FilesListSort(num) {
      switch (num) {
        case 0:
          // A - Z
          this.fileInfoList.sort((a, b) => {
            return a.file_name.localeCompare(b.file_name, "zh", {sensitivity: 'base'})
          })
          this.folderInfoList.sort((a, b) => {
            return a.folder_name.localeCompare(b.folder_name, "zh", {sensitivity: 'base'})
          })
          break;
        case 1:
          // Z - A
          this.fileInfoList.sort((a, b) => {
            return b.file_name.localeCompare(a.file_name, "zh", {sensitivity: 'base'})
          })
          this.folderInfoList.sort((a, b) => {
            return b.folder_name.localeCompare(a.folder_name, "zh", {sensitivity: 'base'})
          })
          break;
        case 2:
          // 最早 - 最晚
          this.fileInfoList.sort((a, b) => {
            return new Date(a.modified_date) - new Date(b.modified_date)
          })
          this.folderInfoList.sort((a, b) => {
            return new Date(a.modified_date) - new Date(b.modified_date)
          })
          break;
        case 3:
          // 最晚 - 最早
          this.fileInfoList.sort((a, b) => {
            return new Date(b.modified_date) - new Date(a.modified_date)
          })
          this.folderInfoList.sort((a, b) => {
            return new Date(b.modified_date) - new Date(a.modified_date)
          })
          break;
        case 4:
          // 最大 - 最小
          this.fileInfoList.sort((a, b) => {
            let sizeA = this.FileSizeToBytes(a.file_size)
            let sizeB = this.FileSizeToBytes(b.file_size)
            return sizeB - sizeA
          })
          break;
        case 5:
          // 最小 - 最大
          this.fileInfoList.sort((a, b) => {
            let sizeA = this.FileSizeToBytes(a.file_size)
            let sizeB = this.FileSizeToBytes(b.file_size)
            return sizeA - sizeB
          })
          break;
      }
    },

    // 右键菜单
    showContextMenu(file_path, event) {
      event.preventDefault();
      document.addEventListener("click", this.closeContextMenu);
      this.MenuFilePath = file_path
      this.isContextMenuOpen = true;
      this.contextMenuPosition = {x: event.clientX, y: event.clientY};
    },
    closeContextMenu(event) {
      // 检查点击事件是否发生在菜单框以外
      const contextMenu = this.$refs.contextMenu; // 通过 ref 获取菜单组件的引用
      if (contextMenu) {
        this.isContextMenuOpen = false;
        document.removeEventListener("click", this.closeContextMenu); // 移除点击事件监听器
      }
    },
    // 新建文件夹
    createNewFolder(){
      if (this.currentPath === "" || this.currentPath === undefined) {
        this.CreateNewFolderDto.folderPath = "/"
      } else {
        this.CreateNewFolderDto.folderPath = this.currentPath
      }
      this.request.put("/Cloud/createNewFolder",this.CreateNewFolderDto).then(res =>{
        if (res.code === 200){
          // 新建成功
          location.reload()
        }else{
          this.$message({
            message: "新建文件夹失败",
            type: "error",
            center: true
          });
        }
      })
    }

  },
  mounted() {
    this.getCloudFiles("/")
    // this.getCloudFiles("/")
    EventBus.$on('returnListChange', (data) => {
      this.searchInfo = ""
      this.selectedList = data
    })
    EventBus.$on("categoryFiles", (type) => {
      this.searchInfo = ""
      switch (type) {
        case 0:
          this.getCloudFiles("/")
          this.isMain = true
          break;
        default:
          this.getSpecifiedTypeFiles(type)
          this.isMain = false
          break;
      }
    })
    EventBus.$on("getSearchedFiles", (keyword) => {
      const _this = this
      this.searchInfo = keyword
      this.isLoading = true
      this.request.get("/Cloud/search?keyword=" + encodeURIComponent(keyword)).then(res => {
        if (res.data.fileList === [] && res.data.folderList === []) {
          return;
        }
        this.selectedList = []
        this.fileInfoList = res.data.fileList;
        this.folderInfoList = res.data.folderList;
        this.isLoading = false
      }).catch(err => {
        console.log(err)
      })
    })
    // 接收传来的参数ContextMenu
    EventBus.$on("menuOperating", async (Operating, filePath) => {
      // todo 根据Operating操作直接调用各种方法
      console.log(Operating)
      switch (Operating) {
        // case "copy":
        //   console.log("复制文件")
        //   break;
        // case "move":
        //   console.log("移动文件")
        //   break;
        case "delete":
          console.log("删除文件")
          const _this = this
          if (this.currentPath === "" || this.currentPath === undefined) {
            this.moveDto.folderPath = "/"
          } else {
            this.moveDto.folderPath = this.currentPath
          }
          this.selectedList = [this.getFileName(filePath)]
          if (this.selectedList.length < 1) {
            this.$message({
              message: "未选择文件",
              type: "info",
              center: true
            });
          } else {
            this.moveDto.deleteList = this.selectedList
          }
          console.log(this.moveDto)
          _this.request.put("/Cloud/toRecycleBin", this.moveDto).then(res => {
            if (res.code === 200) {
              location.reload()
            } else {
              this.$message({
                message: "删除失败",
                type: "error",
                center: true
              });
            }
          })
          break;
        case "download":
          // TODO 这里下载文件夹未能实现
          console.log("下载文件")
          // 如果用户只选择了一个文件就进行分段下载
          if (this.currentPath === "" || this.currentPath === undefined) {
            this.downloadDto.folderPath = "/"
          } else {
            this.downloadDto.folderPath = this.currentPath
          }
          this.downloadDto.selectedList = this.getFileName(filePath)

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
          const response = await axios.head(url, config);
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
            this.downloadProgress = (i / numChunks * 100).toFixed(2) + "%"
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
          link.download = this.downloadDto.selectedList;
          link.click();

          break;
        case "refresh":
          console.log("刷新")
          location.reload()
          break;
      }
      console.log(filePath)
    })

  },
}
</script>

<style scoped>

.tableArea {
  height: 78vh;
  overflow: hidden;
}

#main {
  padding: 0;
  float: left;;
  display: inline-block;
  background-color: #303030;
  max-height: 80vh;
}

.my-nav {
  /*float: left;*/
  /*display: inline-block;*/
  /*border: 1px rgba(255,255,255,0.3) solid;*/
  text-align: center;
  line-height: 47px;
  /*width: calc(100vw - 200px);*/
  height: 47px;
  background-color: #424242;
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

.nav-options {
  display: inline-block;
}

.diary_ico_border_right {
  margin: 0 0.5rem;
  display: inline-block;
  cursor: pointer;
  line-height: 40px;
  text-align: center;
  border-radius: 24px;
  width: 42px;
  height: 42px;
}

.diary_ico_border_right:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.dropdown-toggle {
  border-radius: 5px;
}

.dropdown-toggle::after {
  content: none;
}

.dropdown-menu {
  padding: 0;
  background-color: #424242;
}

.dropdown-item:hover {
  background-color: rgba(255, 255, 255, 0.2);
  color: white;
}

.myCloudDevice {
  /*background-color: pink;*/
  /*width: calc(100vw - 200px);*/
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
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 1000px !important;
  margin-left: 1rem;
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

.iconView {
  padding: 1rem;
  height: 100vh;
}

.iconView-folderArea, .iconView-fileArea {
  margin-bottom: 1rem;
}

.folderDiv, .fileDiv {

}

.singleFile, .singleFolder {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  background-color: #424242;
  cursor: pointer;
  display: inline-block;
  border: 1px solid rgba(255, 255, 255, 0.5);
  border-radius: 1rem;
  padding: 0 1rem;
  margin: 1rem 1.5rem;
  max-width: 15rem;
  height: 3rem;
}

.singleFile:hover, .singleFolder:hover {
  background-color: rgba(255, 255, 255, 0.2);
}


.myPagingPlugin {
  position: relative;
  bottom: 0;
  margin-top: 2rem;
}

.page-link {
  background-color: rgba(255, 255, 255, 0.3);
}

.page-link:hover {
  background-color: rgba(255, 255, 255, 0.6);
}

.pagination {
  --bs-pagination-padding-x: 0.75rem;
  --bs-pagination-padding-y: 0.375rem;
  --bs-pagination-font-size: 1rem;
  --bs-pagination-color: #fff;
}

.nullTipDiv {
  margin-top: 5rem;
  width: 100%;
  text-align: center;
}

.modal-content {
  background-color: #424242;
}

.searchText {
  color: white;
  font-size: 20px;
  height: 40px;
  line-height: 40px;
  margin: 0.2rem 1rem;
}


</style>
