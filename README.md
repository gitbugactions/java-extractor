# Code Extractor for Java

This tool uses https://github.com/javaparser/javaparser to extract code from Java files.

Currently, it supports the following modes:
- Extract the largest method containing all given lines (using the `--lines` option).
- Extract the method given its name (using the `--method` option).
