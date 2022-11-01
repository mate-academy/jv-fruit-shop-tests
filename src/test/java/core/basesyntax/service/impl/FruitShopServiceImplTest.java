package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.handler.OperationHandler;
import core.basesyntax.service.handler.impl.BalanceHandler;
import core.basesyntax.service.handler.impl.PurchaseHandler;
import core.basesyntax.service.handler.impl.ReturnHandler;
import core.basesyntax.service.handler.impl.SupplyHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static FruitShopService fruitShopService;
    private static List<FruitTransaction> differentFruitTransactions;
    private static Map<String, Integer> expected;

    @BeforeClass
    public static void setUp() {
        expected = new HashMap<>();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationHandlersMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationHandlersMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlersMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        fruitShopService = new FruitShopServiceImpl(operationHandlersMap);
        differentFruitTransactions = new ArrayList<>();

        FruitTransaction firstTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 12);
        differentFruitTransactions.add(firstTransaction);

        FruitTransaction secondTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 50);
        differentFruitTransactions.add(secondTransaction);

        FruitTransaction thirdTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "kiwi", 15);
        differentFruitTransactions.add(thirdTransaction);

        FruitTransaction fourthTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 2);
        differentFruitTransactions.add(fourthTransaction);

        FruitTransaction fifthTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "kiwi", 2);
        differentFruitTransactions.add(fifthTransaction);
    }

    @Test
    public void transfer_DifferentData_Ok() {
        expected.put("apple", 52);
        expected.put("banana", 12);
        expected.put("kiwi", 13);
        fruitShopService.transfer(differentFruitTransactions);
        Map<String, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @Test
    public void transfer_NullData_NotOk() {
        assertThrows(NullPointerException.class, () ->
                fruitShopService.transfer(null));
    }

    @After
    public void clear() {
        Storage.fruits.clear();
    }
}
