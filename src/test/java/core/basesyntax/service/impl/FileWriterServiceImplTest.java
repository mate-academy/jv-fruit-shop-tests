package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService;
    private static final String DEFAULT_PATH = "src/test/resources/fruitsReportTest.csv";
    private static final String DEFAULT_RESULT = "fruits,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90";

    @BeforeClass
    public static void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_isValid() {
        fileWriterService.writeToFile(DEFAULT_RESULT, DEFAULT_PATH);
    }
}
