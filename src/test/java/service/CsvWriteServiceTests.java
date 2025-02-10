package service;

import static org.junit.jupiter.api.Assertions.*;

import dao.TransactionDaoImpl;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strategy.OperationStrategyImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class CsvWriteServiceTests {
  private CsvWriteService csvWriteService;
  private TransactionDaoImpl transactionDao;
  private CsvReadService csvReadService;
  private CsvReportGenerator csvReportGenerator;
  private Path tempFilePath;

  @BeforeEach
  void setUp() throws IOException {
    // Create a temporary file for testing
    tempFilePath = Files.createTempFile("test_output", ".csv");
    tempFilePath.toFile().deleteOnExit(); // Ensure cleanup after test execution

    transactionDao = new TransactionDaoImpl();
    csvReportGenerator = new CsvReportGenerator();
    csvWriteService = new CsvWriteService();
    csvReadService = new CsvReadService();
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
    // Arrange: Create transactions
    transactionDao.saveTransaction("Apple", 10);
    transactionDao.saveTransaction("Banana", 5);

    // Act: Write report to temporary file
    csvWriteService.writeReport(tempFilePath.toString(), csvReportGenerator.generateReport());

    // Assert: Read and verify written content
    List<String> lines = Files.readAllLines(tempFilePath);
    assertEquals("fruit,quantity", lines.get(0), "First line should be header");
    assertEquals("Apple,10", lines.get(1), "First fruit transaction should match");
    assertEquals("Banana,5", lines.get(2), "Second fruit transaction should match");
  }
}
