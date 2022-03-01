#! /bin/sh

SDIR=`dirname $0` 

if [ -e $SDIR/setenv-build-jogl-x86_64.sh ] ; then
    . $SDIR/setenv-build-jogl-x86_64.sh
fi

if [ -e $SDIR/setenv-android-tools.sh ] ; then
    . $SDIR/setenv-android-tools.sh
fi

export NODE_LABEL=.

export HOST_UID=jogamp
# jogamp02 - 10.1.0.122
export HOST_IP=10.1.0.122
export HOST_RSYNC_ROOT=PROJECTS/JOGL

export TARGET_UID=jogamp
export TARGET_IP=panda02
#export TARGET_IP=jautab03
#export TARGET_IP=jauphone04
export TARGET_ADB_PORT=5555
# needs executable bit (probably su)
export TARGET_ROOT=/data/projects
export TARGET_ANT_HOME=/usr/share/ant

export ANDROID_VERSION=9
export SOURCE_LEVEL=1.6
export TARGET_LEVEL=1.6
export TARGET_RT_JAR=/opt-share/jre1.6.0_30/lib/rt.jar

#export GCC_VERSION=4.4.3
export GCC_VERSION=4.8
HOST_ARCH=linux-x86_64
export TARGET_TRIPLE=arm-linux-androideabi

export NDK_TOOLCHAIN_ROOT=$NDK_ROOT/toolchains/${TARGET_TRIPLE}-${GCC_VERSION}/prebuilt/${HOST_ARCH}
export TARGET_PLATFORM_ROOT=${NDK_ROOT}/platforms/android-${ANDROID_VERSION}/arch-arm

# Need to add toolchain bins to the PATH. 
export PATH="$NDK_TOOLCHAIN_ROOT/$TARGET_TRIPLE/bin:$ANDROID_HOME/platform-tools:$ANDROID_HOME/build-tools/$ANDROID_BUILD_TOOLS_VERSION:$PATH"

export GLUEGEN_CPPTASKS_FILE="lib/gluegen-cpptasks-android-armv6.xml"

#export JUNIT_DISABLED="true"
#export JUNIT_RUN_ARG0="-Dnewt.test.Screen.disableScreenMode"

which gcc 2>&1 | tee make.gluegen.all.android-armv6-cross.log

#export JOGAMP_JAR_CODEBASE="Codebase: *.jogamp.org"
export JOGAMP_JAR_CODEBASE="Codebase: *.goethel.localnet"

#BUILD_ARCHIVE=true \
ant \
    -Drootrel.build=build-android-armv6 \
    $* 2>&1 | tee -a make.gluegen.all.android-armv6-cross.log
