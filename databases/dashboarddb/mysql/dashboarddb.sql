-- phpMyAdmin SQL Dump
-- version 4.9.5deb2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Erstellungszeit: 01. Okt 2021 um 15:21
-- Server-Version: 8.0.26-0ubuntu0.20.04.3
-- PHP-Version: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `dashboarddb`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `kundentabelle`
--

CREATE TABLE `kundentabelle` (
  `id` int DEFAULT NULL,
  `name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `kundentabelle`
--

INSERT INTO `kundentabelle` (`id`, `name`) VALUES
(1, 'meier'),
(2, 'wimmer'),

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `produkttabelle`
--

CREATE TABLE `produkttabelle` (
  `id` int DEFAULT NULL,
  `name` varchar(25) DEFAULT NULL,
  `bestand` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `produkttabelle`
--

INSERT INTO `produkttabelle` (`id`, `name`, `bestand`) VALUES
(1, 'VGA Kabel', 5),
(2, 'Monitor', 12);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
