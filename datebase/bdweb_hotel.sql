-- MySQL Script generated by MySQL Workbench
-- mar 20 jun 2017 19:42:33 CEST
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema bdweb_hotel
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `bdweb_hotel` ;

-- -----------------------------------------------------
-- Schema bdweb_hotel
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bdweb_hotel` ;
USE `bdweb_hotel` ;

-- -----------------------------------------------------
-- Table `bdweb_hotel`.`tipos_habitaciones`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bdweb_hotel`.`tipos_habitaciones` ;

CREATE TABLE IF NOT EXISTS `bdweb_hotel`.`tipos_habitaciones` (
  `tip_hab_id` INT(11) NOT NULL AUTO_INCREMENT,
  `tip_hab_tipo` ENUM('INDIVIDUAL', 'DOBLE', 'MATRIMONIAL') NOT NULL,
  `tip_hab_precio` DECIMAL(9,2) NOT NULL,
  PRIMARY KEY (`tip_hab_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bdweb_hotel`.`habitaciones`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bdweb_hotel`.`habitaciones` ;

CREATE TABLE IF NOT EXISTS `bdweb_hotel`.`habitaciones` (
  `hab_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `hab_numero` INT(11) NOT NULL,
  `hab_ocupada` TINYINT(1) NOT NULL,
  `tipos_habitaciones_tip_hab_id` INT(11) NOT NULL,
  PRIMARY KEY (`hab_id`, `tipos_habitaciones_tip_hab_id`),
  INDEX `fk_habitaciones_tipos_habitaciones2_idx` (`tipos_habitaciones_tip_hab_id` ASC),
  CONSTRAINT `fk_habitaciones_tipos_habitaciones2`
    FOREIGN KEY (`tipos_habitaciones_tip_hab_id`)
    REFERENCES `bdweb_hotel`.`tipos_habitaciones` (`tip_hab_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bdweb_hotel`.`clientes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bdweb_hotel`.`clientes` ;

CREATE TABLE IF NOT EXISTS `bdweb_hotel`.`clientes` (
  `cli_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `cli_nombre` VARCHAR(45) NOT NULL,
  `cli_apellidos` VARCHAR(45) NOT NULL,
  `cli_password` VARCHAR(128) NOT NULL,
  `cli_direccion` VARCHAR(255) NOT NULL,
  `cli_dni` VARCHAR(9) NOT NULL,
  `cli_email` VARCHAR(45) NOT NULL,
  `cli_numero_movil` VARCHAR(45) NOT NULL,
  `cli_numero_tarjeta` VARCHAR(16) NOT NULL,
  `cli_tipo` VARCHAR(7) NOT NULL DEFAULT 'cliente',
  PRIMARY KEY (`cli_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bdweb_hotel`.`clientes_habituales`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bdweb_hotel`.`clientes_habituales` ;

CREATE TABLE IF NOT EXISTS `bdweb_hotel`.`clientes_habituales` (
  `cli_id` BIGINT(20) NOT NULL,
  `cli_newsletter` TINYINT(1) NOT NULL DEFAULT 1,
  `cli_descuento` INT(2) NOT NULL DEFAULT 5,
  `cli_tipo` VARCHAR(16) NOT NULL DEFAULT 'cliente_habitual',
  PRIMARY KEY (`cli_id`),
  CONSTRAINT `fk_clientes_habituales_clientes1`
    FOREIGN KEY (`cli_id`)
    REFERENCES `bdweb_hotel`.`clientes` (`cli_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bdweb_hotel`.`promociones`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bdweb_hotel`.`promociones` ;

CREATE TABLE IF NOT EXISTS `bdweb_hotel`.`promociones` (
  `prom_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `prom_fecha_inicio` DATE NOT NULL,
  `prom_fecha_final` DATE NOT NULL,
  `prom_descripcion` TEXT NULL,
  `prom_precio` DECIMAL(9,2) NOT NULL,
  `tipos_habitaciones_tip_hab_id` INT(11) NOT NULL,
  PRIMARY KEY (`prom_id`, `tipos_habitaciones_tip_hab_id`),
  INDEX `fk_promociones_tipos_habitaciones2_idx` (`tipos_habitaciones_tip_hab_id` ASC),
  CONSTRAINT `fk_promociones_tipos_habitaciones2`
    FOREIGN KEY (`tipos_habitaciones_tip_hab_id`)
    REFERENCES `bdweb_hotel`.`tipos_habitaciones` (`tip_hab_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bdweb_hotel`.`reservas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bdweb_hotel`.`reservas` ;

CREATE TABLE IF NOT EXISTS `bdweb_hotel`.`reservas` (
  `res_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `res_fecha_inicio` DATE NOT NULL,
  `res_fecha_final` DATE NOT NULL,
  `res_estado` ENUM('ACTIVA', 'CERRADA', 'CANCELADA', 'PROGRESA') NOT NULL,
  `res_importe` DECIMAL(9,2) NOT NULL,
  `res_cobrada` TINYINT(1) NOT NULL DEFAULT 0,
  `clientes_cli_id` BIGINT(20) NOT NULL,
  `habitaciones_hab_id` BIGINT(20) NOT NULL,
  `promociones_prom_id` BIGINT(20) NULL,
  PRIMARY KEY (`res_id`, `clientes_cli_id`, `habitaciones_hab_id`),
  INDEX `fk_reservas_clientes1_idx` (`clientes_cli_id` ASC),
  INDEX `fk_reservas_habitaciones1_idx` (`habitaciones_hab_id` ASC),
  INDEX `fk_reservas_promociones1_idx` (`promociones_prom_id` ASC),
  CONSTRAINT `fk_reservas_clientes1`
    FOREIGN KEY (`clientes_cli_id`)
    REFERENCES `bdweb_hotel`.`clientes` (`cli_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_reservas_habitaciones1`
    FOREIGN KEY (`habitaciones_hab_id`)
    REFERENCES `bdweb_hotel`.`habitaciones` (`hab_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_reservas_promociones1`
    FOREIGN KEY (`promociones_prom_id`)
    REFERENCES `bdweb_hotel`.`promociones` (`prom_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
