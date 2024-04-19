package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.basesyntax.service.ReaderService;
import core.basesyntax.basesyntax.service.impl.ReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String INVALID_NAME_OF_FILE = "src/test/resources/fruits.csv";
    private static final String INVALID_FORMAT_OF_FILE = "src/test/java/resources/InputFile.doc";
    private static final String VALID_NAME_OF_FILE = "src/test/java/resources/InputFile.csv";

    private static ReaderService readerService;

    @BeforeEach
    void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_null_notOk() {
        assertThrows(NullPointerException.class,
                () -> readerService.readFile(null));
    }

    @Test
    void readFromFile_wrongFileName_notOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.readFile(INVALID_NAME_OF_FILE));
    }

    @Test
    void readFromFile_invalidFormatOfFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.readFile(INVALID_FORMAT_OF_FILE));
    }

    @Test
    void readFromFile_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        List<String> actual = readerService.readFile(VALID_NAME_OF_FILE);
        assertEquals(expected, actual);
    }
}
