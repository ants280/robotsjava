<? session_start(); ?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
 <head>
  <title>Logout Sucessful</title>
  <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
  <link rel="stylesheet" type="text/css"  href="master.css"/>
 </head>

 <body>
  <?php
   session_destroy();
   echo "<strong>You have been logged out</strong>\n<br/>\n";
   echo '<meta http-equiv="refresh" content="1, url=login.php"/>';
  ?>
 </body>
</html>
