package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class SupplyTransactionTest {

    @Test
    void execute_addsFruitQuantity_increasesQuantity() {
        Storage storage = new Storage();
        storage.setFruitQuantity("banana", 50);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.OperationType.SUPPLY,
                "banana",
                20
        );

        SupplyTransaction supplyTransaction = new SupplyTransaction();

        supplyTransaction.execute(transaction, storage);

        assertEquals(70, storage.getQuantity("banana"));
    }

    @Test
    void execute_addsFruitWhenFruitDoesNotExist_createsPosition() {
        Storage storage = new Storage();

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.OperationType.SUPPLY,
                "apple",
                30
        );

        SupplyTransaction supplyTransaction = new SupplyTransaction();

        supplyTransaction.execute(transaction, storage);

        assertEquals(30, storage.getQuantity("apple"));
    }
}
