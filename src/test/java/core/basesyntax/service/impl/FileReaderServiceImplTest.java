package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.exception.InvalidValueExeption;
import core.basesyntax.service.CsvFileReaderService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private static CsvFileReaderService fileReaderService;
    private static final String VALID_FILE_PATH = "src/test/resources/TestData.csv";
    private static final String INVALID_FILE_PATH = "src/resources/NonexistentFile.csv";
    private static final String INVALID_FORMAT_FILE_PATH
            = "src/test/resources/InvalidFormatData.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/EmptyData.csv";

    @BeforeAll
    static void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    void readFile_readValidFile_ok() {
        List<String> expected = List.of("b,banana,20", "b,apple,50");
        List<String> actual = fileReaderService.readFile(VALID_FILE_PATH);
        assertEquals(expected, actual, "Incorrect format of lines read");
    }

    @Test
    void readFile_readInvalidFile_notOk() {
        assertThrows(InvalidValueExeption.class,
                () -> fileReaderService.readFile(INVALID_FORMAT_FILE_PATH));
    }

    @Test
    void readFile_readEmptyFile_ok() {
        List<String> expected = Collections.emptyList();
        List<String> actual = fileReaderService.readFile(EMPTY_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void readFile_nullFile_notOk() {
        assertThrows(NullPointerException.class, () -> fileReaderService.readFile(null));
    }

    @Test
    public void readFile_fileNotFound_notOk() {
        assertThrows(RuntimeException.class, () -> fileReaderService.readFile(INVALID_FILE_PATH));
    }
}
