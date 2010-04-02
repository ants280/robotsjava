<?php include('session.php');
 $username = $_SESSION['username'];
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
 <head>
  <title>Edit profile.</title>
  <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
  <link rel="stylesheet" type="text/css"  href="master.css"/>
 </head>

 <body>
  <div class="header">
   <table>
    <tr>
	 <td>Hello, <?php echo $_SESSION['username']; ?>.</td>
     <td><a href="main.php">Main             </a>    </td>
	 <td><a href="game/robots.php">Game      </a>    </td>
	 <td><a href="highScores.php">High Scores</a>    </td>
     <td><a href="profile.php">View Profile  </a>    </td>
     <td>Donate!                             </a>    </td>
     <td><a href="logout.php">Logout         </a>    </td>
    </tr>
   </table>
  </div>

  <br/>

  <h1>Donate!</h1>

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
    $amount = $_POST['amount'];
    $errors = array();

    //make sure the donation amount is a number and POSITIVE!!!
    if(!is_numeric($amount)) {
     array_push($errors, "Please enter a number!!!.");
    }
    else {
     if($amount <= 0) {
      array_push($errors, "Please enter a POSITIVE amount!");
     }
     else {
      //Rounds the number to  the nearest hundredth.
      if ($amount < .01) {
       array_push($errors, "Please enter a larger amount.");
      }
      else {
       $amount = number_format($amount, 2);
      }
     }
    }

    if(empty($errors)) {
     // Increeases the amount donated.
     $query = sprintf("UPDATE robots SET amount_donated=amount_donated+%s WHERE username='$username'",
         mysql_real_escape_string($amount)); //Just to be safe.
     $result = mysql_query($query);
     if($result) {
      echo "Thanks for donating $$amount!\n<br/>\n<br/>\n";
	 }
    }
    else {
     echo "ERRORS EXIST:";
     foreach ($errors as $error) {
      echo "\n<br/>\n$error";
     }
	 echo "\n<br/>\n<br/>\n";
    }
   }

  //Lists total donated by user.
  $query = "SELECT amount_donated FROM robots WHERE username='$username'";
  $result = mysql_query($query);
  $row = mysql_fetch_array($result, MYSQL_ASSOC);
  echo "You have donated $".number_format($row['amount_donated'], 2)."\n<br/>\n";

  mysql_close($connect);
  ?>

  <form action=<?php echo $_SERVER['PHP_SELF']; ?> method="post">
   <table>
    <tr>
     <td><label for="amount">Amount to donate: $</label></td>
     <td><input type=text" name="amount" maxlength="25"/></td>
    </tr>

    <tr>
     <td/>
     <td><button type="submit" name="Donate!">Change</button></td>
    </tr>

   </table>
  </form>

 </body>
</html>
