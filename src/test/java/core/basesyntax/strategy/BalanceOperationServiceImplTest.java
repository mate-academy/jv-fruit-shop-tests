package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.strategy.impl.BalanceOperationServiceImpl;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationServiceImplTest {
    private static ProductDao productDao;
    private static OperationService balanceOperationService;

    @BeforeAll
    static void setUp() {
        productDao = new ProductDaoImpl();
        balanceOperationService = new BalanceOperationServiceImpl(productDao);
    }

    @Test
    public void calculate_addNewProduct_ok() {
        FruitTransaction transaction =
                new FruitTransaction(Operation.BALANCE, "banana", 10);
        Map<String, Integer> expected = Map.of("banana", 10);
        balanceOperationService.calculate(transaction);
        Map<String, Integer> actual = FruitsStorage.FRUIT_MAP;
        assertEquals(expected, actual);
    }

    @Test
    public void calculate_addToExistProduct_ok() {
        FruitTransaction firstTransaction =
                new FruitTransaction(Operation.BALANCE, "banana", 10);
        FruitTransaction secondTransaction =
                new FruitTransaction(Operation.BALANCE, "banana", 10);
        FruitsStorage.FRUIT_MAP.put(firstTransaction.getFruit(), firstTransaction.getQuantity());
        balanceOperationService.calculate(secondTransaction);
        Map<String, Integer> expected = Map.of("banana", 20);
        Map<String, Integer> actual = FruitsStorage.FRUIT_MAP;
        assertEquals(expected, actual);
    }

    @Test
    public void calculate_addLessThanZero_notOk() {
        FruitTransaction firstTransaction =
                new FruitTransaction(Operation.BALANCE, "banana", 10);
        FruitTransaction secondTransaction =
                new FruitTransaction(Operation.BALANCE, "banana", -15);
        FruitsStorage.FRUIT_MAP.put(firstTransaction.getFruit(), firstTransaction.getQuantity());
        assertThrows(IllegalArgumentException.class,
                () -> balanceOperationService.calculate(secondTransaction));
    }

    @AfterEach
    void tearDown() {
        FruitsStorage.FRUIT_MAP.clear();
    }
}
