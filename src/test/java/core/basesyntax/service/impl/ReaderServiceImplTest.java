package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ReaderServiceImplTest {
    private final String INPUT_DATA_FILE_TEST = "src/test/resources/testData.csv";
    private static ReaderService readerService;

    @Before
    public void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_wrongPathToFile_notOk() {
        Assertions.assertThrows(
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
        Assertions.assertIterableEquals(expected, actual);
    }
}
