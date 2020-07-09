-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 09, 2020 at 06:11 AM
-- Server version: 10.1.40-MariaDB
-- PHP Version: 7.3.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dbadmin_futsal`
--

-- --------------------------------------------------------

--
-- Table structure for table `hubungi_kami`
--

CREATE TABLE `hubungi_kami` (
  `no` int(11) NOT NULL,
  `nama` varchar(99) NOT NULL,
  `email` varchar(99) NOT NULL,
  `subjek` varchar(9999) NOT NULL,
  `pesan` varchar(9999) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hubungi_kami`
--

INSERT INTO `hubungi_kami` (`no`, `nama`, `email`, `subjek`, `pesan`) VALUES
(1, 'har', 'ha@gmail.com', 'asdasdaisd', 'asdkasdadads');

-- --------------------------------------------------------

--
-- Table structure for table `kelola_user`
--

CREATE TABLE `kelola_user` (
  `no` int(99) NOT NULL,
  `id` int(99) NOT NULL,
  `username` varchar(99) NOT NULL,
  `nama` varchar(99) NOT NULL,
  `no_hp` int(99) NOT NULL,
  `alamat` varchar(99) NOT NULL,
  `email` varchar(99) NOT NULL,
  `status` varchar(99) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `lapangan`
--

CREATE TABLE `lapangan` (
  `no` int(99) NOT NULL,
  `idlap` int(99) NOT NULL,
  `nama_pemesan` varchar(99) NOT NULL,
  `status` varchar(99) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `proses_transaksi`
--

CREATE TABLE `proses_transaksi` (
  `no` int(99) NOT NULL,
  `id_transaksi` varchar(99) NOT NULL,
  `nama` varchar(99) NOT NULL,
  `nohp` int(99) NOT NULL,
  `tanggal` varchar(99) NOT NULL,
  `lapangan` varchar(99) NOT NULL,
  `mulai` varchar(1000) NOT NULL,
  `akhir` varchar(1000) NOT NULL,
  `durasi` varchar(100) NOT NULL,
  `totalbayar` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `riwayat_trans`
--

CREATE TABLE `riwayat_trans` (
  `no` int(99) NOT NULL,
  `id_trans` int(99) NOT NULL,
  `nama_pemesan` varchar(99) NOT NULL,
  `no_hp` int(99) NOT NULL,
  `lapangan` varchar(99) NOT NULL,
  `tanggal` varchar(99) NOT NULL,
  `waktu` varchar(99) NOT NULL,
  `total` int(99) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user_admin`
--

CREATE TABLE `user_admin` (
  `id` int(99) NOT NULL,
  `username` varchar(99) NOT NULL,
  `password` varchar(99) NOT NULL,
  `level` varchar(99) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_admin`
--

INSERT INTO `user_admin` (`id`, `username`, `password`, `level`) VALUES
(1, 'admin', 'admin123', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `hubungi_kami`
--
ALTER TABLE `hubungi_kami`
  ADD PRIMARY KEY (`no`);

--
-- Indexes for table `kelola_user`
--
ALTER TABLE `kelola_user`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `lapangan`
--
ALTER TABLE `lapangan`
  ADD PRIMARY KEY (`idlap`);

--
-- Indexes for table `proses_transaksi`
--
ALTER TABLE `proses_transaksi`
  ADD PRIMARY KEY (`id_transaksi`);

--
-- Indexes for table `riwayat_trans`
--
ALTER TABLE `riwayat_trans`
  ADD PRIMARY KEY (`no`);

--
-- Indexes for table `user_admin`
--
ALTER TABLE `user_admin`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `hubungi_kami`
--
ALTER TABLE `hubungi_kami`
  MODIFY `no` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `riwayat_trans`
--
ALTER TABLE `riwayat_trans`
  MODIFY `no` int(99) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user_admin`
--
ALTER TABLE `user_admin`
  MODIFY `id` int(99) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;