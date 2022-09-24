package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileWriterServiceImplTest extends FileWriterServiceImpl {
    private static final String OUTPUT_FILE_PATH = "src/test/java/resources/report.csv";
    private static final String FILE_CONTENT = "fruit,quantity" + System.lineSeparator()
            + "banana,30" + System.lineSeparator() + "apple,60";
    FileWriterService fileWriterService;

    @Before
    public void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void WriterService_OKData_Ok() {
        fileWriterService.writeToFile(FILE_CONTENT, OUTPUT_FILE_PATH);
    }

    @Test (expected = RuntimeException.class)
    public void WriterService_NullReport_NotOk() {
        fileWriterService.writeToFile(null, OUTPUT_FILE_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void WriterService_EmptyPath_NotOk() {
        fileWriterService.writeToFile(FILE_CONTENT, "");
    }
}