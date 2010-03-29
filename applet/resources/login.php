<? session_start(); ?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
 <head>
  <title>Login to Robots</title>
  <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
  <link rel="stylesheet" type="text/css"  href="master.css"/>
 </head>

 <body>

  <h1>Login to Robots</h1>

  <?php
   if($_POST) {
    //Variable initialization
    include ('databaseLogin.php');
    $username = $_POST['username'];
    $password = md5($_POST['password']);

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

    //make sure the user and the password match.
    $query = "SELECT username, password FROM ".$table." WHERE username='".$username."' and password='".$password."'";
    $exists = mysql_query($query);
    if(mysql_affected_rows() == 0) {
     echo "Incorrect Username or password.\n";
    }
    else {
     echo "Successful login, redirecting to Robots game.\n";
     $_SESSION['username']=$username;
     echo '<meta http-equiv="refresh" content="0, url=game/robots.php"/>';
    }

    mysql_close($connect);
   }
  ?>

  <form action=<?php echo $_SERVER['PHP_SELF']; ?> method="post"> 
   <table>
    <tr><td><label for="username">Username:</label></td> <td><input type="text"     name="username" maxlength="25"/></td></tr>
<tr><td><label for="password">Password:</label></td> <td><input type="password" name="password" maxlength="25"/></td></tr>
    <tr><td/><td><button type="submit" name="submit">Login</button></td></tr>
   </table>
  </form>

  <table>
   <tr>
    <td><a href="newUser.php">New User</a></td>
    <td>&nbsp;</td>
    <td><a href="forgotPassword.php">Forgot Password?</a></td>
   </tr>
  </table>

 </body>
</html>
