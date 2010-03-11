<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
 <head>
  <title>Login to Robots</title>
  <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
  <link rel="stylesheet" type="text/css"  href="master.css"/>
 </head>

 <body>
  Login to Robots!
  <form action=login.php method="post">

   <label for="username">Username:</label>
   <input type="text" name="username" maxlength="25"/>
   <br/>

   <lable for="password">Password:</label>
   <input type="text" name="password" maxlength="25"/>
   <br/>

   <button type="submit" name="submit">Login</button>

  </form>

  <table>
   <tr>
    <td><a href="newUser.php">New User</a></td>
    <td>&nbsp;</td>
    <td><a href="forgotPassword.php">Forgot Password?</a></td>
   </tr>
  <table>
 </body>
</html>
