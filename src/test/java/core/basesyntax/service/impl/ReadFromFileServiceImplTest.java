package core.basesyntax.service.impl;

import core.basesyntax.service.ReadFromFileService;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReadFromFileServiceImplTest {
    private static final String NULL_PATH = null;
    private static final String WRONG_PATH = "d/s/inputDat.csv";
    private static final String EMPTY_FILE = "src/main/java/core/basesyntax/resources/empty.csv";
    private static final String TEST_RESULT
            = "src/main/resources/testInputData.csv";
    private static final List<String> EXPECTED_LIST =
            List.of("type,fruit,quantity", "b,banana,20", "p,apple,100");
    private ReadFromFileService read;

    @BeforeEach
    public void setUp() {
        read = new ReadFromFileServiceImpl();
    }

    @Test
    void readingNull_NotOk() {
        Assert.assertThrows(RuntimeException.class, () -> read.readCsv(NULL_PATH));
    }

    @Test
    void readingWrongPath_NotOk() {
        Assert.assertThrows(RuntimeException.class, () -> read.readCsv(WRONG_PATH));
    }

    @Test
    void readingEmptyCsv_NotOk() {
        Assert.assertThrows(RuntimeException.class, () -> read.readCsv(EMPTY_FILE));
    }

    @Test
    void readingCorrectFile_Ok() {
        List<String> actualResult = read.readCsv(TEST_RESULT);
        Assert.assertEquals(EXPECTED_LIST, actualResult);
    }
}
