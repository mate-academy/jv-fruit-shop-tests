package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReadService;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ReadServiceTest {
    private static final String INPUT_FILE_TEST = "src/test/resources/inputDataTest.csv";
    private static final String EMPTY_FILE_NAME = "";
    private final ReadService reader = new ReadServiceImpl();

    @Test
    void readFile_inputData_ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,orange,120",
                "b,lemon,50",
                "p,lemon,10",
                "r,lemon,2",
                "p,orange,25",
                "s,orange,55",
                "p,orange,10",
                "p,lemon,5",
                "s,lemon,30");
        List<String> actual = reader.readFile(INPUT_FILE_TEST);
        assertLinesMatch(expected, actual, "Test failed! You should returned next list "
                + expected + " but you returned "
                + actual.toString());
    }

    @Test
    void readFile_fileNotFound_notOk() {
        assertThrows(RuntimeException.class, () -> reader.readFile(EMPTY_FILE_NAME));
    }
}
