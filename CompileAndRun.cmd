md build
del build\*.class
copy *.jpg build
javac.exe -d build Program.java
cd build
java.exe Program
pause