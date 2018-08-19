rm -rf Build
mkdir -p Build
echo "Building Game..."
javac Model/**.java View/**.java -d Build &&
cp Datafiles/** Build &&
cp Images/** Build &&
cd Build && 
jar cvfe Hero.jar Main * &&
chmod +x Hero.jar &&
mkdir -p ../Output &&
mv Hero.jar ../Output &&
echo "Building Editor..." &&
javac -cp . ../Editor/**.java -d . &&
jar cvfe Editor.jar Editor * &&
mv Editor.jar ../Output &&
cd ..
