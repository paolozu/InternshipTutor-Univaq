/*
SQLyog Community v13.1.0 (64 bit)
MySQL - 5.7.19-log : Database - vwda2oqf9fppbxps
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`vwda2oqf9fppbxps` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `vwda2oqf9fppbxps`;

/*Table structure for table `annuncio` */

DROP TABLE IF EXISTS `annuncio`;

CREATE TABLE `annuncio` (
  `idAnnuncio` int(11) NOT NULL AUTO_INCREMENT,
  `titolo` varchar(255) NOT NULL,
  `corpo` text NOT NULL,
  `dataAvvio` date NOT NULL,
  `dataTermine` date NOT NULL,
  `modalita` varchar(45) NOT NULL,
  `Azienda_idAzienda` int(11) NOT NULL,
  `Tutore_idTutore` int(11) NOT NULL,
  `settore` varchar(45) NOT NULL,
  `sussidio` varchar(45) NOT NULL,
  PRIMARY KEY (`idAnnuncio`),
  KEY `fk_Tirocinio_Azienda_idx` (`Azienda_idAzienda`),
  KEY `fk_Tirocinio_Tutore1_idx` (`Tutore_idTutore`),
  CONSTRAINT `fk_Tirocinio_Azienda` FOREIGN KEY (`Azienda_idAzienda`) REFERENCES `azienda` (`idAzienda`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Tirocinio_Tutore1` FOREIGN KEY (`Tutore_idTutore`) REFERENCES `tutore` (`idTutore`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `annuncio` */

insert  into `annuncio`(`idAnnuncio`,`titolo`,`corpo`,`dataAvvio`,`dataTermine`,`modalita`,`Azienda_idAzienda`,`Tutore_idTutore`,`settore`,`sussidio`) values 
(1,'Qbase SH','Qbase sw invita gli studenti. bla bla bla','2018-05-22','2018-09-25','Sviluppo in sede',1,1,'Sviluppo software','Mensa dei poveri'),
(2,'Titolo2','Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Quam diu etiam furor iste tuus nos eludet? Hi omnes lingua, institutis, legibus inter se differunt. Cum ceteris in veneratione tui montes, nascetur mus. Plura mihi bona sunt, inclinet, amari petere vellent. Ullamco laboris nisi ut aliquid ex ea commodi consequat. Ambitioni dedisse scripsisse iudicaretur. Vivamus sagittis lacus vel augue laoreet rutrum faucibus.','2018-01-01','2019-01-01','Sviluppo in sede',1,1,'Sviluppo software','Mensa'),
(3,'Titolo3','Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Quam diu etiam furor iste tuus nos eludet? Hi omnes lingua, institutis, legibus inter se differunt. Cum ceteris in veneratione tui montes, nascetur mus. Plura mihi bona sunt, inclinet, amari petere vellent. Ullamco laboris nisi ut aliquid ex ea commodi consequat. Ambitioni dedisse scripsisse iudicaretur. Vivamus sagittis lacus vel augue laoreet rutrum faucibus.','2018-01-01','2019-01-01','Sviluppo in sede',1,1,'Sviluppo software','Mensa'),
(4,'Titolo4','Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Quam diu etiam furor iste tuus nos eludet? Hi omnes lingua, institutis, legibus inter se differunt. Cum ceteris in veneratione tui montes, nascetur mus. Plura mihi bona sunt, inclinet, amari petere vellent. Ullamco laboris nisi ut aliquid ex ea commodi consequat. Ambitioni dedisse scripsisse iudicaretur. Vivamus sagittis lacus vel augue laoreet rutrum faucibus.','2018-01-01','2019-01-01','Sviluppo in sede',1,1,'Sviluppo software','Mensa'),
(5,'Titolo5','Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Quam diu etiam furor iste tuus nos eludet? Hi omnes lingua, institutis, legibus inter se differunt. Cum ceteris in veneratione tui montes, nascetur mus. Plura mihi bona sunt, inclinet, amari petere vellent. Ullamco laboris nisi ut aliquid ex ea commodi consequat. Ambitioni dedisse scripsisse iudicaretur. Vivamus sagittis lacus vel augue laoreet rutrum faucibus.','2018-01-01','2019-01-01','Sviluppo in sede',1,1,'Sviluppo software',''),
(6,'Titolo6','Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Quam diu etiam furor iste tuus nos eludet? Hi omnes lingua, institutis, legibus inter se differunt. Cum ceteris in veneratione tui montes, nascetur mus. Plura mihi bona sunt, inclinet, amari petere vellent. Ullamco laboris nisi ut aliquid ex ea commodi consequat. Ambitioni dedisse scripsisse iudicaretur. Vivamus sagittis lacus vel augue laoreet rutrum faucibus.','2018-01-01','2019-01-01','Sviluppo in sede',1,1,'Sviluppo software',''),
(7,'Titolo7','Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Quam diu etiam furor iste tuus nos eludet? Hi omnes lingua, institutis, legibus inter se differunt. Cum ceteris in veneratione tui montes, nascetur mus. Plura mihi bona sunt, inclinet, amari petere vellent. Ullamco laboris nisi ut aliquid ex ea commodi consequat. Ambitioni dedisse scripsisse iudicaretur. Vivamus sagittis lacus vel augue laoreet rutrum faucibus.','2018-01-01','2019-01-01','Sviluppo in sede',1,1,'Sviluppo software','Mensa'),
(8,'Titolo2','Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Quam diu etiam furor iste tuus nos eludet? Hi omnes lingua, institutis, legibus inter se differunt. Cum ceteris in veneratione tui montes, nascetur mus. Plura mihi bona sunt, inclinet, amari petere vellent. Ullamco laboris nisi ut aliquid ex ea commodi consequat. Ambitioni dedisse scripsisse iudicaretur. Vivamus sagittis lacus vel augue laoreet rutrum faucibus.','2018-01-01','2019-01-01','Sviluppo in sede',1,1,'Sviluppo software','Mensa'),
(9,'ok','ok','1970-01-01','1970-01-01','mod',1,1,'set','sus'),
(10,'ok','ok','2014-09-09','1970-01-01','mod',1,1,'set','sus'),
(11,'ok','ok','2014-09-09','1970-01-01','mod',1,1,'set','sus'),
(12,'ok','ok','2014-09-09','1970-01-01','mod',1,1,'set','sus'),
(13,'Titolo1','Corpo1','2014-09-09','2015-09-09','mod1',1,1,'set1','sus1');

/*Table structure for table `azienda` */

DROP TABLE IF EXISTS `azienda`;

CREATE TABLE `azienda` (
  `idAzienda` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `nomeRap` varchar(45) NOT NULL,
  `cognomeRap` varchar(45) NOT NULL,
  `ragSociale` varchar(45) NOT NULL,
  `indirizzoSede` varchar(45) NOT NULL,
  `pIVA` varchar(11) NOT NULL,
  `foro` varchar(45) NOT NULL,
  `telefono` varchar(10) NOT NULL,
  `abilitata` bit(1) NOT NULL,
  `idConvenzione` int(11) DEFAULT NULL,
  `cap` varchar(45) NOT NULL,
  `citta` varchar(45) NOT NULL,
  `provincia` varchar(45) NOT NULL,
  PRIMARY KEY (`idAzienda`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `fk_Azienda_Convenzione1_idx` (`idConvenzione`),
  CONSTRAINT `fk_Azienda_Convenzione1` FOREIGN KEY (`idConvenzione`) REFERENCES `convenzione` (`idConvenzione`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `azienda` */

insert  into `azienda`(`idAzienda`,`email`,`password`,`username`,`nomeRap`,`cognomeRap`,`ragSociale`,`indirizzoSede`,`pIVA`,`foro`,`telefono`,`abilitata`,`idConvenzione`,`cap`,`citta`,`provincia`) values 
(1,'qbase@gmail.com','123','qbase','Carlo','Zavatti','Qbase','via Napoli','29291jjj','Roma','2020020202','',1,'929292','Roma','Roma'),
(2,'azienda2@mail.it','123','azienda2','NomeAzienda2','CognAzienda2','ragione2','indirizzo2','partitaIVA2','foro2','3330000002','\0',NULL,'60002','citta2','provincia2'),
(3,'azienda3@mail.it','123','azienda3','NomeAzienda3','CognAzienda3','ragione3','indirizzo3','partitaIVA3','foro3','3330000003','\0',NULL,'60003','citta3','provincia3'),
(4,'azienda4@mail.it','123','azienda4','NomeAzienda4','CognAzienda4','ragione4','indirizzo4','partitaIVA4','foro4','4440000004','\0',NULL,'60004','citta4','provincia4'),
(5,'azienda5@mail.it','123','azienda5','NomeAzienda5','CognAzienda5','ragione5','indirizzo5','partitaIVA5','foro5','5550000005','\0',NULL,'60005','citta5','provincia5');

/*Table structure for table `convenzione` */

DROP TABLE IF EXISTS `convenzione`;

CREATE TABLE `convenzione` (
  `idConvenzione` int(11) NOT NULL AUTO_INCREMENT,
  `durataConvenzione` varchar(45) NOT NULL,
  `dataConvenzione` varchar(45) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `directory` varchar(255) NOT NULL,
  `estensione` varchar(45) NOT NULL,
  `peso` varchar(45) NOT NULL,
  PRIMARY KEY (`idConvenzione`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `convenzione` */

insert  into `convenzione`(`idConvenzione`,`durataConvenzione`,`dataConvenzione`,`nome`,`directory`,`estensione`,`peso`) values 
(1,'3 mesi','2018-02-02','qbase.pdf','/resources/pdf','.pdf','40kb'),
(2,'3 mesi','2018-02-02','qbase1.pdf','/resources/pdf','.pdf','40kb');

/*Table structure for table `resoconto` */

DROP TABLE IF EXISTS `resoconto`;

CREATE TABLE `resoconto` (
  `idResoconto` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `directory` varchar(45) NOT NULL,
  `estensione` varchar(45) NOT NULL,
  `peso` varchar(45) NOT NULL,
  `oreSvolte` int(3) NOT NULL,
  `attivitaSvolta` text NOT NULL,
  `risConseguito` text NOT NULL,
  `valutazione` int(11) NOT NULL,
  PRIMARY KEY (`idResoconto`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `resoconto` */

insert  into `resoconto`(`idResoconto`,`nome`,`directory`,`estensione`,`peso`,`oreSvolte`,`attivitaSvolta`,`risConseguito`,`valutazione`) values 
(1,'qbase_done','/resources/pdf/','.pdf','23MB',150,'Sviluppo Front-End','Il candidato bla bla bla',5);

/*Table structure for table `richiesta` */

DROP TABLE IF EXISTS `richiesta`;

CREATE TABLE `richiesta` (
  `Studente_idStudente` int(11) NOT NULL AUTO_INCREMENT,
  `Azienda_idAzienda` int(11) NOT NULL,
  PRIMARY KEY (`Studente_idStudente`,`Azienda_idAzienda`),
  KEY `fk_Studente_has_Azienda_Azienda1_idx` (`Azienda_idAzienda`),
  KEY `fk_Studente_has_Azienda_Studente1_idx` (`Studente_idStudente`),
  CONSTRAINT `fk_Studente_has_Azienda_Azienda1` FOREIGN KEY (`Azienda_idAzienda`) REFERENCES `azienda` (`idAzienda`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Studente_has_Azienda_Studente1` FOREIGN KEY (`Studente_idStudente`) REFERENCES `studente` (`idStudente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

/*Data for the table `richiesta` */

insert  into `richiesta`(`Studente_idStudente`,`Azienda_idAzienda`) values 
(42,1),
(43,1),
(44,1),
(45,2);

/*Table structure for table `studente` */

DROP TABLE IF EXISTS `studente`;

CREATE TABLE `studente` (
  `idStudente` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `cognome` varchar(45) NOT NULL,
  `codFiscale` varchar(11) NOT NULL,
  `telefono` varchar(11) NOT NULL,
  `crediti` int(11) NOT NULL,
  `handicap` bit(1) NOT NULL,
  `dataNascita` date NOT NULL,
  `indirizzoResidenza` varchar(45) NOT NULL,
  `corsoLaurea` varchar(45) NOT NULL,
  `diploma` varchar(45) DEFAULT NULL,
  `laurea` varchar(45) DEFAULT NULL,
  `dottorato` varchar(45) DEFAULT NULL,
  `cap_nascita` varchar(45) NOT NULL,
  `citta_nascita` varchar(45) NOT NULL,
  `provincia_nascita` varchar(45) NOT NULL,
  `cap_residenza` varchar(45) NOT NULL,
  `citta_residenza` varchar(45) NOT NULL,
  `provincia_residenza` varchar(45) NOT NULL,
  PRIMARY KEY (`idStudente`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

/*Data for the table `studente` */

insert  into `studente`(`idStudente`,`email`,`nome`,`cognome`,`codFiscale`,`telefono`,`crediti`,`handicap`,`dataNascita`,`indirizzoResidenza`,`corsoLaurea`,`diploma`,`laurea`,`dottorato`,`cap_nascita`,`citta_nascita`,`provincia_nascita`,`cap_residenza`,`citta_residenza`,`provincia_residenza`) values 
(42,'claudio.rossi@gmail.com','Claudio','Rossi','CLRSS82PL0','0772678983',6,'','1970-10-22','via Cala dalla Cierqua','Informatica','Ragioneria','Fisica','N/A','67000','Roma','RM','72882','Chieti','Chieti'),
(43,'studente1@mail.it','studente1','cognStudente1','CLREW0001','3400000001',6,'\0','1970-10-22','indirizzo studente 1','Informatica',NULL,NULL,NULL,'67000','Roma','RM','72882','Roma','RM'),
(44,'studente2@mail.it','studente2','cognStudente2','CLREW0002','3400000002',6,'\0','1970-10-22','indirizzo studente 2','Informatica',NULL,NULL,NULL,'67000','Roma','RM','72882','Roma','RM'),
(45,'studente3@mail.it','studente3','cognStudente3','CLREW0003','3400000003',6,'\0','1970-10-22','indirizzo studente 3','Informatica',NULL,NULL,NULL,'67000','Roma','RM','72882','Roma','RM'),
(46,'studente4@mail.it','studente4','cognStudente4','CLREW0004','3400000004',6,'\0','1970-10-22','indirizzo studente 4','Informatica',NULL,NULL,NULL,'67000','Roma','RM','72882','Roma','RM');

/*Table structure for table `tirocinio` */

DROP TABLE IF EXISTS `tirocinio`;

CREATE TABLE `tirocinio` (
  `idTirocinio` int(11) NOT NULL AUTO_INCREMENT,
  `dataInizio` date NOT NULL,
  `dataFine` date NOT NULL,
  `idAnnuncio` int(11) NOT NULL,
  `Studente_idStudente` int(11) NOT NULL,
  `Resoconto_idResoconto` int(11) DEFAULT NULL,
  PRIMARY KEY (`idTirocinio`,`Studente_idStudente`),
  KEY `fk_Tirocinio_Annuncio1_idx` (`idAnnuncio`),
  KEY `fk_Tirocinio_Studente1_idx` (`Studente_idStudente`),
  KEY `fk_Tirocinio_Resoconto1_idx` (`Resoconto_idResoconto`),
  CONSTRAINT `fk_Tirocinio_Annuncio1` FOREIGN KEY (`idAnnuncio`) REFERENCES `annuncio` (`idAnnuncio`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Tirocinio_Resoconto1` FOREIGN KEY (`Resoconto_idResoconto`) REFERENCES `resoconto` (`idResoconto`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Tirocinio_Studente1` FOREIGN KEY (`Studente_idStudente`) REFERENCES `studente` (`idStudente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tirocinio` */

insert  into `tirocinio`(`idTirocinio`,`dataInizio`,`dataFine`,`idAnnuncio`,`Studente_idStudente`,`Resoconto_idResoconto`) values 
(1,'2018-01-01','2019-01-01',1,43,NULL);

/*Table structure for table `tutore` */

DROP TABLE IF EXISTS `tutore`;

CREATE TABLE `tutore` (
  `idTutore` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `cognome` varchar(45) NOT NULL,
  `telefono` varchar(10) NOT NULL,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`idTutore`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `tutore` */

insert  into `tutore`(`idTutore`,`nome`,`cognome`,`telefono`,`email`) values 
(1,'Giuseppe','Della Penna','20202011','g.dp@univaq.it'),
(2,'Amleto','Di Salle','34500000','amleto@mail.it'),
(3,'Alfonso','Pierantonio','34600000','alfonso@mail.it'),
(4,'Alfondo','Florinfi','34700000','flo@mail.it');

/*Table structure for table `utente` */

DROP TABLE IF EXISTS `utente`;

CREATE TABLE `utente` (
  `idAmministratore` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `Studente_idStudente` int(11) DEFAULT NULL,
  `Azienda_idAzienda` int(11) DEFAULT NULL,
  PRIMARY KEY (`idAmministratore`),
  UNIQUE KEY `Studente_idStudente_UNIQUE` (`Studente_idStudente`),
  UNIQUE KEY `Azienda_idAzienda_UNIQUE` (`Azienda_idAzienda`),
  KEY `fk_Amministratore_Studente1_idx` (`Studente_idStudente`),
  KEY `fk_Utente_Azienda1_idx` (`Azienda_idAzienda`),
  CONSTRAINT `fk_Amministratore_Studente1` FOREIGN KEY (`Studente_idStudente`) REFERENCES `studente` (`idStudente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Utente_Azienda1` FOREIGN KEY (`Azienda_idAzienda`) REFERENCES `azienda` (`idAzienda`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `utente` */

insert  into `utente`(`idAmministratore`,`username`,`password`,`Studente_idStudente`,`Azienda_idAzienda`) values 
(1,'inverardi','123',NULL,NULL),
(2,'loreand','123',NULL,NULL),
(3,'poaolo','123',NULL,NULL),
(4,'stu1','123',43,NULL),
(5,'stu2','123',44,NULL),
(6,'stu3','123',45,NULL),
(7,'stu4','123',46,NULL),
(8,'az1','123',NULL,1),
(9,'az2','123',NULL,2),
(10,'az3','123',NULL,3),
(11,'az4','123',NULL,4),
(12,'az5','123',NULL,5),
(13,'','',NULL,NULL);

/* Trigger structure for table `utente` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `Utente_BEFORE_INSERT` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'dr4jzb24h852pb67'@'%' */ /*!50003 TRIGGER `Utente_BEFORE_INSERT` BEFORE INSERT ON `utente` FOR EACH ROW BEGIN
	IF (NEW.Studente_idStudente IS NOT NULL AND NEW.Azienda_idAzienda IS NOT NULL) THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Studente_idStudente and Azienda_idAzienda cannot both be not null';
	END IF;
END */$$


DELIMITER ;

/* Trigger structure for table `utente` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `Utente_BEFORE_UPDATE` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'dr4jzb24h852pb67'@'%' */ /*!50003 TRIGGER `Utente_BEFORE_UPDATE` BEFORE UPDATE ON `utente` FOR EACH ROW BEGIN
	IF (NEW.Studente_idStudente IS NOT NULL AND NEW.Azienda_idAzienda IS NOT NULL) THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Studente_idStudente and Azienda_idAzienda cannot both be not null';
  END IF;

END */$$


DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
