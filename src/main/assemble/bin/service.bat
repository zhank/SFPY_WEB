@echo off
rem ����������ָ��JAVA_HOME���磺set JAVA_HOME="D:\Program Files\Java\jdk1.6.0"
if "%JAVA_HOME%" == "" goto ERROR

if exist "%JAVA_HOME%"\jre\bin\server\jvm.dll (
	set JVMDIR="%JAVA_HOME%"\jre\bin\server
) else (
  if exist "%JAVA_HOME%"\jre\bin\server\jvm.dll (
  	set JVMDIR="%JAVA_HOME%"\bin\server
  ) else (
  	 goto ERROR1
  )	
)
set JSBINDIR=%CD%
set JSEXE=%JSBINDIR%\JavaService.exe
set SSBINDIR=%JSBINDIR%

rem ���÷�����
set SERVICE_NAME=myWeb
rem ����������
set STRAT_CLASS=com.sfpy.main.Main
rem ������־·��
set LOG_PATH=%CD%\logs
rem ����JMX�����������JMX������ֱ������Ϊ set JMX_SETTING=""
rem set JMX_SETTING="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=8004 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
rem ָ�������classpath
set CLASSPATH=.\classes;.\lib\*;.\WebContent\WEB-INF\lib\*
rem 20160309 ����������̫���޷����еĴ���
rem set CLASSPATH=.\classes
rem ͨ��forѭ��libĿ¼�µ�����jar��zip�ļ��������classpath
rem for %%i in (".\lib\*.jar") do call ".\cpappend.bat" %%i
rem for %%i in (".\WebContent\WEB-INF\lib\*.jar") do call ".\cpappend.bat" %%i

:BEGIN
if "%1"=="start" goto START
if "%1"=="console" goto CONSOLE
if "%1"=="debug" goto DEBUG
if "%1"=="stop" goto STOP
if "%1"=="install" goto INSTALL
if "%1"=="uninstall" goto UNINSTALL
if "%1"=="tool" goto TOOL
if "%1"=="tools" goto TOOL

echo "Usage: $0 {start | stop | debug | console | install | uninstall | tool}"
goto END

:START
start java -server -cp %CLASSPATH% %STRAT_CLASS% %1 %2 %3 %4
goto END

:CONSOLE
java -cp %CLASSPATH% %STRAT_CLASS% start %2 %3 %4
goto END

:DEBUG
java -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5888 -cp %CLASSPATH% %STRAT_CLASS% start %2 %3 %4
goto END

:STOP
java -cp %CLASSPATH% %STRAT_CLASS% %1 %2 %3 %4
goto END

:TOOL
java -cp %CLASSPATH% %STRAT_CLASS% %1 %2 %3 %4 %5 %6
goto END

:INSTALL
rem �����־��·�������ڣ��򴴽���־
if not exist "%LOG_PATH%" mkdir "%LOG_PATH%"
@echo . Using following version of JavaService executable:
@echo .
%JSEXE% -version
@echo .
@echo Installing service... Press Control-C to abort
@echo .
%JSEXE% -install %SERVICE_NAME% %JVMDIR%\jvm.dll -Djava.class.path=%CLASSPATH% -Xms256m -Xmx512m -start %STRAT_CLASS% -params start -stop  %STRAT_CLASS% -params stop -out %JSBINDIR%\logs\stdout.log -err %JSBINDIR%\logs\stderr.log -current %JSBINDIR% -auto -description "KoalCA Service"
@echo .
@echo Starting  service... Press Control-C to abort
@echo .
%windir%\system32\net start %SERVICE_NAME%
@echo .
goto END

:UNINSTALL
if not exist "%LOG_PATH%" mkdir "%LOG_PATH%"
@echo . Using following version of JavaService executable:
@echo .
%JSEXE% -version
@echo .
@echo Stopping service... Press Control-C to abort
@echo .
net stop %SERVICE_NAME%
@echo .
@echo Un-installing service... Press Control-C to abort
@echo .
%JSEXE% -uninstall %SERVICE_NAME%
@echo .
@echo End of script
goto END


:ERROR
echo "ϵͳ��û������JAVA_HOME�����ڻ�������������JAVA_HOME����������ļ��Ŀ�ͷ��������......................"
goto END

:ERROR1
echo "����ָ����JAVA_HOME·������ȷ"
goto END

:END
set CLASSPATH=