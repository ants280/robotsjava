del .\Robots.jar
del .\*.class
..\java\bin\javac.exe Program.java
..\java\bin\jar.exe cfm Robots.jar manifest.txt *.class
pause
