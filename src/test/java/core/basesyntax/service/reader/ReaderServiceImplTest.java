package core.basesyntax.service.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String CORRECT_INPUT_PATH = "src/test/resources/correctInput.csv";
    private static final String EMPTY_INPUT_PATH = "src/test/resources/emptyInput.csv";
    private static final String NONEXISTENT_INPUT_PATH = "src/nonexistentInput.csv";
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_correctInput_ok() {
        List<String> lines = readerService.readFromFile(CORRECT_INPUT_PATH);
        int actualSize = 9; 
        String actualString = "b,banana,20"; 
        assertEquals(actualSize,lines.size());
        assertEquals(actualString,lines.get(1));
    }

    @Test
    void readFromFile_emptyInput_ok() {
        List<String> lines = readerService.readFromFile(EMPTY_INPUT_PATH);
        int actual = 0;
        assertEquals(actual,lines.size());
    }

    @Test
    void readFromFile_nonexistentInput_notOk() {
        assertThrows(RuntimeException.class, () ->
                readerService.readFromFile(NONEXISTENT_INPUT_PATH));
    }
}
