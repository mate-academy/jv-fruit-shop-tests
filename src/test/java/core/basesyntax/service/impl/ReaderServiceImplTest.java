package core.basesyntax.service.impl;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String INPUT_CSV_FILE_TEST = "src/test/resources/inputDataTest.csv";
    private static final String WRONG_PATH = "path";

    private ReaderServiceImpl reader;

    @BeforeEach
    void setUp() {
        reader = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_wrongPath_notOk() {
        Assertions.assertThrows(
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
        Assertions.assertIterableEquals(expected, actual);
    }
}
