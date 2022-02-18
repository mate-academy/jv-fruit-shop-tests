package core.basesyntax;

import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataProcessingService;
import core.basesyntax.service.ReportCreatorService;
import core.basesyntax.service.impl.DataProcessingServiceImpl;
import core.basesyntax.service.impl.ReportCreatorServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceHandlerService;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseHandlerService;
import core.basesyntax.strategy.impl.ReturnHandlerService;
import core.basesyntax.strategy.impl.SupplyHandlerService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataProcessingServiceTest {
    private static final String SEPARATOR = ",";
    private static DataProcessingService dataProcessingService;
    private static final List<FruitTransaction> fruitTransaction;

    static {
        fruitTransaction = new ArrayList<>();
        fruitTransaction.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("banana"), 10));
        fruitTransaction.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("apple"), 20));
        fruitTransaction.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("cherry"), 100));
        fruitTransaction.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                new Fruit("apple"), 70));
        fruitTransaction.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                new Fruit("banana"), 80));
        fruitTransaction.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                new Fruit("banana"), 10));
        fruitTransaction.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                new Fruit("apple"), 30));
        fruitTransaction.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                new Fruit("apple"), 20));
        fruitTransaction.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                new Fruit("banana"), 20));
        fruitTransaction.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                new Fruit("banana"), 40));
    }

    @BeforeClass
    public static void beforeClass() {
        Map<FruitTransaction.Operation, OperationHandler> operationServiceMap = new HashMap<>();
        operationServiceMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandlerService());
        operationServiceMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandlerService());
        operationServiceMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseHandlerService());
        operationServiceMap.put(FruitTransaction.Operation.RETURN, new ReturnHandlerService());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationServiceMap);
        dataProcessingService = new DataProcessingServiceImpl(operationStrategy);
        ReportCreatorService reportCreatorService = new ReportCreatorServiceImpl();
    }

    @Test
    public void processingDate_correctData_ok() {
        Map<Fruit, Integer> expectedMap = new HashMap<>();
        expectedMap.put(new Fruit("banana"), 100);
        expectedMap.put(new Fruit("apple"), 100);
        expectedMap.put(new Fruit("cherry"), 100);
        Map<Fruit, Integer> actualMap = Storage.fruitBalance;
        dataProcessingService.processingDate(fruitTransaction);
        Assert.assertEquals(expectedMap.toString(), actualMap.toString());
    }

    @Test
    public void processingDate_emptyData_notOk() {
        List<FruitTransaction> emptyReport = new ArrayList<>();
        try {
            dataProcessingService.processingDate(emptyReport);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown is pats was wrong");
    }
}
