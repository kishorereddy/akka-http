
set SLATE_HOME=C:\Dev\Tests\akka-http\dist
echo %SLATE_HOME%


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


set SLATE_CLASSPATH=%SLATE_HOME%\classes;%SLATE_HOME%\lib\*;%SLATE_HOME%\lib\akka\*
java -cp "%SLATE_CLASSPATH%" slate.app.WebApp
