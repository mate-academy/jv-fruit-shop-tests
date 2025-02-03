package service;

import dao.TransactionDaoImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

public class ReadServiceTests {
    private CsvWriteService csvWriteService;
    private TransactionDaoImpl transactionDao;
    private CsvParseService csvParserService;
    private CsvReadService csvReadService;

    @BeforeEach
    void setUp() {
        transactionDao = new TransactionDaoImpl();
        csvWriteService = new CsvWriteService(transactionDao);
        csvParserService = new CsvParseService();
        csvReadService = new CsvReadService(csvParserService);
    }

    @Test
    void parseTransaction_WhenWrongLine() {
        String wrongFileName = "";
        String filePath = Paths.get("src", "main", "resources", wrongFileName).toString();
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            csvReadService.readTransactionsFromCsv(wrongFileName);
        });

        Assertions.assertTrue(
                exception.getMessage().contains("Error reading CSV file: " + filePath)
        );
    }
}
