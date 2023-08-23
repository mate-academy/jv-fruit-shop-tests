package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReadingFileService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReadingFileServiceImplTest {
    private static ReadingFileService readingFileService;
    private static final String VALID_FIlE_PATH = "src/test/java/resources/fruits_data.csv";
    private static final String INVALID_FILE_PATH
            = "src/test/java/resources/invalid_fruits_data.csv";
    private static List<String> expected;

    @BeforeAll
    static void beforeAll() {
        readingFileService = new ReadingFileServiceImpl();
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
    void testReadDataFromFile_Ok() {
        assertThrows(RuntimeException.class,
                () -> (readingFileService)
                        .readDataFromFile("Input File"));
    }

    @Test
    void readDataFromTheFileValidFilePath_Ok() {
        assertIterableEquals(expected,
                readingFileService.readDataFromFile(VALID_FIlE_PATH));
    }

    @Test
    void readDataFromTheFileInvalidFilePath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> readingFileService.readDataFromFile(INVALID_FILE_PATH));
    }
}
