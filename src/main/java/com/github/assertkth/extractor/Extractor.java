package com.github.assertkth.extractor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class Extractor {

    public static String extract(String file, List<Integer> lines) throws IOException {
        // Read the code and parse it
        JavaParser parser = new JavaParser();
        Node root = parser.parse(new FileInputStream(file)).getResult().get();

        // Find biggest function in number of lines (methods or constructors) in the code that contain all given lines
        Node function = null;
        for (MethodDeclaration method : root.findAll(MethodDeclaration.class)) {
            boolean containsAllLines = true;
            for (int line : lines) {
                if (!(method.getRange().isPresent() && method.getRange().get().begin.line <= line && method.getRange().get().end.line >= line)) {
                    containsAllLines = false;
                    break;
                }
            }
            if (containsAllLines && (function == null || (method.getRange().get().end.line - method.getRange().get().begin.line) > (function.getRange().get().end.line - function.getRange().get().begin.line)))
                function = method;
        }
        for (ConstructorDeclaration constructor : root.findAll(ConstructorDeclaration.class)) {
            boolean containsAllLines = true;
            for (int line : lines) {
                if (!(constructor.getRange().isPresent() && constructor.getRange().get().begin.line <= line && constructor.getRange().get().end.line >= line)) {
                    containsAllLines = false;
                    break;
                }
            }
            if (containsAllLines && (function == null || (constructor.getRange().get().end.line - constructor.getRange().get().begin.line) > (function.getRange().get().end.line - function.getRange().get().begin.line)))
                function = constructor;
        }

        // Extract string from file that corresponds to the function (including javadoc)
        String extracted = "";
        if (function != null) {
            Integer begin = function.getComment().isPresent() ? function.getComment().get().getRange().get().begin.line - 1 : function.getRange().get().begin.line - 1;
            Integer end = function.getRange().get().end.line - 1;
            String[] linesArray = new String[0];
            try (FileInputStream fis = new FileInputStream(file)) {
                linesArray = new String(fis.readAllBytes()).split("\n");
            }
            for (int i = begin; i <= end; i++) {
                extracted += linesArray[i] + "\n";
            }
        }

        return function != null ? extracted : null;
    }
}
