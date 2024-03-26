package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    public static final String PATH_TO_INPUT_FILE_TEST = "src/test/resources/testInputFile.csv";
    private static ReaderServiceImpl readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_wrongPathToFile_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> readerService.readFromFile("Wrong Path")
        );
    }

    @Test
    void readerService_validInput_ok() {
        List<String> expected = List.of(
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "s,apple,13");
        List<String> actual = readerService.readFromFile(PATH_TO_INPUT_FILE_TEST);
        assertIterableEquals(expected, actual);
    }
}
