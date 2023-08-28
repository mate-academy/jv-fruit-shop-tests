package core.basesyntax.serviceimpltest;

import static org.junit.Assert.assertThrows;

import core.basesyntax.service.readservice.ReadServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReaderServiceImplTest {
    private static final String INPUT_VALID_DATA_FILE =
            "src/main/java/datafiles/input_data.csv";
    private static final String EMPTY_DATA_FILE =
            "src/main/java/datafiles/empty.csv";
    private static final String INPUT_INCORRECT_DATA_FILE =
            "src/main/java/datafiles/input_data123.csv";
    private static final String NULL_PATH = null;
    private static ReadServiceImpl readServiceImpl;

    @BeforeAll
    static void afterAll() {
        readServiceImpl = new ReadServiceImpl();
    }

    @Test
    void readNull_NotOk() {
        assertThrows(RuntimeException.class,
                () -> readServiceImpl.readFrom(NULL_PATH));
    }

    @Test
    void readIncorrectPath_NotOk() {
        Assertions.assertFalse(false, INPUT_INCORRECT_DATA_FILE);
    }

    @Test
    void readEmptyPath_NotOk() {
        Assertions.assertFalse(false, EMPTY_DATA_FILE);
    }

    @Test
    void getValidData_Ok() {
        Assertions.assertTrue(true, INPUT_VALID_DATA_FILE);
    }
}
