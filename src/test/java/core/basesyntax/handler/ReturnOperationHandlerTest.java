package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.FruitTransaction;
import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationHandlerTest {
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
    }

    @Test
    void returnOperation_shouldIncreaseFruitQuantity() {
        storage.getFruits().put("apple", 5);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("apple");
        transaction.setQuantity(3);
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        ReturnOperationHandler handler = new ReturnOperationHandler(storage);
        handler.apply(transaction);
        assertEquals(8, storage.getFruits().get("apple"));
    }
}
