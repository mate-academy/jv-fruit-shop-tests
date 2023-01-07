package core.basesyntax.service.impl;

import org.junit.Before;
import org.junit.Test;

public class FileReaderImplTest {
    public static final String WRONG_FILEPATH = "/var/lib";
    private FileReaderImpl readerService;

    @Before
    public void setUp() {
        readerService = new FileReaderImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_wrongPath_NotOk() {
        readerService.readData(WRONG_FILEPATH);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_nullPath_NotOk() {
        readerService.readData(null);
    }
}
