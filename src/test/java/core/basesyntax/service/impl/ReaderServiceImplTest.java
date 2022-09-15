package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String RIGHT_FILE_NAME
            = "src/main/java/core/basesyntax/resources/inputFile.csv";
    private static final String WRONG_FILE_NAME
            = "src/main/java/core/basesyntax/resources/input.csv";
    private ReaderService readerService;

    @Before
    public void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_Ok() {
        Assert.assertTrue(readerService.readFromFile(RIGHT_FILE_NAME).size() > 0);

    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_NotOk() {
        readerService.readFromFile(WRONG_FILE_NAME);
    }
}
