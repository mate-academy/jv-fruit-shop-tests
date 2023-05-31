package core.basesyntax.sevrice.impl;

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
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static Fruit apple;
    private static FruitDao fruitDao;
    private final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap
            = Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(fruitDao),
            FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(fruitDao),
            FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(fruitDao),
            FruitTransaction.Operation.RETURN, new ReturnOperationHandler(fruitDao));
    private final OperationStrategy operationStrategy
            = new OperationStrategyImpl(operationHandlerMap);
    private final FruitService fruitService = new FruitServiceImpl(fruitDao, operationStrategy);

    private final FruitTransaction appleBalance = new FruitTransaction(
            FruitTransaction.Operation.BALANCE,
            apple,
            50);
    private final FruitTransaction appleSupply = new FruitTransaction(
            FruitTransaction.Operation.SUPPLY,
            apple,
            50);
    private final FruitTransaction applePurchase = new FruitTransaction(
            FruitTransaction.Operation.PURCHASE,
            apple,
            50);
    private final FruitTransaction appleReturn = new FruitTransaction(
            FruitTransaction.Operation.RETURN,
            apple,
            50);

    @BeforeClass
    public static void beforeClass() throws Exception {
        apple = new Fruit("apple");
        fruitDao = new FruitDaoImpl();
    }

    @Before
    public void setUp() throws Exception {
        Storage.fruits.put(apple, 0);
    }

    @Test
    public void calculate_balanceOperation_Ok() {
        fruitService.calculate(appleBalance);
        Integer actual = Storage.fruits.get(apple);
        Integer expected = 50;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void calculate_supplyOperation_Ok() {
        fruitService.calculate(appleSupply);
        Integer actual = Storage.fruits.get(apple);
        Integer expected = 50;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void calculate_returnOperation_Ok() {
        fruitService.calculate(appleReturn);
        Integer actual = Storage.fruits.get(apple);
        Integer expected = 50;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void calculate_purchaseOperation_Ok() {
        Storage.fruits.put(apple, 100);
        fruitService.calculate(applePurchase);
        Integer actual = Storage.fruits.get(apple);
        Integer expected = 50;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void calculate_purchaseOperation_purchaseValueMoreThanValueInStorage_NotOk() {
        fruitService.calculate(applePurchase);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}
