<?php 
// mengaktifkan session pada php
session_start();
 
// menghubungkan php dengan koneksi database
include 'config.php';
 
// menangkap data yang dikirim dari form login
$username = $_POST['username'];
$password = $_POST['password'];
$op = $_GET['op'];
 
 
// menyeleksi data user dengan username dan password yang sesuai
$login = mysqli_query($koneksi,"select * from user_admin where username='$username' and password='$password'");
// menghitung jumlah data yang ditemukan
$cek = mysqli_num_rows($login);
 
if($op=="in"){
// cek apakah username dan password di temukan pada database
		if($cek > 0){
 
		$data = mysqli_fetch_assoc($login);
 
		// cek jika user login sebagai admin
		if($data['level']=="admin"){
 
			// buat session login dan username
			$_SESSION['username'] = $username;
			$_SESSION['level'] = "admin";
			// alihkan ke halaman dashboard admin
			header("location:admin/index.php");
		}
		// cek jika user login sebagai pegawai
		// }else if($data['level']=="pegawai"){
		// 	// buat session login dan username
		// 	$_SESSION['username'] = $username;
		// 	$_SESSION['level'] = "pegawai";
		// 	// alihkan ke halaman dashboard pegawai
		// 	header("location:halaman_pegawai.php");
	
		// // cek jika user login sebagai pengurus
		// }else if($data['level']=="pengurus"){
		// 	// buat session login dan username
		// 	$_SESSION['username'] = $username;
		// 	$_SESSION['level'] = "pengurus";
		// 	// alihkan ke halaman dashboard pengurus
		// 	header("location:halaman_pengurus.php");
	
		else{
	
			// alihkan ke halaman login kembali
			header("location:login_admin.php?pesan=gagal");
		}	
	}else{
		header("location:login_admin.php?pesan=gagal");
		}
	}
else if($op=="out"){
		unset($_SESSION['username']);
		unset($_SESSION['level']);
		header("location:login_admin.php");
		}

 
?>