package core.basesyntax.sevrice.impl;

import static org.junit.Assert.fail;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.sevrice.FruitService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.strategyimpl.BalanceOperationHandler;
import core.basesyntax.strategy.strategyimpl.OperationStrategyImpl;
import core.basesyntax.strategy.strategyimpl.PurchaseOperationHandler;
import core.basesyntax.strategy.strategyimpl.ReturnOperationHandler;
import core.basesyntax.strategy.strategyimpl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitServiceImplTest {
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
    private final FruitService fruitService = new FruitServiceImpl(fruitDao, operationStrategy);

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
    public void balanceOperation_Ok() {
        fruitService.calculate(appleBalance);
        Integer actual = Storage.fruits.get(APPLE);
        Integer expected = 50;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void supplyOperation_Ok() {
        fruitService.calculate(appleSupply);
        Integer actual = Storage.fruits.get(APPLE);
        Integer expected = 50;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void returnOperation_Ok() {
        fruitService.calculate(appleReturn);
        Integer actual = Storage.fruits.get(APPLE);
        Integer expected = 50;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void purchaseOperation_Ok() {
        Storage.fruits.put(APPLE, 100);
        fruitService.calculate(applePurchase);
        Integer actual = Storage.fruits.get(APPLE);
        Integer expected = 50;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void purchaseOperation_purchaseValueMoreThanValueInStorage_NotOk() {
        try {
            fruitService.calculate(applePurchase);
        } catch (RuntimeException e) {
            return;
        }
        fail("If statement in storage less than purchase value "
                + " Runtime exception should be thrown");
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}
