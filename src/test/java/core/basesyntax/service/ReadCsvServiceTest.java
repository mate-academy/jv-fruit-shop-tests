package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.impl.ReadCsvServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ReadCsvServiceTest {
    private final ReadCsvService readCsvService = new ReadCsvServiceImpl();

    @Test
    void readFromFile_correctPath_Ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13");

        String path = "src/test/resources/inputData_Ok.csv";
        List<String> actual = readCsvService.readFromFile(path);
        assertEquals(expected, actual, "The data from the file does not match"
                + " the expected result, result must be - "
                + expected
                + " but it's "
                + actual);
    }

    @Test
    void readFromFile_notExistPath_NotOk() {
        String path = "src/test/resources/notExistPath_NotOk.csv";
        assertThrows(RuntimeException.class, () -> readCsvService.readFromFile(path),
                "Test failed! Exception should be thrown if path is not exist");
    }

    @Test
    void readFromFile_NullPath_NotOk() {
        assertThrows(RuntimeException.class, () -> readCsvService.readFromFile(null),
                "Test failed! Exception should be thrown if path is NULL");
    }

    @Test
    void readFromFile_fileEmpty_Ok() {
        String path = "src/test/resources/emptyFile_Ok.csv";
        List<String> actual = readCsvService.readFromFile(path);
        assertTrue(actual.isEmpty(), "The result must be empty");
    }
}
