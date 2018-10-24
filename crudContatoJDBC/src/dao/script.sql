create database db02102018;

use db02102018;

CREATE TABLE contato (
  idcontato INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(256) ,
  dataNascimento DATE ,
  ativo TINYINT ,
  PRIMARY KEY (idcontato));