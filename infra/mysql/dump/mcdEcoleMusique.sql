CREATE TABLE INSTRUMENT(
   idInstrument INT AUTO_INCREMENT,
   libelleInstrument VARCHAR(30) NOT NULL,
   PRIMARY KEY(idInstrument)
);

CREATE TABLE GROUPE(
   idGroupe INT AUTO_INCREMENT,
   nomGroupe VARCHAR(50) NOT NULL,
   PRIMARY KEY(idGroupe)
);

CREATE TABLE CONCERT(
   idConcert INT AUTO_INCREMENT,
   nomConcert VARCHAR(50) NOT NULL,
   PRIMARY KEY(idConcert)
);

CREATE TABLE PERSONNE(
   idPersonne INT AUTO_INCREMENT,
   nomPersonne VARCHAR(50) NOT NULL,
   prenomPersonne VARCHAR(30) NOT NULL,
   idGroupe INT,
   PRIMARY KEY(idPersonne),
   FOREIGN KEY(idGroupe) REFERENCES GROUPE(idGroupe)
);

CREATE TABLE INSTRUMENT_PERSONNE(
   idPersonne INT,
   idInstrument INT,
   PRIMARY KEY(idPersonne, idInstrument),
   FOREIGN KEY(idPersonne) REFERENCES PERSONNE(idPersonne),
   FOREIGN KEY(idInstrument) REFERENCES INSTRUMENT(idInstrument)
);

CREATE TABLE PARTICIPATION(
   idGroupe INT,
   idConcert INT,
   PRIMARY KEY(idGroupe, idConcert),
   FOREIGN KEY(idGroupe) REFERENCES GROUPE(idGroupe),
   FOREIGN KEY(idConcert) REFERENCES CONCERT(idConcert)
);
