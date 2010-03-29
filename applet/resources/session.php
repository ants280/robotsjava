<?php
session_start();
 if(!isset($_SESSION['username'])) {
  die('<meta http-equiv="refresh" content="0, url=../denied.php"/>');
 }
?>
