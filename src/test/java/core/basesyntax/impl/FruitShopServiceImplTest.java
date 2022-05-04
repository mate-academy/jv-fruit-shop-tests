package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.servise.FruitShopService;
import core.basesyntax.servise.OperationHandlerStrategy;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static FruitShopService fruitShopService;
    private static List<FruitTransaction> fruitTransactionList;

    @BeforeClass
    public static void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> map = new HashMap<>();

        {
            map.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
            map.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
            map.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
            map.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        }
        OperationHandlerStrategy operationHandlerStrategy = new OperationHandlerStrategyImpl();
        fruitShopService = new FruitShopServiceImpl(operationHandlerStrategy);
        fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("banana"), 100));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("apple"), 50));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                new Fruit("banana"), 30));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                new Fruit("apple"), 15));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                new Fruit("banana"), 30));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                new Fruit("apple"), 10));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                new Fruit("apple"), 15));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                new Fruit("banana"), 10));
    }

    @Test
    public void applyCorrectAmount_ok() {
        int expected = 60;
        fruitShopService.apply(fruitTransactionList);
        int actual = Storage.fruitStorage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }
}
