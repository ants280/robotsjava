<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
 <head>
  <title>Login to Robots</title>
  <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
  <link rel="stylesheet" type="text/css"  href="master.css"/>
 </head>

 <body>

  <h1>Email lost password</h1>
 <?php
   if($_POST) {
    //Variable initialization
    include ('databaseLogin.php');
    $username = $_POST['username'];
    $email = $_POST['email'];
 
    echo "<br/>\n";

    //connect to MySQL
    $connect = mysql_connect($db_host, $db_user, $db_pwd);
    if(!$connect) { 
     die("Could not make a connection to MySQL:\n<br>\n".mysql_error());
    } 

    //select the database to work with
    $db_selected = mysql_select_db($database, $connect);
    if(!$db_selected) {
     die("Unable to select database:\n<br/>\n".mysql_error());
    }

    //make sure the user and the password match.
    $query = "SELECT username, email FROM ".$table." WHERE email='".$email."'";
    $exists = mysql_query($query);
    if(mysql_affected_rows() == 0) {
     echo 'Incorrect email:<br/>'.mysql_error()."\n";
    }
    else {
     echo "Mail usename to ".$email."\n";
    }

    mysql_close($connect);
   }
  ?>

  <form action=<?php echo $_SERVER['PHP_SELF']; ?> method="post"> 
	<table>
     <tr><td><label for="email"   >Email   :</label></td> <td><input type="text" name="email"    maxlength="128"/></td></tr> <br/>
     <tr><td/><td><button type="submit" name="submit">Email</button></td></tr>
   </table>
  </form>

 </body>
</html>
