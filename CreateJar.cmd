del .\Robots.jar
md build
del build\*.class
javac.exe -d build Program.java
jar.exe cfm Robots.jar build\manifest.txt build\*.class
pause
