<?php
include('koneksi.php');
require_once("dompdf/autoload.inc.php");
use Dompdf\Dompdf;
$dompdf = new Dompdf();
$query = mysqli_query($koneksi,"select * from riwayat_trans");
$html = '<center><h3>Daftar Riwayat Sewa Lapangan</h3></center><hr/><br/>';
$html .= '<table border="1" width="100%">
        <tr>
            <th>No</th>
            <th>ID Transaksi</th>
            <th>Nama Pemesan</th>
            <th>No HP</th>
            <th>Lapangan</th>
            <th>Tanggal</th>
            <th>Waktu</th>
            <th>Total</th>
        </tr>';
$no = 1;
while($row = mysqli_fetch_array($query))
{
    $html .= "<tr>
        <td>".$no."</td>
        <td>".$row['id_trans']."</td>
        <td>".$row['nama_pemesan']."</td>
        <td>".$row['no_hp']."</td>
        <td>".$row['lapangan']."</td>
        <td>".$row['tanggal']."</td>
        <td>".$row['waktu']."</td>
        <td>".$row['total']."</td>
    </tr>";
    $no++;
}
$html .= "</html>";
$dompdf->loadHtml($html);
// Setting ukuran dan orientasi kertas
$dompdf->setPaper('A4', 'potrait');
// Rendering dari HTML Ke PDF
$dompdf->render();
// Melakukan output file Pdf
$dompdf->stream('laporan_sewa.pdf');
?>