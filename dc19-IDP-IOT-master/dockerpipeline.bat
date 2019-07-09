::builds maven project and pushes to docker hub automatically
::run with dockerpipeline.bat docker-user/docker-repository:tag
::the file has to be in the root folder of the maven repository
::ECHO OFF
SETLOCAL ENABLEDELAYEDEXPANSION
CALL mvn clean
CALL mvn package
CALL mvn install dockerfile:build
SET count=1
FOR /F "tokens=* USEBACKQ" %%F IN (`docker images`) DO (
  SET var!count!=%%F
  SET /a count=!count!+1
)
::ECHO %var2%
FOR /F "tokens=3" %%i in ("%var2%") DO SET dokid=%%i
::ECHO %dokid%
docker tag %dokid% %1
docker push %1
ENDLOCAL