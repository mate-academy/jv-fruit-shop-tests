package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.FruitTransaction;
import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationHandlerTest {
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
    }

    @Test
    void supplyOperation_increasingFruitQuantity() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("apple");
        transaction.setQuantity(10);
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        SupplyOperationHandler handler = new SupplyOperationHandler(storage);
        handler.apply(transaction);
        assertEquals(10, storage.getFruits().get("apple"));
    }
}
