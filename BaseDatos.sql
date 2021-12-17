-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: sgp-ca
-- ------------------------------------------------------
-- Server version	8.0.25

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
-- Table structure for table `academicevent`
--

DROP TABLE IF EXISTS `academicevent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `academicevent` (
  `idAcademicEvent` varchar(45) NOT NULL,
  `title` varchar(100) NOT NULL,
  `type` varchar(45) NOT NULL,
  `date` date NOT NULL,
  `hour` time NOT NULL,
  `place` varchar(100) NOT NULL,
  PRIMARY KEY (`idAcademicEvent`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `academicevent`
--

LOCK TABLES `academicevent` WRITE;
/*!40000 ALTER TABLE `academicevent` DISABLE KEYS */;
INSERT INTO `academicevent` VALUES ('EVT-1','Microservicion','Exposicion','2021-05-04','16:24:00','facultad de estadistica e informatica'),('EVT-2','exposicion1','Evento 1','3822-02-02','03:56:56','facultad de estadistica e informatica');
/*!40000 ALTER TABLE `academicevent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `academicgroup`
--

DROP TABLE IF EXISTS `academicgroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `academicgroup` (
  `key` varchar(45) NOT NULL,
  `name` varchar(100) NOT NULL,
  `degreeConsolidation` varchar(100) NOT NULL,
  `responsible` varchar(45) NOT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `academicgroup`
--

LOCK TABLES `academicgroup` WRITE;
/*!40000 ALTER TABLE `academicgroup` DISABLE KEYS */;
INSERT INTO `academicgroup` VALUES ('AGP-1','Ingeniería y Tecnología de software','ASD','María Karen Cortés Verdín '),('AGP-2','aasda','asdasd','María Karen Cortés Verdín ');
/*!40000 ALTER TABLE `academicgroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `sici` varchar(45) NOT NULL,
  `magazineName` varchar(100) NOT NULL,
  `editorialMagazine` varchar(100) NOT NULL,
  `description` varchar(300) NOT NULL,
  `type` varchar(45) NOT NULL,
  `title` varchar(100) NOT NULL,
  `academicDegree` varchar(100) NOT NULL,
  `idInvestigationProject` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`sici`),
  KEY `idInvestigationProyect10_idx` (`idInvestigationProject`),
  CONSTRAINT `idInvestigationProyect10` FOREIGN KEY (`idInvestigationProject`) REFERENCES `investigationproject` (`idInvestigationProject`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assistant-event`
--

DROP TABLE IF EXISTS `assistant-event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assistant-event` (
  `idAssistantEvent` varchar(45) NOT NULL,
  `idAcademicEvent` varchar(45) DEFAULT NULL,
  `emailAssistant` varchar(320) DEFAULT NULL,
  PRIMARY KEY (`idAssistantEvent`),
  KEY `idAcademicEvent_idx` (`idAcademicEvent`),
  KEY `emailAssistant1_idx` (`emailAssistant`),
  CONSTRAINT `emailAssistant1` FOREIGN KEY (`emailAssistant`) REFERENCES `eventassistant` (`email`),
  CONSTRAINT `idAcademicEvent1` FOREIGN KEY (`idAcademicEvent`) REFERENCES `academicevent` (`idAcademicEvent`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assistant-event`
--

LOCK TABLES `assistant-event` WRITE;
/*!40000 ALTER TABLE `assistant-event` DISABLE KEYS */;
/*!40000 ALTER TABLE `assistant-event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `isbn` varchar(45) NOT NULL,
  `editorial` varchar(100) NOT NULL,
  `editionNumber` int NOT NULL,
  `type` varchar(45) NOT NULL,
  `title` varchar(100) NOT NULL,
  `academicDegree` varchar(100) NOT NULL,
  `idInvestigationProject` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`isbn`),
  KEY `idInvestigationProyect9_idx` (`idInvestigationProject`),
  CONSTRAINT `idInvestigationProyect9` FOREIGN KEY (`idInvestigationProject`) REFERENCES `investigationproject` (`idInvestigationProject`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chapter`
--

DROP TABLE IF EXISTS `chapter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chapter` (
  `idChapter` varchar(45) NOT NULL,
  `bookName` varchar(100) NOT NULL,
  `pageNumber` int NOT NULL,
  `isbnBook` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idChapter`),
  KEY `isbnBook_idx` (`isbnBook`),
  CONSTRAINT `isbnBook` FOREIGN KEY (`isbnBook`) REFERENCES `book` (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chapter`
--

LOCK TABLES `chapter` WRITE;
/*!40000 ALTER TABLE `chapter` DISABLE KEYS */;
/*!40000 ALTER TABLE `chapter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eventassistant`
--

DROP TABLE IF EXISTS `eventassistant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eventassistant` (
  `email` varchar(320) NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventassistant`
--

LOCK TABLES `eventassistant` WRITE;
/*!40000 ALTER TABLE `eventassistant` DISABLE KEYS */;
INSERT INTO `eventassistant` VALUES ('asd@gmail.com','asd'),('asistencia@gmail.com','asistencia'),('hola@gmail.com','hola'),('maestra@uv.mx','Maria Karen Cortes'),('Prueba@gmail.com','Prueba'),('rodri@gmail.com','Rodri'),('S19013985@estudiantes.uv.mx','Jose Rodrigo Sánchez Méndez'),('sdf@gmail.com','sdfsdf');
/*!40000 ALTER TABLE `eventassistant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eventconstancy`
--

DROP TABLE IF EXISTS `eventconstancy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eventconstancy` (
  `idEventConstancy` varchar(45) NOT NULL,
  `recognitionType` varchar(45) NOT NULL,
  `description` varchar(300) NOT NULL,
  `idAcademicEvent` varchar(45) DEFAULT NULL,
  `emailAssistant` varchar(320) DEFAULT NULL,
  PRIMARY KEY (`idEventConstancy`),
  KEY `idAcademicEvent2_idx` (`idAcademicEvent`),
  KEY `emailAssistant_idx` (`emailAssistant`),
  CONSTRAINT `emailAssistant` FOREIGN KEY (`emailAssistant`) REFERENCES `eventassistant` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `idAcademicEvent2` FOREIGN KEY (`idAcademicEvent`) REFERENCES `academicevent` (`idAcademicEvent`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventconstancy`
--

LOCK TABLES `eventconstancy` WRITE;
/*!40000 ALTER TABLE `eventconstancy` DISABLE KEYS */;
INSERT INTO `eventconstancy` VALUES ('EVC-1','Asistencia','Por la organización del taller de microservicios','EVT-1','S19013985@estudiantes.uv.mx'),('EVC-2','asistencia','asistencia','EVT-1','asistencia@gmail.com'),('EVC-3','Prueba','Prueba','EVT-1','Prueba@gmail.com'),('EVC-4','RconocimientoPrueba','RconocimientoPrueba','EVT-1','rodri@gmail.com');
/*!40000 ALTER TABLE `eventconstancy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `investigationproject`
--

DROP TABLE IF EXISTS `investigationproject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `investigationproject` (
  `idInvestigationProject` varchar(45) NOT NULL,
  `title` varchar(100) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `description` varchar(300) NOT NULL,
  PRIMARY KEY (`idInvestigationProject`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `investigationproject`
--

LOCK TABLES `investigationproject` WRITE;
/*!40000 ALTER TABLE `investigationproject` DISABLE KEYS */;
INSERT INTO `investigationproject` VALUES ('IVP-1','Proyecto de investigacion','2020-12-02','2020-12-03','X'),('IVP-2','Servicios','2010-06-20','2010-06-21','X');
/*!40000 ALTER TABLE `investigationproject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lgac- academicgroup`
--

DROP TABLE IF EXISTS `lgac- academicgroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lgac- academicgroup` (
  `idLgacAcademicGroup` varchar(45) NOT NULL,
  `lgac` varchar(200) NOT NULL,
  `keyAcademicGroup` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idLgacAcademicGroup`),
  KEY `keyAcademicGroup1_idx` (`keyAcademicGroup`),
  CONSTRAINT `keyAcademicGroup1` FOREIGN KEY (`keyAcademicGroup`) REFERENCES `academicgroup` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lgac- academicgroup`
--

LOCK TABLES `lgac- academicgroup` WRITE;
/*!40000 ALTER TABLE `lgac- academicgroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `lgac- academicgroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lgac-investigationproject`
--

DROP TABLE IF EXISTS `lgac-investigationproject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lgac-investigationproject` (
  `idLgacInvestigationProject` varchar(45) NOT NULL,
  `lgac` varchar(200) NOT NULL,
  `idInvestigationProject` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idLgacInvestigationProject`),
  KEY `IdInvestigationProyect1_idx` (`idInvestigationProject`),
  CONSTRAINT `idInvestigationProject1` FOREIGN KEY (`idInvestigationProject`) REFERENCES `investigationproject` (`idInvestigationProject`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lgac-investigationproject`
--

LOCK TABLES `lgac-investigationproject` WRITE;
/*!40000 ALTER TABLE `lgac-investigationproject` DISABLE KEYS */;
/*!40000 ALTER TABLE `lgac-investigationproject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lgac-preliminaryproject`
--

DROP TABLE IF EXISTS `lgac-preliminaryproject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lgac-preliminaryproject` (
  `idLgacPreliminaryProject` varchar(45) NOT NULL,
  `lgac` varchar(300) NOT NULL,
  `idPreliminaryProject` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idLgacPreliminaryProject`),
  KEY `idpreeliminaryproyect11_idx` (`idPreliminaryProject`),
  CONSTRAINT `idPreliminaryProject11` FOREIGN KEY (`idPreliminaryProject`) REFERENCES `preliminaryproject` (`idPreliminaryProject`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lgac-preliminaryproject`
--

LOCK TABLES `lgac-preliminaryproject` WRITE;
/*!40000 ALTER TABLE `lgac-preliminaryproject` DISABLE KEYS */;
/*!40000 ALTER TABLE `lgac-preliminaryproject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meet`
--

DROP TABLE IF EXISTS `meet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meet` (
  `idMeet` varchar(45) NOT NULL,
  `place` varchar(45) NOT NULL,
  `hour` time NOT NULL,
  `date` date NOT NULL,
  `subject` varchar(200) NOT NULL,
  `proyectName` varchar(200) NOT NULL,
  `idMemorandumMeet` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idMeet`),
  KEY `idMemorandumMeet_idx` (`idMemorandumMeet`),
  CONSTRAINT `idMemorandumMeet` FOREIGN KEY (`idMemorandumMeet`) REFERENCES `memorandummeet` (`idMemorandumMeet`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meet`
--

LOCK TABLES `meet` WRITE;
/*!40000 ALTER TABLE `meet` DISABLE KEYS */;
INSERT INTO `meet` VALUES ('ss','ss','14:30:00','2021-04-02','dfsd','sdsdfs',NULL);
/*!40000 ALTER TABLE `meet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `curp` varchar(18) NOT NULL,
  `name` varchar(100) NOT NULL,
  `dateOfBirth` date NOT NULL,
  `institutionalMail` varchar(320) NOT NULL,
  `telephoneNumber` varchar(45) NOT NULL,
  `academicRole` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `keyAcademicGroup` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`curp`),
  KEY `keyAcademicGroup_idx` (`keyAcademicGroup`),
  CONSTRAINT `keyAcademicGroup` FOREIGN KEY (`keyAcademicGroup`) REFERENCES `academicgroup` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES ('MOLK010131MNELPTA0','Jorge Octavio Ocharán Hernández','2021-05-04','ocharan@uv.mx','2281963700','Colaborador','E8A1047CD936793EE9D8850D417BA7DB','AGP-2'),('SAMR011202HVZNNDA9','José Rodrigo Sánchez Méndez','2001-12-02','zs19013985@estudiantes.uv.mx','2281963700','Colaborador','DA047C944A727B788709C27363986CC7','AGP-1'),('SASO750909HDFNNS05','María Karen Cortés Verdín ','1985-06-03','himno@gmail.com','2281776699','Responsable','87FBE2C1E60480E387F59F94E1C3BA48','AGP-1');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member-academicevent`
--

DROP TABLE IF EXISTS `member-academicevent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member-academicevent` (
  `idMemberAcademicEvent` varchar(45) NOT NULL,
  `curpMember` varchar(18) DEFAULT NULL,
  `idAcademicEvent` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idMemberAcademicEvent`),
  KEY `CURPMember3_idx` (`curpMember`),
  KEY `idAcademicEvent_idx` (`idAcademicEvent`),
  CONSTRAINT `CURPMember3` FOREIGN KEY (`curpMember`) REFERENCES `member` (`curp`),
  CONSTRAINT `idAcademicEvent` FOREIGN KEY (`idAcademicEvent`) REFERENCES `academicevent` (`idAcademicEvent`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member-academicevent`
--

LOCK TABLES `member-academicevent` WRITE;
/*!40000 ALTER TABLE `member-academicevent` DISABLE KEYS */;
/*!40000 ALTER TABLE `member-academicevent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member-evidence`
--

DROP TABLE IF EXISTS `member-evidence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member-evidence` (
  `idMemberEvidence` varchar(45) NOT NULL,
  `curpMember` varchar(18) DEFAULT NULL,
  `idReceptionWork` varchar(45) DEFAULT NULL,
  `idPrototype` varchar(45) DEFAULT NULL,
  `isbnBook` varchar(45) DEFAULT NULL,
  `siciArticle` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idMemberEvidence`),
  KEY `CURPMember2_idx` (`curpMember`),
  KEY `idReceptionWork_idx` (`idReceptionWork`),
  KEY `idPrototype_idx` (`idPrototype`),
  KEY `isbnBook_idx` (`isbnBook`),
  KEY `siciArticle_idx` (`siciArticle`),
  CONSTRAINT `CURPMember2` FOREIGN KEY (`curpMember`) REFERENCES `member` (`curp`),
  CONSTRAINT `idPrototypeE` FOREIGN KEY (`idPrototype`) REFERENCES `prototype` (`idPrototype`),
  CONSTRAINT `idReceptionWorkK` FOREIGN KEY (`idReceptionWork`) REFERENCES `receptionwork` (`idReceptionWork`),
  CONSTRAINT `isbnBookK` FOREIGN KEY (`isbnBook`) REFERENCES `book` (`isbn`),
  CONSTRAINT `siciArticle` FOREIGN KEY (`siciArticle`) REFERENCES `article` (`sici`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member-evidence`
--

LOCK TABLES `member-evidence` WRITE;
/*!40000 ALTER TABLE `member-evidence` DISABLE KEYS */;
/*!40000 ALTER TABLE `member-evidence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member-investigationproject`
--

DROP TABLE IF EXISTS `member-investigationproject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member-investigationproject` (
  `idMemberInvestigationProject` varchar(45) NOT NULL,
  `curpMember` varchar(18) DEFAULT NULL,
  `idInvestigationProject` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idMemberInvestigationProject`),
  KEY `CURPMember1_idx` (`curpMember`),
  KEY `idInvestigationProyect_idx` (`idInvestigationProject`),
  CONSTRAINT `CURPMember1` FOREIGN KEY (`curpMember`) REFERENCES `member` (`curp`),
  CONSTRAINT `idInvestigationProject` FOREIGN KEY (`idInvestigationProject`) REFERENCES `investigationproject` (`idInvestigationProject`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member-investigationproject`
--

LOCK TABLES `member-investigationproject` WRITE;
/*!40000 ALTER TABLE `member-investigationproject` DISABLE KEYS */;
/*!40000 ALTER TABLE `member-investigationproject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member-meet`
--

DROP TABLE IF EXISTS `member-meet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member-meet` (
  `idMemberMeet` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL,
  `curpMember` varchar(18) DEFAULT NULL,
  `idMeet` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idMemberMeet`),
  KEY `idMeet_idx` (`idMeet`),
  KEY `CURPMember_idx` (`curpMember`),
  CONSTRAINT `CURPMember` FOREIGN KEY (`curpMember`) REFERENCES `member` (`curp`),
  CONSTRAINT `idMeet` FOREIGN KEY (`idMeet`) REFERENCES `meet` (`idMeet`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member-meet`
--

LOCK TABLES `member-meet` WRITE;
/*!40000 ALTER TABLE `member-meet` DISABLE KEYS */;
/*!40000 ALTER TABLE `member-meet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `memorandummeet`
--

DROP TABLE IF EXISTS `memorandummeet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `memorandummeet` (
  `idMemorandumMeet` varchar(45) NOT NULL,
  `subject` varchar(200) NOT NULL,
  `date` date NOT NULL,
  `hour` time NOT NULL,
  `place` varchar(45) NOT NULL,
  PRIMARY KEY (`idMemorandumMeet`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `memorandummeet`
--

LOCK TABLES `memorandummeet` WRITE;
/*!40000 ALTER TABLE `memorandummeet` DISABLE KEYS */;
/*!40000 ALTER TABLE `memorandummeet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `participant-investigationproject`
--

DROP TABLE IF EXISTS `participant-investigationproject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `participant-investigationproject` (
  `idParticipantInvestigationProject` varchar(45) NOT NULL,
  `participantName` varchar(100) NOT NULL,
  `idInvestigationProject` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idParticipantInvestigationProject`),
  KEY `idInvestigationProject2_idx` (`idInvestigationProject`),
  CONSTRAINT `idInvestigationProject2` FOREIGN KEY (`idInvestigationProject`) REFERENCES `investigationproject` (`idInvestigationProject`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `participant-investigationproject`
--

LOCK TABLES `participant-investigationproject` WRITE;
/*!40000 ALTER TABLE `participant-investigationproject` DISABLE KEYS */;
/*!40000 ALTER TABLE `participant-investigationproject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preliminaryproject`
--

DROP TABLE IF EXISTS `preliminaryproject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `preliminaryproject` (
  `idPreliminaryProject` varchar(45) NOT NULL,
  `title` varchar(100) NOT NULL,
  `description` varchar(300) NOT NULL,
  `startDate` date NOT NULL,
  `status` varchar(45) NOT NULL,
  `modality` varchar(45) NOT NULL,
  `duration` int NOT NULL,
  `idInvestigationProject` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idPreliminaryProject`),
  KEY `idInvestigationProject5_idx` (`idInvestigationProject`),
  CONSTRAINT `idInvestigationProject5` FOREIGN KEY (`idInvestigationProject`) REFERENCES `investigationproject` (`idInvestigationProject`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preliminaryproject`
--

LOCK TABLES `preliminaryproject` WRITE;
/*!40000 ALTER TABLE `preliminaryproject` DISABLE KEYS */;
INSERT INTO `preliminaryproject` VALUES ('PLP-1','Teoría de los microservicios','Proyecto de investigación dedicado a la intervención de los protocolos de seguridad para microservicios','2020-12-22','Asignada','Tesina',24,'IVP-1');
/*!40000 ALTER TABLE `preliminaryproject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preliminaryproject-codirector`
--

DROP TABLE IF EXISTS `preliminaryproject-codirector`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `preliminaryproject-codirector` (
  `idPreliminaryProjectCodirector` varchar(45) NOT NULL,
  `codirectorName` varchar(100) NOT NULL,
  `codirectorEmail` varchar(320) NOT NULL,
  `idPreliminaryProject` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idPreliminaryProjectCodirector`),
  KEY `idPreliminaryProject_idx` (`idPreliminaryProject`),
  CONSTRAINT `idPreliminaryProject1` FOREIGN KEY (`idPreliminaryProject`) REFERENCES `preliminaryproject` (`idPreliminaryProject`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preliminaryproject-codirector`
--

LOCK TABLES `preliminaryproject-codirector` WRITE;
/*!40000 ALTER TABLE `preliminaryproject-codirector` DISABLE KEYS */;
/*!40000 ALTER TABLE `preliminaryproject-codirector` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preliminaryproject-studentinformation`
--

DROP TABLE IF EXISTS `preliminaryproject-studentinformation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `preliminaryproject-studentinformation` (
  `idPreliminaryProjectStudentInformation` varchar(45) NOT NULL,
  `studentName` varchar(100) NOT NULL,
  `studentEmail` varchar(320) NOT NULL,
  `idPreliminaryProject` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idPreliminaryProjectStudentInformation`),
  KEY `idPreeliminaryProyect_idx` (`idPreliminaryProject`),
  CONSTRAINT `idPreliminaryProject` FOREIGN KEY (`idPreliminaryProject`) REFERENCES `preliminaryproject` (`idPreliminaryProject`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preliminaryproject-studentinformation`
--

LOCK TABLES `preliminaryproject-studentinformation` WRITE;
/*!40000 ALTER TABLE `preliminaryproject-studentinformation` DISABLE KEYS */;
INSERT INTO `preliminaryproject-studentinformation` VALUES ('PSN-1','Calos Edson Romero','carlos@gmail.com','PLP-1'),('PSN-2','Calos Edson Romero','carlos@gmail.com','PLP-1');
/*!40000 ALTER TABLE `preliminaryproject-studentinformation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prerequirement`
--

DROP TABLE IF EXISTS `prerequirement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prerequirement` (
  `idPrerequirement` varchar(45) NOT NULL,
  `description` varchar(300) NOT NULL,
  `idMeet` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idPrerequirement`),
  KEY `idMeet1_idx` (`idMeet`),
  CONSTRAINT `idMeet1` FOREIGN KEY (`idMeet`) REFERENCES `meet` (`idMeet`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prerequirement`
--

LOCK TABLES `prerequirement` WRITE;
/*!40000 ALTER TABLE `prerequirement` DISABLE KEYS */;
/*!40000 ALTER TABLE `prerequirement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prototype`
--

DROP TABLE IF EXISTS `prototype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prototype` (
  `idPrototype` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `title` varchar(100) NOT NULL,
  `academicDegree` varchar(100) NOT NULL,
  `idInvestigationProject` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idPrototype`),
  KEY `idInvestigationProyect8_idx` (`idInvestigationProject`),
  CONSTRAINT `idInvestigationProyect8` FOREIGN KEY (`idInvestigationProject`) REFERENCES `investigationproject` (`idInvestigationProject`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prototype`
--

LOCK TABLES `prototype` WRITE;
/*!40000 ALTER TABLE `prototype` DISABLE KEYS */;
INSERT INTO `prototype` VALUES ('tt4','','','',NULL);
/*!40000 ALTER TABLE `prototype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prototype-characteristic`
--

DROP TABLE IF EXISTS `prototype-characteristic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prototype-characteristic` (
  `idPrototypeCharacteristic` varchar(45) NOT NULL,
  `characteristic` varchar(200) NOT NULL,
  `idPrototype` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idPrototypeCharacteristic`),
  KEY `idPrototype_idx` (`idPrototype`),
  CONSTRAINT `idPrototype` FOREIGN KEY (`idPrototype`) REFERENCES `prototype` (`idPrototype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prototype-characteristic`
--

LOCK TABLES `prototype-characteristic` WRITE;
/*!40000 ALTER TABLE `prototype-characteristic` DISABLE KEYS */;
/*!40000 ALTER TABLE `prototype-characteristic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receptionwork`
--

DROP TABLE IF EXISTS `receptionwork`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receptionwork` (
  `idReceptionWork` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `title` varchar(100) NOT NULL,
  `condition` varchar(200) NOT NULL,
  `studentName` varchar(100) NOT NULL,
  `academicDegree` varchar(100) NOT NULL,
  `idInvestigationProject` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idReceptionWork`),
  KEY `idInvestigationProyect7_idx` (`idInvestigationProject`),
  CONSTRAINT `idInvestigationProyect7` FOREIGN KEY (`idInvestigationProject`) REFERENCES `investigationproject` (`idInvestigationProject`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receptionwork`
--

LOCK TABLES `receptionwork` WRITE;
/*!40000 ALTER TABLE `receptionwork` DISABLE KEYS */;
INSERT INTO `receptionwork` VALUES ('REW-1','X','X','X','X','X',NULL),('REW-2','X','X','X','X','X',NULL);
/*!40000 ALTER TABLE `receptionwork` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regulatorynotes-eventconstancy`
--

DROP TABLE IF EXISTS `regulatorynotes-eventconstancy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `regulatorynotes-eventconstancy` (
  `idRegulatoryNoteEventConstancy` varchar(45) NOT NULL,
  `regulatoryNote` varchar(300) NOT NULL,
  `idEventConstancy` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idRegulatoryNoteEventConstancy`),
  KEY `idEventConstancy_idx` (`idEventConstancy`),
  CONSTRAINT `idEventConstancy` FOREIGN KEY (`idEventConstancy`) REFERENCES `eventconstancy` (`idEventConstancy`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regulatorynotes-eventconstancy`
--

LOCK TABLES `regulatorynotes-eventconstancy` WRITE;
/*!40000 ALTER TABLE `regulatorynotes-eventconstancy` DISABLE KEYS */;
INSERT INTO `regulatorynotes-eventconstancy` VALUES ('RNC-1','nota reglamentaria','EVC-1'),('RNC-2','nota','EVC-2'),('RNC-3','nottita','EVC-3'),('RNC-4','notita','EVC-4');
/*!40000 ALTER TABLE `regulatorynotes-eventconstancy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `strategy`
--

DROP TABLE IF EXISTS `strategy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `strategy` (
  `idStrategy` varchar(45) NOT NULL,
  `action` varchar(300) NOT NULL,
  `description` varchar(300) NOT NULL,
  `goal` varchar(300) NOT NULL,
  `number` int NOT NULL,
  `result` varchar(300) NOT NULL,
  `idTarget` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idStrategy`),
  KEY `idTarget_idx` (`idTarget`),
  CONSTRAINT `idTarget` FOREIGN KEY (`idTarget`) REFERENCES `target` (`idTarget`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `strategy`
--

LOCK TABLES `strategy` WRITE;
/*!40000 ALTER TABLE `strategy` DISABLE KEYS */;
INSERT INTO `strategy` VALUES ('STR-2','Estrageia2','Estrageia2','Estrageia2',1,'Estrageia2','TRG-2'),('STR-3','Estrageia3','Estrageia3','Estrageia3',2,'Estrageia3','TRG-2'),('STR-4','Estrategia3','Estrategia3','Estrategia3',1,'Estrategia3','TRG-3'),('STR-5','Estrageia444','Estrageia444','Estrageia444',2,'Estrageia444','TRG-1'),('STR-6','estra','estra','estra',3,'estra','TRG-1');
/*!40000 ALTER TABLE `strategy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `target`
--

DROP TABLE IF EXISTS `target`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `target` (
  `idTarget` varchar(45) NOT NULL,
  `description` varchar(300) NOT NULL,
  `title` varchar(100) NOT NULL,
  `status` varchar(45) NOT NULL,
  `keyWorkplan` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idTarget`),
  KEY `workplanKey_idx` (`keyWorkplan`),
  CONSTRAINT `keyWorkplan` FOREIGN KEY (`keyWorkplan`) REFERENCES `workplan` (`keyWorkPlan`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `target`
--

LOCK TABLES `target` WRITE;
/*!40000 ALTER TABLE `target` DISABLE KEYS */;
INSERT INTO `target` VALUES ('TRG-1','Mediante las juntas de cuerpo academico se acordó colegiadamente la necesidad de integrar a un nuevo miembro en el Cuerpo Academico que cuente con titulo de doctor','Incrementar el numero de integrantes del CA','Pendiente','P_LIS_UVitsCA'),('TRG-2','ObjetivoPrueba','ObjetivoPrueba','Cumplido','P_LIS_UVitsCA'),('TRG-3','Objetivo1','Objetivo2','Pendiente','Plan2');
/*!40000 ALTER TABLE `target` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `validators-eventconstancy`
--

DROP TABLE IF EXISTS `validators-eventconstancy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `validators-eventconstancy` (
  `idValidatorEventConstancy` varchar(45) NOT NULL,
  `idEventConstancy` varchar(45) DEFAULT NULL,
  `curpMember` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`idValidatorEventConstancy`),
  KEY `idEventConstancy1_idx` (`idEventConstancy`),
  KEY `CURPMember6_idx` (`curpMember`),
  CONSTRAINT `CURPMember7` FOREIGN KEY (`curpMember`) REFERENCES `member` (`curp`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `idEventConstancy1` FOREIGN KEY (`idEventConstancy`) REFERENCES `eventconstancy` (`idEventConstancy`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `validators-eventconstancy`
--

LOCK TABLES `validators-eventconstancy` WRITE;
/*!40000 ALTER TABLE `validators-eventconstancy` DISABLE KEYS */;
INSERT INTO `validators-eventconstancy` VALUES ('VDC-1','EVC-1','SASO750909HDFNNS05'),('VDC-2','EVC-2','SASO750909HDFNNS05'),('VDC-3','EVC-3','SASO750909HDFNNS05'),('VDC-4','EVC-4','SASO750909HDFNNS05');
/*!40000 ALTER TABLE `validators-eventconstancy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workplan`
--

DROP TABLE IF EXISTS `workplan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workplan` (
  `keyWorkPlan` varchar(45) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `keyAcademicGroup` varchar(45) DEFAULT NULL,
  `curpMember` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`keyWorkPlan`),
  KEY `keyAcademicGroup2_idx` (`keyAcademicGroup`),
  KEY `CURPMember6_idx` (`curpMember`),
  CONSTRAINT `CURPMember6` FOREIGN KEY (`curpMember`) REFERENCES `member` (`curp`),
  CONSTRAINT `keyAcademicGroup2` FOREIGN KEY (`keyAcademicGroup`) REFERENCES `academicgroup` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workplan`
--

LOCK TABLES `workplan` WRITE;
/*!40000 ALTER TABLE `workplan` DISABLE KEYS */;
INSERT INTO `workplan` VALUES ('P_LIS_UVitsCA','2020-12-04','2020-12-04','AGP-1','SASO750909HDFNNS05'),('Plan2','2021-07-01','2021-07-02','AGP-1','SASO750909HDFNNS05');
/*!40000 ALTER TABLE `workplan` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-02 12:29:55
