package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import core.basesyntax.dao.CsvFileWriter;
import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TransactionProcessingServiceTest {

    private TransactionProcessingService transactionProcessingService;

    @Mock
    private FruitTransactionParser parser;

    @Mock
    private FruitShopService fruitShopService;

    @Mock
    private CsvFileWriter fileWriter;

    @Mock
    private ReportGeneratorService reportGeneratorService;

    @Mock
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionProcessingService = new TransactionProcessingService(
                parser,
                fruitShopService,
                fileWriter,
                reportGeneratorService
        );
        transactionProcessingService.setInventoryService(inventoryService);
    }

    @Test
    void processTransactions_shouldReadAndParseTransactions() {
        List<String> fileContent = Arrays.asList("apple,s,10", "banana,p,5");
        String targetFilePath = "target.csv";

        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.OperationType.SUPPLY, "apple", 10),
                new FruitTransaction(FruitTransaction.OperationType.ADD, "banana", 5)
        );

        when(parser.parse(fileContent)).thenReturn(transactions);

        transactionProcessingService.processTransactions(fileContent, targetFilePath);

        verify(parser).parse(fileContent);
        verify(fruitShopService).processTransactions(transactions);
        verify(fileWriter).writeFile(targetFilePath, transactionProcessingService.generateReport());
    }

    @Test
    void processTransactions_shouldThrowExceptionWhenParseFails() {
        List<String> fileContent = Arrays.asList("dummy");
        String targetFilePath = "target.csv";

        when(parser.parse(fileContent)).thenThrow(new RuntimeException("Parse error"));

        assertThrows(RuntimeException.class, () -> {
            transactionProcessingService.processTransactions(fileContent, targetFilePath);
        });
    }
}
