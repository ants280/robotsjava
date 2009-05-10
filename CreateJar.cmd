del .\Robots.jar
del .\*.class
javac.exe Program.java
jar.exe cfm Robots.jar manifest.txt *.class
pause
