package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceTest {
    private static final String CORRECT_FILE_PATH = "src/test/resources/input.csv";
    private static final String NOT_EXISTING_FILE_PATH = "src/test/resources/notExisting.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/empty.csv";
    private static final ReaderService readerService = new ReaderServiceImpl();
    private static final List<String> CORRECT_FILE_CONTENT = new ArrayList<>();

    @BeforeEach
    void setUp() {
        CORRECT_FILE_CONTENT.clear();
        CORRECT_FILE_CONTENT.add("type,fruit,quantity");
        CORRECT_FILE_CONTENT.add("b,banana,20");
        CORRECT_FILE_CONTENT.add("b,apple,100");
        CORRECT_FILE_CONTENT.add("s,banana,100");
        CORRECT_FILE_CONTENT.add("p,banana,13");
        CORRECT_FILE_CONTENT.add("r,apple,10");
        CORRECT_FILE_CONTENT.add("p,apple,20");
        CORRECT_FILE_CONTENT.add("p,banana,5");
        CORRECT_FILE_CONTENT.add("s,banana,50");
    }

    @Test
    void dataReadTest_correctFile_ok() {
        List<String> result = readerService.readFromFile(CORRECT_FILE_PATH);
        assertEquals(CORRECT_FILE_CONTENT, result);
    }

    @Test
    void dataReadTest_notExistingFile_noTok() {
        assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(NOT_EXISTING_FILE_PATH));
    }

    @Test
    void dataReaderTest_emptyFile_notOk() {
        assertThrows(RuntimeException.class, () -> readerService.readFromFile(EMPTY_FILE_PATH));
    }
}
