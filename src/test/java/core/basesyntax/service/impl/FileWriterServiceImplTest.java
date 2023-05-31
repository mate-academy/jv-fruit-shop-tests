package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService;
    private static final String DEFAULT_PATH = "src/test/resources/fruitsReportTest.csv";
    private static final String DEFAULT_RESULT = "fruits,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90";

    @BeforeClass
    public static void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_isValid() {
        String expected = null;
        String actual = DEFAULT_RESULT;
        fileWriterService.writeToFile(actual, DEFAULT_PATH);
        try {
            expected = Files.readString(Path.of(DEFAULT_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data", e);
        }
        assertEquals(expected, actual);
    }
}
