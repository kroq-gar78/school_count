@echo off
echo creating JAR for SchoolCountdown...
jar cfm SchoolCountdown.jar Manifest2.txt *.class schoolCountdown.png help.txt
echo JAR finished
pause