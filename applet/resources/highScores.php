<? session_start(); ?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
 <head>
  <title>High Scores</title>
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
     <td>High Scores                         </a>    </td>
     <td><a href="profile.php">View Profile  </a>    </td>
     <td><a href="logout.php">Logout         </a>    </td>
    </tr>
   </table>
  </div>

  <strong>COMING SOON!</strong>
 </body>
</html>
