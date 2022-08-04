package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileWriterService;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final FileWriterService fileWriter = new FileWriterServiceImpl();
    private static final String CORRECT_PATH = "src/test/resources/report.csv";
    private static final String WRONG_PATH = "src/src/resources/report.csv";
    private static final String REPORT = "Hello, mentor :)";

    @Test
    public void writeToFile_successfulWrite_ok() {
        // changed the return type from void to boolean
        // in writeToFile method for simplicity. its OK?
        // or is this a gross violation of SOLID principles?
        assertTrue(fileWriter.writeToFile(REPORT, CORRECT_PATH)); //changed
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_uncorrectPath_notOk() {
        fileWriter.writeToFile(REPORT, WRONG_PATH);
    }
}
