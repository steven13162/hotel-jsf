-- MySQL dump 10.13  Distrib 5.7.17, for Linux (x86_64)
--
-- Host: localhost    Database: bdweb_hotel
-- ------------------------------------------------------
-- Server version	5.5.52-MariaDB

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
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientes` (
  `cli_id` bigint(20) NOT NULL,
  `cli_nombre` varchar(45) NOT NULL,
  `cli_apellidos` varchar(45) NOT NULL,
  `cli_password` varchar(45) NOT NULL,
  `cli_direccion` varchar(255) NOT NULL,
  `cli_dni` varchar(9) NOT NULL,
  `cli_email` varchar(45) NOT NULL,
  `cli_numero_movil` varchar(45) NOT NULL,
  `cli_numero_tarjeta` varchar(16) NOT NULL,
  `cli_tipo` varchar(7) NOT NULL DEFAULT 'cliente',
  PRIMARY KEY (`cli_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes_habituales`
--

DROP TABLE IF EXISTS `clientes_habituales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientes_habituales` (
  `cli_newsletter` tinyint(4) NOT NULL,
  `cli_descuento` int(2) NOT NULL,
  `cli_id` bigint(20) NOT NULL,
  `cli_tipo` varchar(16) NOT NULL DEFAULT 'cliente_habitual',
  PRIMARY KEY (`cli_id`),
  CONSTRAINT `fk_clientes_habituales_clientes1` FOREIGN KEY (`cli_id`) REFERENCES `clientes` (`cli_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes_habituales`
--

LOCK TABLES `clientes_habituales` WRITE;
/*!40000 ALTER TABLE `clientes_habituales` DISABLE KEYS */;
/*!40000 ALTER TABLE `clientes_habituales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `habitaciones`
--

DROP TABLE IF EXISTS `habitaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `habitaciones` (
  `hab_id` bigint(20) NOT NULL,
  `hab_numero` int(11) NOT NULL,
  `hab_ocupada` tinyint(4) NOT NULL,
  `tipos_habitaciones_tip_hab_id` bigint(20) NOT NULL,
  PRIMARY KEY (`hab_id`,`tipos_habitaciones_tip_hab_id`),
  KEY `fk_habitaciones_tipos_habitaciones2_idx` (`tipos_habitaciones_tip_hab_id`),
  CONSTRAINT `fk_habitaciones_tipos_habitaciones2` FOREIGN KEY (`tipos_habitaciones_tip_hab_id`) REFERENCES `tipos_habitaciones` (`tip_hab_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `habitaciones`
--

LOCK TABLES `habitaciones` WRITE;
/*!40000 ALTER TABLE `habitaciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `habitaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promociones`
--

DROP TABLE IF EXISTS `promociones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `promociones` (
  `prom_id` bigint(20) NOT NULL,
  `prom_fecha_inicio` date NOT NULL,
  `prom_fecha_final` date NOT NULL,
  `prom_descripcion` text,
  `prom_precio` decimal(9,2) NOT NULL,
  `tipos_habitaciones_tip_hab_id` bigint(20) NOT NULL,
  PRIMARY KEY (`prom_id`,`tipos_habitaciones_tip_hab_id`),
  KEY `fk_promociones_tipos_habitaciones2_idx` (`tipos_habitaciones_tip_hab_id`),
  CONSTRAINT `fk_promociones_tipos_habitaciones2` FOREIGN KEY (`tipos_habitaciones_tip_hab_id`) REFERENCES `tipos_habitaciones` (`tip_hab_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promociones`
--

LOCK TABLES `promociones` WRITE;
/*!40000 ALTER TABLE `promociones` DISABLE KEYS */;
/*!40000 ALTER TABLE `promociones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservas`
--

DROP TABLE IF EXISTS `reservas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reservas` (
  `res_id` bigint(20) NOT NULL,
  `res_fecha_inicio` date NOT NULL,
  `res_fecha_final` date NOT NULL,
  `res_estado` enum('ACTIVA','CERRADA','CANCELADA','PROGRESO') NOT NULL,
  `clientes_cli_id` bigint(20) NOT NULL,
  `habitaciones_hab_id` bigint(20) NOT NULL,
  `promociones_prom_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`res_id`,`clientes_cli_id`,`habitaciones_hab_id`),
  KEY `fk_reservas_clientes1_idx` (`clientes_cli_id`),
  KEY `fk_reservas_habitaciones1_idx` (`habitaciones_hab_id`),
  KEY `fk_reservas_promociones1_idx` (`promociones_prom_id`),
  CONSTRAINT `fk_reservas_clientes1` FOREIGN KEY (`clientes_cli_id`) REFERENCES `clientes` (`cli_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_reservas_habitaciones1` FOREIGN KEY (`habitaciones_hab_id`) REFERENCES `habitaciones` (`hab_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_reservas_promociones1` FOREIGN KEY (`promociones_prom_id`) REFERENCES `mydb`.`promociones` (`prom_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservas`
--

LOCK TABLES `reservas` WRITE;
/*!40000 ALTER TABLE `reservas` DISABLE KEYS */;
/*!40000 ALTER TABLE `reservas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipos_habitaciones`
--

DROP TABLE IF EXISTS `tipos_habitaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipos_habitaciones` (
  `tip_hab_id` bigint(20) NOT NULL,
  `tip_hab_tipo` enum('INDIVIDUAL','DOBLE','MATRIMONIAL') NOT NULL,
  `tip_hab_precio` decimal(9,2) NOT NULL,
  PRIMARY KEY (`tip_hab_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipos_habitaciones`
--

LOCK TABLES `tipos_habitaciones` WRITE;
/*!40000 ALTER TABLE `tipos_habitaciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipos_habitaciones` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-07 19:29:31
