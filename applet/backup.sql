-- MySQL dump 10.13  Distrib 5.1.41, for debian-linux-gnu (i486)
--
-- Host: localhost    Database: 
-- ------------------------------------------------------
-- Server version	5.1.41-3ubuntu12.6

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `patterson`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `patterson` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `patterson`;

--
-- Table structure for table `highScores`
--

DROP TABLE IF EXISTS `highScores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `highScores` (
  `username` char(25) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `date` int(11) DEFAULT NULL,
  KEY `username` (`username`),
  CONSTRAINT `highScores_ibfk_1` FOREIGN KEY (`username`) REFERENCES `robots` (`username`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `highScores`
--

LOCK TABLES `highScores` WRITE;
/*!40000 ALTER TABLE `highScores` DISABLE KEYS */;
INSERT INTO `highScores` VALUES ('ants280',29,1271209944),('ants280',570,1271210216),('ants280',5,1271295152),('ants280',977,1271728920),('ants280',5,1272481031),('ants280',101,1272481162),('ants280',22,1273006336),('jack',2,1273115422),('jack',98,1273115515),('jack',1016,1273115928),('jack',0,1273116049),('jack',47,1273551387),('ants280',255,1273876296),('ants280',540,1274247927),('jack',52,1275020338),('jack',609,1277916490),('jack',592,1278530692),('jack',0,1280159965),('ants280',4,1283118633),('ants280',844,1283997008);
/*!40000 ALTER TABLE `highScores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `robots`
--

DROP TABLE IF EXISTS `robots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `robots` (
  `username` char(25) NOT NULL DEFAULT '',
  `password` char(32) DEFAULT NULL,
  `firstname` char(25) DEFAULT NULL,
  `question` char(50) DEFAULT NULL,
  `answer` char(25) DEFAULT NULL,
  `lastname` char(25) DEFAULT NULL,
  `email` char(128) DEFAULT NULL,
  `amountDonated` float unsigned NOT NULL,
  `safeTeleports` int(10) unsigned NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `robots`
--

LOCK TABLES `robots` WRITE;
/*!40000 ALTER TABLE `robots` DISABLE KEYS */;
INSERT INTO `robots` VALUES ('ants280','cb56a30ad659b913e83765b2d0839ed9','Jacob','q','A','Patterson','jacob.patterson@gmail.com',18.99,400),('jack','4699268d17f39f63b3b7c36129393ffe','Lily','who wrote the song?','BOB DYLAN','Rosemary','jakeman828@yahoo.com',5.02,195);
/*!40000 ALTER TABLE `robots` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2010-09-08 20:12:58
