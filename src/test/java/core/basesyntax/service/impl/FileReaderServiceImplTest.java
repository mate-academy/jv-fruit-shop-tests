package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private static final String VALID_FILE_PATH = "src/test/resources/reportToRead.csv";
    private static final String INVALID_FILE_PATH = "src/test/resources/nonExistentFile.csv";
    private static FileReaderService fileReaderService;

    @BeforeAll
    static void beforeAll() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    void read_validFilePath_ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );
        List<String> actual = fileReaderService.read(VALID_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    void read_invalidFilePath_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.read(INVALID_FILE_PATH));
    }
}
