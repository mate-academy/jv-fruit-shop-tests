package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceTest {
    private static ReaderService readerService;
    private static final String NULL_VALUE = null;
    private static final String PATH_WRONG = "path/wrong";
    private static final String PATH_CORRECT = "src/test/resources/fruits.csv";
    private static List<String> linesFromFile;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
        linesFromFile = List.of("type,fruit,quantity",
                "b,banana,20",
                "s,banana,100",
                "b,apple,10",
                "p,apple,20");
    }

    @Test
    void read_pathIsNull_notOk() {
        assertThrows(RuntimeException.class, () -> readerService.readFromFile(NULL_VALUE));
    }

    @Test
    void read_pathIsWrong_notOk() {
        assertThrows(RuntimeException.class, () -> readerService.readFromFile(PATH_WRONG));
    }

    @Test
    void read_pathIsCorrect_ok() {
        List<String> expected = linesFromFile;
        List<String> actual = readerService.readFromFile(PATH_CORRECT);
        assertEquals(expected, actual);
    }
}
