package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.AcceptedDataInvalidException;
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
        String filePath = "src/main/java/core/basesyntax/resources/Empty.csv";
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
        String filePath = "src/main/java/core/basesyntax/resources/Correct.csv";
        List<String> expected = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = fileReaderService.readLines(filePath);
        assertEquals(expected, actual);
    }

    @Test
    void readLines_readFromFileWithInvalidData_notOk() {
        String filePath = "src/main/java/core/basesyntax/resources/InCorrect.csv";
        assertThrows(AcceptedDataInvalidException.class,
                () -> fileReaderService.readLines(filePath));
    }

    @Test
    void readLines_readFromNonExistFile_notOk() {
        String nonExistingFile = "src/main/java/core/basesyntax/resources/NullParameters.txt";
        assertThrows(RuntimeException.class, () -> fileReaderService.readLines(nonExistingFile));
    }
}
