<?php
 session_start();

 //Make sure the user has logged in.
 if(empty($_SESSION['username'])) {
  die('<meta http-equiv="refresh" content="0, url="denied.php"/>');
 }
?>
