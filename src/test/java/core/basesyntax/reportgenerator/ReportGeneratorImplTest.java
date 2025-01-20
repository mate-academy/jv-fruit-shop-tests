package core.basesyntax.reportgenerator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperation;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.OperationStrategy;
import core.basesyntax.operation.OperationStrategyImpl;
import core.basesyntax.shopservice.ShopService;
import core.basesyntax.shopservice.ShopServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private static final String EXPECTED_LINE_FORMAT = "%s,%d";
    private static final String HEADER = "fruit,quantity";
    private static final String TEST_FRUIT = "banana";
    private static final int TEST_QUANTITY = 20;
    private static final FruitTransaction BALANCE_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, TEST_FRUIT, TEST_QUANTITY);
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

    @Test
    void getReport_withBalanceData_returnsFormattedReport() {
        List<FruitTransaction> transactions = List.of(BALANCE_TRANSACTION);
        shopService.process(transactions);

        String report = reportGenerator.getReport();
        String expectedLine = String.format(EXPECTED_LINE_FORMAT, TEST_FRUIT, TEST_QUANTITY);

        assertTrue(report.contains(HEADER), "Report should contain header");
        assertTrue(report.contains(expectedLine),
                "Report should contain correct fruit and quantity");
    }
}
