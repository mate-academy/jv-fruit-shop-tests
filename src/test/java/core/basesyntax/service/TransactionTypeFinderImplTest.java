package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.interfaces.TransactionTypeFinder;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransactionTypeFinderImplTest {
    private static TransactionTypeFinder transactionTypeFinder;

    @BeforeClass
    public static void beforeClass() {
        transactionTypeFinder = new TransactionTypeFinderImpl();
    }

    @Test
    public void TransactionTypeFinder_Null_NotOk() {
        try {
            transactionTypeFinder.operationType(null);
        } catch (RuntimeException e) {
            return;
        }
        fail("Transaction type can not be Null");
    }

    @Test
    public void TransactionTypeFinder_Ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation balance = transactionTypeFinder.operationType("b");
        assertEquals(expected, balance);
    }
}
