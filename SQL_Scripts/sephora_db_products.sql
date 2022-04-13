CREATE DATABASE  IF NOT EXISTS `sephora_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `sephora_db`;
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: sephora_db
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `product_id` int NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `family` int DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `price` float(8,2) DEFAULT NULL,
  `stock` int unsigned DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `family` (`family`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`family`) REFERENCES `family` (`family_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Lipstick red',3,'Pintalabios rojo mate',19.99,5),(2,'Lipstick green shine',3,'Pintalabios verde brillo',12.99,3),(3,'Lipstick orange',3,'Pintalabios naranja mate',9.99,1),(4,'Lipstick black',3,'Pintalabios negro brillo',14.99,0),(5,'Hugo Boss',2,'Fragancia ',49.99,5),(6,'Carolina Herrera',2,'Fragancia',39.99,0),(7,'Jean Paul Gaultier',2,'Colonia',59.99,5),(8,'Night Cream',1,'Crema reparadora noche',19.99,2),(9,'Nivea cream',1,'crema hidratante',6.99,2),(10,'Rosa mosqueta',1,'crema reparadora',19.99,0),(11,'Crema solar 30',1,'Protector solar factor 30',13.99,10),(12,'Crema solar 50',1,'Protector solar factor 50',15.99,5),(13,'Rimmel',3,'Volumen pestañas',18.99,2),(14,'Angel',2,'Colonia ',59.99,0),(15,'Brummel',2,'Colonia',39.99,8),(16,'Desodorante Sanex',2,'Desodorante Roll-on',3.99,0),(17,'Aceite barba v7',1,'Aceite hidratante para barba',8.99,0);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-24 22:07:05
