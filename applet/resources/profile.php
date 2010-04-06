<?php include('session.php'); 
 include ('databaseLogin.php');
 $username = $_SESSION['username'];
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
 <head>
  <title>Profile</title>
  <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
  <link rel="stylesheet" type="text/css" href="master.css"/>
 </head>

 <body>
  <div class="header">
   <table>
    <tr>
     <td>Hello, <?php echo $_SESSION['username']; ?>.</td>
     <td><a href="main.php">Main             </a>    </td>
     <td><a href="robots.php">Game      </a>    </td>
     <td><a href="highScores.php">High Scores</a>    </td>
     <td>View Profile                                </td>
     <td><a href="donate.php">Donate!        </a>    </td>
     <td><a href="logout.php">Logout         </a>    </td>
    </tr>
   </table>
  </div>

  <br/>

  <h1>Profile</h1>

  <?php

   // Sets user info
   $query = sprintf("SELECT username, firstname, lastname, email, safeTeleports FROM robots WHERE username='%s'",
       mysql_real_escape_string($username));
   $result = mysql_query($query);
   if($result) {
    if($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
     $firstname     = $row['firstname'];
     $lastname      = $row['lastname'];
     $email         = $row['email'];
     $safeTeleports = $row['safeTeleports'];
    }
    else {
     die("Error reading ".$username."'s info");
    }
   }
   else {
    die("Error reading user info");
   }

   // Gets high score info
   $query = sprintf("SELECT score, date FROM highScores WHERE username='%s'",
       mysql_real_escape_string($username));
   $result = mysql_query($query);
   if($result) {

    // Indicates that the player has been playing for over a week
    $oldPlayer = false;
    $total = 0;
	$gamesPlayed = 0;

    $weekAgo = date(U)-7*24*60*60;
    $weekGamesPlayed = 0;
    $weekTotal = 0;

    while($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
	 $gamesPlayed++;
     $total += $row['score'];

     if($row['date'] > $weekAgo) {
      $weekGamesPlayed++;
      $weekTotal += $row['score'];
     }
     else {
      $oldPlayer = true;
     }
    }
	// Round the average (if it is not zero).
    $averageScore     = $gamesPlayed     == 0 ? "NaN" : number_format($total     / $gamesPlayed,     2);
    $weekAverageScore = $weekGamesPlayed == 0 ? "NaN" : number_format($weekTotal / $weekGamesPlayed, 2);
   }
   else {
    die("Error reading user info");
   }
  
  ?>

  <table>
   <tr>
    <td>
     <table>
      <tr><td>Username   </td><td><?php echo $_SESSION['username' ]; ?></td></tr>
      <tr><td>FirstName: </td><td><?php echo $_SESSION['firstname']; ?></td></tr>
      <tr><td>Lastname:  </td><td><?php echo $_SESSION['lastname' ]; ?></td></tr>
      <tr><td>Email:     </td><td><?php echo $_SESSION['email'    ]; ?></td></tr>
       <tr/>
       <tr><td><a href="editProfile.php">Edit Profile</a></td><td/></tr>
      </table>
    </td>
    <td>
	 <table>
	  <?php
       if($oldPlayer) {
        echo "<tr><td>This week: </td></tr>\n";
        echo "<tr><td>Games played: </td><td><?php echo $weekGamesPlayed;  ?></td></tr>\n";
        echo "<tr><td>Average score:</td><td><?php echo $weekAverageScore; ?></td></tr>\n";
        echo "<tr><td>Ever: </td></tr>\n";
       }
      ?>
      <tr><td>Games played: </td><td><?php echo $gamesPlayed;  ?></td></tr>
      <tr><td>Average score:</td><td><?php echo $averageScore; ?></td></tr>
      <tr><td>Total Safe Teleports:</td><td><?php echo $safeTeleports; ?></td></tr>
     </table>
    </td>
   </tr>
  </table>

 </body>
</html>
