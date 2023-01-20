package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String VALID_PATH = "src/test/resources/report.csv";
    private static final String REPORT = "fruit,quantity"
            + System.lineSeparator() + "banana,152"
            + System.lineSeparator() + "apple,90"
            + System.lineSeparator();
    private FileWriterService fileWriterService;

    @Test (expected = RuntimeException.class)
    public void writeToFile_nullData_isNotOk() {
        fileWriterService.writeToFile(VALID_PATH, null);
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_nullPath_isNotOk() {
        fileWriterService = new FileWriterServiceImpl();
        fileWriterService.writeToFile(null, REPORT);
    }
}
