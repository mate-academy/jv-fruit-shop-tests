package core.basesyntax.service.impl;

import core.basesyntax.service.Reader;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderImplTest {
    private static final String NORMAL_DATA_FILE_PATH = "src/main/resources/test/dataForTests.csv";
    private static final String EMPTY_FILE_PATH = "src/main/resources/test/empty.csv";
    private static final String WRONG_FILE_PATH = "src/main/resources/test/epty.csv";
    private static final String NULL_PATH = null;
    private static Reader reader;

    @BeforeAll
    static void beforeAll() {
        reader = new ReaderImpl();
    }

    @Test
    void readingNull_NotOk() {
        Assert.assertThrows(NullPointerException.class, () -> reader.readFile(NULL_PATH));
    }

    @Test
    void readingWrongPath_NotOk() {
        Assert.assertThrows(RuntimeException.class, () -> reader.readFile(WRONG_FILE_PATH));
    }

    @Test
    void readingEmptyFile_NotOk() {
        Assert.assertThrows(RuntimeException.class, () -> reader.readFile(EMPTY_FILE_PATH));
    }

    @Test
    void getDataFromFile_Ok() {
        Assert.assertFalse(reader.readFile(NORMAL_DATA_FILE_PATH).isEmpty());
    }
}
