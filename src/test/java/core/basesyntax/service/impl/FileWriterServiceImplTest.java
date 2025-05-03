package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService = new FileWriterServiceImpl();
    private static final String HEADER = "type,fruit,quantity";
    private static final String BANANA_KEY = "banana";
    private static final String APPLE_KEY = "apple";
    private static final String CORRECT_NAME_FILE = "src"
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "ProductBalance.csv";
    private static final String NOT_CORRECT_NAME_FILE = "src main"
            + File.separator + "resources"
            + File.separator + "Product.csv";
    private static String DATA = HEADER + " b, "
            + APPLE_KEY + ", 100, "
            + " b, " + BANANA_KEY + ", 100";

    @Test
    public void writeToFile() {
        List<String> expected = new ArrayList<>(List.of(HEADER + " b, "
                + APPLE_KEY + ", 100, " + " b, " + BANANA_KEY + ", 100"));
        List<String> actual;

        fileWriterService.writeToFile(CORRECT_NAME_FILE, DATA);
        try {
            actual = Files.readAllLines(Path.of(CORRECT_NAME_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't read such a file there");
        }
        assertEquals("Test failed! The contents of the file should be "
                + expected + " but was: " + actual, expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFileNoCorrectFileName_Ok() {
        fileWriterService.writeToFile(NOT_CORRECT_NAME_FILE, DATA);
        try {
            Files.readAllLines(Path.of(NOT_CORRECT_NAME_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't read such a file there");
        }
    }

    @Test
    public void writeEmptyDataToFile_Ok() {
        String emptyData = " ";
        List<String> expected = new LinkedList<>(List.of(emptyData));
        List<String> actual;

        fileWriterService.writeToFile(CORRECT_NAME_FILE, emptyData);
        try {
            actual = Files.readAllLines(Path.of(CORRECT_NAME_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file" + CORRECT_NAME_FILE);
        }
        assertEquals("Test failed! The contents of the file should be "
                + expected + " but was: " + actual, expected,actual);
    }
}
