-- MySQL dump 10.13  Distrib 8.0.23, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: tourism
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `cost_type`
--

DROP TABLE IF EXISTS `cost_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `cost_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cost_type`
--

LOCK TABLES `cost_type` WRITE;
/*!40000 ALTER TABLE `cost_type` DISABLE KEYS */;
INSERT INTO `cost_type` VALUES (1,'Phí ăn uống','5000'),(2,'Phí phương tiện','5000');
/*!40000 ALTER TABLE `cost_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8





 */;
CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `identity_card` varchar(255) DEFAULT NULL,
  `address1` varchar(255) DEFAULT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `address3` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (2,'Vy','4534654645','Tỉnh Đồng Nai','Thành phố Biên Hòa','Phường Trảng Dài','435 A Random Street','nữ','0912345564'),(3,'Nhi','094535346','Thành phố Hồ Chí Minh','Quận 1','Phường Tân Định','34 A Random Street','nữ','0123456789');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `identity_card` varchar(255) DEFAULT NULL,
  `address1` varchar(255) DEFAULT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `address3` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'name1','1223343','45 ng','address2','address3','34 A Random Street','nam','0123435678','deleted'),(6,'Anh','9088987','89yutryuty','null','null','null','nữ','0987765456','deleted'),(9,'Hào','12233436','76 Nguyen Hue','address2','address3','34 A Random Street','nam','0123456787','active'),(10,'Hào','1223343','76 Nguyen Hue','address2','address3','34 A Random Street','nam','0123435677','deleted'),(11,'Hào','12233436','76 Nguyen Hue','address2','address3','34 A Random Street','nam','0123456789','deleted');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `address1` varchar(255) DEFAULT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `address3` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'Tỉnh Khánh Hòa - Thành phố Nha Trang - Phường Vĩnh Hòa','Tỉnh Khánh Hòa','Thành phố Nha Trang','Phường Vĩnh Hòa','23 A Random Street',NULL),(2,'Tỉnh An Giang - Thành phố Long Xuyên - Phường Mỹ Xuyên','Tỉnh An Giang','Thành phố Long Xuyên','Phường Mỹ Xuyên','23 A Random Street',NULL),(3,'Thành phố Hồ Chí Minh - Quận 1 - Phường Tân Định','Thành phố Hồ Chí Minh','Quận 1','Phường Tân Định','23 A Random Street',NULL),(4,'Tỉnh Lâm Đồng - Thành phố Đà Lạt - Phường 7','Tỉnh Lâm Đồng','Thành phố Đà Lạt','Phường 7','23 A Random Street',NULL),(5,'Tỉnh Lâm Đồng - Thành phố Bảo Lộc - Phường Lộc Phát','Tỉnh Lâm Đồng','Thành phố Bảo Lộc','Phường Lộc Phát','23 A Random Street',NULL);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position`
--

DROP TABLE IF EXISTS `position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `position` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position`
--

LOCK TABLES `position` WRITE;
/*!40000 ALTER TABLE `position` DISABLE KEYS */;
INSERT INTO `position` VALUES (1,'lái xe'),(2,'hướng dẫn viên'),(3,'phục vụ'),(4,'thông dịch viên'),(5,'tiền trạm');
/*!40000 ALTER TABLE `position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position_in_tour`
--

DROP TABLE IF EXISTS `position_in_tour`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `position_in_tour` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tourist_group_id` int NOT NULL,
  `position_id` int NOT NULL,
  `employee_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `role_tour_id` (`position_id`),
  KEY `employee_id` (`employee_id`),
  KEY `position_in_tour_ibfk_1_idx` (`tourist_group_id`),
  CONSTRAINT `position_in_tour_ibfk_1` FOREIGN KEY (`tourist_group_id`) REFERENCES `tourist_group` (`id`),
  CONSTRAINT `position_in_tour_ibfk_2` FOREIGN KEY (`position_id`) REFERENCES `position` (`id`),
  CONSTRAINT `position_in_tour_ibfk_3` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position_in_tour`
--

LOCK TABLES `position_in_tour` WRITE;
/*!40000 ALTER TABLE `position_in_tour` DISABLE KEYS */;
INSERT INTO `position_in_tour` VALUES (121,3,1,1),(131,1,1,6),(133,6,1,9),(134,6,1,1),(135,7,1,11),(136,9,1,9),(137,9,2,9);
/*!40000 ALTER TABLE `position_in_tour` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tour`
--

DROP TABLE IF EXISTS `tour`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `tour` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type_id` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` text,
  `status` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `tour_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `type_of_tour` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tour`
--

LOCK TABLES `tour` WRITE;
/*!40000 ALTER TABLE `tour` DISABLE KEYS */;
INSERT INTO `tour` VALUES (1,1,'Tour Sài Gòn – Nha Trang','description','active',NULL),(2,3,'Sài Gòn – Đà Lạt','description','active',NULL),(3,2,'Sài Gòn – Miền Tây','description','active',NULL),(4,2,'Sài Gòn – Tây Nguyên','descrip','active',NULL);
/*!40000 ALTER TABLE `tour` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tour_cost`
--

DROP TABLE IF EXISTS `tour_cost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `tour_cost` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tour_id` int NOT NULL,
  `price` decimal(15,0) NOT NULL,
  `price_from_time` date NOT NULL,
  `price_to_time` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tour_cost_ibfk_2` (`tour_id`),
  CONSTRAINT `tour_cost_ibfk_2` FOREIGN KEY (`tour_id`) REFERENCES `tour` (`id`)
<<<<<<< HEAD
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
=======
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
>>>>>>> 5525bcc25ff2d8e58911f3e2503cbea8b50d208e
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tour_cost`
--

LOCK TABLES `tour_cost` WRITE;
/*!40000 ALTER TABLE `tour_cost` DISABLE KEYS */;
INSERT INTO `tour_cost` VALUES (2,2,700,'2021-01-01','2021-06-01'),(4,1,799,'2021-02-01','2021-02-28'),(5,1,599,'2021-03-01','2021-03-31'),(6,1,5675,'2021-04-01','2021-04-30');
/*!40000 ALTER TABLE `tour_cost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tour_location`
--

DROP TABLE IF EXISTS `tour_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `tour_location` (
  `tour_id` int NOT NULL,
  `location_id` int NOT NULL,
  PRIMARY KEY (`tour_id`,`location_id`),
  KEY `location_id` (`location_id`),
  CONSTRAINT `tour_location_ibfk_1` FOREIGN KEY (`tour_id`) REFERENCES `tour` (`id`),
  CONSTRAINT `tour_location_ibfk_2` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tour_location`
--

LOCK TABLES `tour_location` WRITE;
/*!40000 ALTER TABLE `tour_location` DISABLE KEYS */;
/*!40000 ALTER TABLE `tour_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tourist_group`
--

DROP TABLE IF EXISTS `tourist_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `tourist_group` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tour_id` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `depature_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `revenue` decimal(15,0) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tourist_group_tour_fk` (`tour_id`),
  CONSTRAINT `tourist_group_tour_fk` FOREIGN KEY (`tour_id`) REFERENCES `tour` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tourist_group`
--

LOCK TABLES `tourist_group` WRITE;
/*!40000 ALTER TABLE `tourist_group` DISABLE KEYS */;
INSERT INTO `tourist_group` VALUES (1,1,'name tour','2021-02-01','2021-02-05','Chưa đi',17025),(2,1,'name tour 2','2020-02-01','2020-02-05','deleted',80),(3,3,'name tour 3','2020-03-01','2020-03-05','Đang đi',80),(4,4,'Tour 4','2020-06-06','2020-06-06','deleted',80),(5,2,'Đoàn 2','2021-02-01','2021-02-09','deleted',80),(6,1,'newwwww','2021-03-02','2021-03-31','deleted',1598),(7,1,'Đoàn đi vòng vòng','2021-03-15','2021-03-16','Chưa đi',1198),(8,1,'Đưa nhau đi trốn','2021-03-15','2021-03-25','Chưa đi',599),(9,1,'Đi cùng anh qua bao đại dương','2021-03-09','2021-03-30','Chưa đi',599);
/*!40000 ALTER TABLE `tourist_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tourist_group_cost`
--

DROP TABLE IF EXISTS `tourist_group_cost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `tourist_group_cost` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tourist_group_id` int DEFAULT NULL,
  `total_price` decimal(15,0) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`),
  KEY `tourist_group_id` (`tourist_group_id`),
  CONSTRAINT `tourist_group_cost_ibfk_1` FOREIGN KEY (`tourist_group_id`) REFERENCES `tourist_group` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tourist_group_cost`
--

LOCK TABLES `tourist_group_cost` WRITE;
/*!40000 ALTER TABLE `tourist_group_cost` DISABLE KEYS */;
INSERT INTO `tourist_group_cost` VALUES (4,1,30000,'[ { \"name\":\"An uong\", \"cost\":\"454.0\", \"quantity\":\"3\" }, ]'),(5,1,50000,'[ { \"name\":\"An uong\", \"cost\":\"454.0\", \"quantity\":\"3\" }, { \"name\":\"Phí ăn uống\", \"cost\":\"5000.0\", \"quantity\":\"2\" }, ]'),(6,3,6362,'[ { \"name\":\"An uong\", \"cost\":\"454.0\", \"quantity\":\"3\" }, { \"name\":\"Phí phương tiện\", \"cost\":\"5000.0\", \"quantity\":\"1\" }, ]'),(7,3,65000,'[ { \"name\":\"Phí ăn uống\", \"cost\":\"5000.0\", \"quantity\":\"1\" }, { \"name\":\"Phí phương tiện\", \"cost\":\"5000.0\", \"quantity\":\"12\" }, ]'),(8,6,15000,'[ { \"name\":\"Phí ăn uống\", \"cost\":\"5000.0\", \"quantity\":\"1\" }, { \"name\":\"Phí ăn uống\", \"cost\":\"5000.0\", \"quantity\":\"2\" }, ]'),(9,9,15000,'[ { \"name\":\"Phí ăn uống\", \"cost\":\"5000.0\", \"quantity\":\"1\" }, { \"name\":\"Phí phương tiện\", \"cost\":\"5000.0\", \"quantity\":\"2\" }, ]');
/*!40000 ALTER TABLE `tourist_group_cost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tourist_group_customer`
--

DROP TABLE IF EXISTS `tourist_group_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `tourist_group_customer` (
  `tourist_group_id` int NOT NULL,
  `customer_id` int NOT NULL,
  PRIMARY KEY (`tourist_group_id`,`customer_id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `tourist_group_customer_ibfk_1` FOREIGN KEY (`tourist_group_id`) REFERENCES `tourist_group` (`id`),
  CONSTRAINT `tourist_group_customer_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tourist_group_customer`
--

LOCK TABLES `tourist_group_customer` WRITE;
/*!40000 ALTER TABLE `tourist_group_customer` DISABLE KEYS */;
INSERT INTO `tourist_group_customer` VALUES (1,2),(7,2),(1,3),(6,3),(7,3),(8,3),(9,3);
/*!40000 ALTER TABLE `tourist_group_customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type_of_tour`
--

DROP TABLE IF EXISTS `type_of_tour`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `type_of_tour` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type_of_tour`
--

LOCK TABLES `type_of_tour` WRITE;
/*!40000 ALTER TABLE `type_of_tour` DISABLE KEYS */;
INSERT INTO `type_of_tour` VALUES (1,'du lịch di động'),(2,'du lịch kết hợp nghề nghiệp'),(3,'du lịch xã hội và gia đình');
/*!40000 ALTER TABLE `type_of_tour` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Long','123456','0858267296');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-16 19:36:01
