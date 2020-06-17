<?php
$koneksi = mysqli_connect 
			(
				"localhost",
				"root",
				"",
				"dbadmin_futsal"
			);
if (mysqli_connect_errno())
	{
		echo "Koneksi Gagal"
		.mysqli_connect_error();
	}
?>
