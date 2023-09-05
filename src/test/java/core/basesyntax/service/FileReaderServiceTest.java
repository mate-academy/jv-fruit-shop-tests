package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderServiceTest {
    private static final String VALID_DATA_PATH = "src/test/resources/ValidDataFile";
    private static final String INVALID_DATA_PATH = "src/test/resources/InvalidDataFile";
    private static final String EXCEPTION_MESSAGE = "Null Pointer Exception should be thrown here";
    private static FileReaderService fileReaderService;

    @BeforeAll
    static void beforeAll() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    void readFromFile_validData_Ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10");
        List<String> actual = fileReaderService.readFromFile(VALID_DATA_PATH);
        assertEquals(expected, actual);
    }

    @Test
    void readFromFile_invalidData_Ok() {
        List<String> expected = List.of("type,fruit,quantity,<<()>>",
                "B,banana,20", "b,apple,100", "s,banana,100", "p,banana,14fddf",
                "r,apple,1000000000000000000");
        List<String> actual = fileReaderService.readFromFile(INVALID_DATA_PATH);
        assertEquals(expected, actual);
    }

    @Test
    void readFromFile_NullInput_NotOk() {
        assertThrows(NullPointerException.class, () ->
                fileReaderService.readFromFile(null), EXCEPTION_MESSAGE);
    }
}
