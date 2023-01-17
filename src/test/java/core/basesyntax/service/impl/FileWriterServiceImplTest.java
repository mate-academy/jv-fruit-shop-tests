package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String VALID_OUTPUT_PATH = "src/test/resources/output_test_file.csv";
    private static final String INVALID_OUTPUT_PATH = "";
    private static final String REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeAll() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_isOk() throws IOException {
        fileWriterService.writeToFile(VALID_OUTPUT_PATH, REPORT);
        String actual = Files.readString(Path.of(VALID_OUTPUT_PATH));
        assertEquals(REPORT, actual);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_writeToNonExistingFile_isNotOk() {
        fileWriterService.writeToFile(INVALID_OUTPUT_PATH, REPORT);
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_nullFilePath_isNotOk() {
        fileWriterService.writeToFile(null, REPORT);
    }
}
