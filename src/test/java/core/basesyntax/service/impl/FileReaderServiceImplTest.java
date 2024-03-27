package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.exceptions.InvalidFileException;
import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private static final String VALID_PATH_WAY = "src/main/resources/valid.csv";
    private static final String NOT_EXIST_PATH_NAME = "src/main/resources/notExistFile.csv";
    private static final String EMPTY_FILE_PATH = "src/main/resources/emptyFile.csv";
    private static final List<String> EXPECTED_VALID_RESULT = List.of(
            "type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13");

    private FileReaderService fileReaderService;

    @BeforeEach
    void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @BeforeEach
    void tearDown() {
        fileReaderService = null;
    }

    @Test
    void readFileValid_Ok() {
        List<String> actualResult = fileReaderService.readFile(VALID_PATH_WAY);
        assertEquals(EXPECTED_VALID_RESULT, actualResult);
    }

    @Test
    void readFileNotExist_NotOk() {
        String filePath = NOT_EXIST_PATH_NAME;
        String expectedMessage = "File does not exist: " + filePath;
        InvalidFileException actualException = assertThrows(InvalidFileException.class,
                () -> fileReaderService.readFile(filePath));
        assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    void readFile_EmptyFile_NotOk() {
        String filePath = EMPTY_FILE_PATH;
        String expectedMessage = "File is empty: " + filePath;
        InvalidFileException actualException
                = assertThrows(
                        InvalidFileException.class, () -> fileReaderService.readFile(filePath));
        assertEquals(expectedMessage,actualException.getMessage());
    }
}
