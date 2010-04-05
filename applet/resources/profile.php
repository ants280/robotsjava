<?php include('session.php'); 
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
								   
   // Sets user info
   $query = sprintf("SELECT username, firstname, lastname, email FROM robots WHERE username='%s'",
       mysql_real_escape_string($username));
   $result = mysql_query($query);
   if($result) {
    if($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
     $firstname = $row['firstname'];
     $lastname  = $row['lastname'];
     $email     = $row['email'];
    }
    else {
     die("Error reading ".$username."'s info");
    }
   }
   else {
    die("Error reading user info");
   }

   // Gets high score info
   $query = sprintf("SELECT score FROM highScores WHERE username='%s'",
       mysql_real_escape_string($username));
   $result = mysql_query($query);
   if($result) {
    $total = 0;
	$gamesPlayed = 0;
    while($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
	 $gamesPlayed++;
     $total += $row['score'];
    }
	// Round the average
    $averageScore = number_format($total / $gamesPlayed, 2);
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
     <tr><td>Games played: </td><td><?php echo $gamesPlayed;  ?></td></tr>
     <tr><td>Average score:</td><td><?php echo $averageScore; ?></td></tr>
     </table>
    </td>
   </tr>
  </table>

 </body>
</html>
