package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.ReportService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseHandler;
import core.basesyntax.strategy.impl.ReturnHandler;
import core.basesyntax.strategy.impl.SupplyHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private static FruitService fruitService;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationsMap = new HashMap<>();
        operationsMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationsMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationsMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationsMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        fruitService = new FruitServiceImpl(new OperationStrategyImpl(operationsMap));
    }

    @Test
    void processTransactions_testData_Ok() {
        ReportService reportService = new ReportServiceImpl();
        List<String> testData = new ArrayList<>(Arrays.asList(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"));
        String reportExpected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,152"
                + System.lineSeparator()
                + "apple,90"
                + System.lineSeparator();
        fruitService.processTransactions(new ParserInFruitTransactionImpl().parseData(testData));
        assertEquals(reportExpected, reportService.reportStorage());
    }
}
