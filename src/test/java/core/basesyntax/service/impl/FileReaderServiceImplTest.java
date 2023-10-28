package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private static final String REGULAR_INPUT = "src/test/resources/test_input.csv";
    private static final String EMPTY_INPUT = "src/test/resources/test_empty_input.csv";
    private static FileReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new FileReaderServiceImpl();
    }

    @Test
    void read_regularInput_Ok() {
        List<String> expected = List.of("b,banana,20", "b,apple,100", "s,banana,100");
        Assertions.assertEquals(expected, readerService.read(REGULAR_INPUT));
    }

    @Test
    void read_invalidFile_NotOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.read("file"));
    }

    @Test
    void read_emptyInput_Ok() {
        List<String> expected = List.of();
        Assertions.assertEquals(expected, readerService.read(EMPTY_INPUT));
    }

    @Test
    void read_nullInput_NotOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> readerService.read(null));
    }
}
