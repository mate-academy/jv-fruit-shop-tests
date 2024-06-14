package core.basesyntax.service.impl;

import core.basesyntax.service.CantWorkWithThisFileException;
import core.basesyntax.service.CsvFileReaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CsvFileReaderServiceImplTest {
    CsvFileReaderService readerService;

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
        String nullPath = null;
        assertThrows(CantWorkWithThisFileException.class,
                () -> readerService.readFromFile(nullPath),
                "Null path");
    }
}
