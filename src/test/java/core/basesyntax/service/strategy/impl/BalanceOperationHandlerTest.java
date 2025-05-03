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

class BalanceOperationHandlerTest {
    private static ProductDao productDao;
    private static OperationHandler balanceOperationHandler;

    @BeforeAll
    static void setUp() {
        productDao = new ProductDaoImpl();
        balanceOperationHandler = new BalanceOperationHandler(productDao);
    }

    @Test
    public void calculate_addNewProduct_ok() {
        FruitTransaction transaction =
                new FruitTransaction(Operation.BALANCE, "banana", 10);
        Map<String, Integer> expected = Map.of("banana", 10);
        balanceOperationHandler.handle(transaction);
        Map<String, Integer> actual = FruitStorage.Storage_Map;
        assertEquals(expected, actual);
    }

    @Test
    public void calculate_addLessThanZero_notOk() {
        FruitTransaction firstTransaction =
                new FruitTransaction(Operation.BALANCE, "banana", 10);
        FruitTransaction secondTransaction =
                new FruitTransaction(Operation.BALANCE, "banana", -15);
        FruitStorage.Storage_Map.put(firstTransaction.getFruit(), firstTransaction.getQuantity());
        assertThrows(RuntimeException.class,
                () -> balanceOperationHandler.handle(secondTransaction));
    }

    @AfterEach
    void tearDown() {
        FruitStorage.Storage_Map.clear();
    }
}
