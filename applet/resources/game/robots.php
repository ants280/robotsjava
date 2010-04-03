<?php include('../session.php'); ?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
 <head>
  <title>Robots</title>
  <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
  <link rel="stylesheet" type="text/css" href="../master.css"/>
  <script language="javascript">
   //Maximizes the screen
   window.moveTo(0,0);
   window.resizeTo(screen.width, screen.height);
  </script>
  <style>
   html
   {
    background-image:url("game_background.png");
    background-repeat:repeat;
   }
   h1
   {
    width:20%;
    background-color:black;
    margin:0 auto;
   }
   table
   {
    background-color:black;
   }
  </style>
 </head>

 <body>
  <div class="header">
   <table>
    <tr>
     <td>Hello, <?php echo $_SESSION['username']; ?>.</td>
     <td><a href="../main.php">Main             </a> </td>
     <td>Game                                        </td>
     <td><a href="../highScores.php">High Scores</a> </td>
     <td><a href="../profile.php">View Profile  </a> </td>
     <td><a href="../donate.php">Donate!        </a> </td>
     <td><a href="../logout.php">Logout         </a> </td>
    </tr>
   </table>
  </div>

  <br/>

  <h1>Robots</h1>

  <br/>

  <applet code="com.ants280.robots.Gui" archive="Robots.jar" height="610" width="788">
  <br/>

   <param name="username" value='<?php echo $_SESSION['username']; ?>'/>
   <strong>ERROR: NO JAVA PLUGIN DETECTED!</strong>
   <br/>
   Click <a href="http://www.java.com/getjava/">here</a> to get java! (required to run game)
  </applet>

  <br/>

  <table>

   <tr>
    <td><a href="Robots.jar">Get source</a></td>
    <td><a href= "GPL.txt">License</a></td>
   </tr>

   <tr>
    <td><a href="README.txt">Readme</a></td>
   </tr>

  </table>

 </body>
</html>
