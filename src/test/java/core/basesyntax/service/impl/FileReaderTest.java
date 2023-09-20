package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderTest {
    private static final String VALID_FILE_PATH = "src/test/resources/validInputData.csv";
    private static final String INVALID_FILE_PATH = "src/test/validInputData.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/emptyFile.csv";
    private static FileReader fileReader;

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void dataToProcess_validFile_Ok() {
        List<String> expected = List.of("b,banana,20", "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5", "s,banana,50");
        List<String> actual = fileReader.dataToProcess(VALID_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    void dataToProcess_InvalidFile_NotOk() {
        assertThrows(RuntimeException.class, () -> fileReader.dataToProcess(INVALID_FILE_PATH));
    }

    @Test
    void dataToProcess_EmptyFile_Ok() {
        List<String> expected = List.of();
        List<String> actual = fileReader.dataToProcess(EMPTY_FILE_PATH);
        assertEquals(expected, actual);
    }
}
