-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: hotel_parcel_tracking
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flyway_schema_history`
--

LOCK TABLES `flyway_schema_history` WRITE;
/*!40000 ALTER TABLE `flyway_schema_history` DISABLE KEYS */;
INSERT INTO `flyway_schema_history` VALUES (1,'1.01','Create Origin Tables','SQL','V1_01__Create_Origin_Tables.sql',1399144741,'root','2025-03-25 09:52:35',32,1),(2,'1.02','Insert Origin Data','SQL','V1_02__Insert_Origin_Data.sql',-848002202,'root','2025-03-25 09:52:35',6,1);
/*!40000 ALTER TABLE `flyway_schema_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guests`
--

DROP TABLE IF EXISTS `guests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guests` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `room_number` varchar(50) NOT NULL,
  `is_checked_out` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guests`
--

LOCK TABLES `guests` WRITE;
/*!40000 ALTER TABLE `guests` DISABLE KEYS */;
INSERT INTO `guests` VALUES (1,'John Doe','101',0),(2,'Jane Smith','102',0),(3,'Alice Johnson','103',1),(4,'Bob Brown','104',0),(5,'Charlie Davis','105',1),(6,'Daniel Evans','106',0),(7,'Evelyn Foster','107',0),(8,'Frank Green','108',1),(9,'Grace Harris','109',0),(10,'Henry Irving','110',0),(11,'Irene Jackson','111',1),(12,'Jack King','112',0),(13,'Katherine Lewis','113',0),(14,'Leo Martin','114',1),(15,'Mia Nelson','115',0),(16,'Noah Oliver','116',1),(17,'Olivia Perez','117',0),(18,'Paul Quinn','118',0),(19,'Quinn Roberts','119',1),(20,'Rachel Scott','120',0),(21,'Samuel Thompson','121',0),(22,'Tina Underwood','122',1),(23,'Ursula Vaughn','123',0),(24,'Victor White','124',0),(25,'Wendy Xander','125',1),(26,'Xavier Young','126',0),(27,'Yvonne Zimmer','127',0),(28,'Zachary Adams','128',1),(29,'Anthony Brooks','129',0),(30,'Bella Carter','130',0);
/*!40000 ALTER TABLE `guests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parcels`
--

DROP TABLE IF EXISTS `parcels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parcels` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tracking_number` varchar(255) NOT NULL,
  `recipient_name` varchar(255) NOT NULL,
  `guest_id` bigint DEFAULT NULL,
  `is_picked_up` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `guest_id` (`guest_id`),
  CONSTRAINT `parcels_ibfk_1` FOREIGN KEY (`guest_id`) REFERENCES `guests` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parcels`
--

LOCK TABLES `parcels` WRITE;
/*!40000 ALTER TABLE `parcels` DISABLE KEYS */;
INSERT INTO `parcels` VALUES (1,'PKG001','John Doe',1,0),(2,'PKG002','Jane Smith',2,0),(3,'PKG003','Alice Johnson',3,1),(4,'PKG004','Bob Brown',4,0),(5,'PKG005','Charlie Davis',5,1),(6,'PKG006','Daniel Evans',6,0),(7,'PKG007','Evelyn Foster',7,0),(8,'PKG008','Frank Green',8,1),(9,'PKG009','Grace Harris',9,0),(10,'PKG010','Henry Irving',10,0),(11,'PKG011','Irene Jackson',11,1),(12,'PKG012','Jack King',12,0),(13,'PKG013','Katherine Lewis',13,0),(14,'PKG014','Leo Martin',14,1),(15,'PKG015','Mia Nelson',15,0),(16,'PKG016','Noah Oliver',16,1),(17,'PKG017','Olivia Perez',17,0),(18,'PKG018','Paul Quinn',18,0),(19,'PKG019','Quinn Roberts',19,1),(20,'PKG020','Rachel Scott',20,0),(21,'PKG021','Samuel Thompson',21,0),(22,'PKG022','Tina Underwood',22,1),(23,'PKG023','Ursula Vaughn',23,0),(24,'PKG024','Victor White',24,0),(25,'PKG025','Wendy Xander',25,1),(26,'PKG026','Xavier Young',26,0),(27,'PKG027','Yvonne Zimmer',27,0),(28,'PKG028','Zachary Adams',28,1),(29,'PKG029','Anthony Brooks',29,0),(30,'PKG030','Bella Carter',30,0),(31,'PKG031','John Doe',1,0),(32,'PKG032','Jane Smith',2,0),(33,'PKG033','Bob Brown',4,0),(34,'PKG034','Daniel Evans',6,0),(35,'PKG035','Evelyn Foster',7,0),(36,'PKG036','Grace Harris',9,0),(37,'PKG037','Henry Irving',10,0),(38,'PKG038','Jack King',12,0),(39,'PKG039','Katherine Lewis',13,0),(40,'PKG040','Mia Nelson',15,0),(41,'PKG041','Olivia Perez',17,0),(42,'PKG042','Paul Quinn',18,0),(43,'PKG043','Rachel Scott',20,0),(44,'PKG044','Samuel Thompson',21,0),(45,'PKG045','Ursula Vaughn',23,0),(46,'PKG046','Victor White',24,0),(47,'PKG047','Xavier Young',26,0),(48,'PKG048','Yvonne Zimmer',27,0),(49,'PKG049','Anthony Brooks',29,0),(50,'PKG050','Bella Carter',30,0);
/*!40000 ALTER TABLE `parcels` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-25 16:58:47
