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

class ReturnOperationHandlerTest {
    private static ProductDao productDao;
    private static OperationHandler returnOperationHandler;

    @BeforeAll
    static void setUp() {
        productDao = new ProductDaoImpl();
        returnOperationHandler = new ReturnOperationHandler(productDao);
    }

    @Test
    public void calculate_addToExistProduct_ok() {
        FruitTransaction firstTransaction =
                new FruitTransaction(Operation.BALANCE, "apple", 0);
        FruitTransaction secondTransaction =
                new FruitTransaction(Operation.RETURN, "apple", 10);
        FruitStorage.Storage_Map.put(firstTransaction.getFruit(), firstTransaction.getQuantity());
        Map<String, Integer> expected = Map.of("apple", 10);
        returnOperationHandler.handle(secondTransaction);
        Map<String, Integer> actual = FruitStorage.Storage_Map;
        assertEquals(expected, actual);
    }

    @Test
    public void calculate_addLessThanZero_notOk() {
        FruitTransaction firstTransaction =
                new FruitTransaction(Operation.BALANCE, "apple", 5);
        FruitTransaction secondTransaction =
                new FruitTransaction(Operation.RETURN, "apple", -6);
        FruitStorage.Storage_Map.put(firstTransaction.getFruit(), firstTransaction.getQuantity());
        assertThrows(IllegalArgumentException.class,
                () -> returnOperationHandler.handle(secondTransaction));
    }

    @AfterEach
    void tearDown() {
        FruitStorage.Storage_Map.clear();
    }
}
