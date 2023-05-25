package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationServiceImpl;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationServiceImplTest {
    private static final ProductDao PRODUCT_DAO = new ProductDaoImpl();
    private static OperationService balanceOperationService;

    @BeforeAll
    static void setUp() {
        balanceOperationService = new BalanceOperationServiceImpl(PRODUCT_DAO);
    }

    @Test
    public void calculate_addNewProduct_ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
        Map<String, Integer> expected = Map.of("banana", 10);
        balanceOperationService.calculate(transaction);
        Map<String, Integer> actual = FruitsStorage.FRUIT_MAP;
        assertEquals(expected, actual);
    }

    @Test
    public void calculate_addToExistProduct_ok() {
        FruitTransaction firstTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
        FruitTransaction secondTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
        FruitsStorage.FRUIT_MAP.put(firstTransaction.getFruit(), firstTransaction.getQuantity());
        balanceOperationService.calculate(secondTransaction);
        Map<String, Integer> expected = Map.of("banana", 20);
        Map<String, Integer> actual = FruitsStorage.FRUIT_MAP;
        assertEquals(expected, actual);
    }

    @Test
    public void calculate_addLessThanZero_notOk() {
        FruitTransaction firstTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
        FruitTransaction secondTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", -15);
        FruitsStorage.FRUIT_MAP.put(firstTransaction.getFruit(), firstTransaction.getQuantity());
        assertThrows(IllegalArgumentException.class,
                () -> balanceOperationService.calculate(secondTransaction));
    }

    @AfterEach
    void tearDown() {
        FruitsStorage.FRUIT_MAP.clear();
    }
}
