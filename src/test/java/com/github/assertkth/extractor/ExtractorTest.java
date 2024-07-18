package com.github.assertkth.extractor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.github.assertkth.extractor.Extractor;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ConstructorDeclaration;

class ExtractorTest {

    @Test
    void testConstructor() throws IOException {
        // Regular constructor
        String function = Extractor.extract("src/test/resources/ExampleClass.java", List.of(3, 4));
        assertEquals("    public ExampleClass() {\n" + //
                        "        System.out.println(\"Constructor\");\n" + //
                        "    }\n", function);
    }

    @Test
    void testMethod1() throws IOException {
        // Regular method
        String function = Extractor.extract("src/test/resources/ExampleClass.java", List.of(7, 8, 9));
        assertEquals("    public void method1() {\n" + //
                        "        System.out.println(\"Method 1\");\n" + //
                        "    }\n", function);
    }

    @Test
    void testMethod2() throws IOException {
        // Method with annotation
        String function = Extractor.extract("src/test/resources/ExampleClass.java", List.of(12, 13, 14));
        assertEquals("    @Override\n" + //
                        "    private void method2() {\n" + //
                        "        System.out.println(\"Method 2\");\n" + //
                        "        System.out.println(\"Method 2\");\n" + //
                        "    }\n", function);
    }

    @Test
    void testMethod3() throws IOException {
        // Method with Javadoc and annotation
        String function = Extractor.extract("src/test/resources/ExampleClass.java", List.of(20, 22, 23));
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
        String function = Extractor.extract("src/test/resources/ExampleClass.java", List.of(1, 2, 10, 23));
        assertNull(function);

        function = Extractor.extract("src/test/resources/ExampleClass.java", List.of(5, 6));
        assertNull(function);
    }

}