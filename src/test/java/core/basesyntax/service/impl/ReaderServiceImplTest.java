package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String INPUT_CSV_FILE_TEST = "src/test/resources/inputDataTest.csv";
    private static final String WRONG_PATH = "path";

    private static ReaderService reader;

    @BeforeAll
    static void beforeAll() {
        reader = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_wrongPath_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> reader.readFromFile(WRONG_PATH)
        );
    }

    @Test
    void readFromFile_correctFile_ok() {
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
        List<String> actual = reader.readFromFile(INPUT_CSV_FILE_TEST);
        assertIterableEquals(expected, actual);
    }
}
