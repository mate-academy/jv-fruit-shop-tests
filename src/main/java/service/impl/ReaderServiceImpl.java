package service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import service.ReaderService;

public class ReaderServiceImpl implements ReaderService {
    private static final int LENGTH = 2;
    private static final int INPUT_INDEX = 0;
    private static final int OUTPUT_INDEX = 1;

    @Override
    public List<String> readFromFile(String filePath) {
        try {
            List<String> file = Files.lines(Paths.get(filePath)).toList();
            if (file.isEmpty()) {
                throw new IllegalArgumentException("File must contain at least a header");
            }
            return file;
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filePath, e);
        }
    }

    @Override
    public void fileValidation(String [] args) {
        if (args.length != LENGTH) {
            throw new IllegalArgumentException("Please provide input and output file names "
                + "as arguments: <inputFile> <outputFile>");
        }

        String inputFilePath = args[INPUT_INDEX];
        String outputFilePath = args[OUTPUT_INDEX];

        if ((inputFilePath == null && outputFilePath == null)) {
            throw new NullPointerException("Input and output file names cannot be null");
        }

        if (inputFilePath == null) {
            throw new NullPointerException("Input file names cannot be null");
        }

        if (outputFilePath == null) {
            throw new NullPointerException("Output file names cannot be null");
        }

        if (inputFilePath.trim().isEmpty() && outputFilePath.trim().isEmpty()) {
            throw new IllegalArgumentException("Input and output file names cannot be empty");
        }

        if (inputFilePath.trim().isEmpty()) {
            throw new IllegalArgumentException("Input file names cannot be empty");
        }

        if (outputFilePath.trim().isEmpty()) {
            throw new IllegalArgumentException("Output file names cannot be empty");
        }

        if (!Files.exists(Paths.get(inputFilePath))) {
            throw new IllegalArgumentException("Input file does not exist: " + inputFilePath);
        }
    }
}
