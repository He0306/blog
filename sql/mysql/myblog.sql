/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : myblog

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 11/12/2022 14:28:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for h_article
-- ----------------------------
DROP TABLE IF EXISTS `h_article`;
CREATE TABLE `h_article`  (
  `id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文章内容',
  `summary` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章摘要',
  `category_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章分类id',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章缩略图',
  `is_top` int(11) NULL DEFAULT NULL COMMENT '是否置顶(0否，1是)',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态(1已发布，0未发布)',
  `view_count` int(11) NULL DEFAULT 1 COMMENT '访问量',
  `user_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发布人ID',
  `is_delete` int(11) NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of h_article
-- ----------------------------
INSERT INTO `h_article` VALUES ('1594328971267235841', '第一章', '# 1\n### 略略略\n### 运行成功', '第一章 测试1', '1592895219102085122', 'https://hcd-blog.oss-cn-beijing.aliyuncs.com/2022/11/27/d7bad0df88374314b9c142ea1412d576图片1.png', 1, 1, 639, '1', 0, '2022-12-11 13:54:00', '2022-12-11 13:54:00');
INSERT INTO `h_article` VALUES ('1594333521634091009', '第二章', '![2023届Java开发工程师何超.jpg](https://hcd-blog.oss-cn-beijing.aliyuncs.com/2022/11/20/321557b957c34f9e9801f54c3ad07c612023届-Java开发工程师-何超.jpg)', '第二章 测试2', '1592895219102085122', 'https://hcd-blog.oss-cn-beijing.aliyuncs.com/2022/11/20/a94fb7cbaec74923aebcd87fb77a35891.jpg', 0, 1, 73, '1', 0, '2022-12-10 14:15:00', '2022-12-10 14:15:00');
INSERT INTO `h_article` VALUES ('1597497959778332674', ' 第三章', '3333333', '3333333', '1597570215200112641', '', 0, 1, 58, '1', 0, '2022-12-02 23:42:00', '2022-12-02 23:42:00');
INSERT INTO `h_article` VALUES ('1597499028159836161', '第四章', '', '43433', '1592895197694357506', '', 0, 1, 68, '1', 0, '2022-12-11 13:55:00', '2022-12-11 13:55:00');
INSERT INTO `h_article` VALUES ('1597572831699222529', '第五章', '# 烦烦烦方法', '112312121', '1592895197694357506', '', 0, 1, 115, '1', 0, '2022-12-11 12:27:00', '2022-12-11 12:27:00');
INSERT INTO `h_article` VALUES ('1597576599111184385', '第六章', '', '121212', '1597570215200112641', '', 0, 1, 31, '1', 0, '2022-12-11 12:28:00', '2022-12-11 12:28:00');
INSERT INTO `h_article` VALUES ('1597577029207699458', '第七章', '# 发生的光辐射大概发生的', '发士大夫士大夫是', '1597570302357749761', '', 0, 1, 25, '1', 0, '2022-12-10 17:20:00', '2022-12-10 17:20:00');
INSERT INTO `h_article` VALUES ('1597577101781741570', '第八章', '广东佛山广泛大使馆', '回复的更好的风格和', '1597570302357749761', '', 0, 1, 10, '1', 0, '2022-12-10 14:18:00', '2022-12-10 14:18:00');
INSERT INTO `h_article` VALUES ('1597577543517442050', '第九章', '飞洒发我', '广东佛山公司的', '1597570302357749761', '', 0, 1, 5, '1', 0, '2022-12-10 14:18:00', '2022-12-10 14:18:00');
INSERT INTO `h_article` VALUES ('1597578128048205826', '第十章', '', '个好地方好地方', '1592895219102085122', '', 0, 1, 7, '1', 0, '2022-12-11 12:27:00', '2022-12-11 12:27:00');

-- ----------------------------
-- Table structure for h_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `h_article_tag`;
CREATE TABLE `h_article_tag`  (
  `article_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章ID',
  `tag_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标签ID',
  `create_date` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `update_date` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of h_article_tag
-- ----------------------------
INSERT INTO `h_article_tag` VALUES ('1594328971267235841', '1592894934296260609', '2022-12-02 23:38:44', '2022-12-02 23:38:44');
INSERT INTO `h_article_tag` VALUES ('1594328971267235841', '1592895144376365058', '2022-12-02 23:38:44', '2022-12-02 23:38:44');
INSERT INTO `h_article_tag` VALUES ('1594328971267235841', '1597560401380196354', '2022-12-02 23:38:44', '2022-12-02 23:38:44');
INSERT INTO `h_article_tag` VALUES ('1594333521634091009', '1592894934296260609', '2022-12-02 23:38:53', '2022-12-02 23:38:53');
INSERT INTO `h_article_tag` VALUES ('1594333521634091009', '1597560582678986753', '2022-12-02 23:38:53', '2022-12-02 23:38:53');
INSERT INTO `h_article_tag` VALUES ('1597576599111184385', '1592894934296260609', '2022-12-02 23:39:14', '2022-12-02 23:39:14');
INSERT INTO `h_article_tag` VALUES ('1597576599111184385', '1592895011702140930', '2022-12-02 23:39:14', '2022-12-02 23:39:14');
INSERT INTO `h_article_tag` VALUES ('1597577101781741570', '1592895011702140930', '2022-12-02 23:39:19', '2022-12-02 23:39:19');
INSERT INTO `h_article_tag` VALUES ('1597577101781741570', '1597560540509454337', '2022-12-02 23:39:19', '2022-12-02 23:39:19');
INSERT INTO `h_article_tag` VALUES ('1597577543517442050', '1592894934296260609', '2022-12-02 23:39:29', '2022-12-02 23:39:29');
INSERT INTO `h_article_tag` VALUES ('1597577543517442050', '1592895144376365058', '2022-12-02 23:39:29', '2022-12-02 23:39:29');
INSERT INTO `h_article_tag` VALUES ('1597577543517442050', '1597560582678986753', '2022-12-02 23:39:29', '2022-12-02 23:39:29');
INSERT INTO `h_article_tag` VALUES ('1597577029207699458', '1597560401380196354', '2022-12-02 23:39:37', '2022-12-02 23:39:37');
INSERT INTO `h_article_tag` VALUES ('1597497959778332674', '1592894965648683009', '2022-12-02 23:41:56', '2022-12-02 23:41:56');
INSERT INTO `h_article_tag` VALUES ('1597497959778332674', '1597560540509454337', '2022-12-02 23:41:56', '2022-12-02 23:41:56');
INSERT INTO `h_article_tag` VALUES ('1597497959778332674', '1597560433156243458', '2022-12-02 23:41:56', '2022-12-02 23:41:56');
INSERT INTO `h_article_tag` VALUES ('1597499028159836161', '1592894934296260609', '2022-12-02 23:42:07', '2022-12-02 23:42:07');
INSERT INTO `h_article_tag` VALUES ('1597578128048205826', '1592895144376365058', '2022-12-02 23:52:09', '2022-12-02 23:52:09');
INSERT INTO `h_article_tag` VALUES ('1597578128048205826', '1592894965648683009', '2022-12-02 23:52:09', '2022-12-02 23:52:09');
INSERT INTO `h_article_tag` VALUES ('1597572831699222529', '1592895011702140930', '2022-12-02 23:52:35', '2022-12-02 23:52:35');
INSERT INTO `h_article_tag` VALUES ('1597572831699222529', '1597560401380196354', '2022-12-02 23:52:35', '2022-12-02 23:52:35');
INSERT INTO `h_article_tag` VALUES ('1597572831699222529', '1597560433156243458', '2022-12-02 23:52:35', '2022-12-02 23:52:35');
INSERT INTO `h_article_tag` VALUES ('1597572831699222529', '1592894965648683009', '2022-12-02 23:52:35', '2022-12-02 23:52:35');

-- ----------------------------
-- Table structure for h_category
-- ----------------------------
DROP TABLE IF EXISTS `h_category`;
CREATE TABLE `h_category`  (
  `id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类ID',
  `category_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `is_delete` int(11) NULL DEFAULT 0 COMMENT '逻辑删除（0未删除，1已删除）',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of h_category
-- ----------------------------
INSERT INTO `h_category` VALUES ('1592895197694357506', '前端', 0, '2022-11-20 21:59:03', '2022-11-20 21:59:03');
INSERT INTO `h_category` VALUES ('1592895219102085122', '后端', 0, '2022-11-20 21:59:04', '2022-11-20 21:59:04');
INSERT INTO `h_category` VALUES ('1597570215200112641', '多线程', 0, '2022-11-29 20:36:35', '2022-11-29 20:36:35');
INSERT INTO `h_category` VALUES ('1597570302357749761', '项目介绍', 0, '2022-11-29 20:36:56', '2022-11-29 20:36:56');

-- ----------------------------
-- Table structure for h_link
-- ----------------------------
DROP TABLE IF EXISTS `h_link`;
CREATE TABLE `h_link`  (
  `id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'logo',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `status` int(11) NULL DEFAULT 0 COMMENT '状态（1为正常，0为不展示）',
  `is_delete` int(11) NULL DEFAULT 0,
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of h_link
-- ----------------------------
INSERT INTO `h_link` VALUES ('1596854947486343169', 'Java小白', 'https://hcd-blog.oss-cn-beijing.aliyuncs.com/2022/10/23/07ebb60885124876a2d0c4c287723d841.jpg', 'http://hechao.online', '为小白而生', 1, 0, '2022-11-28 17:40:24', '2022-11-28 17:40:24');

-- ----------------------------
-- Table structure for h_tag
-- ----------------------------
DROP TABLE IF EXISTS `h_tag`;
CREATE TABLE `h_tag`  (
  `id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标签ID',
  `tag_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签名称',
  `is_delete` int(11) NULL DEFAULT 0 COMMENT '逻辑删除（0未删除，1已删除）',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of h_tag
-- ----------------------------
INSERT INTO `h_tag` VALUES ('1592894934296260609', 'Java', 0, '2022-11-29 16:12:57', '2022-11-29 16:12:57');
INSERT INTO `h_tag` VALUES ('1592894965648683009', 'Vue', 0, '2022-11-29 16:12:59', '2022-11-29 16:12:59');
INSERT INTO `h_tag` VALUES ('1592895011702140930', 'JavaSecript', 0, '2022-11-29 16:13:00', '2022-11-29 16:13:00');
INSERT INTO `h_tag` VALUES ('1592895144376365058', 'SpringBoot', 0, '2022-11-29 16:13:01', '2022-11-29 16:13:01');
INSERT INTO `h_tag` VALUES ('1597560401380196354', 'Spring', 0, '2022-11-29 19:57:35', '2022-11-29 19:57:35');
INSERT INTO `h_tag` VALUES ('1597560433156243458', 'Redis', 0, '2022-11-29 19:57:43', '2022-11-29 19:57:43');
INSERT INTO `h_tag` VALUES ('1597560540509454337', '微服务', 0, '2022-11-29 19:58:08', '2022-11-29 19:58:08');
INSERT INTO `h_tag` VALUES ('1597560582678986753', '前后端分离', 0, '2022-11-29 19:58:18', '2022-11-29 19:58:18');

-- ----------------------------
-- Table structure for operation_log
-- ----------------------------
DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log`  (
  `id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `opt_module` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作模块',
  `opt_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作类型',
  `opt_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作url',
  `opt_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作方法',
  `opt_desc` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '操作描述',
  `request_param` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求参数',
  `request_method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方式',
  `response_data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '返回数据',
  `user_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `ip_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作ip',
  `ip_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作地址',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of operation_log
-- ----------------------------
INSERT INTO `operation_log` VALUES ('1601460413545820162', '日志模块', '删除', '/admin/optLog/delete/batch', 'org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.deleteBatch', '根据ID批量删除', '[[\"1601460298777079809\",\"1601460302728114178\",\"1601460365634285570\",\"1601460403869560833\"]]', 'POST', '{\"code\":200,\"data\":true,\"msg\":\"操作成功\"}', '1', '管理员', '0:0:0:0:0:0:0:1', '', '2022-12-10 14:14:50', '2022-12-10 14:14:50');

-- ----------------------------
-- Table structure for sys_icon
-- ----------------------------
DROP TABLE IF EXISTS `sys_icon`;
CREATE TABLE `sys_icon`  (
  `id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型',
  `is_delete` int(11) NULL DEFAULT 0 COMMENT '逻辑删除（0未删除，1已删除）',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_icon
-- ----------------------------
INSERT INTO `sys_icon` VALUES ('1', 'el-icon-user', 'el-icon-user', 'icon', 0, '2022-11-16 13:28:10', '2022-11-16 13:28:10');
INSERT INTO `sys_icon` VALUES ('10', 'el-icon-folder-opened', 'el-icon-folder-opened', 'icon', 0, '2022-11-16 13:28:59', '2022-11-16 13:28:59');
INSERT INTO `sys_icon` VALUES ('11', 'el-icon-location-outline', 'el-icon-location-outline', 'icon', 0, '2022-11-16 13:29:05', '2022-11-16 13:29:05');
INSERT INTO `sys_icon` VALUES ('12', 'el-icon-chat-dot-round', 'el-icon-chat-dot-round', 'icon', 0, '2022-11-16 13:29:10', '2022-11-16 13:29:10');
INSERT INTO `sys_icon` VALUES ('1592889558179999745', 'el-icon-collection', 'el-icon-collection', 'icon', 0, '2022-11-16 22:37:19', '2022-11-16 22:37:19');
INSERT INTO `sys_icon` VALUES ('1593058689332264962', '1', '1', '1', 1, '2022-11-17 09:49:28', '2022-11-17 09:49:28');
INSERT INTO `sys_icon` VALUES ('1593059516000202753', '1', '12', '1', 1, '2022-11-17 09:52:51', '2022-11-17 09:52:51');
INSERT INTO `sys_icon` VALUES ('2', 'el-icon-house', 'el-icon-house', 'icon', 0, '2022-11-16 13:28:18', '2022-11-16 13:28:18');
INSERT INTO `sys_icon` VALUES ('3', 'el-icon-menu', 'el-icon-menu', 'icon', 0, '2022-11-16 13:28:24', '2022-11-16 13:28:24');
INSERT INTO `sys_icon` VALUES ('4', 'el-icon-s-custom', 'el-icon-s-custom', 'icon', 0, '2022-11-16 13:28:29', '2022-11-16 13:28:29');
INSERT INTO `sys_icon` VALUES ('5', 'el-icon-document', 'el-icon-document', 'icon', 0, '2022-11-16 13:28:34', '2022-11-16 13:28:34');
INSERT INTO `sys_icon` VALUES ('6', 'el-icon-setting', 'el-icon-setting', 'icon', 0, '2022-11-16 13:28:38', '2022-11-16 13:28:38');
INSERT INTO `sys_icon` VALUES ('7', 'el-icon-map-location', 'el-icon-map-location', 'icon', 0, '2022-11-16 13:28:42', '2022-11-16 13:28:42');
INSERT INTO `sys_icon` VALUES ('8', 'el-icon-s-claim', 'el-icon-s-claim', 'icon', 0, '2022-11-16 13:28:47', '2022-11-16 13:28:47');
INSERT INTO `sys_icon` VALUES ('9', 'el-icon-view', 'el-icon-view', 'icon', 0, '2022-11-16 13:28:51', '2022-11-16 13:28:51');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路径',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `pid` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父级ID',
  `fid` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限id',
  `page_path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '页面组件路径',
  `perms` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限编码',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `is_delete` int(11) NOT NULL DEFAULT 0 COMMENT '逻辑删除（1已删除，0未删除）',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '主页', '/home', 'el-icon-s-home', NULL, NULL, 'home/home', NULL, '主页面', 1, 0, '2022-11-16 14:40:03', '2022-11-16 14:40:03');
INSERT INTO `sys_menu` VALUES ('10', '编辑按钮', NULL, NULL, NULL, '5', NULL, 'sys:user:update', NULL, NULL, 0, '2022-11-16 21:16:46', '2022-11-16 21:16:46');
INSERT INTO `sys_menu` VALUES ('11', '重置密码', NULL, NULL, NULL, '5', NULL, 'sys:user:restPassword', NULL, NULL, 0, '2022-11-16 21:16:51', '2022-11-16 21:16:51');
INSERT INTO `sys_menu` VALUES ('12', '删除按钮', NULL, NULL, NULL, '5', NULL, 'sys:user:delete', NULL, NULL, 0, '2022-11-16 21:17:09', '2022-11-16 21:17:09');
INSERT INTO `sys_menu` VALUES ('13', '批量删除按钮', NULL, NULL, NULL, '5', NULL, 'sys:user:deleteBatch', NULL, NULL, 0, '2022-11-16 21:17:18', '2022-11-16 21:17:18');
INSERT INTO `sys_menu` VALUES ('14', '查询全部', NULL, NULL, NULL, '3', NULL, 'sys:role:list', NULL, NULL, 0, '2022-11-16 21:17:25', '2022-11-16 21:17:25');
INSERT INTO `sys_menu` VALUES ('15', '新增按钮', NULL, NULL, NULL, '3', NULL, 'sys:role:add', NULL, NULL, 0, '2022-11-16 21:17:32', '2022-11-16 21:17:32');
INSERT INTO `sys_menu` VALUES ('1592889703848177665', '日志管理', NULL, 'el-icon-collection', NULL, NULL, NULL, NULL, '日志管理', 3, 0, '2022-11-16 22:37:54', '2022-11-16 22:37:54');
INSERT INTO `sys_menu` VALUES ('1592890195949088769', '操作日志', '/log', NULL, '1592889703848177665', NULL, 'log/log', NULL, NULL, NULL, 0, '2022-11-16 22:39:51', '2022-11-16 22:39:51');
INSERT INTO `sys_menu` VALUES ('1592891163658907649', '文章管理', NULL, 'el-icon-document', NULL, NULL, NULL, NULL, '文章管理', 2, 0, '2022-11-16 22:43:42', '2022-11-16 22:43:42');
INSERT INTO `sys_menu` VALUES ('1592891366893907970', '分类管理', '/category', NULL, '1592891163658907649', NULL, 'article/category', NULL, NULL, NULL, 0, '2022-11-16 22:44:31', '2022-11-16 22:44:31');
INSERT INTO `sys_menu` VALUES ('1592891494497218561', '标签管理', '/tag', NULL, '1592891163658907649', NULL, 'article/tag', NULL, NULL, NULL, 0, '2022-11-16 22:45:01', '2022-11-16 22:45:01');
INSERT INTO `sys_menu` VALUES ('1592891647060832258', '文章列表', '/articleList', NULL, '1592891163658907649', NULL, 'article/articleList', NULL, NULL, NULL, 0, '2022-11-16 22:45:37', '2022-11-16 22:45:37');
INSERT INTO `sys_menu` VALUES ('1592891766208425986', '新增文章', '/addArticle', NULL, '1592891163658907649', NULL, 'article/addArticle', NULL, NULL, NULL, 0, '2022-11-16 22:46:06', '2022-11-16 22:46:06');
INSERT INTO `sys_menu` VALUES ('1593059379785986049', '修改按钮', NULL, NULL, NULL, '6', NULL, 'sys:icon:update', NULL, NULL, 0, '2022-11-17 09:52:08', '2022-11-17 09:52:08');
INSERT INTO `sys_menu` VALUES ('1593059935975862273', '修改按钮', NULL, NULL, NULL, '4', NULL, 'sys:menu:update', NULL, NULL, 0, '2022-11-17 09:54:21', '2022-11-17 09:54:21');
INSERT INTO `sys_menu` VALUES ('1593060693030875137', '修改按钮', NULL, NULL, NULL, '3', NULL, 'sys:role:update', NULL, NULL, 0, '2022-11-17 09:57:21', '2022-11-17 09:57:21');
INSERT INTO `sys_menu` VALUES ('1596804130133745665', '友链管理', '/link', NULL, '1592891163658907649', NULL, 'article/link', NULL, NULL, NULL, 0, '2022-11-27 17:52:26', '2022-11-27 17:52:26');
INSERT INTO `sys_menu` VALUES ('16', '删除按钮', NULL, NULL, NULL, '3', NULL, 'sys:role:delete', NULL, NULL, 0, '2022-11-16 21:17:41', '2022-11-16 21:17:41');
INSERT INTO `sys_menu` VALUES ('17', '批量删除按钮', NULL, NULL, NULL, '3', NULL, 'sys:role:deleteBatch', NULL, NULL, 0, '2022-11-16 21:17:48', '2022-11-16 21:17:48');
INSERT INTO `sys_menu` VALUES ('18', '新增按钮', NULL, NULL, NULL, '4', NULL, 'sys:menu:add', NULL, NULL, 0, '2022-11-16 21:17:57', '2022-11-16 21:17:57');
INSERT INTO `sys_menu` VALUES ('19', '批量删除按钮', NULL, NULL, NULL, '4', NULL, 'sys:menu:deleteBatch', NULL, NULL, 0, '2022-11-16 21:18:04', '2022-11-16 21:18:04');
INSERT INTO `sys_menu` VALUES ('2', '权限管理', NULL, 'el-icon-share', NULL, NULL, NULL, NULL, '权限管理', 4, 0, '2022-11-16 17:46:16', '2022-11-16 17:46:16');
INSERT INTO `sys_menu` VALUES ('20', '查询全部', NULL, NULL, NULL, '6', NULL, 'sys:icon:list', NULL, NULL, 0, '2022-11-16 21:18:08', '2022-11-16 21:18:08');
INSERT INTO `sys_menu` VALUES ('21', '新增按钮', NULL, NULL, NULL, '6', NULL, 'sys:icon:add', NULL, NULL, 0, '2022-11-16 21:18:14', '2022-11-16 21:18:14');
INSERT INTO `sys_menu` VALUES ('22', '批量删除按钮', NULL, NULL, NULL, '6', NULL, 'sys:icon:deleteBatch', NULL, NULL, 0, '2022-11-16 21:18:19', '2022-11-16 21:18:19');
INSERT INTO `sys_menu` VALUES ('23', '删除按钮', NULL, NULL, NULL, '6', NULL, 'sys:icon:deleteById', NULL, NULL, 0, '2022-11-16 21:18:23', '2022-11-16 21:18:23');
INSERT INTO `sys_menu` VALUES ('3', '角色管理', '/role', NULL, '2', NULL, 'sys/role', NULL, NULL, NULL, 0, '2022-11-16 17:45:44', '2022-11-16 17:45:44');
INSERT INTO `sys_menu` VALUES ('4', '菜单管理', '/menu', NULL, '2', NULL, 'sys/menu', NULL, NULL, NULL, 0, '2022-11-16 17:45:48', '2022-11-16 17:45:48');
INSERT INTO `sys_menu` VALUES ('5', '用户管理', '/user', NULL, '2', NULL, 'sys/user', NULL, NULL, NULL, 0, '2022-11-16 17:45:52', '2022-11-16 17:45:52');
INSERT INTO `sys_menu` VALUES ('6', '图标管理', '/icon', NULL, '2', NULL, 'sys/icon', NULL, NULL, NULL, 0, '2022-11-16 17:45:55', '2022-11-16 17:45:55');
INSERT INTO `sys_menu` VALUES ('7', '删除按钮', NULL, NULL, NULL, '4', NULL, 'sys:menu:delete', NULL, NULL, 0, '2022-11-16 21:18:34', '2022-11-16 21:18:34');
INSERT INTO `sys_menu` VALUES ('8', '查询全部', NULL, NULL, NULL, '5', NULL, 'sys:user:list', NULL, NULL, 0, '2022-11-16 21:18:38', '2022-11-16 21:18:38');
INSERT INTO `sys_menu` VALUES ('9', '新增按钮', NULL, NULL, NULL, '5', NULL, 'sys:user:add', NULL, NULL, 0, '2022-11-16 21:18:41', '2022-11-16 21:18:41');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `is_delete` int(11) NULL DEFAULT 0 COMMENT '逻辑删除（0未删除，1已删除）',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '管理员', 'ROLE_ADMIN', '管理员', 0, '2022-11-16 13:15:37', '2022-11-16 13:15:37');
INSERT INTO `sys_role` VALUES ('2', '普通用户', 'ROLE_NORMAL', '普通用户', 0, '2022-11-16 13:16:02', '2022-11-16 13:16:02');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色ID',
  `menu_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单ID',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '插入时间',
  `udpate_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('2', '1', '2022-11-26 21:11:59', '2022-11-26 21:11:59');
INSERT INTO `sys_role_menu` VALUES ('1', '1', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '1592891163658907649', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '1596804130133745665', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '1592891366893907970', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '1592891494497218561', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '1592891647060832258', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '1592891766208425986', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '1592889703848177665', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '1592890195949088769', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '2', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '3', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '14', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '15', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '1593060693030875137', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '16', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '17', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '4', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '7', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '1593059935975862273', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '18', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '19', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '5', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '10', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '11', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '12', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '13', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '8', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '9', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '6', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '1593059379785986049', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '20', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '21', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '22', '2022-11-27 17:54:14', '2022-11-27 17:54:14');
INSERT INTO `sys_role_menu` VALUES ('1', '23', '2022-11-27 17:54:14', '2022-11-27 17:54:14');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录账号',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录密码',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `email` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `is_delete` int(11) NULL DEFAULT 0 COMMENT '逻辑删除（0未删除，1已删除）',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '$2a$10$VXCVwcpLIYpp6BJQaZkMsulN/IOV4DhVfCoZaPqDQsXiMRNPAPdva', '管理员', 'https://hcd-blog.oss-cn-beijing.aliyuncs.com/2022/10/23/07ebb60885124876a2d0c4c287723d841.jpg', '2740860037@qq.com', 0, '2022-11-16 13:11:45', '2022-11-16 13:11:45');
INSERT INTO `sys_user` VALUES ('1592893545260216321', 'hechao', '$2a$10$Qyx3Q5hAIS.BR0Rb2hwSm.4E.Orvo5nOeIDrq8WzCCe6Q001t/Bom', '何超', 'https://hcd-blog.oss-cn-beijing.aliyuncs.com/2022/11/16/482e2b29326843c0bf0990fe2d618c5c图片1.png', '1@qq.com', 0, '2022-11-16 22:53:10', '2022-11-16 22:53:10');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `role_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色ID',
  `user_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '插入时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '2022-11-16 14:39:22', '2022-11-16 14:39:22');
INSERT INTO `sys_user_role` VALUES ('2', '1592893545260216321', '2022-11-16 22:53:10', '2022-11-16 22:53:10');

SET FOREIGN_KEY_CHECKS = 1;
