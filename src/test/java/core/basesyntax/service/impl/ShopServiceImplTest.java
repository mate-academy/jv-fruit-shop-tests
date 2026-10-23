package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private static Storage storage;
    private static Map<FruitTransaction.Operation, OperationHandler> params;
    private static OperationStrategyImpl operationStrategy;
    private static ShopServiceImpl shopService;
    private static List<FruitTransaction> fruitTransactions;

    @BeforeAll
    public static void setUp() {
        storage = new Storage();
        params = new HashMap<>();
        params.put(FruitTransaction.Operation.BALANCE, new BalanceOperation(storage));

        operationStrategy = new OperationStrategyImpl(params);
        shopService = new ShopServiceImpl(operationStrategy);

        fruitTransactions = new ArrayList<>();
    }

    @Test
    public void shopServiceTest() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(10);

        fruitTransactions.add(fruitTransaction);
        shopService.process(fruitTransactions);

        assertEquals(10, storage.getAll().get("apple"));
    }

    @AfterEach
    public void clearStorage() {
        storage.getAll().clear();
        fruitTransactions.clear();
    }
}
