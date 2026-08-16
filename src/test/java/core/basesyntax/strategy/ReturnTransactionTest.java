package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class ReturnTransactionTest {

    @Test
    void execute_addsFruitQuantity() {
        Storage storage = new Storage();
        storage.setFruitQuantity("banana", 50);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.OperationType.RETURN,
                "banana",
                10
        );

        ReturnTransaction returnTransaction = new ReturnTransaction();

        returnTransaction.execute(transaction, storage);

        assertEquals(60, storage.getQuantity("banana"));
    }

    @Test
    void execute_addsFruitWhenFruitDoesNotExist() {
        Storage storage = new Storage();

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.OperationType.RETURN,
                "apple",
                20
        );

        ReturnTransaction returnTransaction = new ReturnTransaction();

        returnTransaction.execute(transaction, storage);

        assertEquals(20, storage.getQuantity("apple"));
    }
}
