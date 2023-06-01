package core.basesyntax.service.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.impl.ProductDaoImpl;
import core.basesyntax.service.strategy.OperationHandler;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PursheOperationHandlerTest {
    private static ProductDao productDao;
    private static OperationHandler purchaseOperationHandler;

    @BeforeAll
    static void setUp() {
        productDao = new ProductDaoImpl();
        purchaseOperationHandler = new PursheOperationHandler(productDao);
    }

    @Test
    public void calculate_subtractProduct_ok() {
        FruitTransaction firstTransaction =
                new FruitTransaction(Operation.BALANCE, "banana", 10);
        FruitTransaction seondTransaction =
                new FruitTransaction(Operation.PURCHASE, "banana", 10);
        FruitStorage.Storage_Map.put(firstTransaction.getFruit(),firstTransaction.getQuantity());
        Map<String, Integer> expected = Map.of("banana", 0);
        purchaseOperationHandler.handle(seondTransaction);
        Map<String, Integer> actual = FruitStorage.Storage_Map;
        assertEquals(expected, actual);
    }

    @Test
    public void calculate_subtractMoreThanStorageHas_notOk() {
        FruitTransaction firstTransaction =
                new FruitTransaction(Operation.BALANCE, "banana", 10);
        FruitTransaction seondTransaction =
                new FruitTransaction(Operation.PURCHASE, "banana", 15);
        FruitStorage.Storage_Map.put(firstTransaction.getFruit(),firstTransaction.getQuantity());
        assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.handle(seondTransaction));
    }

    @AfterEach
    void tearDown() {
        FruitStorage.Storage_Map.clear();
    }
}
