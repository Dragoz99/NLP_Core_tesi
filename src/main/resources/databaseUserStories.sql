CREATE TABLE `attributi` (
  `id_attributo` int(11) NOT NULL AUTO_INCREMENT,
  `nome_attributo` varchar(255) NOT NULL,
  `tipo_attributo` varchar(255) NOT NULL,
  `fileName_attributo` int(11) NOT NULL,
  PRIMARY KEY (`id_attributo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE `class` (
  `class_id` int(11) NOT NULL AUTO_INCREMENT,
  `class_name` varchar(255) DEFAULT NULL,
  `class_filename_id` bigint(20) NOT NULL,
  `class_type` varchar(255) NOT NULL,
  `name_cangiante` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`class_id`,`class_filename_id`)
) ENGINE=InnoDB AUTO_INCREMENT=365 DEFAULT CHARSET=latin1;

CREATE TABLE `filename` (
  `filename_id` bigint(20) NOT NULL,
  `filename_name` varchar(255) NOT NULL,
  `filename_tag` varchar(255) NOT NULL,
  `filename_data` datetime NOT NULL,
  PRIMARY KEY (`filename_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `h_relazioni` (
  `h_rel_id` int(11) NOT NULL AUTO_INCREMENT,
  `h_className_1` varchar(255) NOT NULL,
  `h_className_2` varchar(255) NOT NULL,
  `h_classID_1` int(11) NOT NULL,
  `h_classID_2` int(11) NOT NULL,
  `h_rel_type` varchar(255) NOT NULL,
  `h_class_1_fileID` int(11) DEFAULT NULL,
  `h_class_2_fileID` int(11) DEFAULT NULL,
  PRIMARY KEY (`h_rel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

CREATE TABLE `relazioni` (
  `relazioni_id` int(11) NOT NULL AUTO_INCREMENT,
  `relazioni_className_1` varchar(255) DEFAULT NULL,
  `relazioni_className_2` varchar(255) DEFAULT NULL,
  `relazioni_classID_1` int(11) DEFAULT NULL,
  `relazioni_classID_2` int(11) DEFAULT NULL,
  `relazioni_classFileName_id_1` bigint(20) DEFAULT NULL,
  `relazioni_classFileName_id_2` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`relazioni_id`)
) ENGINE=InnoDB AUTO_INCREMENT=207 DEFAULT CHARSET=latin1;