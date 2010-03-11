<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
 
 <head>
  <title>Example Form/XHTML page</title>
  <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
  <link rel="stylesheet" type="text/css" href="form.css"/>
  <link rel="stylesheet" type="text/css" href="table.css"/>
 </head>
 
 <body>
  <?php
   //Variable initialization
   include ('databaseLogin.php');
   $username = $_POST['username'];
   $password = $_POST['password'];

   echo "<br/>\n";

   //connect to MySQL
   $connect = mysql_connect($db_host, $db_user, $db_pwd);
   if ($connect) { 
	echo "Database connection sucessful!\n";
	echo "<br/>\n";
   }
   else {
    die('Could not make a connection to MySQL:<br>'.mysql_error());
   } 

   //select the database to work with
   $db_selected = mysql_select_db($database, $connect);
   if ($db_selected) {
    //Pass username to game.
    echo "The database username and password are good\n";
	echo "<br/>\n";	
   }
   else {
    die('unable to insert into table:<br>'.mysql_error());
   }

   //make sure the user and the password match.
   $query = "SELECT username, password FROM ".$table." WHERE username='".$username."' and password='".$password."'";
   $exists = mysql_query($query);
   if(mysql_affected_rows() == 0) {
    die('Incorrect Username or password:<br>'.mysql_error());
   }
   else {
    echo "Successful login, redirecting in 3 seconds.\n";
	echo '<meta http-equiv="refresh" content="3, url=game/robots.html"/>';
   }

   mysql_close($connect);
  ?>
 </body>
</html>
