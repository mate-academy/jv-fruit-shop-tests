package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitTransaction;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvReaderServiceTest {
    private static final String TEST_CSV_FILE_PATH = "src/test/resources/test-transactions.csv";
    private CsvReaderService csvReaderService;

    @BeforeEach
    public void setUp() {
        csvReaderService = new CsvReaderService();
    }

    @Test
    public void readTransactions_ValidFile_ReturnsCorrectList() throws IOException {
        List<FruitTransaction> transactions = csvReaderService.readTransactions(TEST_CSV_FILE_PATH);
        assertEquals(3, transactions.size());

        FruitTransaction transaction1 = transactions.get(0);
        assertEquals(FruitTransaction.Operation.PURCHASE, transaction1.getOperation());
        assertEquals("Apple", transaction1.getFruit());
        assertEquals(5, transaction1.getQuantity());
    }

    @Test
    public void readTransactions_InvalidFile_ThrowsFileNotFoundException() {
        assertThrows(RuntimeException.class, () -> {
            csvReaderService.readTransactions("nonexistent-file.csv");
        });
    }

    @Test
    public void readTransactions_InvalidDataInFile_ThrowsNumberFormatException() {
        assertThrows(IllegalArgumentException.class, () -> {
            try {
                csvReaderService.readTransactions("src/test/resources/invalid-transactions.csv");
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid data in CSV file", e);
            }
        });
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Path.of("test.csv"));
    }
}
