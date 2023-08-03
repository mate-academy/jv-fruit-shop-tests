package core.basesyntax.service.impl;

import core.basesyntax.service.interfaces.ReaderService;
import org.junit.jupiter.api.Assertions;
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
        String input = "type,fruit,quantity\r\nb,banana,100\r\nb,apple,50\r\ns,banana,10";
        String transactionsFilePath = "src/test/resources/test_input.txt";
        String actualData = readerService.readFromFile(transactionsFilePath);
        Assertions.assertEquals(input, actualData, "Data read from file incorrectly!");
    }

    @Test
    void readFromNonExistingFile_ExceptionThrown() {
        String notExistingFilePath = "not_existing_path";
        RuntimeException actual = Assertions.assertThrows(RuntimeException.class, () -> {
            readerService.readFromFile(notExistingFilePath);
        });
        String expectedMessage = "Oops! File not found: not_existing_path";
        Assertions.assertEquals(expectedMessage, actual.getMessage());
    }
}
