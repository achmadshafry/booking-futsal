<?php
    include("config.php");
    session_start();

    if(isset($_SESSION['username'])){
        header("Location: admin/index.php");
    }
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Panel Admin Lapangan Futsal</title>

    <!-- icon title -->
    <link rel="icon" type="image/png" href="img/bola.png">

    <!-- bootstrap css -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <link rel="stylesheet" href="css/admin.css">
</head>
<body>
    <!-- <nav class="navbar navbar-expand-lg navbar-light">
        <a href="#" class="navbar-left navbar-brand"><img src="img/bola.png" width="30" height="30" class="d-inline-block align-top mr-1" alt="">Admin Lapangan Futsal</a>
        <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
            <ul class="navbar-nav">
            </ul>
        </div>
    </nav> -->
    <div class="container">
        <!-- <div class="row">
            <div class="col-lg-8 col-md-10 mx-auto"> -->
                <div class="login-form">
                    <div class="main-div">
                        <?php 
                            if(isset($_GET['pesan'])){
                                if($_GET['pesan']=="gagal"){
                                    echo "<div class='alert'>Username dan Password tidak sesuai !</div>";
                                }
                            }
                        ?>

                        <div class="panel">
                            <img src="img/bola.png" width="50" height="50" alt="">
                            <h2>Admin Lapangan Futsal</h2>
                        </div>
                        <form id="Login" method="POST" action="cek_login.php?op=in">
                            <div class="form-group">
                                <input type="text" class="form-control" id="inputEmail" placeholder="Username" name="username">
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control" id="inputPassword" placeholder="Password" name="password">
                            </div>
                            <button type="submit" class="btn btn-primary" name="login" value="login">Login</button>
                        </form>
                    </div>
                </div>
            <!-- </div>
        </div> -->
        
    </div>
</body>
</html>