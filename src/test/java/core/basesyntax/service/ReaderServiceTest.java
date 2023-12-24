package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceTest {
    private static final String VALID_FILE_PATH = "src/test/resources/validData.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/emptyFile.csv";
    private static final List<String> TEST_LIST = List.of("type,fruit,quantity",
                                                        "b,banana,20",
                                                        "b,apple,100",
                                                        "b,avocado,8",
                                                        "b,mango,14",
                                                        "s,banana,100",
                                                        "p,banana,13",
                                                        "r,apple,10");
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_validPath_ok() {
        List<String> actual = readerService.readFromFile(VALID_FILE_PATH);
        assertEquals(TEST_LIST, actual);
    }

    @Test
    void readFromFile_invalidPath_notOk() {
        assertThrows(NullPointerException.class, () -> readerService.readFromFile(null));
        assertThrows(RuntimeException.class, () -> readerService.readFromFile(""));
    }

    @Test
    void readFromFile_emptyFile_ok() {
        List<String> expected = Collections.emptyList();
        List<String> actual = readerService.readFromFile(EMPTY_FILE_PATH);
        actual.removeIf(String::isEmpty);
        assertEquals(expected, actual);
    }
}
