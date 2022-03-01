#! /bin/sh

#    -Dc.compiler.debug=true 
#    -Dgluegen.cpptasks.detected.os=true \
#    -DisUnix=true \
#    -DisLinux=true \
#    -DisLinuxX86=true \
#    -DisX11=true \

# aarch64-linux-gnueabi == armel triplet
export TARGET_PLATFORM_LIBS=/usr/lib/aarch64-linux-gnueabi
export TARGET_JAVA_LIBS=/usr/lib/jvm/default-java/jre/lib/aarch64

export GLUEGEN_CPPTASKS_FILE="lib/gluegen-cpptasks-linux-aarch64.xml"

#export JOGAMP_JAR_CODEBASE="Codebase: *.jogamp.org"
export JOGAMP_JAR_CODEBASE="Codebase: *.goethel.localnet"

ant \
    -Drootrel.build=build-linux-aarch64 \
    $* 2>&1 | tee make.gluegen.all.linux-aarch64.log
