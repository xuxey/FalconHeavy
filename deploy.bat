call echo running Falcon build routine
call del "D:\Java Workspace\FalconHeavy\build\libs\falcon.jar"
call gradle build
call gradle jar
call rename "D:\Java Workspace\FalconHeavy\build\libs\FalconHeavy-1.0-SNAPSHOT-all.jar" "falcon.jar"
call echo starting SCP upload
call scp "D:\Java Workspace\FalconHeavy\build\libs\falcon.jar" xuxe@40.77.111.129:/home/xuxe/falcon
echo Finished Falcon build routine