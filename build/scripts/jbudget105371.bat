@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  jbudget105371 startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and JBUDGET105371_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\jbudget105371-0.2.jar;%APP_HOME%\lib\fontawesomefx-8.9.jar;%APP_HOME%\lib\javafx-fxml-14-win.jar;%APP_HOME%\lib\javafx-controls-14-win.jar;%APP_HOME%\lib\javafx-controls-14.jar;%APP_HOME%\lib\javafx-graphics-14-win.jar;%APP_HOME%\lib\javafx-graphics-14.jar;%APP_HOME%\lib\javafx-base-14-win.jar;%APP_HOME%\lib\javafx-base-14.jar;%APP_HOME%\lib\gson-2.8.6.jar

@rem Execute jbudget105371
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %JBUDGET105371_OPTS%  -classpath "%CLASSPATH%" it.unicam.cs.pa.jbudget105371.AppFX %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable JBUDGET105371_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%JBUDGET105371_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega