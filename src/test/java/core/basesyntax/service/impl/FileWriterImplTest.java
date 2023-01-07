package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import org.junit.Before;
import org.junit.Test;

public class FileWriterImplTest {
    public static final String WRONG_FILEPATH = "/var/lib";
    public static final String TEST_STRING = "type,fruit,quantity\nb,banana,20";
    private FileWriter writerService;

    @Before
    public void setUp() {
        writerService = new FileWriterImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_wrongPath_NotOk() {
        writerService.writeData(TEST_STRING, WRONG_FILEPATH);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullPath_NotOk() {
        writerService.writeData(TEST_STRING, null);
    }
}
