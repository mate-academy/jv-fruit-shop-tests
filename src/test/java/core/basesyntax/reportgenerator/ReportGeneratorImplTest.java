package core.basesyntax.reportgenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperation;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.OperationStrategy;
import core.basesyntax.operation.OperationStrategyImpl;
import core.basesyntax.shopservice.ShopService;
import core.basesyntax.shopservice.ShopServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private static final FruitTransaction APPLE_BALANCE_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 50);
    private static final FruitTransaction BANANA_BALANCE_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20);
    private static final String EXPECTED_REPORT = "fruit,quantity";
    private static final FruitTransaction INVALID_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", -50);
    private static final String REPORT_DATA_FILE = "src/test/resources/expected_report.csv";
    private ReportGenerator reportGenerator;
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(handlers);

        shopService = new ShopServiceImpl(operationStrategy);
        reportGenerator = new ReportGeneratorImpl(shopService);
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void getReport_withBalanceData_returnsCorrectReport() throws IOException {
        List<FruitTransaction> bananaBalanceTransaction = List.of(BANANA_BALANCE_TRANSACTION);
        List<FruitTransaction> appleBalancetransaction = List.of(APPLE_BALANCE_TRANSACTION);
        shopService.process(bananaBalanceTransaction);
        shopService.process(appleBalancetransaction);

        String actualReport = reportGenerator.getReport().trim();
        String expectedReport = Files.readString(Path.of(REPORT_DATA_FILE));

        assertEquals(expectedReport, actualReport, "Generated report should match expected report");
    }

    @Test
    void generate_emptyStorage_returnsOnlyHeader() {
        String actualReport = reportGenerator.getReport().trim();
        assertEquals(EXPECTED_REPORT, actualReport,
                "Report for empty storage should only contain header");
    }

    @Test
    void getReport_withInvalidData_throwsException() {
        List<FruitTransaction> invalidTransactions = List.of(INVALID_TRANSACTION);
        assertThrows(IllegalArgumentException.class,
                () -> shopService.process(invalidTransactions),
                "Should throw exception when processing invalid transaction data");
    }
}
