/* !---- DO NOT EDIT: This file autogenerated by com/jogamp/gluegen/JavaEmitter.java on Sun Feb 01 23:28:47 CET 2015 ----! */


package jogamp.common.os.elf;

import java.nio.*;

import com.jogamp.gluegen.runtime.*;
import com.jogamp.common.os.*;
import com.jogamp.common.nio.*;
import jogamp.common.os.MachineDataInfoRuntime;


public class Ehdr_p1 {

  StructAccessor accessor;

  private static final int mdIdx = 0;
  private final MachineDataInfo md;

  private static final int[] Ehdr_p1_size     = new int[] { 24 /* ARM_MIPS_32 */, 24 /* X86_32_UNIX */, 24 /* X86_32_MACOS */, 24 /* PPC_32_UNIX */, 24 /* SPARC_32_SUNOS */, 24 /* X86_32_WINDOWS */, 24 /* LP64_UNIX */, 24 /* X86_64_WINDOWS */ };
  private static final int[] e_ident_offset   = new int[] {  0 /* ARM_MIPS_32 */,  0 /* X86_32_UNIX */,  0 /* X86_32_MACOS */,  0 /* PPC_32_UNIX */,  0 /* SPARC_32_SUNOS */,  0 /* X86_32_WINDOWS */,  0 /* LP64_UNIX */,  0 /* X86_64_WINDOWS */ };
  private static final int[] e_ident_size     = new int[] { 16 /* ARM_MIPS_32 */, 16 /* X86_32_UNIX */, 16 /* X86_32_MACOS */, 16 /* PPC_32_UNIX */, 16 /* SPARC_32_SUNOS */, 16 /* X86_32_WINDOWS */, 16 /* LP64_UNIX */, 16 /* X86_64_WINDOWS */ };
  private static final int[] e_type_offset    = new int[] { 16 /* ARM_MIPS_32 */, 16 /* X86_32_UNIX */, 16 /* X86_32_MACOS */, 16 /* PPC_32_UNIX */, 16 /* SPARC_32_SUNOS */, 16 /* X86_32_WINDOWS */, 16 /* LP64_UNIX */, 16 /* X86_64_WINDOWS */ };
//private static final int[] e_type_size      = new int[] {  2 /* ARM_MIPS_32 */,  2 /* X86_32_UNIX */,  2 /* X86_32_MACOS */,  2 /* PPC_32_UNIX */,  2 /* SPARC_32_SUNOS */,  2 /* X86_32_WINDOWS */,  2 /* LP64_UNIX */,  2 /* X86_64_WINDOWS */ };
  private static final int[] e_machine_offset = new int[] { 18 /* ARM_MIPS_32 */, 18 /* X86_32_UNIX */, 18 /* X86_32_MACOS */, 18 /* PPC_32_UNIX */, 18 /* SPARC_32_SUNOS */, 18 /* X86_32_WINDOWS */, 18 /* LP64_UNIX */, 18 /* X86_64_WINDOWS */ };
//private static final int[] e_machine_size   = new int[] {  2 /* ARM_MIPS_32 */,  2 /* X86_32_UNIX */,  2 /* X86_32_MACOS */,  2 /* PPC_32_UNIX */,  2 /* SPARC_32_SUNOS */,  2 /* X86_32_WINDOWS */,  2 /* LP64_UNIX */,  2 /* X86_64_WINDOWS */ };
  private static final int[] e_version_offset = new int[] { 20 /* ARM_MIPS_32 */, 20 /* X86_32_UNIX */, 20 /* X86_32_MACOS */, 20 /* PPC_32_UNIX */, 20 /* SPARC_32_SUNOS */, 20 /* X86_32_WINDOWS */, 20 /* LP64_UNIX */, 20 /* X86_64_WINDOWS */ };
//private static final int[] e_version_size   = new int[] {  4 /* ARM_MIPS_32 */,  4 /* X86_32_UNIX */,  4 /* X86_32_MACOS */,  4 /* PPC_32_UNIX */,  4 /* SPARC_32_SUNOS */,  4 /* X86_32_WINDOWS */,  4 /* LP64_UNIX */,  4 /* X86_64_WINDOWS */ };

  public static int size() {
    return Ehdr_p1_size[mdIdx];
  }

  public static Ehdr_p1 create() {
    return create(Buffers.newDirectByteBuffer(size()));
  }

  public static Ehdr_p1 create(java.nio.ByteBuffer buf) {
      return new Ehdr_p1(buf);
  }

  Ehdr_p1(java.nio.ByteBuffer buf) {
    md = MachineDataInfo.StaticConfig.values()[mdIdx].md;
    accessor = new StructAccessor(buf);
  }

  public java.nio.ByteBuffer getBuffer() {
    return accessor.getBuffer();
  }

  /** Getter for native field: CType['char *', size [fixed false, lnx64 16], [array*1]], with array length of <code>16</code> */
  public static final int getE_identArrayLength() {
    return 16;
  }

  /** Setter for native field: CType['char *', size [fixed false, lnx64 16], [array*1]], with array length of <code>16</code> */
  public Ehdr_p1 setE_ident(final int offset, byte[] val) {
    final int arrayLength = 16;
    if( offset + val.length > arrayLength ) { throw new IndexOutOfBoundsException("offset "+offset+" + val.length "+val.length+" > array-length "+arrayLength); };
    final int elemSize = Buffers.SIZEOF_BYTE;
    final ByteBuffer destB = getBuffer();
    final int bTotal = arrayLength * elemSize;
    if( bTotal > e_ident_size[mdIdx] ) { throw new IndexOutOfBoundsException("bTotal "+bTotal+" > size "+e_ident_size[mdIdx]+", elemSize "+elemSize+" * "+arrayLength); };
    int bOffset = e_ident_offset[mdIdx];
    final int bLimes = bOffset + bTotal;
    if( bLimes > destB.limit() ) { throw new IndexOutOfBoundsException("bLimes "+bLimes+" > buffer.limit "+destB.limit()+", elemOff "+bOffset+", elemSize "+elemSize+" * "+arrayLength); };
    bOffset += elemSize * offset;
    accessor.setBytesAt(bOffset, val);
    return this;
  }

  /** Getter for native field: CType['char *', size [fixed false, lnx64 16], [array*1]], with array length of <code>16</code> */
  public ByteBuffer getE_ident() {
    return accessor.slice(e_ident_offset[mdIdx],  Buffers.SIZEOF_BYTE * 16);
  }

  /** Getter for native field: CType['char *', size [fixed false, lnx64 16], [array*1]], with array length of <code>16</code> */
  public byte[] getE_ident(final int offset, byte result[]) {
    final int arrayLength = 16;
    if( offset + result.length > arrayLength ) { throw new IndexOutOfBoundsException("offset "+offset+" + result.length "+result.length+" > array-length "+arrayLength); };
    return accessor.getBytesAt(e_ident_offset[mdIdx] + (Buffers.SIZEOF_BYTE * offset), result);
  }


  /** Setter for native field: CType['uint16_t', size [fixed true, lnx64 2], [int]] */
  public Ehdr_p1 setE_type(short val) {
    accessor.setShortAt(e_type_offset[mdIdx], val);
    return this;
  }

  /** Getter for native field: CType['uint16_t', size [fixed true, lnx64 2], [int]] */
  public short getE_type() {
    return accessor.getShortAt(e_type_offset[mdIdx]);
  }

  /** Setter for native field: CType['uint16_t', size [fixed true, lnx64 2], [int]] */
  public Ehdr_p1 setE_machine(short val) {
    accessor.setShortAt(e_machine_offset[mdIdx], val);
    return this;
  }

  /** Getter for native field: CType['uint16_t', size [fixed true, lnx64 2], [int]] */
  public short getE_machine() {
    return accessor.getShortAt(e_machine_offset[mdIdx]);
  }

  /** Setter for native field: CType['uint32_t', size [fixed true, lnx64 4], [int]] */
  public Ehdr_p1 setE_version(int val) {
    accessor.setIntAt(e_version_offset[mdIdx], val);
    return this;
  }

  /** Getter for native field: CType['uint32_t', size [fixed true, lnx64 4], [int]] */
  public int getE_version() {
    return accessor.getIntAt(e_version_offset[mdIdx]);
  }
}
