#ifndef __GLIBC_COMPAT_SYMBOLS_H__
#define __GLIBC_COMPAT_SYMBOLS_H__ 1
 
/**
 *
 * Note: Patching a specific glibc symbol version is only required if *not* statically linking glibc,
 *       which should be preferred.
 *
 * Note: JogAmp's minimum GLIBC is 2.4 due to '__stack_chk_fail' (stack overflow checking)
 *   
 *   GLIBC 2.4 - March 2006  - Standard for LSB 4.0, Used in SLES 10
 *   
 * We could add compile/link option '-fno-stack-protector', however stack protection seems reasonable
 * and a pre 2006 distribution a bit too 'far fetched' for our multimedia bindings anyway.
 *
 * Check build-in macro definitions via 'gcc -dM -E - < /dev/null'
 */
#if defined(__linux__) /* Actually we like to test whether we link against GLIBC .. */
    #define GLIBC_COMPAT_SYMBOL(FFF)
#else
    #warning GLIBC_COMPAT_SYMBOL not supported with target OS
    #define GLIBC_COMPAT_SYMBOL(FFF)
#endif

GLIBC_COMPAT_SYMBOL(memcpy)
 
#endif /*__GLIBC_COMPAT_SYMBOLS_H__*/
