package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReaderServiceTest {
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readerService_validData_ok() {
        String pathToTestCsvFile = "src/test/resources/validInput.txt";
        List<String> expectedValidValue = new ArrayList<>(List.of(
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        ));
        List<String> actualValue = readerService.readFromFile(pathToTestCsvFile);

        assertEquals(expectedValidValue, actualValue);
    }

    @Test
    public void readerService_emptyInput_ok() {
        String pathToTestCsvFile = "src/test/resources/emptyInput.txt";
        List<String> output = readerService.readFromFile(pathToTestCsvFile);

        assertTrue(output.isEmpty());
    }

    @Test
    public void readerService_nonexistentPath_notOk() {
        String pathToTestCsvFile = "src/test/resources/emptyInp.txt";

        assertThrows(RuntimeException.class, () -> readerService.readFromFile(pathToTestCsvFile));
    }
}
