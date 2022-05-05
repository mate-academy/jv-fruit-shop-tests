package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String VALID_FILE = "src/test/java/resources/output_valid_data.csv";
    private static final String EMPTY_FILE_REFERENCE = "src/test/java/resources/empty.csv";
    private static String testReport;
    private static WriterService writerService;

    @BeforeClass
    public static void setUp() {
        writerService = new WriterServiceImpl();
        testReport = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_EmptyFilePathString_NotOk() {
        writerService.writeToFile("", testReport);
    }

    @Test
    public void writeToFile_EmptyReport_Ok() {
        writerService.writeToFile(VALID_FILE, "");
        List<String> expected = readFromFile(EMPTY_FILE_REFERENCE);
        List<String> actual = readFromFile(VALID_FILE);
        assertEquals(expected, actual);
    }

    private List<String> readFromFile(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file by path: " + filePath, e);
        }
    }
}
