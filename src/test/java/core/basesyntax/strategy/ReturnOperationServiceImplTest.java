package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.impl.ProductDaoImpl;
import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.strategy.impl.ReturnOperationServiceImpl;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationServiceImplTest {
    private static ProductDao productDao;
    private static OperationService returnOperationService;

    @BeforeAll
    static void setUp() {
        productDao = new ProductDaoImpl();
        returnOperationService = new ReturnOperationServiceImpl(productDao);
    }

    @Test
    public void calculate_addToExistProduct_ok() {
        FruitTransaction firstTransaction =
                new FruitTransaction(Operation.BALANCE, "apple", 0);
        FruitTransaction secondTransaction =
                new FruitTransaction(Operation.RETURN, "apple", 10);
        FruitsStorage.FRUIT_MAP.put(firstTransaction.getFruit(), firstTransaction.getQuantity());
        Map<String, Integer> expected = Map.of("apple", 10);
        returnOperationService.calculate(secondTransaction);
        Map<String, Integer> actual = FruitsStorage.FRUIT_MAP;
        assertEquals(expected, actual);
    }

    @Test
    public void calculate_addLessThanZero_notOk() {
        FruitTransaction firstTransaction =
                new FruitTransaction(Operation.BALANCE, "apple", 5);
        FruitTransaction secondTransaction =
                new FruitTransaction(Operation.RETURN, "apple", -6);
        FruitsStorage.FRUIT_MAP.put(firstTransaction.getFruit(), firstTransaction.getQuantity());
        assertThrows(IllegalArgumentException.class,
                () -> returnOperationService.calculate(secondTransaction));
    }

    @AfterEach
    void tearDown() {
        FruitsStorage.FRUIT_MAP.clear();
    }
}
