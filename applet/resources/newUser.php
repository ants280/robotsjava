<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
 <head>
  <title>Create new User account.</title>
  <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
  <link rel="stylesheet" type="text/css"  href="master.css"/>
  <style type="text/css">
   div.footnote
   {
    color:#FFA500;
	font-size:8;
   }
  </style>
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

    $errors = array();
    /* --- BEGIN CHECK TO MAKE SURE INFO IS OK -- */

    //make sure there is no username the same as the one given.
    $query = sprintf("SELECT username FROM ".$table." WHERE username='%s'",
        mysql_real_escape_string($username));
    $exists = mysql_query($query);
    if(mysql_affected_rows() != 0) {
     array_push($errors, "Username already exists.");
    }
	elseif(preg_match("/\s+/", $username))
    {
     array_push($errors, "No whitespace allowed in username.");
	}

    //make sure the passwords are the same.
    if($password1 != $password2) {
     array_push($errors, "Passwords are not the same.");
    }
	if(strlen($password1) < 8) {
     array_push($errors, "Please choose a password that is 8 letters long.");
	}
	elseif(preg_match("/\s+/", $password1))
    {
     array_push($errors, "No whitespace allowed in password.");
	}

    //make sure the emails are the same.
    if($email1 != $email2) {
     array_push($errors, "Emails are not the same.");
    }
	elseif(preg_match("/\s+/", $email1))
    {
     array_push($errors, "Only 1 email allowed.");
	}
	elseif(!preg_match("/@.*(.)/", $email1))
    {
     array_push($errors, "Please enter a valid email!");
	}

    /* --- END CHECK TO MAKE SURE INFO IS OK --- */

    if(empty($errors)) {
     $query = sprintf("INSERT INTO ".$table." (username, password, email, firstname, lastname) VALUES('%s', '%s', '%s', '%s', '%s')",
         mysql_real_escape_string($username),
         mysql_real_escape_string(md5($password1)), //hashes the password
         mysql_real_escape_string($email1),
         mysql_real_escape_string($firstname),
         mysql_real_escape_string($lastname));

     $inserted = mysql_query($query);
     if($inserted) {
      //mail user info to email
      echo "User added sucessfully, redirecting to the game.\n";

      //'Submits' login information to login.php
      echo '<form action="login.php" method="post" name="login">';
      echo ' <input type="hidden" name="username" value="'.$username.'" />';
      echo ' <input type="hidden" name="password" value="'.$password1.'"/>';
      echo '</form>';
      echo '<script type="text/javascript">';
      echo ' document.login.submit();';
      echo '</script>';
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

  <form action=<?php echo $_SERVER['PHP_SELF']; ?> method="post">
   <table>
    <tr><td><div class="footnote">*</div></td> <td><label for="username" >Username:       </label></td> <td><input type="text"     name="username"   maxlength="25" /></td></tr>
    <tr><td><div class="footnote">*</div></td> <td><label for="password1">Password:       </label></td> <td><input type="password" name="password1"  maxlength="25" /></td></tr>
    <tr><td><div class="footnote">*</div></td> <td><label for="password2">Retype Password:</label></td> <td><input type="password" name="password2"  maxlength="25" /></td></tr>
    <tr></tr>
    <tr><td/>                                  <td><label for="firstname">First Name:     </label></td> <td><input type="text"     name="firstname"  maxlength="25" /></td></tr>
    <tr><td/>                                  <td><label for="lastname" >Last  Name:     </label></td> <td><input type="text"     name="lastname"   maxlength="25" /></td></tr>
    <tr><td><div class="footnote">*</div></td> <td><label for="email1"   >Email:          </label></td> <td><input type="text"     name="email1"     maxlength="128"/></td></tr>
    <tr><td><div class="footnote">*</div></td> <td><label for="email2"   >Retype Email:   </label></td> <td><input type="text"     name="email2"     maxlength="128"/></td></tr>
    <tr><td/> <td><small><div class="footnote">* required field</div></small></td> <td/></tr>
    <tr><td/> <td><button type="submit" name="submit">Create</button></td> <td/></tr>
   </table>
  </form>

 </body>
</html>
