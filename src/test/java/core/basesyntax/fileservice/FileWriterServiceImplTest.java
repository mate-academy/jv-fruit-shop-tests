package core.basesyntax.fileservice;

import core.basesyntax.fileservice.impl.FileWriterServiceImpl;
import org.junit.Before;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String REPORT_FILE_PATH = "src/test/java/resources/report.csv";
    private static final String FILE_CONTENT = "fruit,quantity" + System.lineSeparator()
            + "banana,100" + System.lineSeparator() + "apple,60";
    private FileWriterService fileWriterService;

    @Before
    public void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeService_ExistedPath_Ok() {
        fileWriterService.writeToFile(FILE_CONTENT, REPORT_FILE_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void writeService_EmptyPath_NotOk() {
        fileWriterService.writeToFile(FILE_CONTENT, "");
    }

    @Test (expected = RuntimeException.class)
    public void writeService_nullFile_NotOk() {
        fileWriterService.writeToFile(null, REPORT_FILE_PATH);
    }
}
