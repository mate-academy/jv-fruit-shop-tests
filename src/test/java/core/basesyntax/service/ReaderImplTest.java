package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

class ReaderImplTest {
    private static final String VALID_FILE_PATH =
            "D:/java/jv-fruit-shop-tests/src/main/java/resources/input.csv";
    private static final String INVALID_FILE_PATH =
            "D:/java/jv-fruit-shop-tests/src/main/java/resources/report.csv";
    private final Reader reader = new ReaderImpl();

    @Test
    void readFile_ValidPath_Ok() {
        List<String> expectedLines = List.of(
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );
        assertEquals(expectedLines, reader.readFile(VALID_FILE_PATH));
    }

    @Test
    void readFile_InvalidPath_ThrowsException() {
        assertThrows(RuntimeException.class, () -> reader.readFile(INVALID_FILE_PATH));
    }
}
