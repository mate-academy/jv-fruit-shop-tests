package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.CantWorkWithThisFileException;
import core.basesyntax.service.CsvFileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileReaderServiceImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String NULL_PATH = null;
    private CsvFileReaderService readerService;

    @BeforeEach
    void setUp() {
        readerService = new CsvFileReaderServiceImpl();
    }

    @Test
    void readFromFile_WrongPath_NotOk() {
        String wrongPath1 = "wrongPath1";
        String wrongPath2 = "wrongPath2";
        assertThrows(CantWorkWithThisFileException.class,
                () -> readerService.readFromFile(wrongPath1),
                "Wrong path");

        assertThrows(CantWorkWithThisFileException.class,
                () -> readerService.readFromFile(wrongPath2),
                "Wrong path");
    }

    @Test
    void readFromFile_NullPath_NotOk() {
        assertThrows(CantWorkWithThisFileException.class,
                () -> readerService.readFromFile(NULL_PATH),
                "Null path");
    }

    @Test
    void readFromFile_CorrectFileName_Ok() {
        String fileName1 = "xyz.csv";
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual = readerService.readFromFile(fileName1);

        assertEquals(expected,actual);
    }
}
