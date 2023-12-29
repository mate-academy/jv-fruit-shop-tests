package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private static final String VALID_FILE_PATH =
            "src/test/java/core/basesyntax/resources/infoFile.csv";
    private static final String INVALID_FILE_PATH = "invalid Path";
    private static final String EMPTY_FILE_PATH =
            "src/test/java/core/basesyntax/resources/empty.csv";
    private static final String WRONG_FORMAT_FILE_PATH =
            "src/test/java/core/basesyntax/resources/wrongFormatFile.csv";
    private static FileReaderService fileReaderService;
    private static List<String> expected;

    @BeforeAll
    static void beforeAll() {
        fileReaderService = new FileReaderServiceImpl();
        expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
    }

    @Test
    void readFile_fromValidFilePath_Ok() {
        List<String> actual = fileReaderService.readFile(VALID_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    void readFile_fromEmptyFile_Ok() {
        List<String> dataFromFile = fileReaderService.readFile(EMPTY_FILE_PATH);
        assertTrue(dataFromFile.isEmpty());
    }

    @Test
    void readFile_fromInvalidFilePath_NotOk() {
        assertThrows(RuntimeException.class, () ->
                fileReaderService.readFile(INVALID_FILE_PATH));
    }

    @Test
    void readFile_fromInvalidFileFormat_NotOk() {
        assertThrows(RuntimeException.class, () ->
                fileReaderService.readFile(WRONG_FORMAT_FILE_PATH));
    }
}
