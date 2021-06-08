/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.7.34 : Database - haibing
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`haibing` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `haibing`;

/*Table structure for table `README` */

DROP TABLE IF EXISTS `README`;

CREATE TABLE `README` (
  `id` int(11) NOT NULL,
  `readme` text,
  `BTC_address` text,
  `email` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `README` */

insert  into `README`(`id`,`readme`,`BTC_address`,`email`) values (1,'以下数据库已被删除：exam, haibing, ry-vue, zsyyds。 我们有一个完整的备份。 要恢复它，您必须将0.007比特币（BTC）支付给我们的比特币地址bc1qfadew7npkda0ke7wnfj57hlcu3c70vxr6hr8gg。 有关说明，请通过wang16@tutanota.com通过电子邮件联系我们。 任何与付款无关的邮件都将被忽略！','bc1qfadew7npkda0ke7wnfj57hlcu3c70vxr6hr8gg','wang16@tutanota.com');

/*Table structure for table `hb_comment` */

DROP TABLE IF EXISTS `hb_comment`;

CREATE TABLE `hb_comment` (
  `pk_comment_id` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `pk_good_id` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `content` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `pk_user_ed_id` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '被评论者Id',
  `pk_user_ing_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '评论者Id',
  `type` int(11) DEFAULT NULL COMMENT '1星到5星',
  `created_time` datetime DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  PRIMARY KEY (`pk_comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Data for the table `hb_comment` */

insert  into `hb_comment`(`pk_comment_id`,`pk_good_id`,`content`,`pk_user_ed_id`,`pk_user_ing_id`,`type`,`created_time`,`updated_time`) values ('b56e1916-f08f-488b-','1','特别好吃！！','1','1',5,'2021-06-02 00:09:19','2021-06-02 00:09:19');

/*Table structure for table `hb_good` */

DROP TABLE IF EXISTS `hb_good`;

CREATE TABLE `hb_good` (
  `pk_good_id` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `good_name` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `price` double(16,2) DEFAULT NULL,
  `image` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '未激活：0  已激活：1',
  `type` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  `user_id` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`pk_good_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Data for the table `hb_good` */

insert  into `hb_good`(`pk_good_id`,`good_name`,`description`,`price`,`image`,`status`,`type`,`count`,`created_time`,`updated_time`,`user_id`) values ('1','饭扫光香菇拌面酱香辣酱','饭扫光香菇牛肉酱拌饭酱200g*2瓶四川特产下饭菜炸酱拌面酱香辣酱',20.70,'https://img.alicdn.com/imgextra/i4/2776560753/O1CN01G5pRbo1HQuqwhH4ag_!!2776560753-0-lubanu-s.jpg_430x430q90.jpg',1,'美食',996,'2021-03-29 17:25:31','2021-03-29 17:25:36','2'),('10','ESC MAN STUDIO防晒服','ESC MAN STUDIO/自制oversize半高领衬衫外套 无惧夏日炎热防晒服',67.90,'https://gd3.alicdn.com/imgextra/i1/2930246619/O1CN01HDcNM01ylYEVZs1GO_!!2930246619.jpg',1,'服装',9999,'2021-06-01 20:26:25','2021-06-01 20:26:22','1'),('2','元气森林乳茶迪士尼联名奶茶低糖低脂肪茶饮料','【联名限定款】元气森林乳茶迪士尼联名奶茶低糖低脂肪茶饮料12瓶',15.50,'https://img.alicdn.com/imgextra/i3/3311179966/O1CN01OTD09X2NUTs4VjljI_!!3311179966.jpg_430x430q90.jpg',1,'饮料',74,'2021-03-29 17:30:07','2021-03-29 17:30:16','2'),('3','羊绒帽子女双色双层边男潮秋','Pure cashmere英单客供 羊绒帽子女双色双层边男潮秋冬季保暖针织',112.45,'https://gd3.alicdn.com/imgextra/i3/189359744/O1CN01e2KhjQ2LqnoQ7rtd6_!!189359744.jpg',1,'服装',5,'2021-03-29 17:32:05','2021-03-29 17:32:17','2'),('4','星巴克夏季保温杯','星巴克夏季第二波羊驼粉色儿童双盖6.11上市新品妙趣之选保温杯',100.00,'https://gd1.alicdn.com/imgextra/i2/64886400/O1CN01gMFENl1x9FN5gFIIm_!!64886400.jpg',1,'服装',69,'2021-04-01 15:02:00','2021-04-01 15:02:05','1'),('5','意式北欧客厅整装','意式极简免洗科技布沙发转角轻奢现代简约乳胶布沙发北欧客厅整装',1111.00,'https://img.alicdn.com/imgextra/i3/81619630/O1CN01sMCP4J2L0anNdrei5_!!81619630-0-picasso.jpg',1,'家具',100,'2021-06-01 20:22:19','2021-06-01 20:22:23','1'),('6','gunandrose单肩斜挎包','gunandrose小众小动物网红可爱牛仔公仔玩偶卡通手拎包单肩斜挎包',30.00,'https://img.alicdn.com/imgextra/i2/1737289768/O1CN01aluzmD2M1nRRafNFH_!!1737289768.jpg',1,'挎包',1000,'2021-06-01 20:23:22','2021-06-01 20:23:26','1'),('7','2021新款链条单肩包','2021新款透明珍珠夹子包包少女百搭仙露手提斜挎小包包链条单肩包',34.00,'https://gd2.alicdn.com/imgextra/i4/4079310414/O1CN01IyDEM11Evee03CENs_!!4079310414.jpg',1,'挎包',10000,'2021-06-01 20:24:07','2021-06-01 20:24:03','1'),('7bf75e9b-1b23-4c9c-','sony a6000','微单相机',1100.00,'http://nttbucket.oss-cn-beijing.aliyuncs.com/img/1622603032000.jpeg',1,'数码',1000,'2021-06-02 11:04:06','2021-06-02 11:04:06','925d9eda-b33f-4710-'),('8','FUN HO物理实验透镜','FUN HO /光之礼物减压神器合色棱镜立方六面七彩光学物理实验透镜',40.00,'https://gd2.alicdn.com/imgextra/i2/904576284/O1CN01wvfywP1wI7YjZTLQX_!!904576284-0-picasso.jpg',1,'玩具',10009,'2021-06-01 20:25:30','2021-06-01 20:25:35','1'),('9','短袖衬衣上衣','衬衫女设计感小众新款2021年V领复古港味学生宽松短袖衬衣上衣女',58.09,'https://gd3.alicdn.com/imgextra/i1/3508832047/O1CN01vxFKzM1QzZPrsuzuT_!!3508832047.jpg',1,'服装',10000,'2021-06-01 20:26:08','2021-06-01 20:26:11','1');

/*Table structure for table `hb_order` */

DROP TABLE IF EXISTS `hb_order`;

CREATE TABLE `hb_order` (
  `pk_order_id` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `pk_good_id` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `user_id` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `phone` varchar(11) COLLATE utf8mb4_bin DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL COMMENT '0 未支付  1已支付 2 已取消',
  `number` int(11) DEFAULT NULL,
  `ranked` int(11) DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  PRIMARY KEY (`pk_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Data for the table `hb_order` */

insert  into `hb_order`(`pk_order_id`,`pk_good_id`,`user_id`,`phone`,`updated_time`,`created_time`,`state`,`number`,`ranked`,`rank`) values ('59e14b90-f9ea-4341-','1','1','18851855103','2021-06-01 22:19:25','2021-06-01 22:19:25',3,1,4,NULL),('6c9ceeda-fb52-4406-','10','925d9eda-b33f-4710-','18851699003','2021-06-02 11:14:21','2021-06-02 11:14:21',0,3,1,NULL),('9757db24-3330-43d3-','1','1','18851855103','2021-06-01 21:25:28','2021-06-01 21:25:28',3,1,2,NULL),('995699b4-09fc-4afe-','1','1','18851855103','2021-06-01 21:23:01','2021-06-01 21:23:01',3,1,1,NULL),('b273f13e-adf4-40cb-','1','1','18851855103','2021-06-01 21:45:20','2021-06-01 21:45:20',2,1,3,NULL),('c627d6ba-5cfe-4648-','2','925d9eda-b33f-4710-','18851699003','2021-06-02 11:05:26','2021-06-02 11:05:26',1,1,1,NULL),('d7a3b49d-c024-4b94-','5','2','18851855105','2021-06-02 14:08:11','2021-06-02 14:08:11',0,1,1,NULL);

/*Table structure for table `hb_strategy` */

DROP TABLE IF EXISTS `hb_strategy`;

CREATE TABLE `hb_strategy` (
  `pk_strategy_id` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `strategy_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `good_id` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `rank_start` int(11) NOT NULL COMMENT '起始范围',
  `rank_end` int(11) NOT NULL COMMENT '终止范围',
  `discount` double(16,2) DEFAULT NULL COMMENT '折扣。0-1之间',
  `created_time` datetime DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  PRIMARY KEY (`pk_strategy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Data for the table `hb_strategy` */

insert  into `hb_strategy`(`pk_strategy_id`,`strategy_name`,`good_id`,`rank_start`,`rank_end`,`discount`,`created_time`,`updated_time`) values ('26c8aaa0-9f67-4d3a-','打折！','1',501,1000,0.80,'2021-04-15 19:31:02','2021-04-15 19:31:02'),('3a829f5a-a547-49ad-','打折','1',300,600,0.40,'2021-04-12 12:49:54','2021-04-12 12:49:54'),('3e9664ea-5ae7-4254-','打折','a3c66246-92e8-4131-',1,100,0.20,'2021-06-02 21:04:08','2021-06-02 21:04:08'),('62cc9b84-46d1-401d-','打折！！','7bf75e9b-1b23-4c9c-',1,1000,0.90,'2021-06-02 23:04:23','2021-06-02 23:04:23'),('739e6bbe-6ec0-4a33-','demoData','2',11,500,0.50,'2021-04-10 12:51:00','2021-04-10 12:51:00'),('864924a3-85c2-433c-','打折！','4',4,5,0.60,'2021-04-15 19:31:02','2021-04-15 19:31:02'),('bc406de3-ba07-4d41-','打折','1',1,10,0.10,'2021-04-12 12:49:54','2021-04-12 12:49:54');

/*Table structure for table `hb_user` */

DROP TABLE IF EXISTS `hb_user`;

CREATE TABLE `hb_user` (
  `pk_user_id` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `username` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `password` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `sex` int(11) DEFAULT NULL COMMENT '1:男  2:女',
  `address` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `money` double(16,2) DEFAULT NULL,
  `phone` varchar(11) COLLATE utf8mb4_bin DEFAULT NULL,
  `nickname` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `avatar` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '未激活:0 已激活:1',
  `created_time` timestamp NULL DEFAULT NULL,
  `updated_time` timestamp NULL DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  PRIMARY KEY (`pk_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Data for the table `hb_user` */

insert  into `hb_user`(`pk_user_id`,`username`,`password`,`sex`,`address`,`money`,`phone`,`nickname`,`avatar`,`email`,`status`,`created_time`,`updated_time`,`birthday`) values ('1','ntt','123',1,'江苏省南京市',9995.86,'18851855103','醉忆丶无回路','http://nttbucket.oss-cn-beijing.aliyuncs.com/img/1617282580000.jpeg','1914866205@qq.com',1,'2021-03-29 09:49:07','2021-06-01 22:21:27',NULL),('2','crq','456',2,'江苏省南京市',139.82,'18851855105','骸冰','https://avatars.githubusercontent.com/u/59445871?v=4','2631315464@qq.com',1,'2021-03-30 12:47:24','2021-06-02 11:12:54',NULL),('2f78a0ef-c1e4-4c2f-','','',0,'',0.00,'18851699003','','','',1,'2021-05-31 17:45:45','2021-05-31 17:45:45',NULL),('3','123','123',1,'江苏省南京市',0.00,'18851855106','骸冰','','2631315464@qq.com',1,'2021-03-31 14:53:40','2021-04-01 21:05:16',NULL),('925d9eda-b33f-4710-','','123',0,'',984.50,'18851699003','poi','http://nttbucket.oss-cn-beijing.aliyuncs.com/img/1622602946000.jpeg','2631315464@qq.com',1,'2021-06-02 11:02:17','2021-06-02 11:12:54',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
