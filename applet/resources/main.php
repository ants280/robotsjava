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

  <table>
   <tr>
   <td>New High Scores</td>
   <td>News</td>
   </tr>
  </table>

 </body>
</html>
