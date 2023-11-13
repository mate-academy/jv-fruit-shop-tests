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

public class ReturnOperationHandlerTest {
    private FruitTransaction fruitTransaction;
    private StorageDao storageDao;
    private ReturnOperationHandler returnOperationHandler;

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction();
        storageDao = new FruitDao();
        returnOperationHandler = new ReturnOperationHandler(storageDao);
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
        int returnQuantity = 5;
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(returnQuantity);
        returnOperationHandler.handle(fruitTransaction);
        int expectedNewBalance = initialQuantity + returnQuantity;
        assertEquals(expectedNewBalance, FruitStorage.fruitStorage.get(fruit));
    }

    @Test
    void handle_zeroReturnQuantity_ok() {
        String fruit = "banana";
        int initialQuantity = 45;
        FruitStorage.fruitStorage.put(fruit, initialQuantity);
        int returnQuantity = 0;
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(returnQuantity);
        returnOperationHandler.handle(fruitTransaction);
        int expectedNewBalance = initialQuantity + returnQuantity;
        assertEquals(expectedNewBalance, FruitStorage.fruitStorage.get(fruit));
    }

    @Test
    void handle_nullTransaction_notOk() {
        assertThrows(IllegalArgumentException.class, () -> returnOperationHandler.handle(null));
    }

    @Test
    void handle_nullFruit_notOk() {
        int quantity = 15;
        fruitTransaction.setFruit(null);
        fruitTransaction.setQuantity(quantity);
        assertThrows(RuntimeException.class, () -> returnOperationHandler.handle(fruitTransaction));
    }
}
