package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static ReaderService readerService;

    @BeforeAll
    static void setup() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_successful() {
        String correctAddress = "testInput.csv";
        List<String> expected = List.of("Line 1", "Line 2", "Line 3");
        List<String> actual = readerService.readFromFile(correctAddress);
        assertEquals(expected, actual);
    }

    @Test
    void readFromFile_nullInput_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            readerService.readFromFile(null);
        });
    }

    @Test
    void readFromFile_throwException_Ok() {
        String incorrectAddress = "dontcorrect.txt";
        assertThrows(RuntimeException.class, () -> {
            readerService.readFromFile(incorrectAddress);
        });
    }
}
