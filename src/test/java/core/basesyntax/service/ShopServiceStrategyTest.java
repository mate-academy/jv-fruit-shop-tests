package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.operation.BalanceHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseHandler;
import core.basesyntax.service.operation.ReturnHandler;
import core.basesyntax.service.operation.SupplyHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceStrategyTest {
    private static ShopServiceStrategy shopServiceStrategy;
    private static StorageDaoImpl storageDao;
    private static Map<Operation, OperationHandler> opHandlerMap;

    @BeforeAll
    static void setUp() {
        storageDao = new StorageDaoImpl();
        opHandlerMap = new HashMap<>(
                Map.of(
                        Operation.BALANCE, new BalanceHandler(storageDao),
                        Operation.SUPPLY, new SupplyHandler(storageDao),
                        Operation.PURCHASE, new PurchaseHandler(storageDao),
                        Operation.RETURN, new ReturnHandler(storageDao)
                ));
        shopServiceStrategy = new ShopServiceStrategy(opHandlerMap);
    }

    @AfterAll
    static void reset() {
        Storage.foodStorage.clear();
    }

    @Test
    public void processTransactions_validInput_ok() {
        Map<String, Integer> expected = Map.of(
                "banana", 107,
                "apple", 90
        );

        List<FruitTransaction> inputValid = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.BALANCE, "apple", 100),
                new FruitTransaction(Operation.SUPPLY, "banana", 100),
                new FruitTransaction(Operation.PURCHASE, "banana", 13),
                new FruitTransaction(Operation.RETURN, "apple", 10),
                new FruitTransaction(Operation.PURCHASE, "apple", 20)
        );

        shopServiceStrategy.processTransactions(inputValid);
        var actual = storageDao.getAllFruits();
        assertEquals(expected, actual);
        var actualSize = Storage.foodStorage.size();
        assertEquals(2, actualSize);
    }

    @Test
    public void processTransactions_invalidInput_notOk() {
        List<FruitTransaction> inputInvalid = List.of(
                new FruitTransaction(Operation.EXTRA, "banana", 20),
                new FruitTransaction(Operation.BALANCE, "apple", 100)
        );

        assertThrows(RuntimeException.class, () -> {
            shopServiceStrategy.processTransactions(inputInvalid);
        });
    }
}
