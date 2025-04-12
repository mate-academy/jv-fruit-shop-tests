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
        assertThrows(NullPointerException.class,
                () -> readerService.readFromFile(null));
    }

    @Test
    void readFromFile_wrongFileName_notOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(INVALID_NAME_OF_FILE));
    }

    @Test
    void readFromFile_invalidFormatOfFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(INVALID_FORMAT_OF_FILE));
    }

    @Test
    void readFromFile_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        List<String> actual = readerService.readFromFile(VALID_NAME_OF_FILE);
        assertEquals(expected, actual);
    }
}
