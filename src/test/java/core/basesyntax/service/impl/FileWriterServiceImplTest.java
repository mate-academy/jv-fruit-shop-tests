package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String VALID_PATH = "src/test/java/resources/report.csv";
    private static final String REPORT = "fruit,quantity"
            + System.lineSeparator() + "banana,152"
            + System.lineSeparator() + "apple,90"
            + System.lineSeparator();
    private FileWriterService fileWriterService;

    @Test
    public void writeToFile_validData_ok() throws IOException {
        String expected = "fruit,quantity";
        String actual = Files.readAllLines(Path.of(VALID_PATH)).get(0);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_nullData_notOk() {
        fileWriterService.writeToFile(VALID_PATH, null);
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_nullPath_notOk() {
        fileWriterService = new FileWriterServiceImpl();
        fileWriterService.writeToFile(null, REPORT);
    }
}
