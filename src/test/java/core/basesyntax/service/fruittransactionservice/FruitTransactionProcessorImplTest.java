package core.basesyntax.service.fruittransactionservice;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operationsservice.BalanceFruitOperationHandler;
import core.basesyntax.service.operationsservice.FruitOperationHandler;
import core.basesyntax.service.operationsservice.PurchaseFruitOperationHandler;
import core.basesyntax.service.operationsservice.ReturnFruitOperationHandler;
import core.basesyntax.service.operationsservice.SupplyFruitOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import org.junit.Assert;
import org.junit.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FruitTransactionProcessorImplTest {
    Map<FruitTransaction.Operation, FruitOperationHandler> operationsMap = new HashMap<>();
    FruitDao fruitDao = new FruitDaoImpl();
    OperationStrategy operationStrategy = new OperationStrategyImpl(operationsMap);
    FruitTransactionProcessor fruitTransaction = new FruitTransactionProcessorImpl(operationStrategy);

    @Test
    public void makeDailyFruitsUpdateIncludingAllTypesOfOperations() {
        operationsMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceFruitOperationHandler(fruitDao));
        operationsMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseFruitOperationHandler(fruitDao));
        operationsMap.put(FruitTransaction.Operation.RETURN,
                new ReturnFruitOperationHandler(fruitDao));
        operationsMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyFruitOperationHandler(fruitDao));

        FruitTransaction bananaTransaction = new FruitTransaction();
        bananaTransaction.setFruit("apple");
        bananaTransaction.setQuantity(120);
        bananaTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        FruitTransaction appleTransaction = new FruitTransaction();
        appleTransaction.setFruit("apple");
        appleTransaction.setQuantity(20);
        appleTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        FruitTransaction orangeTransaction = new FruitTransaction();
        orangeTransaction.setFruit("apple");
        orangeTransaction.setQuantity(40);
        orangeTransaction.setOperation(FruitTransaction.Operation.RETURN);
        FruitTransaction kiwiTransaction = new FruitTransaction();
        kiwiTransaction.setFruit("apple");
        kiwiTransaction.setQuantity(10);
        kiwiTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        List<FruitTransaction> listOfTransactions = List.of(bananaTransaction, appleTransaction, orangeTransaction, kiwiTransaction);

        fruitTransaction.makeDailyFruitsUpdate(listOfTransactions);
        Map<String, Integer> actualFruitsMap = FruitsStorage.fruits;
        Map<String, Integer> expectedFruitsMap = new HashMap<>();
        expectedFruitsMap.put("apple", 150);
        Assert.assertEquals(expectedFruitsMap, actualFruitsMap);
    }
}
