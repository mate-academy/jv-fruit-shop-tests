package core.basesyntax.service;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.OperationProcessorImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.*;
import org.junit.After;
import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

public class OperationProcessorTest<TransactionService> {
    private static final Fruit APPLE = new Fruit("apple");
    private final FruitDao fruitDao = new FruitDaoImpl();
    private final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap
            = new HashMap<>() {
        {
            put(FruitTransaction.Operation.BALANCE,
                    new BalanceOperationHandler(fruitDao));
            put(FruitTransaction.Operation.PURCHASE,
                    new PurchaseOperationHandler(fruitDao));
            put(FruitTransaction.Operation.SUPPLY,
                    new SupplyOperationHandler(fruitDao));
            put(FruitTransaction.Operation.RETURN,
                    new ReturnOperationHandler(fruitDao));
        }
    };
    private final OperationStrategy operationStrategy
            = new OperationStrategyImpl(operationHandlerMap);
    private final OperationProcessor operationProcessor = new OperationProcessorImpl(fruitDao, operationStrategy);

    private final FruitTransaction appleBalance = new FruitTransaction(
            FruitTransaction.Operation.BALANCE,
            APPLE,
            50);
    private final FruitTransaction appleSupply = new FruitTransaction(
            FruitTransaction.Operation.SUPPLY,
            APPLE,
            50);
    private final FruitTransaction applePurchase = new FruitTransaction(
            FruitTransaction.Operation.PURCHASE,
            APPLE,
            50);
    private final FruitTransaction appleReturn = new FruitTransaction(
            FruitTransaction.Operation.RETURN,
            APPLE,
            50);

    @Before
    public void setUp() throws Exception {
        Storage.fruits.put(APPLE, 0);
    }

    @Test
    public void calculate_balanceOperation_Ok() {
        operationProcessor.process(appleBalance);
        Integer actual = Storage.fruits.get(APPLE);
        Integer expected = 50;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void calculate_supplyOperation_Ok() {
        operationProcessor.process(appleSupply);
        Integer actual = Storage.fruits.get(APPLE);
        Integer expected = 50;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void calculate_returnOperation_Ok() {
        operationProcessor.process(appleReturn);
        Integer actual = Storage.fruits.get(APPLE);
        Integer expected = 50;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void calculate_purchaseOperation_Ok() {
        Storage.fruits.put(APPLE, 100);
        operationProcessor.process(applePurchase);
        Integer actual = Storage.fruits.get(APPLE);
        Integer expected = 50;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void calculate_purchaseOperation_purchaseValueMoreThanValueInStorage_NotOk() {
        operationProcessor.process(applePurchase);
        fail("If statement in storage less than purchase value "
                + " Runtime exception should be thrown");
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}
