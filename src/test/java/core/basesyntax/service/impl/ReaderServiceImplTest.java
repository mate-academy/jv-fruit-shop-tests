package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {

    private static ReaderService readerService;
    private static List<String> expected;
    private static final String INPUT_FILE = "src/main/resources/input_file.csv";
    private static final String MASSAGE = "Should throw runtime exception.";

    @BeforeAll
    public static void init() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void reader_fileNotExist_notOk() {
        expected = new ArrayList<>();
        String nonExistingFile = "not_exist";
        assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(nonExistingFile),
                MASSAGE);
    }

    @Test
    void reader_fileIsEmpty_notOk() {
        expected = new ArrayList<>();
        expected = readerService.readFromFile(INPUT_FILE);
        assertThrows(AssertionError.class,
                () -> assertTrue(expected.isEmpty()),
                MASSAGE);
    }

    @Test
    void reader_readFromFile_Ok() {
        expected = readerService.readFromFile(INPUT_FILE);
        List<String> actualList = new ArrayList<>();
        actualList.add("b,banana,20");
        actualList.add("b,apple,100");
        actualList.add("s,banana,100");
        actualList.add("p,banana,13");
        actualList.add("r,apple,10");
        actualList.add("p,apple,20");
        actualList.add("p,banana,5");
        actualList.add("s,banana,50");
        assertEquals(expected, actualList);
    }
}
