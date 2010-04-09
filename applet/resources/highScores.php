<?php include('session.php'); ?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
 <head>
  <title>HighScores.</title>
  <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
  <link rel="shortcut icon" href="robot.ico"/>
  <link rel="stylesheet" type="text/css" href="master.css"/>
  <link rel="stylesheet" type="text/css" href="cellCenter.css"/>
 </head>

 <body>
  <div class="header">
   <table>
    <tr>
     <td>Hello, <?php echo $_SESSION['username']; ?>.</td>
     <td><a href="main.php">Main             </a>    </td>
     <td><a href="robots.php">Game      </a>    </td>
     <td>High Scores                                 </td>
     <td><a href="profile.php">View Profile  </a>    </td>
     <td><a href="donate.php">Donate!        </a>    </td>
     <td><a href="logout.php">Logout         </a>    </td>
    </tr>
   </table>
  </div>

  <br/>

  <h1>High scores</h1>

  <table>
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

    $username = $_SESSION['username'];

    echo "<tr>";
    echo "<td/><td><strong>$username</strong></td>";
    echo "<td width=100></td>";
    echo "<td/><td><strong>    Global   </strong></td><td>";
    echo "</tr>\n";

    echo "<tr>";
    echo "<td>Score:</td><td>Date:</td>";
    echo "<td/>";
    echo "<td>User:</td><td>Score:</td><td>Date:</td>";
    echo "</tr>\n";

    $global_query = sprintf("SELECT username, score, date FROM highScores                     ORDER BY score DESC");
    $user_query   = sprintf("SELECT username, score, date FROM highScores WHERE username='%s' ORDER BY score DESC",
        mysql_real_escape_string($username));

    $global_result = mysql_query($global_query);
    $user_result   = mysql_query($user_query);

    if($global_result || $user_result) {
     for($i = 1; $i <= 5; $i++) {
      echo "<tr>";

      if($user_row   = mysql_fetch_array($user_result,   MYSQL_ASSOC)) {
       //Put the date in seconds.
       $user_date = $user_row['date'];
       echo "<td>".$user_row['score']."</td>";
       echo "<td>".date('M d Y', $user_date)."</td>";
      }
      else {
       echo "<td/><td/>";
      }

      echo "<td/>";

      if($global_row = mysql_fetch_array($global_result, MYSQL_ASSOC)) {
       //Put the date in seconds
       $global_date = $global_row['date'];
       echo "<td>".$global_row['username']."</td>";
       echo "<td>".$global_row['score']   ."</td>";
       echo "<td>".date('M d Y', $global_date)."</td>";
      }
      else {
       echo "<td/><td/><td/>";
      }
      echo "</tr>\n";
     }
    }

    mysql_close($connect);
   ?>
  </table>

 </body>
</html>
