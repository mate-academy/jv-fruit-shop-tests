package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String HEADER = "type,fruit,quantity";
    private static final String BANANA_KEY = "banana";
    private static final String APPLE_KEY = "apple";
    private static FileWriterService fileWriterService;
    private static String correctNameFile;
    private static String data;
    private static List<String> expected;
    private static List<String> actual;

    @BeforeClass
    public static void beforeClass() {
        correctNameFile = "src"
                + File.separator + "main"
                + File.separator + "resources"
                + File.separator + "ProductBalance.csv";
        fileWriterService = new FileWriterServiceImpl();
        expected = new ArrayList<>(List.of(HEADER + " b, "
                + APPLE_KEY + ", 100, "
                + " b, " + BANANA_KEY + ", 100"));
        data = HEADER + " b, "
                + APPLE_KEY + ", 100, "
                + " b, " + BANANA_KEY + ", 100";
    }

    @Test
    public void writeToFile() {
        fileWriterService.writeToFile(correctNameFile, data);
        try {
            actual = Files.readAllLines(Path.of(correctNameFile));
        } catch (IOException e) {
            throw new RuntimeException("Can't read such a file there");
        }
        assertEquals("Test failed! The contents of the file should be "
                + expected + " but was: " + actual, expected, actual);
    }
}
