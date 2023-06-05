package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String PATH_TO_TEST_INPUT_CSV_FILE = "src/test/resources/testInput.csv";
    private static final String NOT_EXISTING_PATH = "/non_existing_path.txt";
    private ReaderServiceImpl readerService;

    @BeforeEach
    void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_existingFile_ok() {
        List<String> lines = List.of(
                "type,fruit,quantity",
                "b,orange,20",
                "b,strawberries,100"
        );
        List<String> result = readerService.readFromFile(PATH_TO_TEST_INPUT_CSV_FILE);
        assertEquals(lines, result);
    }

    @Test
    void readFromFile_nonExistingPath_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(NOT_EXISTING_PATH));
    }
}
