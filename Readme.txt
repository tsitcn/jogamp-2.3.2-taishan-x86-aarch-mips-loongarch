How to build:
1, Open build.sh, modify JAVA_HOME.
2、Copy swt.jar to ${JAVA_HOME}/jre/lib/ext


Improvements:
1, Can compile the project on x86/arm/mips64el/loongarch
2, Resoved coredump problems on some linux oses.
    a, gluegen/src/java/jogamp/common/os/elf/ElfHeaderPart1.java
    b, jogl/src/jogl/classes/com/jogamp/opengl/GLProfile.java
    c, jogl/src/jogl/classes/jogamp/opengl/x11/glx/X11GLXDynamicLibraryBundleInfo.java
