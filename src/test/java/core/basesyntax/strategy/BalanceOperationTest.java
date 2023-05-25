package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private Storage storage;
    private BalanceOperation balanceOperation;
    private FruitTransaction transaction;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        balanceOperation = new BalanceOperation();
        transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,null, 0);
    }

    @Test
    public void balanceOperation_validTransactionApples_ok() {
        transaction.setQuantity(10);
        new BalanceOperation().operateTransaction(transaction, storage);
        int actualQuantity = storage.get(transaction.getFruit());
        int expectedQuantity = 10;
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void balanceOperation_NegativeValue_notOk() {
        transaction.setQuantity(-10);
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                balanceOperation.operateTransaction(transaction, storage));
        String expectedMessage = "Balance should be positive.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @AfterEach
    public void afterEach() {
        Storage.fruitStorage.clear();
    }
}
