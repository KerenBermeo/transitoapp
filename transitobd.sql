-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-08-2024 a las 21:47:30
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `transitobd`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tblmulta`
--

CREATE TABLE `tblmulta` (
  `multaId` int(11) NOT NULL,
  `multaValorMulta` decimal(10,2) NOT NULL,
  `multaDiasMora` int(11) NOT NULL,
  `multaValorPagar` decimal(10,2) NOT NULL,
  `personaId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tblpersona`
--

CREATE TABLE `tblpersona` (
  `personaId` int(11) NOT NULL,
  `personaNombre` varchar(100) NOT NULL,
  `personaCorreo` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbluser`
--

CREATE TABLE `tbluser` (
  `userId` int(11) NOT NULL,
  `userName` varchar(50) NOT NULL,
  `userLogin` varchar(30) NOT NULL,
  `userPassword` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tbluser`
--

INSERT INTO `tbluser` (`userId`, `userName`, `userLogin`, `userPassword`) VALUES
(1, 'user 1', 'user1', '$2a$10$JED9wZJJhzJz3TsHE.T1f.JA2RF6ONMozvhL5TqCx2Lxnv7BJ5bvS');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `tblmulta`
--
ALTER TABLE `tblmulta`
  ADD PRIMARY KEY (`multaId`),
  ADD KEY `personaId` (`personaId`);

--
-- Indices de la tabla `tblpersona`
--
ALTER TABLE `tblpersona`
  ADD PRIMARY KEY (`personaId`),
  ADD UNIQUE KEY `personaCorreo` (`personaCorreo`);

--
-- Indices de la tabla `tbluser`
--
ALTER TABLE `tbluser`
  ADD PRIMARY KEY (`userId`),
  ADD UNIQUE KEY `userLogin` (`userLogin`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `tblmulta`
--
ALTER TABLE `tblmulta`
  MODIFY `multaId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `tblpersona`
--
ALTER TABLE `tblpersona`
  MODIFY `personaId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tbluser`
--
ALTER TABLE `tbluser`
  MODIFY `userId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `tblmulta`
--
ALTER TABLE `tblmulta`
  ADD CONSTRAINT `tblmulta_ibfk_1` FOREIGN KEY (`personaId`) REFERENCES `tblpersona` (`personaId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
