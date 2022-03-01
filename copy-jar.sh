#!/bin/sh

ARCH_DIR=amd64
ARCH_JAR=amd64

# ARCH_DIR=arm64
# ARCH_JAR=aarch64

JOGL_DIR=jogl/build
JOGL_JAR=${JOGL_DIR}/jar/jogl-all.jar
JOGL_NATIVE_JAR=${JOGL_DIR}/jar/jogl-all-natives-linux-${ARCH_JAR}.jar
JOGL_SRC=${JOGL_DIR}/jogl-java-src.zip

GLUEGEN_DIR=gluegen/build
GLUEGEN_JAR=${GLUEGEN_DIR}/gluegen-rt.jar
GLUEGEN_NATIVE_JAR=${GLUEGEN_DIR}/gluegen-rt-natives-linux-${ARCH_JAR}.jar
GLUEGEN_SRC=${GLUEGEN_DIR}/gluegen-java-src.zip

# DEST_DIR=/opt/Taishan/Office 
DEST_DIR=/opt/apps/cn.ts-it.suite/files
DEST_DIR=${DEST_DIR}/program
for jarfile in ${JOGL_JAR}    ${JOGL_NATIVE_JAR}   \
               ${GLUEGEN_JAR} ${GLUEGEN_NATIVE_JAR}
do
    if [ -d ${DEST_DIR} ]; then
        sudo cp ${jarfile} ${DEST_DIR}
    fi
done

J3DTEST_PATH=${HOME}/eclipse-workspace/J3dTest
DEST_DIR=${J3DTEST_PATH}/lib
for file in ${JOGL_JAR}    ${JOGL_SRC}   \
            ${GLUEGEN_JAR} ${GLUEGEN_SRC}
do
    cp ${file} ${DEST_DIR}
done

DEST_DIR=${J3DTEST_PATH}/lib-natives/linux-${ARCH_DIR}
for file in ${JOGL_NATIVE_JAR} ${GLUEGEN_NATIVE_JAR}
do
    cp ${file} ${DEST_DIR}
done
