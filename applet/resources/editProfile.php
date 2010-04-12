<?php include('session.php');
 $username = $_SESSION['username'];
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
 <head>
  <title>Robots -- Edit profile</title>
  <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
  <link rel="shortcut icon" href="robot.ico"/>
  <link rel="stylesheet" type="text/css" href="master.css"/>
  <link rel="stylesheet" type="text/css" href="form.css"/>
 </head>

 <body>
  <div class="header">
   <table>
    <tr>
     <td>Hello, <?php echo $_SESSION['username']; ?>.</td>
     <td><a href="main.php">Main             </a>    </td>
     <td><a href="robots.php">Game           </a>    </td>
     <td><a href="highScores.php">High Scores</a>    </td>
     <td><a href="profile.php">View Profile  </a>    </td>
     <td><a href="donate.php">Donate!        </a>    </td>
     <td><a href="logout.php">Logout         </a>    </td>
    </tr>
   </table>
  </div>

  <br/>

  <h1>Edit account for <?php echo $username; ?></h1>

  <p>Enter any account information you wish to change:</p>

  <?php
 
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

   if($_POST) {
    //Variable initialization
    $old_password  = $_POST['old_password'];
    $new_password1 = $_POST['new_password1'];
    $new_password2 = $_POST['new_password2'];
    $new_firstname = $_POST['new_firstname'];
    $new_lastname  = $_POST['new_lastname'];
    $new_email1    = $_POST['new_email1'];
    $new_email2    = $_POST['new_email2'];

    $update_firstname = !empty($new_firstname);
    $update_lastname  = !empty($new_lastname);
    $update_email     = !empty($new_email1)    && !empty($new_email2);
    $update_password  = !empty($new_password1) && !empty($new_password2);

    $errors = array();
    if($update_firstname || $update_lastname || $update_email || $update_password) {
     $query = sprintf("SELECT password FROM robots WHERE username='%s'",
         mysql_real_escape_string($username));

     $result = mysql_query($query);
     if($result) {
      if($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
       if($row['password'] != md5($old_password)) {
         array_push($errors, "Old password is not correct!");
       }
      }
      else {
       array_push($errors, "Cannot access mysql database to make sure the old password was correct!");
      }
     }

     //make sure the passwords are the same.
     if($update_password) {
      if($new_password1 != $new_password2) {
       array_push($errors, "New passwords are not the same.");
      }
      if(strlen($new_password1) < 8) {
       array_push($errors, "Please choose a password that is 8 letters long.");
      }
      elseif(preg_match("/\s/", $new_password1)) {
       array_push($errors, "No whitespace allowed in password.");
      }
     }

     //make sure the emails are the same.
     if($update_email) {
      if($new_email1 != $new_email2) {
       array_push($errors, "New emails are not the same.");
      }
      elseif(preg_match("/\s/", $new_email1)) {
       array_push($errors, "Only 1 email allowed.");
      }
      elseif(!preg_match("/^.*@\w+(.)\w+$/", $new_email1)) {
       array_push($errors, "Please enter a valid email!");
      }
      else {
       $query = sprintf("SELECT username FROM robots WHERE username='%s'",
           mysql_real_escape_string($new_email1));
       $exists = mysql_query($query);
       if(mysql_affected_rows() != 0) {
        array_push($errors, "Username already exists.");
       }
      }
     }
    }
	else {
     // User posted without filling ANYTHING out.
     array_push($errors, "Pick something to change!!!");
    }

    if(empty($errors)) {

     if($update_firstname) {
      $query = sprintf("UPDATE robots SET firstname='%s' WHERE username='$username'",
          mysql_real_escape_string($new_firstname));
      $result = mysql_query($query);
      if($result) {
       echo "First name successfully updated.\n<br>\n";
      }
      else {
       echo "Failed to update first name.\n<br>\n";
      }
     }

     if($update_lastname) {
      $query = sprintf("UPDATE robots SET lastname='%s' WHERE username='$username'",
          mysql_real_escape_string($new_lastname));
      $result = mysql_query($query);
      if($result) {
       echo"Last name successfully updated.\n<br>\n";
      }
      else {
       echo "Failed to update last name.\n<br>\n";
      }
     }

     if($update_email) {
      $query = sprintf("UPDATE robots SET email='%s' WHERE username='$username.",
          mysql_real_escape_string($new_email1));
      $result = mysql_query($query);
      if($result) {
       //TODO: send confirm email to new email?
       echo"Email successfully updated.\n<br>\n";
      }
      else {
       echo "Failed to update email.\n<br>\n";
      }
     }

     if($update_password) {
      $query = sprintf("UPDATE robots SET password='%s' WHERE username='$username'",
          md5($new_password1));
      $result = mysql_query($query);
      if($result) {
       echo"Password successfully updated.\n<br>\n";
      }
      else {
       echo "Failed to update password.\n<br>\n";
      }
     }

	 echo "\n<br>\n";

    }
    else {
     echo "ERRORS EXIST:";
     foreach ($errors as $error) {
      echo "\n<br/>\n$error";
     }
	 echo "\n<br/>\n";
    }
   }
  
   // Sets variable names for table.
   $query = sprintf("SELECT username, firstname, lastname, email FROM robots WHERE username='%s'",
       mysql_real_escape_string($username));
   $result = mysql_query($query);
   if($result) {
    if($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
     $firstname = $row['firstname'];
     $lastname  = $row['lastname'];
     $email     = $row['email'];
    }
    // Still part of if statement: (pure html for easy reading)
  ?>

  <form action=<?php echo $_SERVER['PHP_SELF']; ?> method="post">
   <table>
    
    <tr>
     <td>Current First Name:</td>
     <td/>
     <td><?php echo $firstname; ?></td>
     <td><label for="new_firstname">New First Name:</label></td>
     <td><input type="text" name="new_firstname" maxlength="25"/></td>
    </tr>

    <tr>
     <td>Current Last Name: </td>
     <td/>
     <td><?php echo $lastname; ?></td>
     <td><label for="new_lastname">New Last Name:</label></td>
     <td><input type="text" name="new_lastname" maxlength="25"/></td>
    </tr>

    <tr>
     <td>Current email:</td>
     <td/>
     <td><?php echo $email; ?></td>
     <td><label for="new_email1">New Email:</label></td>
     <td><input type="text" name="new_email1" maxlength="128"/></td>
    </tr>

    <tr>
     <td/>
     <td/>
     <td/>
     <td><label for="new_email2">Retype New Email:</label></td>
     <td><input type="text" name="new_email2" maxlength="128"/></td>
    </tr>

 	<tr>
     <td/>
     <td/>
     <td/>
     <td><label for="new_password1">New Password:</label></td>
     <td><input type="password" name="new_password1" maxlength="25" /></td>
    </tr>

    <tr>
     <td/>
     <td/>
     <td/>
     <td><label for="new_password2">Retype New Password:</label></td>
     <td><input type="password" name="new_password2" maxlength="25"/></td>
    </tr>

    <tr>
     <td/>
     <td><div class="footnote">*</div></td>
     <td><label for="old_password">Old Password:</label></td>
     <td><input type="password" name="old_password" maxlength="25"/></td>
    </tr>

    <tr>
     <td/>
     <td/>
     <td><small><div class="footnote">* required field</div></small></td>
    </tr>

    <tr>
     <td/>
     <td/>
     <td/>
     <td><button type="submit" name="submit">Change</button></td>
    </tr>

   </table>
  </form>

  <?php
   }
   else {
    echo "ERROR: Could not get user info!!!";
   }

   mysql_close($connect);
  ?>

 </body>
</html>
