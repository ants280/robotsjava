<?php
 session_start();
 session_destroy();
 echo "You have been logged out\n<br/>";
 echo '<meta http-equiv="refresh" content="1, url=login.php"/>';
?>
