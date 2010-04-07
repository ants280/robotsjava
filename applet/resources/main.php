<?php include('session.php'); ?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
 <head>
  <title>Profile</title>
  <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
  <link rel="stylesheet" type="text/css" href="master.css"/>
  <link rel="stylesheet" type="text/css" href="cellCenter.css"/>
 </head>

 <body>
 
  <h1>Hello, <?php echo $_SESSION['username']; ?>.</h1>

  <table>
   <tr><td><a href="robots.php">Game      </a></td></tr>
   <tr><td><a href="highScores.php">High Scores</a></td></tr>
   <tr><td><a href="profile.php">View Profile  </a></td></tr>
   <tr><td><a href="donate.php">Donate!        </a></td></tr>
   <tr><td><a href="logout.php">Logout         </a></td></tr>
  </table>

  <br/>

  <?php
   include('databaseLogin.php');

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

   $query = sprintf("SELECT username, score, date FROM highScores ORDER BY date DESC");
   $result = mysql_query($query);
   if($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
    // Display different if the score was made today.
    $today = date('d', date(U)) == date('d', $row['date']);
    // Display different if the score was made by the current user.
    echo "<strong>\n";
    echo ($row['username'] == $_SESSION['username']) ? "You" : $row['username'];
    echo $today ? " just" : "";
    echo " scored a ".$row['score'];
    echo $today ? "" : " on ".date('F j, Y');
    echo "\n<br/>\n";
    echo "Can you beat it?\n<br/>\n";
    echo "</strong>\n";
   }
   else {
    echo "Failure to access database";
   }

   mysql_close($connect);
  ?>

 </body>
</html>
