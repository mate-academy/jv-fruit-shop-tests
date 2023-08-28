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
        String correctAddress = "src/main/resources/input.csv";
        List<String> expected = List.of("Operation,Fruit,Quantity",
                "s,banana,100",
                "s,apple,100",
                "r,banana,10",
                "r,apple,10",
                "p,banana,10");
        List<String> actual = readerService.readFromFile(correctAddress);
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void readFromFile_nullInput_throwsException() {
        assertThrows(RuntimeException.class, () -> {
            readerService.readFromFile(null);
        });
    }

    @Test
    void readFromFile_incorrectAddress_throwsException() {
        String incorrectAddress = "dontcorrect.txt";
        assertThrows(RuntimeException.class, () -> {
            readerService.readFromFile(incorrectAddress);
        });
    }
}
