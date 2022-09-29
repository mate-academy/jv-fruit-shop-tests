package core.basesyntax.impl;

import core.basesyntax.service.FileWriterService;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String NO_FILE = "";
    private static final String TO_FILE_PATH = "src/test/java/core/basesyntax/report.csv";
    private static final String FRUITS_REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();
    private FileWriterService fileWriterService = new FileWriterServiceImpl();

    @Test (expected = RuntimeException.class)
    public void writeToFile_NotExistingFile_NotOk() {
        fileWriterService.writeToFile(NO_FILE, FRUITS_REPORT);
    }

    @Test
    public void writeToFile_ExistingFile_Ok() {
        fileWriterService.writeToFile(TO_FILE_PATH, FRUITS_REPORT);
    }
}
