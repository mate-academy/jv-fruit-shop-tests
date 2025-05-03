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
import org.junit.Test;

public class FruitShopServiceImplTest {

    @Test
    public void transfer_differentData_isOk() {
        Map<String, Integer> expected = new HashMap<>();

        final FruitShopService fruitShopService =
                new FruitShopServiceImpl(createOperationHandlersMap());

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

        final List<FruitTransaction> fruitTransactions = List.of(firstTransaction,
                secondTransaction, thirdTransaction, fourthTransaction, fifthTransaction);

        expected.put("apple", 201);
        expected.put("banana", 100);
        expected.put("kiwi", 299);
        fruitShopService.transfer(fruitTransactions);
        Map<String, Integer> actual = ShopStorage.fruitsStorage;
        assertEquals(expected, actual);
    }

    @After
    public void clear() {
        ShopStorage.fruitsStorage.clear();
    }

    private Map<FruitTransaction.Operation, GeneralOperation> createOperationHandlersMap() {
        Map<FruitTransaction.Operation, GeneralOperation> operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationHandlersMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationHandlersMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlersMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        return operationHandlersMap;
    }
}
