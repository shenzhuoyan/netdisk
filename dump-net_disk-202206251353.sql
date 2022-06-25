-- MySQL dump 10.13  Distrib 8.0.29, for Linux (x86_64)
--
-- Host: 192.168.56.101    Database: net_disk
-- ------------------------------------------------------
-- Server version	5.5.5-10.6.7-MariaDB-2ubuntu1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `file_del`
--

DROP TABLE IF EXISTS `file_del`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `file_del` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_id` int(11) NOT NULL COMMENT '此表不展示给用户，所以只是删除（实际存在的）文件',
  `del_time` datetime NOT NULL DEFAULT current_timestamp(),
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '1回收站，0彻底删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file_del`
--

LOCK TABLES `file_del` WRITE;
/*!40000 ALTER TABLE `file_del` DISABLE KEYS */;
/*!40000 ALTER TABLE `file_del` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file_dir`
--

DROP TABLE IF EXISTS `file_dir`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `file_dir` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '新建文件',
  `parent_id` int(11) DEFAULT 0 COMMENT '父目录id',
  `type` int(11) NOT NULL DEFAULT 1 COMMENT '0为目录，1为文件',
  `level` int(11) DEFAULT 1 COMMENT '层级，最大为9',
  `user_id` int(11) NOT NULL,
  `path` varchar(1000) DEFAULT NULL COMMENT '父目录的path+文件名',
  `status` int(11) DEFAULT 1 COMMENT '1存在，0删除',
  `size` varchar(100) DEFAULT NULL COMMENT '文件有大小，目录没有',
  `gen_path` varchar(1000) DEFAULT NULL COMMENT '文件有真实路径，目录没有',
  `create_time` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file_dir`
--

LOCK TABLES `file_dir` WRITE;
/*!40000 ALTER TABLE `file_dir` DISABLE KEYS */;
INSERT INTO `file_dir` VALUES (3,'9',0,0,1,9,'',1,NULL,NULL,'2022-06-24 17:20:30'),(11,'2022-04-27_01-33-46-46default.jpg',3,1,2,9,'/2022-04-27_01-33-46-46default.jpg',2,'3.390625KB','9/e36ff5256a6944938331a62d30f0b2452022-04-27_01-33-46-46default.jpg','2022-06-24 17:20:30'),(12,'2022-04-27_01-32-59-59default.jpg',3,1,2,9,'/2022-04-27_01-32-59-59default.jpg',2,'3.39KB','9/2868da8d2cdc47199405c1e0036574352022-04-27_01-32-59-59default.jpg','2022-06-24 17:20:30'),(13,'2022-04-25_10-54-18-182.png',3,1,2,9,'/2022-04-25_10-54-18-182.png',2,'5.88KB','9/857419dfbdeb4854a10459a3e05464952022-04-25_10-54-18-182.png','2022-06-24 17:20:30'),(14,'2022-04-26_03-00-13-132.jpg',3,1,2,9,'/2022-04-26_03-00-13-132.jpg',1,'5.88KB','9/a0364c6441cd49b192b0ac63054cf9c82022-04-26_03-00-13-132.jpg','2022-06-24 17:20:30'),(15,'package-lock.json',3,1,2,9,'/package-lock.json',1,'765.32KB','9/7db7658d8a84444c9c819ff41fe7b35fpackage-lock.json','2022-06-24 17:20:30'),(16,'index.js',3,1,2,9,'/index.js',1,'1.40KB','9/1dde34f4cdd4404ca51ccc0ec91bdeceindex.js','2022-06-24 17:20:30'),(17,'asdf',3,0,2,9,'/asdf',1,NULL,NULL,'2022-06-24 18:19:34'),(18,'asdf',3,0,2,9,'/asdf',2,NULL,NULL,'2022-06-24 18:19:42'),(19,'asdf',3,0,2,9,'/asdf',2,NULL,NULL,'2022-06-24 18:19:49'),(20,'asdf',3,0,2,9,'/asdf',2,NULL,NULL,'2022-06-24 18:20:29'),(21,'阿萨德发',3,0,2,9,'/阿萨德发',2,NULL,NULL,'2022-06-24 18:20:31'),(22,'测试1',3,0,2,9,'/测试1',1,NULL,NULL,'2022-06-24 18:32:35'),(23,'测试2',3,0,2,9,'/测试2',1,NULL,NULL,'2022-06-24 18:34:18'),(24,'测试3',3,0,2,9,'/测试3',1,NULL,NULL,'2022-06-24 18:35:28'),(25,'测试4',3,0,2,9,'/测试4',1,NULL,NULL,'2022-06-24 18:36:09'),(26,'测试5',3,0,2,9,'/测试5',1,NULL,NULL,'2022-06-24 18:36:19'),(27,'数学秘籍.pdf',17,1,3,9,'/asdf/数学秘籍.pdf',1,'5.46MB','9/2e94240865814568ae0613d050c01447数学秘籍.pdf','2022-06-24 19:42:44'),(28,'新建目录的交互',3,0,2,9,'/新建目录的交互',1,NULL,NULL,'2022-06-24 20:40:01'),(29,'二级目录',28,0,3,9,'/新建目录的交互/二级目录',1,NULL,NULL,'2022-06-24 20:40:17'),(30,'三级目录',29,0,4,9,'/新建目录的交互/二级目录/三级目录',1,NULL,NULL,'2022-06-24 20:40:24'),(31,'四级目录',30,0,5,9,'/新建目录的交互/二级目录/三级目录/四级目录',2,NULL,NULL,'2022-06-24 20:40:30');
/*!40000 ALTER TABLE `file_dir` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operation_log`
--

DROP TABLE IF EXISTS `operation_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `operation_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL COMMENT '0登录，1新建文件夹，2添加文件，3删除文件夹，4删除文件, 5下载,6重命名',
  `content` varchar(100) DEFAULT NULL COMMENT '操作的文件名',
  `date_time` datetime NOT NULL DEFAULT current_timestamp(),
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COMMENT='操作日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operation_log`
--

LOCK TABLES `operation_log` WRITE;
/*!40000 ALTER TABLE `operation_log` DISABLE KEYS */;
INSERT INTO `operation_log` VALUES (7,2,'/数学秘籍.pdf','2022-06-24 15:39:29',9),(8,2,'/2022-04-27_01-33-46-46default.jpg','2022-06-24 15:43:37',9),(9,2,'/2022-04-27_01-32-59-59default.jpg','2022-06-24 15:48:35',9),(10,2,'/2022-04-25_10-54-18-182.png','2022-06-24 15:52:31',9),(11,2,'/2022-04-26_03-00-13-132.jpg','2022-06-24 16:05:39',9),(12,2,'/package-lock.json','2022-06-24 16:06:06',9),(13,2,'/index.js','2022-06-24 16:06:26',9),(14,1,'/asdf','2022-06-24 18:19:34',9),(15,1,'/asdf','2022-06-24 18:19:42',9),(16,1,'/asdf','2022-06-24 18:19:49',9),(17,1,'/asdf','2022-06-24 18:20:29',9),(18,1,'/阿萨德发','2022-06-24 18:20:31',9),(19,1,'/测试1','2022-06-24 18:32:35',9),(20,1,'/测试2','2022-06-24 18:34:18',9),(21,1,'/测试3','2022-06-24 18:35:28',9),(22,1,'/测试4','2022-06-24 18:36:09',9),(23,1,'/测试5','2022-06-24 18:36:19',9),(24,2,'/asdf/数学秘籍.pdf','2022-06-24 19:42:44',9),(25,3,'/asdf','2022-06-24 20:35:07',9),(26,3,'/asdf','2022-06-24 20:36:59',9),(27,3,'/asdf','2022-06-24 20:37:02',9),(28,3,'/阿萨德发','2022-06-24 20:39:46',9),(29,1,'/新建目录的交互','2022-06-24 20:40:01',9),(30,1,'/新建目录的交互/二级目录','2022-06-24 20:40:17',9),(31,1,'/新建目录的交互/二级目录/三级目录','2022-06-24 20:40:24',9),(32,1,'/新建目录的交互/二级目录/三级目录/四级目录','2022-06-24 20:40:30',9),(33,3,'/新建目录的交互/二级目录/三级目录/四级目录','2022-06-24 20:41:49',9),(34,4,'/2022-04-27_01-33-46-46default.jpg','2022-06-24 20:42:45',9),(35,4,'/2022-04-27_01-32-59-59default.jpg','2022-06-24 20:42:48',9),(36,4,'/2022-04-25_10-54-18-182.png','2022-06-24 20:42:50',9);
/*!40000 ALTER TABLE `operation_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recycle_bin`
--

DROP TABLE IF EXISTS `recycle_bin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recycle_bin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_dir_id` int(11) NOT NULL,
  `del_time` datetime NOT NULL DEFAULT current_timestamp(),
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '1回收站，0非回收站（比如恢复，比如彻底删除）',
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recycle_bin`
--

LOCK TABLES `recycle_bin` WRITE;
/*!40000 ALTER TABLE `recycle_bin` DISABLE KEYS */;
/*!40000 ALTER TABLE `recycle_bin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `e_mail` varchar(100) NOT NULL,
  `date_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '注册时间',
  `password` varchar(100) NOT NULL,
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '0为删除',
  `root_dir_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`e_mail`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (9,'123','2022-06-24 13:47:49','123',1,3);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'net_disk'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-25 13:53:29
