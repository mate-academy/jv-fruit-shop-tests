package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.interfaces.ReaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {

    private ReaderService readerService;

    @BeforeEach
    public void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_OK() {
        String expected = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,100" + System.lineSeparator()
                + "b,apple,50" + System.lineSeparator()
                + "s,banana,10";
        String transactionsFilePath = "src/test/resources/test_input.txt";
        String actual = readerService.readFromFile(transactionsFilePath);
        assertEquals(expected, actual, "Data read from file incorrectly!");
    }

    @Test
    void readFromNonExistingFile_ExceptionThrown() {
        String notExistingFilePath = "not_existing_path";
        RuntimeException actual = assertThrows(RuntimeException.class, () -> {
            readerService.readFromFile(notExistingFilePath);
        });
        String expectedMessage = "Oops! File not found: not_existing_path";
        assertEquals(expectedMessage, actual.getMessage());
    }
}
