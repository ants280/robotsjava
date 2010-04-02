<?php include('session.php'); ?>

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
     <td><a href="game/robots.php">Game      </a>    </td>
     <td><a href="highScores.php">High Scores</a>    </td>
     <td>View Profile                                </td>
     <td><a href="donate.php">Donate!        </a>    </td>
     <td><a href="logout.php">Logout         </a>    </td>
    </tr>
   </table>
  </div>

  <br/>

  <h1>Profile</h1>

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
     Statistics: <strong>COMING SOON!</strong>
    </td>
   </tr>
  </table>

 </body>
</html>
