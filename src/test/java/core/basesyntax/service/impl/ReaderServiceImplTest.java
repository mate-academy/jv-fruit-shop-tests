package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReaderServiceImplTest {
    private static final String PATH_TO_FILE = "src/resources/testInput.csv";
    private static final String FAKE_PATH_TO_FILE = "src/resourses/testFile12.csv";

    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_validPathToFile_ok() {
        List<String> expected = List.of(
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );
        List<String> actual = readerService.readFromFile(PATH_TO_FILE);
        assertEquals(expected, actual);
    }

    @Test
    void readFromFile_fakePathToFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(FAKE_PATH_TO_FILE));
    }
}
