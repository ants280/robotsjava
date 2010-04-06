<?php
 //Variable initialization
 $db_host='localhost';
 $db_user='patterson';
 $db_pwd='patterson';
 $database='patterson';

 //connect to MySQL
 $connect = mysql_connect($db_host, $db_user, $db_pwd);
 if(!$connect) {
  die("Could not make a connection to MySQL:\n<br/>\n".mysql_error());
 }

 //select the database to work with
 $db_selected = mysql_select_db($database, $connect);
 if(!$db_selected) {
  die("Unable to select database:\n<br/>\n".mysql_error());
 }
?>
