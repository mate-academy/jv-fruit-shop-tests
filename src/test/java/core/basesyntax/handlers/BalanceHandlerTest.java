package core.basesyntax.handlers;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Test;

import static org.junit.Assert.*;

public class BalanceHandlerTest {
    BalanceHandler balanceHandler = new BalanceHandler();
    FruitTransaction validTransaction = new FruitTransaction();
    FruitTransaction invalidTransaction = new FruitTransaction();

    @Test
    public void putToStorage_ZeroValue_ok() {
        validTransaction.setFruit("banana");
        validTransaction.setQuantity(0);
        Integer expected = validTransaction.getQuantity();
        balanceHandler.handle(validTransaction);
        Integer actual = Storage.storage.get(validTransaction.getFruit());
        assertEquals(actual,expected);
    }

    @Test
    public void putToStorage_ValidValue_ok() {
        validTransaction.setFruit("banana");
        validTransaction.setQuantity(100);
        Integer expected = validTransaction.getQuantity();
        balanceHandler.handle(validTransaction);
        Integer actual = Storage.storage.get(validTransaction.getFruit());
        assertEquals(actual,expected);
    }

    @Test(expected = RuntimeException.class)
    public void putToStorage_InValidTransactionValue_notOk() {
        invalidTransaction.setFruit("banana");
        invalidTransaction.setQuantity(-10);
        balanceHandler.handle(invalidTransaction);
    }
}