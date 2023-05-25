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
                "Should throw runtime exception.");
    }

    @Test
    void reader_fileIsEmpty_notOk() {
        expected = new ArrayList<>();
        expected = readerService.readFromFile("src/main/resources/input_file.csv");
        assertThrows(AssertionError.class,
                () -> assertTrue(expected.isEmpty()),
                "Should throw runtime exception.");
    }

    @Test
    void reader_readFromFile_ok() {
        expected = readerService.readFromFile("src/main/resources/input_file.csv");
        List<String> actualList = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        assertEquals(expected, actualList);
    }
}
