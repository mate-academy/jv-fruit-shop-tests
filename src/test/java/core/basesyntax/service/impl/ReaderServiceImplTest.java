package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReaderServiceImplTest {
    private static final String INPUT_DATA_FILE_TEST = "src/test/resources/testData.csv";
    private static ReaderService readerService;

    @BeforeAll
    public static void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_wrongPathToFile_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> readerService.readFromFile("wrong path")
        );
    }

    @Test
    public void readFromFile_validInput_ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100"
        );
        List<String> actual = readerService.readFromFile(INPUT_DATA_FILE_TEST);
        assertIterableEquals(expected, actual);
    }
}
