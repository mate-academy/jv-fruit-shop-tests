package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class BalanceTransactionTest {

    @Test
    void execute_setsFruitQuantity() {
        Storage storage = new Storage();

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.OperationType.BALANCE,
                "banana",
                100
        );

        BalanceTransaction balanceTransaction = new BalanceTransaction();

        balanceTransaction.execute(transaction, storage);

        assertEquals(100, storage.getQuantity("banana"));
    }
}
