package core.basesyntax.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String EMPTY_PATH = "src/test/java/resources/empty";
    private static final String TITLE_PATH = "src/test/java/resources/title";
    private static final String ACTIVITIES_PATH = "src/test/java/resources/storeActivities";
    private static final String WRONG_PATH_1 = "src/test/java/resources/store";
    private static final String WRONG_PATH_2 = "src/test/java/storeActivities";
    private static ReaderService readerService;
    private static List<String> expectedEmpty;
    private static List<String> expectedTitle;
    private static List<String> expectedFull;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
        expectedEmpty = new ArrayList<>();
        expectedTitle = List.of("type,fruit,quantity");
        expectedFull = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
    }

    @Test
    void read_FromFilePathIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> readerService.readFromFile(null));
    }

    @Test
    void read_FromFileValidPath_Ok() {
        assertEquals(expectedEmpty, readerService.readFromFile(EMPTY_PATH));
        assertEquals(expectedTitle, readerService.readFromFile(TITLE_PATH));
        assertEquals(expectedFull, readerService.readFromFile(ACTIVITIES_PATH));
    }

    @Test
    void read_FromFileInvalidPath_NotOk() {
        assertThrows(RuntimeException.class, () -> readerService.readFromFile(WRONG_PATH_1));
        assertThrows(RuntimeException.class, () -> readerService.readFromFile(WRONG_PATH_2));
    }
}
