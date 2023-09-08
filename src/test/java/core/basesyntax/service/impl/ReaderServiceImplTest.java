package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String EMPTY_FILE_NAME = "";
    private static final String WRONG_FILE_NAME = "src/main/resources/file.csv";
    private static final String RIGHT_FILE_NAME = "src/main/resources/fruits.csv";

    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void read_emptyFileName_notOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(EMPTY_FILE_NAME));
    }

    @Test
    void read_nullFileName_notOK() {
        assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(null));
    }

    @Test
    void read_wrongFileName_notOK() {
        assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(WRONG_FILE_NAME));
    }

    @Test
    void read_rightFileName_Ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = readerService.readFromFile(RIGHT_FILE_NAME);
        assertEquals(expected, actual);
    }
}
