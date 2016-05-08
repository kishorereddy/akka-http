
set APP_HOME=C:\Dev\github\akka-http
set APP_BUILD=%APP_HOME%\build
set APP_DIST=%APP_HOME%\dist
set APP_TEMP=%APP_DIST%\temp
set APP_DEST=%APP_DIST%\dest
set APP_JAR=slate.jar
set APP_TARG=%APP_HOME%\target\scala-2.11\classes
c:
cd %APP_HOME%

REM Delete the classes 
del del /f /s /q %APP_TEMP%
rmdir /s /q %APP_TEMP%

REM Copy classes over from target to dist\temp
xcopy /e /v /y %APP_TARG% %APP_TEMP%\

REM move to temp and jar all the files with custom manifest
cd %APP_TEMP%
jar -cvfm %APP_JAR% %APP_BUILD%\manifest.txt *

REM move the the slate.jar to dest dir
move %APP_JAR% %APP_DEST%\%APP_JAR%

cd %APP_HOME%

REM C:\Dev\Tests\akka-http\dist\classes
REM C:\Dev\Tests\akka-http\dist\lib\akka
REM C:\Dev\Tests\akka-http\dist\lib\scala-library.jar
REM C:\Dev\Tests\akka-http\dist\lib\akka\akka-actor_2.11-2.3.11.jar
REM C:\Dev\Tests\akka-http\dist\lib\akka\akka-http-core-experimental_2.11-1.0-RC4.jar
REM C:\Dev\Tests\akka-http\dist\lib\akka\akka-http-experimental_2.11-1.0-RC4.jar
REM C:\Dev\Tests\akka-http\dist\lib\akka\akka-http-spray-json-experimental_2.11-1.0-RC4.jar
REM C:\Dev\Tests\akka-http\dist\lib\akka\akka-http-testkit-experimental_2.11-1.0-RC4.jar
REM C:\Dev\Tests\akka-http\dist\lib\akka\akka-parsing-experimental_2.11-1.0-RC4.jar
REM C:\Dev\Tests\akka-http\dist\lib\akka\akka-stream-experimental_2.11-1.0-RC4.jar
REM C:\Dev\Tests\akka-http\dist\lib\akka\akka-testkit_2.11-2.3.11.jar
REM C:\Dev\Tests\akka-http\dist\lib\akka\config-1.2.1.jar
REM C:\Dev\Tests\akka-http\dist\lib\akka\jline-2.12.1.jar
REM C:\Dev\Tests\akka-http\dist\lib\akka\reactive-streams-1.0.0.jar
REM C:\Dev\Tests\akka-http\dist\lib\akka\scala-reflect.jar
REM C:\Dev\Tests\akka-http\dist\lib\akka\scala-xml_2.11-1.0.4.jar
REM C:\Dev\Tests\akka-http\dist\lib\akka\slate-http-restapi_2.11-0.0.1.jar
REM C:\Dev\Tests\akka-http\dist\lib\akka\spray-json_2.11-1.3.1.jar
:exit
echo done!