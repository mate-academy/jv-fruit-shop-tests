package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String EMPTY_FILE_NAME = "";
    private static final String WRONG_WILE_NAME = "wrongFileName.csv";
    private static final String CORRECT_FILE_NAME = "src/test/resources/input_db.csv";
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_emptyFileName_notOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(EMPTY_FILE_NAME));
    }

    @Test
    void readFromFile_nullFileName_notOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(null));
    }

    @Test
    void readFromFile_wrongFileName_notOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(WRONG_WILE_NAME));
    }

    @Test
    void readFromFile_CorrectFileName_Ok() {
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
        List<String> actual = readerService.readFromFile(CORRECT_FILE_NAME);
        assertEquals(expected, actual);
    }
}
