package core.basesyntax.service.impl;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    public static final String PATH_TO_INPUT_FILE = "src/test/resources/testInputFile.csv";
    private ReaderServiceImpl readerService;

    @BeforeEach
    void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_wrongPath_notOk() {
        Assertions.assertThrows(
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
                "p,banana,13");
        List<String> actual = readerService.readFromFile(PATH_TO_INPUT_FILE);
        Assertions.assertIterableEquals(expected, actual);
    }
}
