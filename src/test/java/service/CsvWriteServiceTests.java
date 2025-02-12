package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dao.TransactionDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import java.io.IOException;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;

class CsvWriteServiceTests {
    private CsvWriteService csvWriteService;
    private TransactionDaoImpl transactionDao;
    private CsvReportGenerator csvReportGenerator;
    private Path tempFilePath;

    @BeforeEach
    void setUp() throws IOException {
        tempFilePath = Files.createTempFile("test_output", ".csv");
        tempFilePath.toFile().deleteOnExit();
        transactionDao = new TransactionDaoImpl();
        csvReportGenerator = new CsvReportGenerator();
        csvWriteService = new CsvWriteService();
        transactionDao.clearTransactions();
    }

    @Test
    void writeReport_WhenInvalidFileName_ThrowsException() {
        String invalidFileName = "";
        Exception exception = assertThrows(
                RuntimeException.class,
                () -> csvWriteService.writeReport(invalidFileName, csvReportGenerator.generateReport())
        );

        assertTrue(exception.getMessage().contains("Error writing to CSV file: "),
                "Expected exception message to contain 'Error writing to CSV file'");
    }

    @Test
    void writeReport_WhenValidTransactions_WritesSuccessfully() throws IOException {
        transactionDao.saveTransaction("Apple", 10);
        transactionDao.saveTransaction("Banana", 5);
        csvWriteService.writeReport(tempFilePath.toString(), csvReportGenerator.generateReport());
        List<String> lines = Files.readAllLines(tempFilePath);
        assertEquals("fruit,quantity", lines.get(0), "First line should be header");
        assertEquals("Apple,10", lines.get(1), "First fruit transaction should match");
        assertEquals("Banana,5", lines.get(2), "Second fruit transaction should match");
    }
}
