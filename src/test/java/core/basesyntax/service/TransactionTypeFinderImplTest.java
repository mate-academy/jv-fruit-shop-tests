package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.interfaces.TransactionTypeFinder;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionTypeFinderImplTest {
    private static TransactionTypeFinder transactionTypeFinder;

    @BeforeClass
    public static void beforeClass() {
        transactionTypeFinder = new TransactionTypeFinderImpl();
    }

    @Test(expected = RuntimeException.class)
    public void transactionTypeFinder_Null_NotOk() {
        transactionTypeFinder.operationType(null);
    }

    @Test
    public void transactionTypeFinder_Ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation balance = transactionTypeFinder.operationType("b");
        assertEquals(expected, balance);
    }
}
