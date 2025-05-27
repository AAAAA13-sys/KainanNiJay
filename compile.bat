@echo off
cls
echo Compiling Java files...
javac Item.java Main.java Menu.java Order.java TTL.java Drink.java Food.java

cls
java Main

echo.
echo.
echo Program finished. Press any key to close this window...
pause >nul
