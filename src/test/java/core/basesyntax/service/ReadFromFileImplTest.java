package core.basesyntax.service;

import static org.junit.Assert.assertThrows;

import core.basesyntax.service.impl.ReadFromFieImpl;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReadFromFileImplTest {
    private static final String INPUT = "src/test/resources/input.csv";
    private static final String INPUT_EMPTY = "src/test/resources/inputEmpty.csv";
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReadFromFieImpl();
    }

    @Test
    void readFile_correctFile_Ok() {
        List<String> expected = List.of("banana", "apple");
        List<String> actual = readerService.readFile(INPUT);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void readFile_incorrectPathToFile_NotOk() {
        assertThrows(RuntimeException.class, () -> readerService.readFile("src/test.csv"));
    }

    @Test
    void readFile_emptyFile_Ok() {
        List<String> expected = List.of();
        List<String> actual = readerService.readFile(INPUT_EMPTY);
        Assertions.assertEquals(expected, actual);
    }
}
