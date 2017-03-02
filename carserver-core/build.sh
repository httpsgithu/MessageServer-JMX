echo 
echo -----------------------------------------------------------------------------------
echo Cleaning up targets...; rm -rf target/*
echo Now start the real build...
echo 
mvn install -DskipTests;
