package core.basesyntax.service.reader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderFileImplTest {
    private static final String INPUT_FILE = "src/test/resources/input.csv";
    private static final String EMPTY_FILE = "src/main/resources/empty.csv";
    private static final String NEGATIVE_FRUIT = "src/main/resources/negativeFruit.csv";
    private static final String NOT_EXIST_FILE = "src/test/resources/notExist.csv";
    private ReaderFile readerFile;

    @BeforeEach
    void setUp() {
        readerFile = new ReaderFileImpl();
    }

    @Test
    void fileIsEmpty_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            readerFile.readFile(EMPTY_FILE);
        });
    }

    @Test
    void fileIsNotExist_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            readerFile.readFile(NOT_EXIST_FILE);
        });
    }

    @Test
    void quantityInFileLessZero_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            readerFile.readFile(NEGATIVE_FRUIT);
        });
    }

    @Test
    void fileReaderWorkCorrectly_ok() {
        Assertions.assertDoesNotThrow(() -> {
            readerFile.readFile(INPUT_FILE);
        });
    }
}
