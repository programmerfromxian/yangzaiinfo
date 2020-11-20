set CLASSPATH=%cd%\software\jre\bin\java
set DATE_FILE_DIR=%cd%
%CLASSPATH -jar back-1.0-SNAPSHOT.jar% -Ddate_file_dir=%DATE_FILE_DIR%