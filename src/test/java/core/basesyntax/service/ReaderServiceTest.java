package core.basesyntax.service;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceTest {
    private static final String NOT_EXISTING_FILE_NAME = "src/test/resources/qwer.csv";
    private static final String NOT_VALID_FORMAT_OF_FILE = "src/test/resources/inputData.doc";
    private static final String VALID_FILE_NAME = "src/test/resources/input.csv";
    private static ReaderService service;

    @BeforeEach
    void setUp() {
        service = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_null_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> service.readFromFile(null));
    }

    @Test
    void readFromFile_notExistingFileName_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> service.readFromFile(NOT_EXISTING_FILE_NAME));
    }

    @Test
    void readFromFile_notValidFormatOfFile_nokOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> service.readFromFile(NOT_VALID_FORMAT_OF_FILE));
    }

    @Test
    void readFromFile_validFileName_ok() {
        List<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"type", "fruit", "quantity"});
        expected.add(new String[]{"b", "banana", "20"});
        expected.add(new String[]{"b", "apple", "100"});
        expected.add(new String[]{"s", "banana", "100"});
        List<String[]> actual = service.readFromFile(VALID_FILE_NAME);
        Assertions.assertArrayEquals(expected.toArray(), actual.toArray());
    }
}
