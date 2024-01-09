-- phpMyAdmin SQL Dump
-- version 4.7.1
-- https://www.phpmyadmin.net/
--
-- Host: sql12.freesqldatabase.com
-- Generation Time: Jan 09, 2024 at 09:53 PM
-- Server version: 5.5.62-0ubuntu0.14.04.1
-- PHP Version: 7.0.33-0ubuntu0.16.04.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sql12654012`
--

-- --------------------------------------------------------

--
-- Table structure for table `Product`
--

CREATE TABLE `Product` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `category` varchar(12) NOT NULL,
  `price` float NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Product`
--

INSERT INTO `Product` (`id`, `name`, `category`, `price`, `quantity`) VALUES
(1, 'Spoiler', 'exterior', 120.5, 10),
(2, 'steering-cover', 'interior', 39.99, 30),
(3, 'reverse camera', 'electronics', 40, 8);

-- --------------------------------------------------------

--
-- Table structure for table `Profile`
--

CREATE TABLE `Profile` (
  `name` varchar(20) NOT NULL,
  `phoneNumber` varchar(13) NOT NULL,
  `location` varchar(50) NOT NULL,
  `profileId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Profile`
--

INSERT INTO `Profile` (`name`, `phoneNumber`, `location`, `profileId`) VALUES
('Shehab Kharaz', '+972592459191', 'Nablus- bait wazan -near  ', 0),
('valid', 'number', 'location', 1),
('ali', '08765', 'nablus', 3),
('shehab', '00', 'nablus', 72),
('ss', '000', 'ss', 73);

-- --------------------------------------------------------

--
-- Table structure for table `Request`
--

CREATE TABLE `Request` (
  `id` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  `userId` varchar(30) NOT NULL,
  `date` datetime DEFAULT NULL,
  `description` varchar(30) NOT NULL,
  `done` tinyint(1) NOT NULL,
  `selected` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Request`
--

INSERT INTO `Request` (`id`, `productId`, `userId`, `date`, `description`, `done`, `selected`) VALUES
(1, 1, 'asd@gmail.com', '2024-01-06 15:52:48', 'on time pleasee!', 1, 1),
(2, 2, 's12027670@stu.najah.edu', '2023-12-27 05:05:11', 'secondRequest', 1, 1),
(5, 2, 's12027747@stu.najah.edu', '2023-12-25 09:34:56', 'secondRequest', 0, 0),
(6, 1, 'asd@gmail.com', '2023-12-30 17:57:09', 'important!', 0, 1),
(8, 3, 'asd@gmail.com', '2023-12-29 23:17:41', 'importanat', 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `email` varchar(30) NOT NULL,
  `password` varchar(20) NOT NULL,
  `role` char(1) NOT NULL,
  `profileId` int(11) NOT NULL,
  `signInStatus` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`email`, `password`, `role`, `profileId`, `signInStatus`) VALUES
('asd@gmail.com', '132', 'u', 3, 1),
('s12027670@stu.najah.edu', '12345', 'i', 1, 1),
('s12027747@stu.najah.edu', '12345', 'a', 0, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Product`
--
ALTER TABLE `Product`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `Profile`
--
ALTER TABLE `Profile`
  ADD PRIMARY KEY (`profileId`);

--
-- Indexes for table `Request`
--
ALTER TABLE `Request`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Foreign key` (`productId`),
  ADD KEY `User foreign key` (`userId`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`email`),
  ADD KEY `profileId` (`profileId`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Request`
--
ALTER TABLE `Request`
  ADD CONSTRAINT `Foreign key` FOREIGN KEY (`productId`) REFERENCES `Product` (`id`),
  ADD CONSTRAINT `User foreign key` FOREIGN KEY (`userId`) REFERENCES `user` (`email`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`profileId`) REFERENCES `Profile` (`profileId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
