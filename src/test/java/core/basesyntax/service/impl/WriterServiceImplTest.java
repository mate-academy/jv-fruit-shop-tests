package core.basesyntax.service.impl;

import static org.junit.Assert.fail;

import core.basesyntax.service.WriterService;
import org.junit.Before;
import org.junit.Test;

public class WriterServiceImplTest {
    private WriterService writerService;
    private String data;
    private String filePath;

    @Before
    public void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writeDataToFile_nullFilePath_notOk() {
        data = "qwerty";
        filePath = null;
        try {
            writerService.writeDataToFile(data, filePath);
        } catch (RuntimeException e) {
            return;
        }
        fail("Exception should be thrown if file path is null");
    }

    @Test
    public void writeDataToFile_emptyFilePath_notOk() {
        data = "qwerty";
        filePath = "";
        try {
            writerService.writeDataToFile(data, filePath);
        } catch (RuntimeException e) {
            return;
        }
        fail("Exception should be thrown if file path is empty");
    }

    @Test
    public void writeDataToFIle_nullData_notOk() {
        data = null;
        filePath = "src/main/resources/report.csv";
        try {
            writerService.writeDataToFile(data, filePath);
        } catch (RuntimeException e) {
            return;
        }
        fail("Exception should be thrown if data is null");
    }
}
