package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ReaderServiceCsv implements ReaderService<File, String> {

    private static final String READING_EXCEPTION = "IOException while reading from file ";
    private static final String FILE_NOT_FOUND = "File to read from not found ";

    @Override
    public List<String> readLines(File source) {
        try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
            return reader.lines().collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(FILE_NOT_FOUND + source);
        } catch (IOException e) {
            throw new RuntimeException(READING_EXCEPTION + source.getName());
        }
    }
}
