package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static ReaderService readerService;

    @BeforeAll
    public static void init() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void reader_fileNotExist_notOk() {
        String nonExistingFile = "not_exist";
        assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(nonExistingFile),
                "Should throw runtime exception.");
    }

    @Test
    void reader_fileIsEmpty_notOk() {
        List<String> actual = readerService.readFromFile("src/test/resources/input_file_test.csv");
        assertThrows(AssertionError.class,
                () -> assertTrue(actual.isEmpty()),
                "Should throw runtime exception.");
    }

    @Test
    void readFromFile_validInput_ok() {
        List<String> actual = readerService.readFromFile("src/test/resources/input_file_test.csv");
        List<String> expected = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        assertEquals(expected, actual);
    }
}
