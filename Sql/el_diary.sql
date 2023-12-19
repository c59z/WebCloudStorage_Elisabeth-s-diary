/*
 Navicat Premium Data Transfer

 Source Server         : 本机mysql
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : el_diary

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 19/12/2023 17:13:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `perm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_menu_id_uindex`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '下载', 'sys:download');
INSERT INTO `sys_menu` VALUES (2, '上传', 'sys:upload');
INSERT INTO `sys_menu` VALUES (3, '新建', 'sys:new');
INSERT INTO `sys_menu` VALUES (4, '分享', 'sys:share');
INSERT INTO `sys_menu` VALUES (5, '删除', 'sys:delete');
INSERT INTO `sys_menu` VALUES (6, '超级管理员', 'sys:root');

-- ----------------------------
-- Table structure for sys_menu_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_role`;
CREATE TABLE `sys_menu_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` int NOT NULL,
  `menu_id` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_menu_role_id_uindex`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu_role
-- ----------------------------
INSERT INTO `sys_menu_role` VALUES (1, 1, 1);
INSERT INTO `sys_menu_role` VALUES (2, 1, 2);
INSERT INTO `sys_menu_role` VALUES (3, 1, 3);
INSERT INTO `sys_menu_role` VALUES (4, 1, 4);
INSERT INTO `sys_menu_role` VALUES (5, 1, 5);
INSERT INTO `sys_menu_role` VALUES (6, 1, 6);
INSERT INTO `sys_menu_role` VALUES (7, 2, 1);
INSERT INTO `sys_menu_role` VALUES (8, 2, 2);
INSERT INTO `sys_menu_role` VALUES (9, 2, 3);
INSERT INTO `sys_menu_role` VALUES (10, 2, 4);
INSERT INTO `sys_menu_role` VALUES (11, 2, 5);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色的标识符',
  `remark` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '说明该身份可以做什么事情',
  `createDate` date NOT NULL,
  `updated` date NOT NULL,
  `state` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_role_id_uindex`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', '拥有管理该网站的一切权限', '2022-11-14', '2022-11-14', 1);
INSERT INTO `sys_role` VALUES (2, '普通用户', 'normal', '一般用户', '2022-11-14', '2022-11-14', 1);

-- ----------------------------
-- Table structure for sys_storage
-- ----------------------------
DROP TABLE IF EXISTS `sys_storage`;
CREATE TABLE `sys_storage`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `storage` int NOT NULL DEFAULT 5,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_storage
-- ----------------------------
INSERT INTO `sys_storage` VALUES (1, 5);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_id` int NOT NULL DEFAULT 2,
  `createDate` datetime(0) NOT NULL,
  `updateDate` datetime(0) NULL DEFAULT NULL,
  `status` int NOT NULL DEFAULT 1,
  `storage_id` int NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'yuki', '46f94c8de14fb36680850768ff1b7f2a', '114514@gmail.com', 'D:/code/Project/java/前后端分离-伊丽莎白的日记/CloudDiary/UserAvatars/1/20230915203754_1_.jpg', 1, '2023-09-11 15:22:14', NULL, 1, 1);
INSERT INTO `sys_user` VALUES (2, 'bzh', '46f94c8de14fb36680850768ff1b7f2a', NULL, 'D:/code/Project/java/前后端分离-伊丽莎白的日记/CloudDiary/UserAvatars/2/20230916141049_2_.jpg', 2, '2023-09-13 16:24:15', '2023-09-15 11:08:34', 1, 1);
INSERT INTO `sys_user` VALUES (15, 'bzh666', '46f94c8de14fb36680850768ff1b7f2a', NULL, 'D:/code/Project/java/前后端分离-伊丽莎白的日记/CloudDiary/UserAvatars/15/20230916143835_15_.jpg', 2, '2023-09-16 14:33:01', NULL, 1, 1);
INSERT INTO `sys_user` VALUES (17, 'testUser001', '46f94c8de14fb36680850768ff1b7f2a', NULL, NULL, 2, '2023-09-22 10:54:58', NULL, 1, 1);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_user_role_id_uindex`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2, 2);

SET FOREIGN_KEY_CHECKS = 1;
