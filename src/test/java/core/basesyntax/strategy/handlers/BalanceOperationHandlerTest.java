package core.basesyntax.strategy.handlers;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertEquals(quantity, storageDao.getQuantityByObjectType(fruit));
    }

    @Test
    void handle_negativeQuantity_notOk() {
        String fruit = "apple";
        int quantity = -8;
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(quantity);
        assertThrows(IllegalArgumentException.class, () -> balanceOperationHandler.handle(fruitTransaction));
    }
}
