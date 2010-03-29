<?php
/**
 * This file is a header for navigation throughout the game pages once a user is logged in.
 */
 
 echo "<table>\n<tr>\n";
 echo "<td>Hello, ".$_SESSION['username'].".</td>\n";
 echo "<td><a href=\"../logout.php\">Logout</a></td>\n";
 echo "\n</tr></table>\n";
?>
