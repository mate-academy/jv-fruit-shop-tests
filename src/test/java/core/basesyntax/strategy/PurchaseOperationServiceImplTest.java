package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.impl.ProductDaoImpl;
import core.basesyntax.db.FruitsStorage;
import core.basesyntax.exception.NotEnoughProductsException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.strategy.impl.PurchaseOperationServiceImpl;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationServiceImplTest {
    private static ProductDao productDao;
    private static OperationService purchaseOperationService;

    @BeforeAll
    static void setUp() {
        productDao = new ProductDaoImpl();
        purchaseOperationService = new PurchaseOperationServiceImpl(productDao);
    }

    @Test
    public void calculate_subtractProduct_ok() {
        FruitTransaction firstTransaction =
                new FruitTransaction(Operation.BALANCE, "banana", 10);
        FruitTransaction secondTransaction =
                new FruitTransaction(Operation.PURCHASE, "banana", 10);
        FruitsStorage.FRUIT_MAP.put(firstTransaction.getFruit(),firstTransaction.getQuantity());
        Map<String, Integer> expected = Map.of("banana", 0);
        purchaseOperationService.calculate(secondTransaction);
        Map<String, Integer> actual = FruitsStorage.FRUIT_MAP;
        assertEquals(expected, actual);
    }

    @Test
    public void calculate_subtractMoreThanStorageHas_notOk() {
        FruitTransaction firstTransaction =
                new FruitTransaction(Operation.BALANCE, "banana", 10);
        FruitTransaction seondTransaction =
                new FruitTransaction(Operation.PURCHASE, "banana", 15);
        FruitsStorage.FRUIT_MAP.put(firstTransaction.getFruit(),firstTransaction.getQuantity());
        assertThrows(NotEnoughProductsException.class,
                () -> purchaseOperationService.calculate(seondTransaction));
    }

    @AfterEach
    void tearDown() {
        FruitsStorage.FRUIT_MAP.clear();
    }
}

