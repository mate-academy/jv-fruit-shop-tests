package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.data.ProcessDataService;
import core.basesyntax.strategy.AdditionHandler;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.SubtractionHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProcessDataImplTest {
    private static ProcessDataService processDataService;
    private static final Map<Operation, OperationHandler> operationsMap = new HashMap<>();

    @BeforeClass
    public static void setUp() {
        operationsMap.put(Operation.BALANCE, new BalanceHandler());
        operationsMap.put(Operation.RETURN, new AdditionHandler());
        operationsMap.put(Operation.SUPPLY, new AdditionHandler());
        operationsMap.put(Operation.PURCHASE, new SubtractionHandler());
        OperationStrategy operationStrategy = new OperationStrategy(operationsMap);
        processDataService = new ProcessDataImpl(operationStrategy);
    }

    @Test
    public void processData_goodDataBalance_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setOperation(Operation.BALANCE);
        fruitTransaction.setQuantity(100);
        List<FruitTransaction> fruitTransactionList = List.of(fruitTransaction);
        processDataService.process(fruitTransactionList);
    }

    @Test
    public void processData_goodDataPurchase_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setOperation(Operation.PURCHASE);
        fruitTransaction.setQuantity(100);
        List<FruitTransaction> fruitTransactionList = List.of(fruitTransaction);
        processDataService.process(fruitTransactionList);
    }

    @Test
    public void processData_goodDataReturn_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setOperation(Operation.RETURN);
        fruitTransaction.setQuantity(100);
        List<FruitTransaction> fruitTransactionList = List.of(fruitTransaction);
        processDataService.process(fruitTransactionList);
    }

    @Test(expected = RuntimeException.class)
    public void processData_emptyDataOperation_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(100);
        List<FruitTransaction> fruitTransactionList = List.of(fruitTransaction);
        processDataService.process(fruitTransactionList);
    }

    @Test(expected = RuntimeException.class)
    public void processData_emptyDataFruit_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(Operation.RETURN);
        fruitTransaction.setQuantity(100);
        List<FruitTransaction> fruitTransactionList = List.of(fruitTransaction);
        processDataService.process(fruitTransactionList);
    }

    @Test(expected = RuntimeException.class)
    public void processData_emptyDataQuality_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setOperation(Operation.RETURN);
        List<FruitTransaction> fruitTransactionList = List.of(fruitTransaction);
        processDataService.process(fruitTransactionList);
    }

    @Test
    public void processData_emptyData_Ok() {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        processDataService.process(fruitTransactionList);
    }
}
