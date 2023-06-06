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
    private final String correctFile
            = "src/main/java/core/basesyntax/resources/Correct.csv";
    private final String emptyFile
            = "src/main/java/core/basesyntax/resources/Empty.csv";
    private final String inCorrectFile
            = "src/main/java/core/basesyntax/resources/InCorrect.csv";
    private final String nonExistingFile
            = "src/main/java/core/basesyntax/resources/NullParameters.txt";

    @BeforeAll
    static void setUp() {
        fileReaderService = new CsvFileReaderServiceImpl();
    }

    @Test
    void readLines_readFromEmptyFile_ok() {
        List<String> expected = Collections.emptyList();
        List<String> actual = fileReaderService.readLines(emptyFile);
        assertEquals(expected, actual);
    }

    @Test
    void readLines_filePathIsEmpty_notOk() {
        String filePath = "";
        assertThrows(RuntimeException.class, () -> fileReaderService.readLines(filePath));
    }

    @Test
    void readLines_readFromFileWithValidData_ok() {
        List<String> expected = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = fileReaderService.readLines(correctFile);
        assertEquals(expected, actual);
    }

    @Test
    void readLines_readFromFileWithInvalidData_notOk() {
        assertThrows(AcceptedDataInvalidException.class,
                () -> fileReaderService.readLines(inCorrectFile));
    }

    @Test
    void readLines_readFromNonExistFile_notOk() {
        assertThrows(RuntimeException.class, () -> fileReaderService.readLines(nonExistingFile));
    }
}
