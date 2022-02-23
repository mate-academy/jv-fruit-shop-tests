package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FruitTransactionTest {
    @Test
    public void createInstance_Ok() {
        FruitTransaction testTransaction1 = new FruitTransaction("b", "banana", 10);
        assertEquals(FruitTransaction.Operation.BALANCE, testTransaction1.getOperation());
    }

    @Test(expected = RuntimeException.class)
    public void createInstance_NoExistEnum_NotOk() {
        FruitTransaction testTransaction = new FruitTransaction("k", "banana", 10);
    }

    @Test(expected = RuntimeException.class)
    public void createInstance_NullToEnum_NotOk() {
        FruitTransaction testTransaction = new FruitTransaction(null, "banana", 10);
    }
}
