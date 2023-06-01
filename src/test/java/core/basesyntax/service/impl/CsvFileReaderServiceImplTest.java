package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.CsvFileReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileReaderServiceImplTest {
    private static CsvFileReaderService fileReaderService;

    @BeforeAll
    static void setUp() {
        fileReaderService = new CsvFileReaderServiceImpl();
    }

    @Test
    void readLines_filePathIsEmpty_notOk() {
        String filePath = "";
        assertThrows(RuntimeException.class, () -> fileReaderService.readLines(filePath));
    }

    @Test
    void readLines_readFromFileWithInvalidData_notOk() {
        String filePath = "src/test/resources/InvalidData.csv";
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readLines(filePath));
    }

    @Test
    void readLines_readFromNonExistFile_notOk() {
        String nonExistingFile = "non_existing_file.txt";
        assertThrows(RuntimeException.class, () -> fileReaderService.readLines(nonExistingFile));
    }

    @Test
    void readFromFile_validInput_ok() {
        List<String> actual = fileReaderService.readLines(
                "src/test/resources/StoreInformation.csv");
        List<String> expected = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        assertEquals(expected, actual);
    }
}
