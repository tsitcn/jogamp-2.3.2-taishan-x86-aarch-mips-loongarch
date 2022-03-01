package com.jogamp.gluegen.jcpp;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;
import java.util.logging.Level;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.jogamp.gluegen.Logging;
import com.jogamp.gluegen.Logging.LoggerIf;
import com.jogamp.junit.util.SingletonJunitCase;

import static com.jogamp.gluegen.jcpp.Token.*;
import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PreprocessorTest extends SingletonJunitCase {

    private static final LoggerIf LOG = Logging.getLogger(PreprocessorTest.class);

    private OutputStreamWriter writer;
    private Preprocessor p;

    @Before
    public void setUp() throws Exception {
        LOG.setLevel(Level.INFO);
        final PipedOutputStream po = new PipedOutputStream();
        writer = new OutputStreamWriter(po);

        p = new Preprocessor();
        // p.addFeature(Feature.DEBUG);
        p.addInput(
                new LexerSource(
                        new InputStreamReader(
                                new PipedInputStream(po)
                        ),
                        true
                )
        );
    }

    private static class I {
        private final String t;

        public I(final String t) {
            this.t = t;
        }

        public String getText() {
            return t;
        }

        @Override
        public String toString() {
            return getText();
        }
    }
    private static I I(final String t) {
        return new I(t);
    }
    private static class N {
        private final String t;

        public N(final String t) {
            this.t = t;
        }

        public String getText() {
            return t;
        }

        @Override
        public String toString() {
            return getText();
        }
    }
    private static N N(final String t) {
        return new N(t);
    }

    /*
     * When writing tests in this file, remember the preprocessor
     * stashes NLs, so you won't see an immediate NL at the end of any
     * input line. You will see it right before the next nonblank on
     * the following input line.
     */
    @Test
    public void test01Preprocessor() throws Exception {
        /* Magic macros */
        testInput("line = __LINE__\n",
                I("line"), WHITESPACE, '=', WHITESPACE, NUMBER
        /*, NL - all nls deferred so as not to block the reader */
        );
        testInput("file = __FILE__\n", NL, /* from before, etc */
                I("file"), WHITESPACE, '=', WHITESPACE, STRING
        );

        /* Simple definitions */
        testInput("#define A a /* a defined */\n", NL);
        testInput("A /* a */\n", NL, I("a"), WHITESPACE, CCOMMENT);
        testConstMacro("A", true, I("a"));
        testInput("#define B b /* b defined */\n", NL);
        testInput("B /* b */\n", NL, I("b"), WHITESPACE, CCOMMENT);
        testConstMacro("B", false, I("b"));
        testInput("#define C c /* c defined */\n", NL);

        /* Expansion of arguments */
        testInput("#define EXPAND(x) x\n", NL);
        testInput("EXPAND(a)\n", NL, I("a"));
        testInput("EXPAND(A)\n", NL, I("a"));

        /* Stringification */
        testInput("#define _STRINGIFY(x) #x\n", NL);
        testInput("_STRINGIFY(A)\n", NL, "A");
        testInput("#define STRINGIFY(x) _STRINGIFY(x)\n", NL);
        testInput("STRINGIFY(b)\n", NL, "b");
        testInput("STRINGIFY(A)\n", NL, "a");

        /* Concatenation */
        testInput("#define _CONCAT(x, y) x ## y\n", NL);
        testInput("_CONCAT(A, B)\n", NL, I("AB"));
        testInput("#define A_CONCAT done_a_concat\n", NL);
        testInput("_CONCAT(A, _CONCAT(B, C))\n", NL,
                I("done_a_concat"), '(', I("b"), ',', WHITESPACE, I("c"), ')'
        );
        testInput("#define CONCAT(x, y) _CONCAT(x, y)\n", NL);
        testInput("CONCAT(A, CONCAT(B, C))\n", NL, I("abc"));
        testInput("#define _CONCAT3(x, y, z) x ## y ## z\n", NL);
        testInput("_CONCAT3(a, b, c)\n", NL, I("abc"));
        testInput("_CONCAT3(A, B, C)\n", NL, I("ABC"));
        testInput("_CONCAT(test_, inline)\n", NL, I("test_inline"));
        testInput("_CONCAT(test_, \nnewline)\n", NL, I("test_newline"));

        /* Redefinitions, undefinitions. */
        testInput("#define two three\n", NL);
        testInput("two /* three */\n", NL, I("three"), WHITESPACE, CCOMMENT);
        testInput("one /* one */\n", NL, I("one"), WHITESPACE, CCOMMENT);
        testConstMacro("two", false, I("three"));
        testConstMacro("two", true, I("three"));

        testInput("#define one two\n", NL);
        testInput("one /* three */\n", NL, I("three"), WHITESPACE, CCOMMENT);
        testConstMacro("one", false, I("two"));
        testConstMacro("one", true, I("three"));

        testInput("#undef two\n", NL);
        testInput("one /* two */\n", NL, I("two"), WHITESPACE, CCOMMENT);
        testConstMacro("one", false, I("two"));
        testConstMacro("one", true, I("two"));

        testInput("#define two five\n", NL);
        testInput("one /* five */\n", NL, I("five"), WHITESPACE, CCOMMENT);
        testConstMacro("one", false, I("two"));
        testConstMacro("one", true, I("five"));

        testInput("#undef two\n", NL);
        testInput("one /* two */\n", NL, I("two"), WHITESPACE, CCOMMENT);
        testConstMacro("one", false, I("two"));
        testConstMacro("one", true, I("two"));

        testInput("#undef one\n", NL);
        testInput("#define one four\n", NL);
        testInput("one /* four */\n", NL, I("four"), WHITESPACE, CCOMMENT);
        testConstMacro("one", false, I("four"));
        testConstMacro("one", true, I("four"));

        testInput("#undef one\n", NL);
        testInput("#define one one\n", NL);
        testInput("one /* one */\n", NL, I("one"), WHITESPACE, CCOMMENT);
        testConstMacro("one", false, I("one"));
        testConstMacro("one", true, I("one"));

        testInput("#define NUM1 1\n", NL);
        testInput("#define NUM4 ( 1 << ( NUM1 + NUM1 ) )\n", NL);
        testInput("NUM4 /* ( 1 << ( 1 + 1 ) ) */\n", NL,
                '(', WHITESPACE, N("1"), WHITESPACE, LSH, WHITESPACE,
                '(', WHITESPACE, N("1"), WHITESPACE, '+', WHITESPACE, N("1"), WHITESPACE, ')', WHITESPACE, ')',
                WHITESPACE, CCOMMENT);
        testConstMacro("NUM4", false, '(', WHITESPACE, N("1"), WHITESPACE, LSH, WHITESPACE,
                                      '(', WHITESPACE, I("NUM1"), WHITESPACE, '+', WHITESPACE, I("NUM1"), WHITESPACE, ')',
                                      WHITESPACE, ')');
        testConstMacro("NUM4", true, '(', WHITESPACE, N("1"), WHITESPACE, LSH, WHITESPACE,
                                     '(', WHITESPACE, N("1"), WHITESPACE, '+', WHITESPACE, N("1"), WHITESPACE, ')', WHITESPACE, ')');

        /* Variadic macros. */
        testInput("#define var(x...) a x __VA_ARGS__ b\n", NL);
        testInput("var(e, f, g)\n", NL,
                I("a"), WHITESPACE,
                I("e"), ',', WHITESPACE,
                I("f"), ',', WHITESPACE,
                I("g"), WHITESPACE,
                I("__VA_ARGS__"), WHITESPACE, // __VA_ARGS__ is not expanded in this case.
                I("b")
        );
        /* Missing arguments are fine. */
        testInput("var()\n", NL,
                I("a"), WHITESPACE,
                /* No expansion for 'x'. */ WHITESPACE,
                I("__VA_ARGS__"), WHITESPACE,
                I("b")
        );

        /* Variadic macros with anonymous args. */
        testInput("#define var2(x, ...) a x __VA_ARGS__ e\n", NL);
        testInput("var2(b, c, d)\n", NL,
                I("a"), WHITESPACE,
                I("b"), WHITESPACE,
                I("c"), ',', WHITESPACE,
                I("d"), WHITESPACE,
                I("e")
        );
        /* Missing arguments are fine. */
        testInput("var2(b)\n", NL,
                I("a"), WHITESPACE,
                I("b"), WHITESPACE,
                /* No expansion for '__VA_ARGS__'. */ WHITESPACE,
                I("e")
        );

        testInput("#define var3(...) a __VA_ARGS__ d\n", NL);
        testInput("var3(b, c)\n", NL,
                I("a"), WHITESPACE,
                I("b"), ',', WHITESPACE,
                I("c"), WHITESPACE,
                I("d")
        );
        testInput("var3()\n", NL,
                I("a"), WHITESPACE,
                /* No expansion for '__VA_ARGS__'. */ WHITESPACE,
                I("d")
        );

        testInput("#define _Widen(x) L ## x\n", NL);
        testInput("#define Widen(x) _Widen(x)\n", NL);
        testInput("#define LStr(x) _Widen(#x)\n", NL);
        testInput("LStr(x);\n", NL, I("L"), "x", ';');

        testInput("'foo'\n", NL, SQSTRING);
        testInput("#if 1 ? 2 : 0\nTEXT\n#endif\n", NL, NL, I("TEXT"), NL);
        testInput("#if 1 ? 0 : 2\nTEXT\n#endif\n", NL, NL, NL);
        testInput("#if 0 ? 0 : 2\nTEXT\n#endif\n", NL, NL, I("TEXT"), NL);
        testInput("#if 0 ? 2 : 0\nTEXT\n#endif\n", NL, NL, NL);

        writer.close();

        Token t;
        do {
            t = p.token();
            LOG.warning("Remaining token " + t);
        } while (t.getType() != EOF);
    }

    @Test
    public void test02PreprocessorUnterminated() throws Exception {
        testInput("#ifndef X\na\n#else\nb\n");   // Bug #16

        writer.close();

        Token t;
        do {
            t = p.token();
            LOG.warning("Remaining token " + t);
        } while (t.getType() != EOF);
    }

    public static void assertType(final int type, final Token t) {
        final String typeExpect = TokenType.getTokenName(type);
        final String typeActual = TokenType.getTokenName(t.getType());
        assertEquals("Expected " + typeExpect + " but got " + typeActual, type, t.getType());
    }

    private void testInput(final String in, final Object... out)
            throws Exception {
        LOG.info("Input: " + in);
        writer.write(in);
        writer.flush();
        for (final Object v : out) {
            final Token t = p.token();
            LOG.info("READ: "+String.valueOf(t));
            if (v instanceof String) {
                if (t.getType() != STRING)
                    fail("Expected STRING, but got " + t);
                assertEquals(v, t.getValue());
            } else if (v instanceof I) {
                if (t.getType() != IDENTIFIER) {
                    fail("Expected IDENTIFIER " + v + ", but got " + t);
                }
                assertEquals(((I) v).getText(), t.getText());
            } else if (v instanceof N) {
                if (t.getType() != NUMBER) {
                    fail("Expected NUMBER " + v + ", but got " + t);
                }
                assertEquals(((N) v).getText(), t.getText());
            } else if (v instanceof Character) {
                assertType(((Character) v).charValue(), t);
            } else if (v instanceof Integer) {
                assertType(((Number) v).intValue(), t);
            } else {
                fail("Bad object " + v.getClass());
            }
        }
    }
    // slow ..
    private Macro findMacro(final List<Macro> macros, final String macroName) {
        final int count = macros.size();
        for(int i=0; i<count; i++) {
            final Macro m = macros.get(i);
            if( m.getName().equals(macroName) ) {
                return m;
            }
        }
        return null;
    }
    private void dumpMacros(final List<Macro> macros) {
        final int count = macros.size();
        System.err.println("Macro count: "+count);
        for(int i=0; i<count; i++) {
            final Macro m = macros.get(i);
            System.err.println(" ["+i+"]: "+m);
        }
    }
    private void testConstMacro(final String macroName, final boolean expandMacro, final Object... out)
            throws Exception {
        final List<Macro> macros = p.getMacros(expandMacro);
        final Macro m = findMacro(macros, macroName);
        if( null == m ) {
            dumpMacros(macros);
        }
        Assert.assertNotNull("Macro <"+macroName+"> is missing!", m);
        Assert.assertFalse(m.isFunctionLike());

        final Source s = new MacroTokenSource(m, null);
        try {
            for (final Object v : out) {
                final Token t = s.token();
                LOG.info("READ: "+String.valueOf(t));
                if (v instanceof String) {
                    if (t.getType() != STRING) {
                        fail("Expected STRING, but got " + t);
                    }
                    assertEquals(v, t.getValue());
                } else if (v instanceof I) {
                    if (t.getType() != IDENTIFIER) {
                        fail("Expected IDENTIFIER " + v + ", but got " + t);
                    }
                    assertEquals(((I) v).getText(), t.getText());
                } else if (v instanceof N) {
                    if (t.getType() != NUMBER) {
                        fail("Expected NUMBER " + v + ", but got " + t);
                    }
                    assertEquals(((N) v).getText(), t.getText());
                } else if (v instanceof Character) {
                    assertType(((Character) v).charValue(), t);
                } else if (v instanceof Integer) {
                    assertType(((Number) v).intValue(), t);
                } else {
                    fail("Bad object " + v.getClass());
                }
            }
        } finally {
            s.close();
        }
    }
    public static void main(final String args[]) throws IOException {
        final String tstname = PreprocessorTest.class.getName();
        org.junit.runner.JUnitCore.main(tstname);
    }
}
