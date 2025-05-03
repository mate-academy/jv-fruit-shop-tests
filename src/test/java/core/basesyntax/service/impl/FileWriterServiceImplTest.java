package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileWriterService;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String CORRECT_PATH = "src/test/resources/report.csv";
    private static final String WRONG_PATH = "src/src/resources/report.csv";
    private static final String REPORT = "Hello, mentor :)";
    private static FileWriterService fileWriter;

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_successfulWrite_ok() {
        assertTrue(fileWriter.writeToFile(REPORT, CORRECT_PATH));
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_uncorrectPath_notOk() {
        fileWriter.writeToFile(REPORT, WRONG_PATH);
    }
}
