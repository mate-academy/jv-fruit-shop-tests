package core.basesyntax.service;

import org.junit.Before;
import org.junit.Test;

public class FileWriterServiceTest {
    private FileWriterService fileWriterService;

    @Before
    public void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void write_pathToFileIsNull_notOk() {
        String pathToFile = null;
        String report = "fruit, quantity";
        fileWriterService.write(pathToFile, report);
    }

    @Test(expected = RuntimeException.class)
    public void write_pathToFileIsEmpty_notOk() {
        String pathToFile = "";
        String report = "fruit, quantity";
        fileWriterService.write(pathToFile, report);
    }

    @Test
    public void write_report_Ok() {
        String pathToFile = "src/main/resources/report_test.csv";
        String report = "report";
        fileWriterService.write(pathToFile, report);
    }
}
