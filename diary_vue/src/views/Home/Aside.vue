<template>
  <div>
    <div class="my-folder">
      <button href="#myCollapse" data-bs-toggle="collapse" class="btn dropdown-toggle">
        <div class="img_svg">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-folder2"
            viewBox="0 0 16 16">
            <path
              d="M1 3.5A1.5 1.5 0 0 1 2.5 2h2.764c.958 0 1.76.56 2.311 1.184C7.985 3.648 8.48 4 9 4h4.5A1.5 1.5 0 0 1 15 5.5v7a1.5 1.5 0 0 1-1.5 1.5h-11A1.5 1.5 0 0 1 1 12.5v-9zM2.5 3a.5.5 0 0 0-.5.5V6h12v-.5a.5.5 0 0 0-.5-.5H9c-.964 0-1.71-.629-2.174-1.154C6.374 3.334 5.82 3 5.264 3H2.5zM14 7H2v5.5a.5.5 0 0 0 .5.5h11a.5.5 0 0 0 .5-.5V7z" />
          </svg>
        </div>
        <span>我的文件</span>
      </button>
      <hr style="padding: 0;margin: 0;position:relative;">
      
      <div id="myCollapse" class="collapse show">
         <!-- 动态显示的5个div -->
        <!--全部文件-->
        <router-link  style="text-decoration: none" to="/main">
           <div @click="toMain(0)" class="drawer-content" key="1">
             <div>
               <div class="img_svg">
                 <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-boxes"
                      viewBox="0 0 16 16">
                   <path
                       d="M7.752.066a.5.5 0 0 1 .496 0l3.75 2.143a.5.5 0 0 1 .252.434v3.995l3.498 2A.5.5 0 0 1 16 9.07v4.286a.5.5 0 0 1-.252.434l-3.75 2.143a.5.5 0 0 1-.496 0l-3.502-2-3.502 2.001a.5.5 0 0 1-.496 0l-3.75-2.143A.5.5 0 0 1 0 13.357V9.071a.5.5 0 0 1 .252-.434L3.75 6.638V2.643a.5.5 0 0 1 .252-.434L7.752.066ZM4.25 7.504 1.508 9.071l2.742 1.567 2.742-1.567L4.25 7.504ZM7.5 9.933l-2.75 1.571v3.134l2.75-1.571V9.933Zm1 3.134 2.75 1.571v-3.134L8.5 9.933v3.134Zm.508-3.996 2.742 1.567 2.742-1.567-2.742-1.567-2.742 1.567Zm2.242-2.433V3.504L8.5 5.076V8.21l2.75-1.572ZM7.5 8.21V5.076L4.75 3.504v3.134L7.5 8.21ZM5.258 2.643 8 4.21l2.742-1.567L8 1.076 5.258 2.643ZM15 9.933l-2.75 1.571v3.134L15 13.067V9.933ZM3.75 14.638v-3.134L1 9.933v3.134l2.75 1.571Z" />
                 </svg>
               </div>
               <span>全部</span>
             </div>
          </div>
        </router-link>
        <!--视频-->
        <router-link  style="text-decoration: none" :to="{query: {type: 1}}">
          <div @click="toMain(1)" class="drawer-content" key="2">
            <div class="img_svg">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                class="bi bi-file-earmark-play" viewBox="0 0 16 16">
                <path
                  d="M6 6.883v4.234a.5.5 0 0 0 .757.429l3.528-2.117a.5.5 0 0 0 0-.858L6.757 6.454a.5.5 0 0 0-.757.43z" />
                <path
                  d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2zM9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5v2z" />
              </svg>
            </div>
            <span>视频</span>
          </div>
        </router-link>
        <!--图片-->
        <router-link  style="text-decoration: none" :to="{query: {type: 2}}">
          <div @click="toMain(2)" class="drawer-content" key="3">
          <div class="img_svg">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-images"
              viewBox="0 0 16 16">
              <path d="M4.502 9a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3z" />
              <path
                d="M14.002 13a2 2 0 0 1-2 2h-10a2 2 0 0 1-2-2V5A2 2 0 0 1 2 3a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2v8a2 2 0 0 1-1.998 2zM14 2H4a1 1 0 0 0-1 1h9.002a2 2 0 0 1 2 2v7A1 1 0 0 0 15 11V3a1 1 0 0 0-1-1zM2.002 4a1 1 0 0 0-1 1v8l2.646-2.354a.5.5 0 0 1 .63-.062l2.66 1.773 3.71-3.71a.5.5 0 0 1 .577-.094l1.777 1.947V5a1 1 0 0 0-1-1h-10z" />
            </svg>
          </div>
          <span>图片</span>
        </div>
        </router-link>
        <!--音乐-->
        <router-link  style="text-decoration: none" :to="{query: {type: 3}}">
          <div @click="toMain(3)" class="drawer-content" key="4">
          <div class="img_svg">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
              class="bi bi-file-earmark-music" viewBox="0 0 16 16">
              <path
                d="M11 6.64a1 1 0 0 0-1.243-.97l-1 .25A1 1 0 0 0 8 6.89v4.306A2.572 2.572 0 0 0 7 11c-.5 0-.974.134-1.338.377-.36.24-.662.628-.662 1.123s.301.883.662 1.123c.364.243.839.377 1.338.377.5 0 .974-.134 1.338-.377.36-.24.662-.628.662-1.123V8.89l2-.5V6.64z" />
              <path
                d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2zM9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5v2z" />
            </svg>
          </div>
          <span>音乐</span>
        </div>
        </router-link>
        <!--文档-->
        <router-link  style="text-decoration: none" :to="{query: {type: 4}}">
          <div @click="toMain(4)" class="drawer-content" key="5">
          <div class="img_svg">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-file-text"
              viewBox="0 0 16 16">
              <path
                d="M5 4a.5.5 0 0 0 0 1h6a.5.5 0 0 0 0-1H5zm-.5 2.5A.5.5 0 0 1 5 6h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5zM5 8a.5.5 0 0 0 0 1h6a.5.5 0 0 0 0-1H5zm0 2a.5.5 0 0 0 0 1h3a.5.5 0 0 0 0-1H5z" />
              <path
                d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2zm10-1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1z" />
            </svg>
          </div>
          <span>文档</span>
        </div>
        </router-link>
      </div>
    </div>
    <div class="other-folder">
      <hr class="my-hr" style="top: 0">
      <!--  任务队列    -->
<!--      <div class="other-drawer-content">-->
<!--        <div>-->
<!--          <div class="img_svg">-->
<!--            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-list-task"-->
<!--                 viewBox="0 0 16 16">-->
<!--              <path fill-rule="evenodd"-->
<!--                    d="M2 2.5a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5V3a.5.5 0 0 0-.5-.5H2zM3 3H2v1h1V3z" />-->
<!--              <path-->
<!--                  d="M5 3.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zM5.5 7a.5.5 0 0 0 0 1h9a.5.5 0 0 0 0-1h-9zm0 4a.5.5 0 0 0 0 1h9a.5.5 0 0 0 0-1h-9z" />-->
<!--              <path fill-rule="evenodd"-->
<!--                    d="M1.5 7a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5H2a.5.5 0 0 1-.5-.5V7zM2 7h1v1H2V7zm0 3.5a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5H2zm1 .5H2v1h1v-1z" />-->
<!--            </svg>-->
<!--          </div>-->
<!--          <span style="position:relative;bottom: -1px">任务队列</span>-->
<!--        </div>-->
<!--      </div>-->
      <!--  回收站    -->
      <div class="other-drawer-content">
        <router-link style="text-decoration: none" to="/recyclebin">
          <div @click="clearSelectedList">
            <div class="img_svg">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">
                <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
              </svg>
            </div>
            <span style="position:relative;bottom: -1px;">回收站</span>
          </div>
        </router-link>
      </div>
    </div>
    <div class="userStorage">
      <hr class="my-hr" style="top: 0;">
      <div class="myStorage">
        <div>
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-sd-card" viewBox="0 0 16 16">
            <path d="M6.25 3.5a.75.75 0 0 0-1.5 0v2a.75.75 0 0 0 1.5 0v-2zm2 0a.75.75 0 0 0-1.5 0v2a.75.75 0 0 0 1.5 0v-2zm2 0a.75.75 0 0 0-1.5 0v2a.75.75 0 0 0 1.5 0v-2zm2 0a.75.75 0 0 0-1.5 0v2a.75.75 0 0 0 1.5 0v-2z"/>
            <path fill-rule="evenodd" d="M5.914 0H12.5A1.5 1.5 0 0 1 14 1.5v13a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 14.5V3.914c0-.398.158-.78.44-1.06L4.853.439A1.5 1.5 0 0 1 5.914 0zM13 1.5a.5.5 0 0 0-.5-.5H5.914a.5.5 0 0 0-.353.146L3.146 3.561A.5.5 0 0 0 3 3.914V14.5a.5.5 0 0 0 .5.5h9a.5.5 0 0 0 .5-.5v-13z"/>
          </svg>
          存储空间
        </div>
        <div class="storageProgress">
          <div style="height: 6px;margin-bottom: 5px;" class="progress" role="progressbar" aria-label="Info example" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100">
            <div class="progress-bar bg-info" :style="usedStorageWidth"></div>
          </div>
          <div style="text-align: center">
            <span class="userStorageInfoText">
              <span>{{this.usedStorage.usedStorageSpace}}</span>
                /
              <span>{{this.usedStorage.storageSpace}}</span>
            </span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {EventBus} from "@/utils/EventBus";

export default {
  name: "Aside",
  props: {

  },
  data() {
    return {
      isOpen: false,
      widthValue: '0%',
      usedStorage: {
        "occupancy": 0.0,
        "usedStorageSpace": 0,
        "storageSpace":0
      },
    }
  },
  methods: {
    clearSelectedList(){
      EventBus.$emit("selectedListChange",undefined,undefined)
    },
    toMain(fileType){
      EventBus.$emit("categoryFiles", fileType)
    },
  },
  computed: {
    usedStorageWidth(){
      return {
        width: this.widthValue
      }
    },
  },
  mounted() {
    // TODO 获取用户剩余空间
    this.getUserStorageInfo()
  },
}
</script>

<style scoped>
.my-folder {
  /*position: relative;*/
  padding: 0;
}

.other-folder {
  /*position: relative;*/
  height: 50vh;
  padding: 0;
}


.dropdown-toggle {
  font-size: 16px;
  cursor: pointer;
  padding: 10px;
  width: 160px;
  text-align: center;
}

.dropdown-toggle span {
  position: relative;
  bottom: -2px;
}

.img_svg {
  display: inline-block;
  position: relative;
  left: -5px;
}

.drawer-content span {
  font-size: 16px;
  bottom: -1px;
  position: relative;
  margin-left: 0.1rem
}

.dropdown-toggle::after {
  border: none;
}

.dropdown-toggle:hover,
.drawer-content:hover,
.other-drawer-content:hover{
  background-color: rgba(255, 255, 255, 0.3);
}

.drawer-content,
.other-drawer-content {
  cursor: pointer;
  text-align: center;
  padding: 10px;
}

/* .other-drawer-content {

} */

.my-hr {
  padding: 0;
  margin: 0;
  position: relative;
  top: -1rem;
}

.userStorage {
  padding: 0;
  position: absolute;
  left: 0;
  bottom: 0;
  height: 10vh;
}

.myStorage {
  width: 100%;
  height: 100%;
  padding: 12px 24px;
}

.storageProgress {
  padding-top: 10px;
}

.userStorageInfoText {
  font-size: 12px;
}

</style>