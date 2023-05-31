package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import org.junit.Before;
import org.junit.Test;

public class FileWriterServiceImplTest extends FileWriterServiceImpl {
    private static final String OUTPUT_FILE_PATH = "src/test/java/resources/report.csv";
    private static final String FILE_CONTENT = "fruit,quantity" + System.lineSeparator()
            + "banana,30" + System.lineSeparator() + "apple,60";
    private FileWriterService fileWriterService;

    @Before
    public void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_ValidData_OK() {
        fileWriterService.writeToFile(FILE_CONTENT, OUTPUT_FILE_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_NullReport_NotOK() {
        fileWriterService.writeToFile(null, OUTPUT_FILE_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_EmptyPath_NotOK() {
        fileWriterService.writeToFile(FILE_CONTENT, "");
    }
}
