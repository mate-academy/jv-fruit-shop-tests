package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String VALID_OUTPUT_PATH = "src/test/resources/output_test_file.csv";
    private static final String INVALID_OUTPUT_PATH = "";
    private static final String REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,152\n" + System.lineSeparator()
            + "apple,90";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeAll() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_isOk() {
        fileWriterService.writeToFile(VALID_OUTPUT_PATH, REPORT);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_WriteToNotExistingFile_NotOk() {
        fileWriterService.writeToFile(INVALID_OUTPUT_PATH, REPORT);
    }
}
