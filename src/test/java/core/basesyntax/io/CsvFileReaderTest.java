package core.basesyntax.io;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvFileReaderTest {
    private static final String VALID_PATH = "src/test/resources/file.csv";
    private static final String INVALID_PATH = "src20/test/resources/file.csv";
    private static final String FILE_EMPTY_PATH = "src/test/resources/file_empty.csv";
    private static final String FILE_HAS_ONLY_HEAD_PATH =
            "src/test/resources/file_has_only_head.csv";
    private ReaderFromFile readerFromFile;

    @BeforeEach
    void setUp() {
        readerFromFile = new CsvFileReader();
    }

    @Test
    void validFilePath_Ok() {
        List<String> actual = readerFromFile.readFile(VALID_PATH);
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
        assertIterableEquals(expected, actual);
    }

    @Test
    void invalidFilePath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> readerFromFile.readFile(INVALID_PATH));
    }

    @Test
    void emptyFile_NotOk() {
        assertThrows(RuntimeException.class,
                () -> readerFromFile.readFile(FILE_EMPTY_PATH));
    }

    @Test
    void fileHasOnlyHead_NotOk() {
        assertThrows(RuntimeException.class,
                () -> readerFromFile.readFile(FILE_HAS_ONLY_HEAD_PATH));
    }
}
