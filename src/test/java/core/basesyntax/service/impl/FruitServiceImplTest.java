package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportService;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private static FruitServiceImpl fruitService;

    @BeforeAll
    static void beforeAll() {

        Map<FruitTransaction.Operation, OperationHandler> operationsMap = new HashMap<>();
        operationsMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationsMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationsMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        operationsMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        Storage.fruitStorage.clear();
        fruitService = new FruitServiceImpl(new OperationStrategy(operationsMap));
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
        String reportExpected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        fruitService.executeTransactions(new TransactionServiceImpl().parseTransactions(testData));
        Assertions.assertEquals(reportExpected, reportService.createReport());
    }
}
