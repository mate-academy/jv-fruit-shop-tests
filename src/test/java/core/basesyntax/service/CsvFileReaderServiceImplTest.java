package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.AcceptedDataInvalidExeption;
import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import java.util.Collections;
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
    void readLines_readFromEmptyFile_ok() {
        String filePath = "src/test/resources/Empty.csv";
        List<String> expected = Collections.emptyList();
        List<String> actual = fileReaderService.readLines(filePath);
        assertEquals(expected, actual);
    }

    @Test
    void readLines_filePathIsEmpty_notOk() {
        String filePath = "";
        assertThrows(RuntimeException.class, () -> fileReaderService.readLines(filePath));
    }

    @Test
    void readLines_readFromFileWithValidData_ok() {
        String filePath = "src/test/resources/Information.csv";
        List<String> expected = List.of("b,banana,10", "b,apple,10", "s,banana,10", "s,apple,10");
        List<String> actual = fileReaderService.readLines(filePath);
        assertEquals(expected, actual);
    }

    @Test
    void readLines_readFromFileWithInvalidData_notOk() {
        String filePath = "src/test/resources/InvalidData.csv";
        assertThrows(AcceptedDataInvalidExeption.class,
                () -> fileReaderService.readLines(filePath));
    }

    @Test
    void readLines_readFromNonExistFile_notOk() {
        String nonExistingFile = "non_existing_file.txt";
        assertThrows(RuntimeException.class, () -> fileReaderService.readLines(nonExistingFile));
    }
}
