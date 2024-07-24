package com.github.assertkth.extractor;

import java.util.List;
import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "App", mixinStandardHelpOptions = true, description = "Java Code Extractor")
public class App implements Callable<Integer> {

    @Option(names = { "-i", "--input" }, required = true, description = "Input file")
    private String input;

    @Option(names = { "-l", "--lines" }, required = true, description = "List of modified lines")
    private List<Integer> lines;

    @Override
    public Integer call() throws Exception {
        String result = Extractor.extract(input, lines);

        if (result != null) {
            System.out.print(result);
            return 0;
        } else {
            return 1;
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args); 
        System.exit(exitCode); 
    }

}
