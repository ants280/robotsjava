<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
 <head>
  <title>Create new User account.</title>
  <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
  <link rel="stylesheet" type="text/css"  href="master.css"/>
 </head>

 <body>

  <h1>Create Robots account</h1>

  <?php
   if($_POST) {
    //Variable initialization
    include ('databaseLogin.php');
    $username  = $_POST['username'];
    $password1 = $_POST['password1'];
    $password2 = $_POST['password2'];
    $firstname = $_POST['firstname'];
    $lastname  = $_POST['lastname'];
    $email1    = $_POST['email1'];
    $email2    = $_POST['email2'];

    echo "<br/>\n";
 
    //connect to MySQL
    $connect = mysql_connect($db_host, $db_user, $db_pwd);
    if($connect) {
     echo "Database connection sucessful!\n";
     echo "<br/>\n";
    }
    else {
     die('Could not make a connection to MySQL:<br/>'.mysql_error());
    }

    //select the database to work with
    $db_selected = mysql_select_db($database, $connect);
    if($db_selected) {
     //Pass username to game.
     echo "The database username and password are good\n";
     echo "<br/>\n<br/>\n";
    }
    else {
     die('unable to insert into table:<br/>'.mysql_error());
    }

    $errors = array();
    /* --- BEGIN CHECK TO MAKE SURE INFO IS OK -- */

    //make sure there is no username the same as the one given.
    $query = "SELECT username FROM ".$table." WHERE username='".$username."'";
    $exists = mysql_query($query);
    if(mysql_affected_rows() != 0) {
     array_push($errors, "Username already exists.");
    }

    //make sure the passwords are the same.
    if($password1 != $password2) {
     array_push($errors, "Passwords are not the same.");
    }

    //TODO: make sure firstname and lastname are mysql-safe.

    //make sure the emails are the same.
    if($email1 != $email2) {
     array_push($errors, "Emails are not the same.");
    }

    /* --- END CHECK TO MAKE SURE INFO IS OK --- */

    if(empty($errors)) {
     $query = "INSERT INTO ".$table." (username, password, email, firstname, lastname) VALUES('".$username."', '".$password1."', '".$email1."', '".$firstname."', '".$lastname."');";
     $inserted = mysql_query($query);
     if($inserted) {
      //mail user info to email
      echo "User added sucessfully, redirecting to the game.\n<br/>\n";
      //echo '<meta http-equiv="refresh" content="3, url=game/robots.html"/>';:
     }
     else {
      echo "ERROR: Unable to add user to system.\n";
     }
    }
    else {
     echo "ERRORS EXIST:";
     foreach ($errors as $error) {
      echo "\n<br/>\n".$error;
     }
    }

    mysql_close($connect);
   }
  ?>

  <form action=<?php echo $_SEVRER['PHP_SELF']; ?> method="post">
   <table>
    <tr><td><label for="username" >Username:       </label></td> <td><input type="text"     name="username"   maxlength="25" /></td></tr> <br/>
    <tr><td><label for="password1">Password:       </label></td> <td><input type="password" name="password1"  maxlength="25" /></td></tr> <br/>
    <tr><td><label for="password2">Retype Password:</label></td> <td><input type="password" name="password2"  maxlength="25" /></td></tr> <br/>
    <tr><td><br/></td></tr>
    <tr><td><label for="firstname">First Name:     </label></td> <td><input type="text"     name="firstname"  maxlength="25" /></td></tr> <br/>
    <tr><td><label for="lastname" >Last  Name:     </label></td> <td><input type="text"     name="lastname"   maxlength="25" /></td></tr> <br/>
    <tr><td><label for="email1"   >Email:          </label></td> <td><input type="text"     name="email1"     maxlength="128"/></td></tr> <br/>
    <tr><td><label for="email2"   >Retype Email:   </label></td> <td><input type="text"     name="email2"     maxlength="128"/></td></tr> <br/>
    <tr><td/><td><button type="submit" name="submit">Login</button></td></tr>
   </table>
  </form>

 </body>
</html>
