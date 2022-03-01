# export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-loongarch64

export JAVA_HOME=${HOME}/tsjdk8-uos

if [ -d build ]; then
    echo
    rm -rf build
    rm -rf build-temp
fi

cd make
ant \
    -Dtarget.sourcelevel=1.8 \
    -Dtarget.targetlevel=1.8 \
    -Dtarget.rt.jar=${JAVA_HOME}/jre/lib/rt.jar

