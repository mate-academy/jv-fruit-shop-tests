package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.SupplyOperationServiceImpl;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationServiceImplTest {
    private static final ProductDao PRODUCT_DAO = new ProductDaoImpl();
    private static OperationService supplyOperationService;

    @BeforeAll
    static void setUp() {
        supplyOperationService = new SupplyOperationServiceImpl(PRODUCT_DAO);
    }

    @Test
    public void calculate_addNewProducts_ok() {
        FruitTransaction firstTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "orange", 10);
        FruitTransaction secondTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 10);
        FruitTransaction thirdTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 10);
        supplyOperationService.calculate(firstTransaction);
        supplyOperationService.calculate(secondTransaction);
        supplyOperationService.calculate(thirdTransaction);
        Map<String, Integer> actual = FruitsStorage.FRUIT_MAP;
        Map<String, Integer> expected = Map.of("orange", 10,
                "apple", 10,
                "banana", 10);
        assertEquals(expected, actual);
    }

    @Test
    public void calculate_addToExistProduct_ok() {
        FruitTransaction firstTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 0);
        FruitTransaction secondTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 10);
        FruitsStorage.FRUIT_MAP.put(firstTransaction.getFruit(), firstTransaction.getQuantity());
        Map<String, Integer> expected = Map.of("apple", 10);
        supplyOperationService.calculate(secondTransaction);
        Map<String, Integer> actual = FruitsStorage.FRUIT_MAP;
        assertEquals(expected, actual);
    }

    @Test
    public void calculate_addLessThanZero_notOk() {
        FruitTransaction firstTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 5);
        FruitTransaction secondTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", -6);
        FruitsStorage.FRUIT_MAP.put(firstTransaction.getFruit(), firstTransaction.getQuantity());
        assertThrows(IllegalArgumentException.class,
                () -> supplyOperationService.calculate(secondTransaction));
    }

    @AfterEach
    void tearDown() {
        FruitsStorage.FRUIT_MAP.clear();
    }
}
