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

public class BalanceOperationHandlerTest {
    private FruitTransaction fruitTransaction;
    private StorageDao storageDao;
    private BalanceOperationHandler balanceOperationHandler;

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction();
        storageDao = new FruitDao();
        balanceOperationHandler = new BalanceOperationHandler(storageDao);
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    void handle_expectedTransaction_ok() {
        String fruit = "banana";
        int quantity = 15;
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(quantity);
        balanceOperationHandler.handle(fruitTransaction);
        assertEquals(quantity, FruitStorage.fruitStorage.get(fruit));
    }

    @Test
    void handle_zeroQuantity_ok() {
        String fruit = "apple";
        int quantity = 0;
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(quantity);
        balanceOperationHandler.handle(fruitTransaction);
        assertEquals(quantity, FruitStorage.fruitStorage.get(fruit));
    }

    @Test
    void handle_nullTransaction_notOk() {
        assertThrows(IllegalArgumentException.class, () -> balanceOperationHandler.handle(null));
    }

    @Test
    void handle_nullFruit_notOk() {
        int quantity = 5;
        fruitTransaction.setFruit(null);
        fruitTransaction.setQuantity(quantity);
        assertThrows(RuntimeException.class,
                () -> balanceOperationHandler.handle(fruitTransaction));
    }
}
