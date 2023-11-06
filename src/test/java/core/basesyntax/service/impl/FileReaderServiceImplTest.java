package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.FileReaderService;
import service.impl.FileReaderServiceImpl;

public class FileReaderServiceImplTest {
    private static final String INPUT_FILE = "src/test/resources/test_input.csv";
    private static final String NON_EXISTENT_INPUT_FILE = "src/test/Resources/Shop_input.csv";

    private static FileReaderService fileReaderService;

    @BeforeAll
    static void beforeAll() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    void readFromFile_readEverythingFromValidFile_ok() {
        List<String> expected = List.of(
                "type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        List<String> actual = fileReaderService.readFromFile(INPUT_FILE);
        assertEquals(expected, actual);
    }

    @Test
    void readFromFile_nonExistentFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readFromFile(NON_EXISTENT_INPUT_FILE));
    }
}
