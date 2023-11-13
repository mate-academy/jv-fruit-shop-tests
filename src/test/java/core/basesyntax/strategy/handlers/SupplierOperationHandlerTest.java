package core.basesyntax.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplierOperationHandlerTest {
    private FruitTransaction fruitTransaction;
    private StorageDao storageDao;
    private SupplierOperationHandler supplierOperationHandler;

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction();
        storageDao = new FruitDao();
        supplierOperationHandler = new SupplierOperationHandler(storageDao);
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    void handle_expectedTransaction_ok() {
        String fruit = "banana";
        int initialQuantity = 10;
        FruitStorage.fruitStorage.put(fruit, initialQuantity);
        int supplyQuantity = 25;
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(supplyQuantity);
        supplierOperationHandler.handle(fruitTransaction);
        int expectedNewBalance = initialQuantity + supplyQuantity;
        assertEquals(expectedNewBalance, FruitStorage.fruitStorage.get(fruit));
    }

    @Test
    void handle_zeroSupplyQuantity_ok() {
        String fruit = "banana";
        int initialQuantity = 35;
        FruitStorage.fruitStorage.put(fruit, initialQuantity);
        int supplyQuantity = 0;
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(supplyQuantity);
        supplierOperationHandler.handle(fruitTransaction);
        int expectedNewBalance = initialQuantity + supplyQuantity;
        assertEquals(expectedNewBalance, FruitStorage.fruitStorage.get(fruit));
    }

    @Test
    void handle_nullTransaction_notOk() {
        assertThrows(NullPointerException.class, () -> supplierOperationHandler.handle(null));
    }

    @Test
    void handle_nullFruit_notOk() {
        int quantity = 5;
        fruitTransaction.setFruit(null);
        fruitTransaction.setQuantity(quantity);
        assertThrows(RuntimeException.class,
                () -> supplierOperationHandler.handle(fruitTransaction));
    }
}
