@echo off
echo Is this running with administrative priveleges? If yes, press any key. If no, press control c (simultaneously, like you are copying text) then the letter y. Right click this file and click the 'run as administrator option'
pause

echo Installing chocolatey package manager...
rem This line to install chocolatey was taken from the website
@"%SystemRoot%\System32\WindowsPowerShell\v1.0\powershell.exe" -NoProfile -InputFormat None -ExecutionPolicy Bypass -Command "[System.Net.ServicePointManager]::SecurityProtocol = 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))" && SET "PATH=%PATH%;%ALLUSERSPROFILE%\chocolatey\bin"
choco install git -y
choco install wpilib -y
echo Install success!
set /p "name=Your name: "
set /p "email=Your email: "
"C:\Program Files\Git\cmd\git.exe" config --global user.name "%name%"
"C:\Program Files\Git\cmd\git.exe" config --global user.email "%email%"
pause