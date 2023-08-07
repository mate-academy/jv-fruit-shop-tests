package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private static final String emptyFileName = "";
    private static final String nullFileName = null;
    private static final String NoExistFileName = "src/main/resources/file.xml";
    private static final String fileName = "src/main/resources/data.csv";

    private static FileReaderService fileReaderService;

    @BeforeAll
    static void beforeAll() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    void readEmptyFileName_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readCsvFile(emptyFileName),
                "File can't be empty");
    }

    @Test
    void readNullFilename_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readCsvFile(nullFileName),
                "File name can't be null");
    }

    @Test
    void readNotExistFile_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readCsvFile(NoExistFileName),
                "File does not exist " + NoExistFileName);
    }

    @Test
    void readValidData_Ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = fileReaderService.readCsvFile(fileName);
        assertEquals(expected, actual);
    }
}
