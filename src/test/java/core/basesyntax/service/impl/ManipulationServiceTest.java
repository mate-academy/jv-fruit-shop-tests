package core.basesyntax.service.impl;

import core.basesyntax.db.*;
import core.basesyntax.model.*;
import core.basesyntax.service.*;
import core.basesyntax.strategy.*;
import core.basesyntax.strategy.impl.*;
import java.util.*;
import org.junit.*;

public class ManipulationServiceTest {
    private static ManipulationService manipulationService;
    private static final List<Transaction> transactionList;

    static {
        transactionList = new ArrayList<>();
        transactionList.add(new Transaction(Transaction.Operation.BALANCE,
                new Fruit("banana"), 20));
        transactionList.add(new Transaction(Transaction.Operation.BALANCE,
                new Fruit("apple"), 100));
        transactionList.add(new Transaction(Transaction.Operation.SUPPLY,
                new Fruit("banana"), 100));
        transactionList.add(new Transaction(Transaction.Operation.PURCHASE,
                new Fruit("banana"), 13));
        transactionList.add(new Transaction(Transaction.Operation.RETURN,
                new Fruit("apple"), 10));
        transactionList.add(new Transaction(Transaction.Operation.PURCHASE,
                new Fruit("apple"), 20));
        transactionList.add(new Transaction(Transaction.Operation.PURCHASE,
                new Fruit("banana"), 5));
        transactionList.add(new Transaction(Transaction.Operation.SUPPLY,
                new Fruit("banana"), 50));
    }

    @BeforeClass
    public static void beforeClass() {
        Map<Transaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Transaction.Operation.BALANCE, new BalanceOperation());
        operationHandlerMap.put(Transaction.Operation.SUPPLY, new SupplyOperation());
        operationHandlerMap.put(Transaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlerMap.put(Transaction.Operation.RETURN, new ReturnOperation());
        Strategy strategy = new StrategyImpl(operationHandlerMap);
        manipulationService = new ManipulationServiceImpl(strategy);
        ReportService reportService = new ReportServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void manipulationData_emptyData_notOk() {
        List<Transaction> emptyList = new ArrayList<>();
        manipulationService.manipulation(emptyList);
    }

    @Test
    public void manipulationData_correctData_ok() {
        Map<Fruit, Integer> expectedData = new HashMap<>();
        expectedData.put(new Fruit("banana"), 152);
        expectedData.put(new Fruit("apple"), 90);
        Map<Fruit, Integer> actualData = Storage.fruits;
        manipulationService.manipulation(transactionList);
        Assert.assertEquals(expectedData.toString(), actualData.toString());
    }
}

