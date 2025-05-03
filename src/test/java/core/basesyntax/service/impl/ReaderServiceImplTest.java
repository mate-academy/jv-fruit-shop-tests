package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.FileReaderException;
import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String VALID_FILE_PATH = "src/test/resources/valid.csv";
    private static final String NON_EXISTENT_FILE_PATH = "src/test/resources/nonexistent.csv";
    private static final String EMPTY_LINES_FILE_PATH = "src/test/resources/all_empty_lines.csv";
    private static final String ERROR_MESSAGE = "Error reading from a file: ";
    private static final String EMPTY_LINES_ERROR_MESSAGE = "You think i'm stupid?...";
    private static final List<String> EXPECTED_VALID_RESULT = List.of(
            "type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13"
    );
    private ReaderService readerService;

    @BeforeEach
    void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_ValidInput_Ok() {
        List<String> actual = readerService.readFromFile(VALID_FILE_PATH);
        assertEquals(EXPECTED_VALID_RESULT, actual);
    }

    @Test
    void readFromFile_TheFileDoesNotExist_NotOk() {
        String expectedMessage = ERROR_MESSAGE + NON_EXISTENT_FILE_PATH;

        var exception = assertThrows(FileReaderException.class, () ->
                readerService.readFromFile(NON_EXISTENT_FILE_PATH));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void readFromFile_AllEmptyLines_NotOk() {
        var exception = assertThrows(RuntimeException.class, () ->
                readerService.readFromFile(EMPTY_LINES_FILE_PATH));
        assertEquals(EMPTY_LINES_ERROR_MESSAGE, exception.getMessage());
    }
}
