package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String INVALID_NAME_OF_FILE = "src/test/resources/harryPotter.csv";
    private static final String INVALID_FORMAT_OF_FILE = "src/test/resources/inputData.doc";
    private static final String VALID_NAME_OF_FILE = "src/test/resources/inputData.csv";

    private static ReaderService readerService;

    @BeforeEach
    void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_null_notOk() {
        assertThrows(
                NullPointerException.class,
                () -> readerService.readFromFile(null)
        );
    }

    @Test
    void readFromFile_invalidNameOfFile_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> readerService.readFromFile(INVALID_NAME_OF_FILE)
        );
    }

    @Test
    void readFromFile_invalidFormatOfFile_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> readerService.readFromFile(INVALID_FORMAT_OF_FILE)
        );
    }

    @Test
    void readFromFile_ok() {
        List<String> expectedValues = new ArrayList<>();
        expectedValues.add("type,fruit,quantity");
        expectedValues.add("b,banana,20");
        expectedValues.add("b,apple,100");
        expectedValues.add("s,banana,100");
        expectedValues.add("p,banana,13");
        List<String> actualValues = readerService.readFromFile(VALID_NAME_OF_FILE);
        assertEquals(expectedValues, actualValues);
    }
}
