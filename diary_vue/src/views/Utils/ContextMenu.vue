<template>
  <div v-show="isOpen" class="context-menu" :style="{ top: `${position.y}px`, left: `${position.x}px` }">
    <ul>
<!--      <li @click="copyFile()">复制</li>-->
<!--      <li @click="moveFile()">移动</li>-->
      <li @click="deleteFile()">删除</li>
      <li @click="downloadFile()">下载</li>
      <li @click="refresh()">刷新</li>
    </ul>
  </div>
</template>

<script>
import {EventBus} from "@/utils/EventBus";

export default {
  name: "ContextMenu",
  props: {
    isOpen: Boolean,
    position: Object, // 包含 x 和 y 坐标的对象
    filePath: "",
  },
  methods: {
    // copyFile() {
    //   EventBus.$emit("menuOperating", "copy",this.filePath)
    // },
    // moveFile() {
    //   EventBus.$emit("menuOperating", "move",this.filePath)
    // },
    deleteFile() {
      EventBus.$emit("menuOperating", "delete",this.filePath)
    },
    downloadFile() {
      EventBus.$emit("menuOperating", "download",this.filePath)
    },
    refresh() {
      EventBus.$emit("menuOperating", "refresh")
    },
  },
}
</script>

<style scoped>
.context-menu {
  position: fixed;
  background-color: #424242;
  border: 1px solid #424242;
  box-shadow: 2px 2px 6px rgba(0, 0, 0, 0.2);
  z-index: 9999;
}

ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

li {
  padding: 8px 16px;
  cursor: pointer;
}

li:hover {
  background-color: rgba(255,255,255,0.3);
}
</style>