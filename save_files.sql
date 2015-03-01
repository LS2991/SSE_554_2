-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 01, 2015 at 07:41 PM
-- Server version: 5.6.21
-- PHP Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `Saved Games`
--

-- --------------------------------------------------------

--
-- Table structure for table `save files`
--

CREATE TABLE IF NOT EXISTS `save files` (
  `File Number` int(3) NOT NULL,
  `File Name` text,
  `Save File` mediumtext
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `save files`
--

INSERT INTO `save files` (`File Number`, `File Name`, `Save File`) VALUES
(1, 'Keith Russ', 'save001.txt'),
(2, 'Louis Santiago', 'save002.txt'),
(3, 'Daimen Estevez', 'save003.txt'),
(4, 'Caleb Grimm', 'save004.txt'),
(5, 'Jess McAnally', 'save005.txt');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `save files`
--
ALTER TABLE `save files`
 ADD PRIMARY KEY (`File Number`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
