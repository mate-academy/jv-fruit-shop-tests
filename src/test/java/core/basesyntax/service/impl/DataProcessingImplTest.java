package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataProcessing;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operation.BalanceHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseHandler;
import core.basesyntax.strategy.operation.ReturnHandler;
import core.basesyntax.strategy.operation.SupplyHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class DataProcessingImplTest {
    private final FruitDao fruitDao = new FruitDaoImpl();

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void convertDataIntoTransaction_Ok() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler(fruitDao));

        List<String> data = new ArrayList<>();
        data.add("type,fruit,quantity");
        data.add("b,banana,20");
        data.add("b,apple,100");
        data.add("s,banana,100");
        data.add("p,banana,13");
        data.add("r,apple,10");
        data.add("p,apple,20");
        data.add("p,banana,5");
        data.add("s,banana,50");

        FruitTransaction transaction1 = new FruitTransaction();
        transaction1.setOperation(FruitTransaction.Operation.BALANCE);
        transaction1.setFruit("banana");
        transaction1.setQuantity(20);

        FruitTransaction transaction2 = new FruitTransaction();
        transaction2.setOperation(FruitTransaction.Operation.BALANCE);
        transaction2.setFruit("apple");
        transaction2.setQuantity(100);

        FruitTransaction transaction3 = new FruitTransaction();
        transaction3.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction3.setFruit("banana");
        transaction3.setQuantity(100);

        FruitTransaction transaction4 = new FruitTransaction();
        transaction4.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction4.setFruit("banana");
        transaction4.setQuantity(13);

        FruitTransaction transaction5 = new FruitTransaction();
        transaction5.setOperation(FruitTransaction.Operation.RETURN);
        transaction5.setFruit("apple");
        transaction5.setQuantity(10);

        FruitTransaction transaction6 = new FruitTransaction();
        transaction6.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction6.setFruit("apple");
        transaction6.setQuantity(20);

        FruitTransaction transaction7 = new FruitTransaction();
        transaction7.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction7.setFruit("banana");
        transaction7.setQuantity(5);

        FruitTransaction transaction8 = new FruitTransaction();
        transaction8.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction8.setFruit("banana");
        transaction8.setQuantity(50);

        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(transaction1);
        expected.add(transaction2);
        expected.add(transaction3);
        expected.add(transaction4);
        expected.add(transaction5);
        expected.add(transaction6);
        expected.add(transaction7);
        expected.add(transaction8);

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        DataProcessing dataProcessing = new DataProcessingImpl(operationStrategy);
        List<FruitTransaction> actual = dataProcessing.convertDataIntoTransaction(data);

        assertEquals(expected, actual);
    }
}
