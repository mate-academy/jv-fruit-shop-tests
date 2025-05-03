package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.servise.FruitShopService;
import core.basesyntax.servise.OperationHandlerStrategy;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static FruitShopService fruitShopService;
    private static List<FruitTransaction> fruitTransactionList;

    @BeforeClass
    public static void setUp() {
        OperationHandlerStrategy operationHandlerStrategy = new OperationHandlerStrategyImpl();
        fruitShopService = new FruitShopServiceImpl(operationHandlerStrategy);
        fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("apple"), 50));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                new Fruit("apple"), 15));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                new Fruit("apple"), 10));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                new Fruit("apple"), 15));
    }

    @Test
    public void applyCorrectAmount_ok() {
        int expected = 60;
        fruitShopService.apply(fruitTransactionList);
        int actual = Storage.fruitStorage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @After
    public void cleanUp() {
        Storage.fruitStorage.clear();
    }
}
