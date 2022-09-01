package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import org.junit.Before;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService;
    private static FileReaderService fileReaderService = new FileReaderServiceImpl();
    private static String default_path = "src/test/resources/fruitsReportTest.csv";
    private static String default_result = "fruits,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90";

    @Before
    public void setUp() throws Exception {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_isValid() {
        fileWriterService.writeToFile(default_result, default_path);
    }
}
