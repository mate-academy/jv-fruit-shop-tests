package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.GeneralOperation;
import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.PurchaseHandler;
import core.basesyntax.strategy.impl.ReturnHandler;
import core.basesyntax.strategy.impl.SupplyHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static FruitShopService fruitShopService;
    private static List<FruitTransaction> fruitTransactions;
    private static Map<String, Integer> expected;

    @BeforeClass
    public static void setUp() {
        expected = new HashMap<>();

        Map<FruitTransaction.Operation, GeneralOperation> operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationHandlersMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationHandlersMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlersMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());

        fruitShopService = new FruitShopServiceImpl(operationHandlersMap);

        FruitTransaction firstTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100);

        FruitTransaction secondTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 200);

        FruitTransaction thirdTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "kiwi", 300);

        FruitTransaction fourthTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 1);

        FruitTransaction fifthTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "kiwi", 1);

        fruitTransactions = List.of(firstTransaction, secondTransaction,
                thirdTransaction, fourthTransaction, fifthTransaction);
    }

    @Test
    public void transfer_differentData_isOk() {
        expected.put("apple", 201);
        expected.put("banana", 100);
        expected.put("kiwi", 299);
        fruitShopService.transfer(fruitTransactions);
        Map<String, Integer> actual = ShopStorage.fruitsStorage;
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void transfer_nullData_notOk() {
        fruitShopService.transfer(null);
    }

    @After
    public void clear() {
        ShopStorage.fruitsStorage.clear();
    }
}
