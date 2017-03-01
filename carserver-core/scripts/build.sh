echo 
echo -----------------------------------------------------------------------------------
echo Cleaning up targets...; rm -rf target/* bin/
echo Now start the real build...
echo 
mvn install;
echo Copying main jar to launcher...; mkdir -p bin/; cp target/car-server-core-1.0-SNAPSHOT.jar bin/; cp target/car-server-core-1.0-SNAPSHOT-jar-with-dependencies.jar bin/
echo Copyng libs...; cp target/lib/ bin/ -r
echo Copying configs...; cp target/configs/ bin/ -r
