<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
 <head>
  <title>Robots -- Create new User account</title>
  <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
  <link rel="shortcut icon" href="robot.ico"/>
  <link rel="stylesheet" type="text/css" href="master.css"/>
  <link rel="stylesheet" type="text/css" href="form.css"/>
 </head>

 <body>
  <h1>Create Robots account</h1>

  <?php
   if($_POST) {
    include ('databaseLogin.php');

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

    //Variable initialization
    $username  = $_POST['username'];
    $password1 = $_POST['password1'];
    $password2 = $_POST['password2'];
    $question  = $_POST['question'];
    $answer    = $_POST['answer'];
    $firstname = $_POST['firstname'];
    $lastname  = $_POST['lastname'];
    $email1    = $_POST['email1'];
    $email2    = $_POST['email2'];

    $errors = array();
    /* --- BEGIN CHECK TO MAKE SURE INFO IS OK -- */

    //make sure there is no username the same as the one given.
    $query = sprintf("SELECT username FROM robots WHERE username='%s'",
        mysql_real_escape_string($username));
    $exists = mysql_query($query);
    if(mysql_affected_rows() != 0) {
     array_push($errors, "Username already exists.");
    }
    elseif(preg_match("/\s+/", $username)) {
     array_push($errors, "No whitespace allowed in username.");
    }

    //make sure the passwords are the same.
    if($password1 != $password2) {
     array_push($errors, "Passwords are not the same.");
    }
    if(strlen($password1) < 8) {
     array_push($errors, "Please choose a password that is 8 letters long.");
    }
    elseif(preg_match("/\s/", $password1)) {
     array_push($errors, "No whitespace allowed in password.");
    }

    //Make sure the question/answer are not left empty.
    if(empty($question)) {
     array_push($errors, "Please add a security question.");
    }
    if(empty($answer)) {
     array_push($errors, "Please add an answer for the security question.");
    }

    //make sure the emails are the same.
    if($email1 != $email2) {
     array_push($errors, "Emails are not the same.");
    }
    elseif(preg_match("/\s/", $email1)) {
     array_push($errors, "Only 1 email allowed.");
    }
    elseif(!preg_match("/^.*@\w+(.)\w+$/", $email1)) {
     array_push($errors, "Please enter a valid email!");
    }
    else {
     $query = sprintf("SELECT email FROM robots WHERE email='%s'",
         mysql_real_escape_string($email1));
     $exists = mysql_query($query);
     if(mysql_affected_rows() != 0) {
      array_push($errors, "Email already exists.");
     }
    }

    /* --- END CHECK TO MAKE SURE INFO IS OK --- */

    if(empty($errors)) {
     // Mail new user info to $email1
     $subject = "Welcome to Robots Game!";
     $message = "Welcome to Robots Game!\n".
                "Please play at http://lucky.cs.montana.edu/~patterson\n".
                "Your username is \"".$username."\"\n".
                "Like the game?  Donations are always nice!";
     // Additional headers
     $headers = 'From: Robots Java Game <robots.java@gmail.com>';
     //if(mail($email1, $subject, $message, $headers)) {
     if(true) {
      //Create account if email was successfully sent.
      $query = sprintf("INSERT INTO robots (username, password, question, answer, email, firstname, lastname) VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s')",
          mysql_real_escape_string($username),
          md5($password1), //hashes the password
          mysql_real_escape_string($question),
          mysql_real_escape_string(strtoupper($answer)),
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
       echo "ERROR: Unable to add user to system.\n<br/>\n";
      }
     }
     else {
      echo "ERROR: Could not send email to $email1\n<br/>\n";
     }
	}
    else {
     echo "ERRORS EXIST:";
     foreach ($errors as $error) {
      echo "\n<br/>\n$error";
     }
    }

    mysql_close($connect);
   }
  ?>

  <form action=<?php echo $_SERVER['PHP_SELF']; ?> method="post">
   <table>
    <tr><td><div class="footnote">*</div></td> <td><label for="username" >Username:         </label></td> <td><input type="text"     name="username"   maxlength="25" /></td></tr>
    <tr><td><div class="footnote">*</div></td> <td><label for="password1">Password:         </label></td> <td><input type="password" name="password1"  maxlength="25" /></td></tr>
    <tr><td><div class="footnote">*</div></td> <td><label for="password2">Retype Password:  </label></td> <td><input type="password" name="password2"  maxlength="25" /></td></tr>
    <tr><td><div class="footnote">*</div></td> <td><label for="question" >Security Question:</label></td> <td><input type="text"     name="question"   maxlength="50" /></td></tr>
    <tr><td><div class="footnote">*</div></td> <td><label for="answer"   >Answer:     </label></td> <td><input type="text"     name="answer"     maxlength="25" /></td></tr>
    <tr></tr>
    <tr><td/>                                  <td><label for="firstname">First Name:       </label></td> <td><input type="text"     name="firstname"  maxlength="25" /></td></tr>
    <tr><td/>                                  <td><label for="lastname" >Last  Name:       </label></td> <td><input type="text"     name="lastname"   maxlength="25" /></td></tr>
    <tr><td><div class="footnote">*</div></td> <td><label for="email1"   >Email:            </label></td> <td><input type="text"     name="email1"     maxlength="128"/></td></tr>
    <tr><td><div class="footnote">*</div></td> <td><label for="email2"   >Retype Email:     </label></td> <td><input type="text"     name="email2"     maxlength="128"/></td></tr>
    <tr><td/><td><small><div class="footnote">* required field</div></small></td></tr>
    <tr><td/><td/><td><button type="submit" name="submit">Create</button></td></tr>
   </table>
  </form>

  <br/>

  <a href="login.php">&lt;&lt;Back</a>

 </body>
</html>
