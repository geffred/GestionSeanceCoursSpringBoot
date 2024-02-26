-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mar. 09 jan. 2024 à 23:02
-- Version du serveur : 10.4.21-MariaDB
-- Version de PHP : 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `ecole`
--

-- --------------------------------------------------------

--
-- Structure de la table `cours`
--

CREATE TABLE `cours` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `cours`
--

INSERT INTO `cours` (`id`, `nom`) VALUES
(2, 'ALGORITHMIQUE ET STRUCTURES DE DONNEES'),
(11, 'ANALYSE ET CONCEPTION DES BASES DE DONNEES'),
(9, 'ARCHITECTURE DES ORDINATEURS'),
(15, 'BIG DATA ET DATA SCIENCE'),
(10, 'CONCEPTION DE LOGICIELS'),
(7, 'CRYPTOGRAPHIE'),
(6, 'DEVELOPPEMENT WEB AVANCE'),
(19, 'ETHIQUE DES AFFAIRES'),
(16, 'ETHIQUE EN INFORMATIQUE'),
(5, 'INTELLIGENCE ARTIFICIELLE'),
(17, 'INTERFACE HOMME MACHINE'),
(1, 'INTRODUCTION A L\' INFORMATIQUE'),
(3, 'PROGRAMMATION EN C'),
(13, 'PROGRAMMATION PARALLELE ET DISTRIBUEE'),
(12, 'REALITE VIRTUELLE ET AUGMENTEE'),
(4, 'RESEAUX INFORMATIQUES'),
(8, 'SECURITE INFORMATIQUE'),
(14, 'SYSTEMES EMBARQUES'),
(18, 'THEORIE DE LA COMPUTATION');

-- --------------------------------------------------------

--
-- Structure de la table `professeurs`
--

CREATE TABLE `professeurs` (
  `date_naissance` date NOT NULL,
  `id` bigint(20) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `sexe` enum('M','F') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `professeurs`
--

INSERT INTO `professeurs` (`date_naissance`, `id`, `nom`, `prenom`, `sexe`) VALUES
('1980-03-15', 1, 'MARTIN', 'CATHERINE', 'F'),
('1975-07-22', 2, 'DUBOIS', 'NICOLAS', 'M'),
('1985-11-10', 3, 'LEROY', 'EMILIE', 'F'),
('1972-09-05', 4, 'MOREAU', 'VINCENT', 'M'),
('1988-06-18', 5, 'FONTAINE', 'CHARLOTTE', 'F'),
('1979-12-03', 6, 'LEFEVRE', 'PIERRE', 'M'),
('1983-04-29', 7, 'DUPONT', 'ISABELLE', 'F'),
('1971-08-14', 8, 'ROUSSEAU', 'OLIVIER', 'M'),
('1987-02-07', 9, 'BERNARD', 'AMANDINE', 'F'),
('1984-09-25', 10, 'GAUTHIER', 'JULIEN', 'M'),
('2000-06-25', 11, 'TCHAPDA', 'GEFFRED', 'M'),
('1976-06-12', 12, 'GARCIA', 'MANUEL', 'M'),
('1989-04-08', 13, 'PETIT', 'CAMILLE', 'F'),
('1982-09-11', 14, 'THOMAS', 'JEROME', 'M'),
('1981-01-27', 15, 'MARTINEZ', 'LAURENCE', 'F'),
('1983-03-05', 16, 'GIRARD', 'AUDREY', 'F');

-- --------------------------------------------------------

--
-- Structure de la table `seances`
--

CREATE TABLE `seances` (
  `heure_deb` time(6) NOT NULL,
  `heure_fin` time(6) NOT NULL,
  `cours_id` bigint(20) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `prof_id` bigint(20) DEFAULT NULL,
  `jours` enum('Lundi','Mardi','Mercredi','Jeudi','Vendredi') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `seances`
--

INSERT INTO `seances` (`heure_deb`, `heure_fin`, `cours_id`, `id`, `prof_id`, `jours`) VALUES
('13:20:00.000000', '16:45:00.000000', 4, 1, 13, 'Mardi'),
('09:00:00.000000', '12:46:00.000000', 5, 2, 10, 'Vendredi'),
('09:00:00.000000', '15:45:00.000000', 1, 3, 6, 'Mercredi'),
('09:00:00.000000', '15:45:00.000000', 17, 4, 14, 'Lundi'),
('10:00:00.000000', '14:25:00.000000', 6, 5, 11, 'Jeudi'),
('14:00:00.000000', '18:00:00.000000', 19, 6, 4, 'Mardi'),
('12:35:00.000000', '16:45:00.000000', 14, 7, 8, 'Jeudi'),
('13:40:00.000000', '17:40:00.000000', 7, 8, 5, 'Lundi'),
('12:40:00.000000', '15:50:00.000000', 5, 9, 2, 'Mercredi'),
('10:45:00.000000', '16:45:00.000000', 10, 10, 9, 'Mercredi'),
('12:46:00.000000', '17:50:00.000000', 9, 11, 15, 'Vendredi'),
('10:47:00.000000', '16:47:00.000000', 15, 12, 7, 'Lundi'),
('09:47:00.000000', '16:47:00.000000', 12, 13, 13, 'Lundi');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `cours`
--
ALTER TABLE `cours`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_qdcw85uf45i98o4euh4svcevl` (`nom`);

--
-- Index pour la table `professeurs`
--
ALTER TABLE `professeurs`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `seances`
--
ALTER TABLE `seances`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKj9gcar4c2al65bp5okqe6ewvn` (`cours_id`),
  ADD KEY `FKfr38egjeh5vuf1csj857vpi2w` (`prof_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `cours`
--
ALTER TABLE `cours`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT pour la table `professeurs`
--
ALTER TABLE `professeurs`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT pour la table `seances`
--
ALTER TABLE `seances`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `seances`
--
ALTER TABLE `seances`
  ADD CONSTRAINT `FKfr38egjeh5vuf1csj857vpi2w` FOREIGN KEY (`prof_id`) REFERENCES `professeurs` (`id`),
  ADD CONSTRAINT `FKj9gcar4c2al65bp5okqe6ewvn` FOREIGN KEY (`cours_id`) REFERENCES `cours` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
