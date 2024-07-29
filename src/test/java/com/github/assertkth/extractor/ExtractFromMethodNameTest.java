package com.github.assertkth.extractor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class ExtractFromMethodNameTest {

    @Test
    void testConstructor() throws IOException {
        // Regular constructor
        String function = Extractor.extractFromMethodName("src/test/resources/ExampleClass.java", "ExampleClass");
        assertEquals("    public ExampleClass() {\n" + //
                        "        System.out.println(\"Constructor\");\n" + //
                        "    }\n", function);
    }

    @Test
    void testMethod1() throws IOException {
        // Regular method
        String function = Extractor.extractFromMethodName("src/test/resources/ExampleClass.java", "method1");
        assertEquals("    public void method1() {\n" + //
                        "        System.out.println(\"Method 1\");\n" + //
                        "    }\n", function);
    }

    @Test
    void testMethod2() throws IOException {
        // Method with annotation
        String function = Extractor.extractFromMethodName("src/test/resources/ExampleClass.java", "method2");
        assertEquals("    @Override\n" + //
                        "    private void method2() {\n" + //
                        "        System.out.println(\"Method 2\");\n" + //
                        "        System.out.println(\"Method 2\");\n" + //
                        "    }\n", function);
    }

    @Test
    void testMethod3() throws IOException {
        // Method with Javadoc and annotation
        String function = Extractor.extractFromMethodName("src/test/resources/ExampleClass.java", "method3");
        assertEquals("    @Override\n" + //
                        "    /*\n" + //
                        "     * This is a javadoc comment\n" + //
                        "     */\n" + //
                        "    public void method3() {\n" + //
                        "        // Normal comment\n" + //
                        "        System.out.println(\"Method 3\");\n" + //
                        "        System.out.println(\"Method 3\");\n" + //
                        "        System.out.println(\"Method 3\");\n" + //
                        "    }\n", function);
    }

    @Test
    void testNoMethod() throws IOException {
        String function = Extractor.extractFromMethodName("src/test/resources/ExampleClass.java", "foo");
        assertNull(function);

        function = Extractor.extractFromMethodName("src/test/resources/ExampleClass.java", "bar");
        assertNull(function);
    }
    
}